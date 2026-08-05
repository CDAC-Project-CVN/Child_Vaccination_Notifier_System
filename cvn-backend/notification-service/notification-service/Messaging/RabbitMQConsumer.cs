using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Options;
using notification_service.Config;
using notification_service.Events;
using notification_service.Models;
using notification_service.Services.Interfaces;
using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using System.Text;
using System.Text.Json;

namespace notification_service.Messaging
{
    public class RabbitMQConsumer : BackgroundService
    {
        private readonly RabbitMQSettings _settings;
        private readonly IServiceScopeFactory _scopeFactory;

        public RabbitMQConsumer(
            IOptions<RabbitMQSettings> options,
            IServiceScopeFactory scopeFactory)
        {
            _settings = options.Value;
            _scopeFactory = scopeFactory;
        }

        protected override async Task ExecuteAsync(CancellationToken stoppingToken)
        {
            var factory = new ConnectionFactory
            {
                HostName = _settings.HostName,
                UserName = _settings.UserName,
                Password = _settings.Password
            };

            var connection = await factory.CreateConnectionAsync(stoppingToken);
            var channel = await connection.CreateChannelAsync(cancellationToken: stoppingToken);

            await channel.ExchangeDeclareAsync(
                exchange: RabbitMQConstants.CHILD_EXCHANGE,
                type: ExchangeType.Direct,
                durable: true,
                cancellationToken: stoppingToken);

            await channel.QueueDeclareAsync(
                queue: RabbitMQConstants.CHILD_REGISTERED_QUEUE,
                durable: true,
                exclusive: false,
                autoDelete: false,
                cancellationToken: stoppingToken);

            await channel.QueueBindAsync(
                queue: RabbitMQConstants.CHILD_REGISTERED_QUEUE,
                exchange: RabbitMQConstants.CHILD_EXCHANGE,
                routingKey: RabbitMQConstants.CHILD_REGISTERED_ROUTING_KEY,
                cancellationToken: stoppingToken);

            var consumer = new AsyncEventingBasicConsumer(channel);

            consumer.ReceivedAsync += async (sender, eventArgs) =>
            {
                var body = eventArgs.Body.ToArray();

                var json = Encoding.UTF8.GetString(body);

                //Console.WriteLine("Raw JSON:");
                //Console.WriteLine(json);

                var options = new JsonSerializerOptions
                {
                    PropertyNameCaseInsensitive = true
                };

                var child =
                    JsonSerializer.Deserialize<ChildRegisteredEvent>(json, options);

                //Console.WriteLine(child == null ? "NULL" : "NOT NULL");
                //Console.WriteLine("==================================");
                //Console.WriteLine("ChildRegisteredEvent RECEIVED");
                //Console.WriteLine($"Child ID      : {child?.ChildId}");
                //Console.WriteLine($"Date Of Birth : {child?.DateOfBirth}");
                //Console.WriteLine("==================================");

                if (child != null)
                {
                    using var scope = _scopeFactory.CreateScope();

                    var emailService =
                        scope.ServiceProvider.GetRequiredService<IEmailService>();

                    await emailService.SendEmailAsync(
                        child.ParentEmail,
                        "Child Registration Successful",
                        $"""
                        Dear {child.ParentName},

                        Your child {child.ChildName} has been registered successfully.

                        Child ID : {child.ChildId}

                        Thank you for using Child Vaccination Notifier System.
                        """);
                }

                await channel.BasicAckAsync(
                    eventArgs.DeliveryTag,
                    false,
                    stoppingToken);
            };

            await channel.BasicConsumeAsync(
                RabbitMQConstants.CHILD_REGISTERED_QUEUE,
                false,
                consumer,
                stoppingToken);

            await Task.Delay(Timeout.Infinite, stoppingToken);
        }
    }
}