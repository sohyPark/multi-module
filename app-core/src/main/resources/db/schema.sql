drop table if exists board
create table board
(
    id          bigint            not null auto_increment,
    active      integer default 1 not null,
    description varchar(255),
    name        varchar(255),
    primary key (id)
)

drop table if exists comment
create table comment
(
    id            bigint            not null auto_increment,
    created_date  datetime,
    modified_date datetime,
    active        integer default 1 not null,
    contents      varchar(255),
    post_id       bigint            not null,
    user_id       bigint,
    primary key (id)
)

drop table if exists post
create table post
(
    id            bigint            not null auto_increment,
    created_date  datetime,
    modified_date datetime,
    active        integer default 1 not null,
    board_id      bigint            not null,
    contents      varchar(255),
    count         integer           not null,
    title         varchar(255),
    user_id       bigint,
    primary key (id)
)

drop table if exists user
create table user
(
    id       bigint            not null auto_increment,
    active   integer default 1 not null,
    count    integer           not null,
    email    varchar(50)       not null,
    name     varchar(16),
    password varchar(255),
    rank     integer           not null,
    role     integer           not null,
    primary key (id)
)

