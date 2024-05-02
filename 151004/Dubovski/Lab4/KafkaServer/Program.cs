using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using Confluent.Kafka;
using Newtonsoft.Json;
using static Confluent.Kafka.ConfigPropertyNames;

namespace KafkaServer
{
    internal class Program
    {
        static void Main(string[] args)
        {
            HttpClient client = new HttpClient();
            client.BaseAddress = new Uri("http://localhost:24130/");
            var config = new ProducerConfig { BootstrapServers = "localhost:9092" };
            string jsonRes;

            var consumerConfig = new ConsumerConfig
            {
                BootstrapServers = "localhost:9092",
                GroupId = "my-group",
                AutoOffsetReset = AutoOffsetReset.Earliest
            };

            var consumer = new ConsumerBuilder<Ignore, string>(consumerConfig).Build();
            var producer = new ProducerBuilder<Null, string>(config).Build();
            consumer.Subscribe("InTopic");
            while (true)
            {
                //Принть сообщение от А
                var consumeResult = consumer.Consume();
                consumer.Commit(consumeResult);
                jsonRes = consumeResult.Message.Value;
                Console.WriteLine(jsonRes);

                switch (jsonRes)
                {
                    case "Create":
                        consumeResult = consumer.Consume();
                        consumer.Commit(consumeResult);
                        jsonRes = consumeResult.Message.Value;
                        Console.WriteLine(jsonRes);

                        var stringContentC = new StringContent(jsonRes, Encoding.UTF8, "application/json");
                        var responseC = client.PostAsync($"api/v1.0/posts", stringContentC).Result;
                        var jsonDeSerC = responseC.Content.ReadAsStringAsync().Result;

                        var postC = new Message<Null, string> { Value = jsonDeSerC };
                        producer.ProduceAsync("OutTopic", postC).GetAwaiter().GetResult();
                        Console.WriteLine("Send OutTopic Create");
                        break;
                    case "Delete":
                        consumeResult = consumer.Consume();
                        consumer.Commit(consumeResult);
                        jsonRes = consumeResult.Message.Value;

                        var responseD = client.DeleteAsync($"api/v1.0/posts/{int.Parse(jsonRes)}").Result;

                        if (responseD.IsSuccessStatusCode)
                        {
                            var postD = new Message<Null, string> { Value = "Success" };
                            producer.ProduceAsync("OutTopic", postD).GetAwaiter().GetResult();
                            Console.WriteLine("Send OutTopic Delete Success");
                        }
                        else
                        {
                            var postD = new Message<Null, string> { Value = "Failure" };
                            producer.ProduceAsync("OutTopic", postD).GetAwaiter().GetResult();
                            Console.WriteLine("Send OutTopic Delete Failure");
                        }
                        break;
                    case "GetPostById":
                        consumeResult = consumer.Consume();
                        consumer.Commit(consumeResult);
                        jsonRes = consumeResult.Message.Value;
                        Console.WriteLine(jsonRes);

                        var responseBI = client.GetAsync($"api/v1.0/posts/{int.Parse(jsonRes)}").Result;
                        var jsonDeSerBI = responseBI.Content.ReadAsStringAsync().Result;

                        var postBI = new Message<Null, string> { Value = jsonDeSerBI };
                        producer.ProduceAsync("OutTopic", postBI).GetAwaiter().GetResult();
                        Console.WriteLine("Send OutTopic GetPostById");

                        break;
                    case "GetAllEnt":

                        var responseGA = client.GetAsync("api/v1.0/posts").Result;
                        var jsonDeSerGA = responseGA.Content.ReadAsStringAsync().Result;

                        var postGA = new Message<Null, string> { Value = jsonDeSerGA };
                        producer.ProduceAsync("OutTopic", postGA).GetAwaiter().GetResult();
                        Console.WriteLine("Send OutTopic GetAllEnt");

                        break;
                    case "UpdatePost":
                        consumeResult = consumer.Consume();
                        consumer.Commit(consumeResult);
                        jsonRes = consumeResult.Message.Value;
                        Console.WriteLine(jsonRes);

                        var stringContentU = new StringContent(jsonRes, Encoding.UTF8, "application/json");
                        var responseU = client.PutAsync($"api/v1.0/posts", stringContentU).Result;
                        var jsonDeSerU = responseU.Content.ReadAsStringAsync().Result;

                        var postU = new Message<Null, string> { Value = jsonDeSerU };
                        producer.ProduceAsync("OutTopic", postU).GetAwaiter().GetResult();
                        Console.WriteLine("Send OutTopic Update");
                        break;
                    default:
                        Console.WriteLine("Unknow post");
                        break;
                }
                Console.WriteLine();
                
            }        
        }
    }
}
