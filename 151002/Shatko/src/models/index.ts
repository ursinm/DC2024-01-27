export interface Editor {
    id: number;
    login: string;
    password: string;
    firstname: string;
    lastname: string;
}

export interface Message {
    id: number;
    storyId: number;
    content: string;
}

export interface Story {
    id: number;
    editorId: number;
    title: string;
    content: string;
    created: string;
    modified: string;
}

export interface Tag {
    id: number;
    name: string;
}
