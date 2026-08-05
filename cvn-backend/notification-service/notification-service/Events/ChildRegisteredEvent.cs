namespace notification_service.Events
{
    public class ChildRegisteredEvent
    {
        public long ChildId { get; set; }

        public string ChildName { get; set; } = string.Empty;

        public int[] DateOfBirth { get; set; } = [];

        public string ParentEmail { get; set; } = string.Empty;

        public string ParentName { get; set; } = string.Empty;
    }
}
