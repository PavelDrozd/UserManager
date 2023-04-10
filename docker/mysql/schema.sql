/*
drop table users;
drop table images;
drop table status;
*/

-- create database usermanager;

create table status(id int not null auto_increment primary key, name varchar(20) unique not null);

create table images(id bigint not null auto_increment primary key, name varchar(100), file_name varchar(250), size bigint, content_type varchar(10), is_preview_image boolean, bytes longblob, user_id bigint);

create table users(id bigint not null auto_increment primary key, username varchar(20), email varchar(40) unique not null, status_id int not null);
