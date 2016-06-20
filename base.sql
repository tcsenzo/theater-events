set foreign_key_checks = false;

drop table if exists `user`;
create table `user` (
	id bigint(20) not null primary key auto_increment,
	name varchar(255) not null,
	email varchar(255) not null,
	password varchar(255) not null,
	created_at datetime not null
);


drop table if exists theater;
create table theater (
	id bigint(20) not null primary key auto_increment,
	name varchar(255) not null,
	street varchar(255) not null,
	number varchar(255) not null,
	complement varchar(255),
	district varchar(255) not null,
	city varchar(255) not null,
	state varchar(255) not null,
	country varchar(255) not null,
	zip_code varchar(255) not null,
	created_at datetime not null
);

drop table if exists event;
create table event (
	id bigint(20) not null primary key auto_increment,
	name varchar(100) not null,
	description varchar(255) not null,
	price varchar(30) not null,
	scheduled_date datetime not null,
	created_at datetime not null,
	theater_id bigint(20) not null,
	CONSTRAINT theater_id_fk FOREIGN KEY (theater_id) REFERENCES theater(id)
);

set foreign_key_checks = true;