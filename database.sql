create database belajar_spring_restful_api;

use belajar_spring_restful_api;

create table users (
    username varchar(100) not null ,
    password varchar(100) not null,
    name varchar(100) not null,
    token varchar(100),
    token_expired_at BIGINT,
    primary key (username),
    UNIQUE(token)
) ENGINE InnoDB;

select *from users;


desc users;

create table contacts(
    id varchar(100) not null,
    username varchar(100) not null ,
    first_name varchar(100) not null,
    last_name varchar(100),
    phone varchar(100),
    email varchar(100),
    primary key (id),
    foreign key fk_user_contacts (username) references users (username)
) ENGINE InnoDB;

select *from contacts;

desc contacts;

create table addresses(
    id varchar(100) not null,
    contact_id varchar(100) not null,
    street varchar(200),
    city varchar(100),
    province varchar(100),
    country varchar(100) not null ,
    postal_code varchar(100),
    primary key (id),
    foreign key fk_contact_addresses (contact_id) references contacts(id)
)engine InnoDB;

select *from addresses;

delete from addresses;

delete from contacts;

delete from users;