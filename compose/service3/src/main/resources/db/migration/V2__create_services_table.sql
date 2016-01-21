create table service3.services (
  id int not null,
  host varchar(50) not null,
  service varchar(50) not null,
  response varchar(50) not null,

  constraint pk_services primary key (id)
);