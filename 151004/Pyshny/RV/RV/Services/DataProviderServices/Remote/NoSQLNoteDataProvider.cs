using Confluent.Kafka;
using IdentityModel.Client;
using Newtonsoft.Json;
using RestSharp;
using RestSharp.Authenticators;
using RV.Models;
using RV.Views.DTO;

namespace RV.Services.DataProviderServices.Remote
{
    public class NoSQLNoteDataProvider : INoteDataProvider
    {
        private RestClientOptions GetOptions() {
            var tempClient = new HttpClient();
            var disco = tempClient.GetDiscoveryDocumentAsync( new DiscoveryDocumentRequest
            {
                Address = "http://identity:8080",
                Policy =
                { 
                    RequireHttps = false,
                }
            }).GetAwaiter().GetResult();
            if (disco.IsError)
            {
                Console.WriteLine(disco.Error);
                throw new Exception("error");
            }
            var tokenResponse = tempClient.RequestClientCredentialsTokenAsync(new ClientCredentialsTokenRequest
            {
                Address = disco.TokenEndpoint,
                ClientId = "client",
                ClientSecret = "secret",
                Scope = "api1"
            }).GetAwaiter().GetResult();
            if (tokenResponse.IsError)
            {
                Console.WriteLine(tokenResponse.Error);
                throw new Exception("error");
            }
            var jwt = new JwtAuthenticator(tokenResponse!.AccessToken);
            return new RestClientOptions($"http://discussion:8080/api/v1.0/")
            {
                Authenticator = jwt,
            };
        }


        public NoteDTO CreateNote(NoteAddDTO item)
        {
            var client = new RestClient(GetOptions());
            var request = new RestRequest("notes", Method.Post);
            var rand = new Random();
            item.id = rand.Next();
            request.AddBody(item, ContentType.Json);
            var response = client.Execute(request);
            var res = JsonConvert.DeserializeObject<NoteDTO>(response.Content);
            return res;
        }

        public int DeleteNote(int id)
        {
            var client = new RestClient(GetOptions());
            var request = new RestRequest($"notes/{id}", Method.Delete);
            var response = client.Execute(request);
            if (response.StatusCode == System.Net.HttpStatusCode.NoContent)
                return 1;
            else
                return 0;
        }

        public NoteDTO GetNote(int id)
        {
            var client = new RestClient(GetOptions());
            var request = new RestRequest($"notes/{id}", Method.Get);
            var response = client.Execute(request);
            var res = JsonConvert.DeserializeObject<NoteDTO>(response.Content);
            return res;
        }

        public List<NoteDTO> GetNotes()
        {
            var client = new RestClient(GetOptions());
            var request = new RestRequest("notes", Method.Get);
            var response = client.Execute(request);
            var res = JsonConvert.DeserializeObject<List<NoteDTO>>(response.Content);
            return res;
        }

        public NoteDTO UpdateNote(NoteUpdateDTO item)
        {
            var client = new RestClient(GetOptions());
            var request = new RestRequest("notes", Method.Put);
            request.AddBody(item, ContentType.Json);
            var response = client.Execute(request);
            var res = JsonConvert.DeserializeObject<NoteDTO>(response.Content);
            return res;
        }
    }
}
