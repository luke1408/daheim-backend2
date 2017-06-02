drop trigger if exists init_status_after_user_create;
drop table if exists user_status;
drop table if exists users;
drop table if exists homes;
drop table if exists status;

drop view if exists v_user_status;
drop view if exists v_actual_status;
drop view if exists v_users_per_home;
drop view if exists v_status_user;

create table homes(
	id integer auto_increment primary key,
	bssid varchar(45),
	name varchar(50)
);

create table users(
	id integer auto_increment primary key,
	name varchar(20),
	uuid char(36),
	home integer,
	foreign key(home) references homes(id)
);

create table status(
	id integer auto_increment primary key,
	name varchar(20)
);

create table user_status(
	id integer auto_increment primary key,
	user integer,
	status integer,
	create_date datetime default current_timestamp,
	expiration_date datetime,
	foreign key(user) references users(id),
	foreign key(status) references status(id)
);

CREATE TRIGGER init_status_after_user_create 
AFTER INSERT ON users 
FOR EACH ROW
insert into user_status (user, status)
values (new.id, 1);


create view v_users_per_home as
select home, count(1) as users from users
where home is not null
group by home;

create or replace view v_active_status as
select * from user_status
where expiration_date > now() or expiration_date is null; 

create view v_user_status as
select max(create_date), user, status from user_status
where expiration_date > now()
group by user;   

create view v_actual_status as
select a2.user, a2.status from
(select max(create_date) as create_date, user from v_active_status
group by user) a1
join v_active_status a2 on a1.user = a2.user and a1.create_date = a2.create_date;

create view v_status_user as
select u.*, s.id as status from v_actual_status vas
join users u on vas.user = u.id
join status s on s.id = vas.status;

insert into status(name) values ('weg');
insert into status(name) values ('daheim');
insert into homes(bssid, name) values ('test', 'test');
insert into users(name, uuid, home) values ('Anke', 'asdf', 1);
insert into users(name, uuid, home) values ('asdf', 'fdsa', 1);
insert into users(name, uuid, home) values ('bx', 'cxbcxf', 1);
insert into users(name, uuid, home) values ('cb', 'fdscba', 1);
insert into users(name, uuid) values ('Anke', 'test');
