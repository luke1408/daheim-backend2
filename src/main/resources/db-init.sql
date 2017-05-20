drop table users if exists;
drop table homes if exists;
create table homes(
	id identity primary key,
	bssid varchar2(45),
	name varchar(50)
);
create table users(
	id identity primary key,
	name varchar2(20),
	uuid char(36),
	home bigint,
	foreign key(home) references homes(id)
);

create view v_users_per_home as
select home, count(1) as users from users
where home is not null
group by home;

insert into homes(bssid, name) values ('test', 'test');
insert into users(name, uuid, home) values ('Anke', 'asdf', 1);
insert into users(name, uuid, home) values ('asdf', 'fdsa', 1);
insert into users(name, uuid, home) values ('bx', 'cxbcxf', 1);
insert into users(name, uuid, home) values ('cb', 'fdscba', 1);
insert into users(name, uuid) values ('Anke', 'test');