CREATE TABLE autor (
id serial,
nome varchar(45) NOT NULL,
constraint pk_Autor PRIMARY KEY (id)
);

CREATE TABLE livro (
id serial,
titulo varchar(45),
constraint pk_Livro PRIMARY KEY (id)
);

CREATE TABLE livro_autor (
id_livro int4,
id_autor int4,
constraint pk_LivroAutor primary key(id_livro, id_autor),
constraint fk_LivroAutor_pk_Livro foreign key(id_livro) references livro(id) on update cascade,
constraint fk_LivroAutor_pk_Autor foreign key(id_autor) references autor(id) on update cascade
);