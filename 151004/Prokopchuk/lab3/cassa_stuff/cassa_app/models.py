import uuid
from cassandra.cqlengine import columns
from django_cassandra_engine.models import DjangoCassandraModel


class Note(DjangoCassandraModel):
    id = columns.BigInt(primary_key=True)
    content = columns.Text(min_length=2, max_length=2048)
    issueId = columns.BigInt()
    country = columns.Text(partition_key=True)

    class Meta:
        get_pk_field = 'id'
