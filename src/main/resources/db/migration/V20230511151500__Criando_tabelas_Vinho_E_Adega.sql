create table if not exists vinho (
   id serial primary key,
   tipo varchar (50) not null
);

create table if not exists adega (
   id serial primary key,
   nome varchar (50) not null,
   capacidade int not null
);