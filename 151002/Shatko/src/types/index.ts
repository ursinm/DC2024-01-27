import { ParsedQs } from 'qs';

export enum TABLES {
    EDITORS = 'tbl_editors',
    STORIES = 'tbl_stories',
    MESSAGES = 'tbl_messages',
    TAGS = 'tbl_tags',
    STORIES_TO_TAGS = 'tbl_stories_m2m_tags',
}

export interface GetQueryParams extends ParsedQs {
    limit?: string;
    offset?: string;
    filter?: string;
    sort?: string;
}
