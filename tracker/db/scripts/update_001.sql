create table if not exists items (
   id serial primary key not null,
   name varchar(2000),
   description varchar(1000)
);

