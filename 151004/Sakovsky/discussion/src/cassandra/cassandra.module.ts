import { Module } from '@nestjs/common';
import { CassandraService } from './cassandra.service';
import { Cassandra } from './cassandra';

@Module({
  providers: [CassandraService],
  exports: [CassandraService]
})
export class CassandraModule {}
