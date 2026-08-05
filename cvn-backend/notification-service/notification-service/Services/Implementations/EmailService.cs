using MailKit.Net.Smtp;
using Microsoft.Extensions.Options;
using MimeKit;
using notification_service.Models;
using notification_service.Services.Interfaces;

namespace notification_service.Services.Implementations
{
    public class EmailService : IEmailService
    {
        private readonly EmailSettings _settings;

        public EmailService(IOptions<EmailSettings> options)
        {
            _settings = options.Value;
        }

        public async Task SendEmailAsync(
            string to,
            string subject,
            string body)
        {
            var message = new MimeMessage();

            message.From.Add(
                MailboxAddress.Parse(_settings.From));

            message.To.Add(
                MailboxAddress.Parse(to));

            message.Subject = subject;

            message.Body = new TextPart("plain")
            {
                Text = body
            };

            using var smtp = new SmtpClient();

            await smtp.ConnectAsync(
                _settings.Host,
                _settings.Port,
                MailKit.Security.SecureSocketOptions.StartTls);

            await smtp.AuthenticateAsync(
                _settings.Username,
                _settings.Password);

            await smtp.SendAsync(message);

            await smtp.DisconnectAsync(true);
        }
    }
}