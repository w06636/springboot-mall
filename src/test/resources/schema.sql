create table if not exists product
(
	product_id          int                not null primary key auto_increment,
    product_name        varchar(128)       not null,
    category            varchar(32)        not null,
    image_url           varchar(256)       not null,
    price               int                not null,
    stock               int                not null,
    description         varchar(1024),
    create_date         timestamp          not null,
    last_modified_date  timestamp          not null
);

create table if not exists user
(
	user_id               int           not null primary key auto_increment,
    email                 varchar(256)  not null unique,
    password              varchar(256)  not null,
    create_date           timestamp     not null,
    last_modified_date    timestamp     not null
);