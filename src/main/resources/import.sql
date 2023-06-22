

--Estados:
INSERT INTO estado ( nome, sigla) VALUES ('Tocantins', 'TO');
INSERT INTO estado ( nome, sigla) VALUES ('Goiás', 'GO');
INSERT INTO estado ( nome, sigla) VALUES ('Maranhão', 'MA');
INSERT INTO estado ( nome, sigla) VALUES ('Pará', 'PA');
INSERT INTO estado ( nome, sigla) VALUES ('Bahia', 'BA');

-- Cidades:
INSERT INTO cidade (  nome, estado) VALUES ( 'Palmas', 1);
INSERT INTO cidade (  nome, estado) VALUES ( 'Anápolis', 2);
INSERT INTO cidade (  nome, estado) VALUES ( 'Imperatriz', 3);
INSERT INTO cidade (  nome, estado) VALUES ( 'Belém', 4);
INSERT INTO cidade (  nome, estado) VALUES ( 'Salvador', 5);

-- Telefones:
INSERT INTO telefone (  codigoArea, numero) VALUES ( '(63)', '99999-0044');
INSERT INTO telefone (  codigoArea, numero) VALUES ( '(62)', '98888-0964');
INSERT INTO telefone (  codigoArea, numero) VALUES ( '(61)', '97779-8044');
INSERT INTO telefone (  codigoArea, numero) VALUES ( '(99)', '99889-1234');

-- Produtores:
INSERT INTO produtor (  nome, pais) VALUES ( 'Marqués del Atrio', 'Espanha');
INSERT INTO produtor (  nome, pais) VALUES ( 'Viña Santa Alicia', 'Chile');
INSERT INTO produtor (  nome, pais) VALUES ( 'DFJ Vinhos', 'Portugal');
INSERT INTO produtor (  nome, pais) VALUES ( 'J. García Carrión', 'Espanha');
INSERT INTO produtor (  nome, pais) VALUES ( 'Felix Solis', 'Espanha');

-- Vinhos:
INSERT INTO vinho (  nome, estoque, preco, descricao, teoralcoolico, tipoUva, produtor, tipovinho)
VALUES ( 'Valtier Reserva Utiel-Requena DOP', 15, 50, 'Produzido a partir das uvas Tempranillo Bobal.', '13%', 'Tempranillo Bobal', 1, 1);
INSERT INTO vinho (  nome, estoque, preco, descricao, teoralcoolico, tipoUva, produtor, tipovinho)
VALUES ( 'Santa Alicia Cabernet Sauvignon', 35, 35, 'Produzido a partir das uvas Cabernet Sauvignon.', '13%', 'Cabernet Sauvignon', 2, 1);
INSERT INTO vinho (  nome, estoque, preco, descricao, teoralcoolico, tipoUva, produtor, tipovinho)
VALUES ( 'Winemakers Selection', 45, 55, 'Produzido a partir das uvas Moscatel.', '12%', 'Moscatel e Chardonnay', 3, 2);
INSERT INTO vinho ( nome, estoque, preco, descricao, teoralcoolico, tipoUva, produtor, tipovinho)
VALUES ( 'Viñapeña Tempranillo', 35, 65, 'Produzido a partir das uvas Tempranillo.', '11,5%', 'Tempranillo', 4, 3);
INSERT INTO vinho (  nome, estoque, preco, descricao, teoralcoolico, tipoUva, produtor, tipovinho)
VALUES ( 'Comte de Chamberi Mousseux Brut', 85, 80, 'Produzido a partir das uvas Airén Viúra.', '10,5%', 'Airén Viúra', 5, 4);

-- Endereços:
INSERT INTO endereco (  rua, numero, bairro, complemento, cep, cidade)
VALUES ( 'Alameda 02', '33', '804 Sul', 'Perto do bar', '97859-256', 1);
INSERT INTO endereco (  rua, numero, bairro, complemento, cep, cidade)
VALUES ( 'Alameda 10', '13', '1004 Sul', 'Casa vermelha', '99789-741', 2);
INSERT INTO endereco (  rua, numero, bairro, complemento, cep, cidade)
VALUES ( 'Alameda 69', '98', '303 Norte', 'Perto da praça', '9741-456', 3);
INSERT INTO endereco (  rua, numero, bairro, complemento, cep, cidade)
VALUES ( 'Alameda 07', '85', '204 Sul', 'Enfrente ao supermercado', '99698-299', 4); 

-- Usuários:
INSERT INTO usuario (  nome, cpf, senha, email, telefone_id)
VALUES ( 'Duda Delo Russo', '001.002.003-78', 'TRwn0XU29Gwl2sagG00bvjrNJvLuYo+dbOBJ7R3xFpU4m/FAUc5q8OoGbVNwPF7F5713RaYkN4qyufNCDHm/mA==', 'dudadelo@gmail.com', 1);
INSERT INTO usuario (  nome, cpf, senha, email, telefone_id )
VALUES ( 'Bruno Alencar da Silva', '021.002.003-33', 'TRwn0XU29Gwl2sagG00bvjrNJvLuYo+dbOBJ7R3xFpU4m/FAUc5q8OoGbVNwPF7F5713RaYkN4qyufNCDHm/mA==', 'brunoalencar@gmail.com', 2);
INSERT INTO usuario (  nome, cpf, senha, email, telefone_id )
VALUES ( 'Amanda Bruna da Silva', '001.962.003-84', 'TRwn0XU29Gwl2sagG00bvjrNJvLuYo+dbOBJ7R3xFpU4m/FAUc5q8OoGbVNwPF7F5713RaYkN4qyufNCDHm/mA==', 'amandasilva@gmail.com', 3);
INSERT INTO usuario (  nome, cpf, senha, email, telefone_id )
VALUES ( 'Victor Soares da Silva', '001.002.003-00', 'TRwn0XU29Gwl2sagG00bvjrNJvLuYo+dbOBJ7R3xFpU4m/FAUc5q8OoGbVNwPF7F5713RaYkN4qyufNCDHm/mA==', 'victorsilva@gmail.com', 4); 

insert into perfis (id_usuario, perfil) values (1, 'Admin');
insert into perfis (id_usuario, perfil) values (1, 'User');
insert into perfis (id_usuario, perfil) values (2, 'User');


-- Lista de desejos: 
/* INSERT INTO listadesejos (usuario_  vinho_id)
VALUES (1, 1);
INSERT INTO listadesejos (usuario_  vinho_id)
VALUES (2, 1);
INSERT INTO listadesejos (usuario_  vinho_id)
VALUES (3, 1);
INSERT INTO listadesejos (usuario_  vinho_id)
VALUES (4, 1); */
