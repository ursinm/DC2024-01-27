using DC_Lab1.DTO.Interface;
using DC_Lab1.DTO;
using Microsoft.EntityFrameworkCore;
using DC_Lab1.Services.Interfaces;
using AutoMapper;
using DC_Lab1.Models;
using Microsoft.AspNetCore.Components.Forms;
using DC_Lab1.DB.BaseDBContext;
using Humanizer;
using System.Net.Http.Json;
using Newtonsoft.Json;
using System.Text;
using Microsoft.AspNetCore.Mvc;
using System.Net.Http;
using Confluent.Kafka;
using static Confluent.Kafka.ConfigPropertyNames;
using DC_Lab1;
using static System.Runtime.InteropServices.JavaScript.JSType;
using StackExchange.Redis;
using Microsoft.EntityFrameworkCore.Metadata.Internal;

namespace DC_Lab1.Services
{
    public class PostsService(IMapper _mapper, BaseContext dbContext, IConsumer<Ignore, string> consumer, IProducer<Null, string> producer, IDatabase redisDB) : IPostService
    {
        HttpClient client = new HttpClient();
        public async Task<IResponseTo> CreateEnt(IRequestTo Dto)
        {
            string json;
            if (Dto is PostRequestTo postRequest)
            {
                var post = new Message<Null, string> { Value = "Create" };
                producer.ProduceAsync("InTopic", post).GetAwaiter().GetResult();
                
                json = $"{{\"Id\":{postRequest.Id},\"tweetId\":{postRequest.TweetId},\"content\":\"{postRequest.Content}\"}}";

                //
                redisDB.StringSet(postRequest.Id.ToString(), json);
                //

                post = new Message<Null, string> { Value = postRequest.Id.ToString() };
                producer.ProduceAsync("InTopic", post).GetAwaiter().GetResult();

                var consumeResult = consumer.Consume();
                consumer.Commit(consumeResult);

                //
                string value = redisDB.StringGet(postRequest.Id.ToString());                                
                //

                var postResponseTo = JsonConvert.DeserializeObject<PostResponseTo>(value);
                return postResponseTo;

            }
            throw new InvalidDataException("Incorrect data for CREATE Post");
        }

        public async Task DeleteEnt(int id)
        {

            var post = new Message<Null, string> { Value = "Delete" };
            producer.ProduceAsync("InTopic", post).GetAwaiter().GetResult();

            string strNumber = id.ToString();
            post = new Message<Null, string> { Value = strNumber };
            producer.ProduceAsync("InTopic", post).GetAwaiter().GetResult();

            var consumeResult = consumer.Consume();
            consumer.Commit(consumeResult);

            string jsonRes = consumeResult.Message.Value;
            if (jsonRes == "Success")
            {
                return;
            }
            else
            {
                throw new Exception("Deletting Post exception");
            }
        }

        public async Task<IResponseTo> GetEntById(int id)
        {
            var post = new Message<Null, string> { Value = "GetPostById" };
            producer.ProduceAsync("InTopic", post).GetAwaiter().GetResult();

            string strNumber = id.ToString();
            post = new Message<Null, string> { Value = strNumber };
            producer.ProduceAsync("InTopic", post).GetAwaiter().GetResult();

            var consumeResult = consumer.Consume();
            consumer.Commit(consumeResult);

            string jsonRes = consumeResult.Message.Value;

            var postResponseTo = JsonConvert.DeserializeObject<PostResponseTo>(jsonRes);
            return postResponseTo;
        }

        public IEnumerable<IResponseTo> GetAllEnt()
        {
            var post = new Message<Null, string> { Value = "GetAllEnt" };
            producer.ProduceAsync("InTopic", post).GetAwaiter().GetResult();

            var consumeResult = consumer.Consume();
            consumer.Commit(consumeResult);
            string jsonRes = consumeResult.Message.Value;

            var postResponses = JsonConvert.DeserializeObject<List<PostResponseTo>>(jsonRes);

            var mappedResponses = postResponses.Select(_mapper.Map<PostResponseTo>);
            try
            {
                return mappedResponses;

            }
            catch
            {
                throw new Exception("Getting all Posts exception");
            }
        }

        public async Task<IResponseTo> UpdateEnt(IRequestTo Dto)
        {
            string json;
            if (Dto is PostRequestTo postRequest)
            {
                var post = new Message<Null, string> { Value = "UpdatePost" };
                producer.ProduceAsync("InTopic", post).GetAwaiter().GetResult();
                Thread.Sleep(1000);
                json = $"{{\"id\":{postRequest.Id},\"tweetId\":{postRequest.TweetId},\"content\":\"{postRequest.Content}\"}}";
                
                //
                redisDB.StringSet(postRequest.Id.ToString(), json);
                //

                post = new Message<Null, string> { Value = postRequest.Id.ToString() };
                producer.ProduceAsync("InTopic", post).GetAwaiter().GetResult();

                var consumeResult = consumer.Consume();
                consumer.Commit(consumeResult);

                //
                string value = redisDB.StringGet(postRequest.Id.ToString());
                //

                var postResponseTo = JsonConvert.DeserializeObject<PostResponseTo>(value);

                return postResponseTo;

            }
            throw new InvalidDataException("Incorrect data for UPDATE Post");
        }

        public bool Validate(PostRequestTo PostDto)
        {
            if (PostDto?.Content?.Length < 2 || PostDto?.Content?.Length > 2048)
                return false;

            return true;
        }
    }
}
