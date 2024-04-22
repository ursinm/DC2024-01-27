import { Injectable } from '@nestjs/common';
import { Client, mapping } from 'cassandra-driver';

@Injectable()
export class Cassandra {
  private cassandraClient: Client;
  private mapper: mapping.Mapper;

  constructor() {
    const contactPoints = ['localhost']; // Адрес(а) Cassandra
    const keyspace = 'distcomp'; // Имя вашего keyspace (в данном случае 'distcomp')

    this.cassandraClient = new Client({
      contactPoints,
      localDataCenter: 'discussion',
      keyspace,
    });
    this.cassandraClient
      .connect()
      .then(() => {
        console.log('Успешное подключение к Cassandra');
        this.mapper = new mapping.Mapper(this.cassandraClient, {
            models: {
                'Comment': {
                    tables: ['tbl_comment']
                }
            }
        });
      })
      .catch((error) =>
        console.error('Ошибка подключения к Cassandra:', error),
      );
  }

  getMapper(): mapping.Mapper {
    return this.mapper;
  }
}
