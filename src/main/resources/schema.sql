create table customer(
    id bigint auto_increment primary key,
    name varchar(50),
    family varchar(100),
    phone_number varchar(20),
    type varchar(10)
);

create table real_customer(
    id bigint primary key ,
    nationality varchar(30),
    foreign key (id) references customer(id) on delete cascade
);

create table legal_customer(
    id bigint primary key ,
    industry varchar(30),
    foreign key (id) references customer(id) on delete cascade
);