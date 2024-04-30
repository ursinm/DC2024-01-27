export enum PROVIDERS {
  DRIZZLE = "DRIZZLE",
}

export enum TOPICS {
  IN_TOPIC = "InTopic",
  OUT_TOPIC = "OutTopic",
}

export enum KAFKA_KEYS {
  GET_ALL = "get.all",
  GET_ONE = "get.one",
  CREATE = "create",
  UPDATE = "update",
  DELETE = "delete",
}

export enum REDIS_KEYS {
  EDITORS_GET_ALL = "editors.get.all",
  EDITORS_GET_ONE = "editors.get",
  MESSAGES_GET_ALL = "messages.get.all",
  MESSAGES_GET_ONE = "messages.get",
  STORIES_GET_ALL = "stories.get.all",
  STORIES_GET_ONE = "stories.get",
  TAGS_GET_ALL = "tags.get.all",
  TAGS_GET_ONE = "tags.get",
}

export const DELAY = 500;
