create table event (
	id bigint(20) not null primary key auto_increment,
	name varchar(100) not null,
	description varchar(255) not null,
	price varchar(30) not null
)