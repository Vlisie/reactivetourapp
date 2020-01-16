create table ploegen(id serial PRIMARY KEY , naam varchar(100) NOT NULL, ploegleider varchar(100), landcode varchar(5));
create table renners(id serial PRIMARY KEY, voornaam varchar(40) NOT NULL, achternaam varchar(40) not null, geboortedatum date not null, rugnummer int, gestart boolean, landcode varchar(5), ploeg serial references ploegen(id), afgestapt boolean);

