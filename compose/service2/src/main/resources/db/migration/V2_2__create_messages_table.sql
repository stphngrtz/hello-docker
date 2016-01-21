create table service2.messages (
  id int not null,
  message varchar(50) not null,

  constraint pk_messages primary key (id)
);

INSERT INTO service2.messages (id, message) VALUES
                        (1, 'Hello World'),
                        (2, 'Docker Compose rocks!');