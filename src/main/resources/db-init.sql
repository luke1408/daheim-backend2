drop table users if exists;
drop table homes if exists;
create table homes(
	id identity primary key,
	bssid varchar2(45)
);
create table users(
	id identity primary key,
	name varchar2(20),
	uuid char(36),
	home bigint,
	foreign key(home) references homes(id)
);
insert into users(name) values ('Adolf'),('Anton'), ('Heidi');
