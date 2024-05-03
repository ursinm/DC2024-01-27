package by.bashlikovvv.data.local.contract

object DatabaseContract {

    object EditorsTable {
        const val TABLE_NAME = "tbl_editor"
        const val COLUMN_ID = "id"
        const val COLUMN_LOGIN = "login"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_FIRSTNAME = "firstname"
        const val COLUMN_LASTNAME = "lastname"
    }

    object TweetsTable {
        const val TABLE_NAME = "tbl_tweet"
        const val COLUMN_ID = "id"
        const val COLUMN_EDITOR_ID = "editor_id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_CONTENT = "content"
        const val COLUMN_CREATED = "created"
        const val COLUMN_MODIFIED = "modified"
    }

    object PostsTable {
        const val TABLE_NAME = "tbl_post"
        const val COLUMN_ID = "id"
        const val COLUMN_TWEET_ID = "tweet_id"
        const val COLUMN_CONTENT = "content"
    }

    object TagsTable {
        const val TABLE_NAME = "tbl_tag"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "NAME"
    }

    object TweetTagsTable {
        const val TABLE_NAME = "tweet_tags"
        const val COLUMN_ID = "tweet_tags_id"
        const val COLUMN_TWEET_ID = "tweet_tags_tweet_id"
        const val COLUMN_TAG_ID = "tweet_tags_tag_id"
    }

}