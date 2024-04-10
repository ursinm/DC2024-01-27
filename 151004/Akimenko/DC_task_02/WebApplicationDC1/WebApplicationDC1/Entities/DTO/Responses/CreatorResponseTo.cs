using Newtonsoft.Json;

namespace WebApplicationDC1.Entity.DTO.Responses
{
    public record class CreatorResponseTO(
        [property: JsonProperty("id")] int Id,
        [property: JsonProperty("login")] string Login,
        [property: JsonProperty("firstname")] string FirstName,
        [property: JsonProperty("lastname")] string LastName);
}
