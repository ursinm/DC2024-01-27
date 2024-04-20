using Confluent.Kafka;
using Discussion.Services.DataProviderServices;
using Discussion.Views.DTO;
using Newtonsoft.Json;
using System.Threading;

namespace Discussion.Services.Kafka 
{
    public class KafkaCore : IKafkaCore
    {
        private INoteDataProvider _dataProvider;
        private ConsumerConfig config = new ConsumerConfig
        {
            BootstrapServers = "localhost:9092",
            GroupId = "foo",
        };

        public KafkaCore(INoteDataProvider dataProvider)
        { 
            _dataProvider = dataProvider;
        }

        public void StartConsuming()
        {
            CancellationTokenSource cts = new CancellationTokenSource();
            using (var consumer = new ConsumerBuilder<Ignore, string>(config).Build())
            {
                consumer.Subscribe("InTopic");
                try
                {
                    while (true)
                    {
                        var consumeResult = consumer.Consume(cts.Token);
                        if (consumeResult != null)
                        { 
                            ProcessMessage(consumeResult);
                        }
                    }
                }
                finally
                {
                    consumer.Close();
                }
            }
        }

        private void ProcessMessage(ConsumeResult<Ignore, string> _message)
        {
            var message = JsonConvert.DeserializeObject<KafkaMessageTemplate>(_message.Value);
            switch (message.Command) {
                case "create": 
                    {
                        var data = JsonConvert.DeserializeObject<NoteAddDTO>(message.Data);
                        _dataProvider.CreateNote(data);
                        break;
                    }
                case "get":
                    {
                        var data = JsonConvert.DeserializeObject<Dictionary<string, int>>(message.Data);
                        var res = _dataProvider.GetNote(data["id"]);
                        Produce(res.newsId.ToString(), "get_response", JsonConvert.SerializeObject(res));
                        break;
                    }
                case "getAll":
                    {
                        var res = _dataProvider.GetNotes();
                        Produce("extra", "getAll_response", JsonConvert.SerializeObject(res));
                        break;
                    }
                case "update":
                    {
                        var data = JsonConvert.DeserializeObject<NoteUpdateDTO>(message.Data);
                        var res = _dataProvider.UpdateNote(data);
                        Produce(res.newsId.ToString(), "update_response", JsonConvert.SerializeObject(res));
                        break;
                    }
                case "delete":
                    {
                        var data = JsonConvert.DeserializeObject<Dictionary<string, int>>(message.Data);
                        var res = _dataProvider.DeleteNote(data["id"]);
                        Produce("extra", "delete_response", JsonConvert.SerializeObject(res));
                        break;
                    }
            }
        }

        private void Produce(string key, string command, string data)
        {
            KafkaMessageTemplate template = new KafkaMessageTemplate();
            template.Command = command;
            template.Data = data;
            using (var producer = new ProducerBuilder<string, string>(config).Build())
            {
                producer.Produce("OutTopic", new Message<string, string>
                {
                    Value = JsonConvert.SerializeObject(template),
                    Key = key,
                });
                producer.Flush();
            }
        }
    }
}
