import { PROVIDERS } from "src/constants";
const cassandra = require("cassandra-driver");

export const CassandraProvider = {
  provide: PROVIDERS.CASSANDRA,
  useFactory: async () => {
    const client = new cassandra.Client({
      contactPoints: [process.env.DB_HOST as string],
      keyspace: process.env.DB_KEYSPACE as string,
      localDataCenter: "datacenter1",
    });
    return client;
  },
  exports: PROVIDERS.CASSANDRA,
};
