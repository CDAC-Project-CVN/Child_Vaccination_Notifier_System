namespace notification_service.Config
{
    public static class RabbitMQConstants
    {
        public const string CHILD_EXCHANGE = "child.exchange";

        public const string CHILD_REGISTERED_QUEUE = "child.registered.notification.queue";

        public const string CHILD_REGISTERED_ROUTING_KEY = "child.registered";
    }
}