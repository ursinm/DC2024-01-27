using Confluent.Kafka;
using Newtonsoft.Json;
using RestSharp;
using RV.Models;
using RV.Views.DTO;

namespace RV.Services.DataProviderServices.Remote
{
    public class NoSQLNoteDataProvider : INoteDataProvider
    {
        public NoteDTO CreateNote(NoteAddDTO item)
        {
            var client = new RestClient("http://localhost:24130/api/v1.0/");
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
            var client = new RestClient("http://localhost:24130/api/v1.0/");
            var request = new RestRequest($"notes/{id}", Method.Delete);
            var response = client.Execute(request);
            if (response.StatusCode == System.Net.HttpStatusCode.NoContent)
                return 1;
            else
                return 0;
        }

        public NoteDTO GetNote(int id)
        {
            var client = new RestClient("http://localhost:24130/api/v1.0/");
            var request = new RestRequest($"notes/{id}", Method.Get);
            var response = client.Execute(request);
            var res = JsonConvert.DeserializeObject<NoteDTO>(response.Content);
            return res;
        }

        public List<NoteDTO> GetNotes()
        {
            var client = new RestClient("http://localhost:24130/api/v1.0/");
            var request = new RestRequest("notes", Method.Get);
            var response = client.Execute(request);
            var res = JsonConvert.DeserializeObject<List<NoteDTO>>(response.Content);
            return res;
        }

        public NoteDTO UpdateNote(NoteUpdateDTO item)
        {
            var client = new RestClient("http://localhost:24130/api/v1.0/");
            var request = new RestRequest("notes", Method.Put);
            request.AddBody(item, ContentType.Json);
            var response = client.Execute(request);
            var res = JsonConvert.DeserializeObject<NoteDTO>(response.Content);
            return res;
        }
    }
}
