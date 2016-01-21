create table service1.greetings (
  id int not null,
  who_to_greet varchar(50) not null,
  who_greets varchar(50) not null,
  additional_information varchar(50),

  constraint pk_greetings primary key (id)
);