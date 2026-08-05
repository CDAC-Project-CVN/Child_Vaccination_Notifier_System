using Microsoft.AspNetCore.Mvc;
using notification_service.Services.Interfaces;

namespace notification_service.Controllers
{
    [ApiController]
    [Route("api/test")]
    public class TestController : ControllerBase
    {
        private readonly IEmailService _emailService;

        public TestController(IEmailService emailService)
        {
            _emailService = emailService;
        }

        [HttpPost("email")]
        public async Task<IActionResult> SendTestEmail()
        {
            await _emailService.SendEmailAsync(
                "pratikshajadhav6102@gmail.com",
                "CVN Notification Test",
                "Congratulations! Your Notification Service is working successfully.");

            return Ok("Email sent successfully.");
        }
    }
}