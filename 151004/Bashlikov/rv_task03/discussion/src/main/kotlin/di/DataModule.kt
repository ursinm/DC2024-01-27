package di

import com.datastax.driver.core.Cluster
import data.local.dao.PostOfflineSource
import data.repository.PostsRepository
import domain.repository.IPostsRepository
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module

internal val postsRepositoryQualifier = StringQualifier("posts_repository")

internal val dataModule = module {

    //cqlsh
    //CREATE KEYSPACE distcomp WITH REPLICATION = {'class' : 'NetworkTopologyStrategy', 'datacenter1' : 3};
    //CREATE TABLE distcomp.tbl_post_by_country (country text, tweet_id bigint, id bigint, content text, PRIMARY KEY ((country), tweet_id, id));
    single<Cluster> {
        Cluster.builder()
            .withoutMetrics()
            .addContactPoints("127.0.0.1")
            .build()
    }

    single<PostOfflineSource> {
        val cluster: Cluster = get()

        PostOfflineSource(cluster.connect("distcomp"))
    }
    
    single<IPostsRepository>(postsRepositoryQualifier) {
        val postOfflineSource: PostOfflineSource = get()

        PostsRepository(postOfflineSource)
    }

}