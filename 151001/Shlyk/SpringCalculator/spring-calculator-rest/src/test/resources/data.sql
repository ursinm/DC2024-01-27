insert into "user" (id, login, password, first_name, last_name)
values (1, 'm_rgan', '12345678', 'Morgan', 'Paulsen'),
       (2, 'marcus', '87654321', 'Marcus', 'Aurelius'),
       (3, 'venya', '12123333', 'Venya', 'Stalin');
insert into "news" (id, title, content, created, modified, user_id)
values (1, 'The Galactic Empire',
        'The richest man on the Earth, Elon Musk, became The Empire of the whole Empire',
        current_timestamp, current_timestamp, 1);
insert into "label" (id, name)
values (1, 'Economics'),
       (2, 'Politics'),
       (3, 'Science'),
       (4, 'Elon Musk');
insert into "news_label" (news_id, label_id)
values (1, 4);
insert into "note" (id, content, news_id)
values (1, 'That''s incredible!', 1),
       (2, 'Liar!', 1);