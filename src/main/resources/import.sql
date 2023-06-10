

-- Estados:
INSERT INTO estado (id, nome, sigla) VALUES (1, 'Tocantins', 'TO');
INSERT INTO estado (id, nome, sigla) VALUES (2, 'Goiás', 'GO');
INSERT INTO estado (id, nome, sigla) VALUES (3, 'Maranhão', 'MA');
INSERT INTO estado (id, nome, sigla) VALUES (4, 'Pará', 'PA');
INSERT INTO estado (id, nome, sigla) VALUES (5, 'Bahia', 'BA');

-- Cidades:
INSERT INTO cidade (id, nome, estado) VALUES (1, 'Palmas', 1);
INSERT INTO cidade (id, nome, estado) VALUES (2, 'Anápolis', 2);
INSERT INTO cidade (id, nome, estado) VALUES (3, 'Imperatriz', 3);
INSERT INTO cidade (id, nome, estado) VALUES (4, 'Belém', 4);
INSERT INTO cidade (id, nome, estado) VALUES (5, 'Salvador', 5);

-- Telefones:
INSERT INTO telefone (id, codigoArea, numero) VALUES (1, '(63)', '99999-0044');
INSERT INTO telefone (id, codigoArea, numero) VALUES (2, '(62)', '98888-0964');
INSERT INTO telefone (id, codigoArea, numero) VALUES (3, '(61)', '97779-8044');
INSERT INTO telefone (id, codigoArea, numero) VALUES (4, '(99)', '99889-1234');

-- Produtores:
INSERT INTO produtor (id, nome, pais) VALUES (1, 'Marqués del Atrio', 'Espanha');
INSERT INTO produtor (id, nome, pais) VALUES (2, 'Viña Santa Alicia', 'Chile');
INSERT INTO produtor (id, nome, pais) VALUES (3, 'DFJ Vinhos', 'Portugal');
INSERT INTO produtor (id, nome, pais) VALUES (4, 'J. García Carrión', 'Espanha');
INSERT INTO produtor (id, nome, pais) VALUES (5, 'Felix Solis', 'Espanha');

-- Vinhos:
INSERT INTO vinho (id, nome, estoque, preco, descricao, teoralcoolico, tipoUva, produtor, tipovinho)
VALUES (1, 'Valtier Reserva Utiel-Requena DOP', 15, 50, 'Produzido a partir das uvas Tempranillo Bobal.', '13%', 'Tempranillo Bobal', 1, 1);
INSERT INTO vinho (id, nome, estoque, preco, descricao, teoralcoolico, tipoUva, produtor, tipovinho)
VALUES (2, 'Santa Alicia Cabernet Sauvignon', 35, 35, 'Produzido a partir das uvas Cabernet Sauvignon.', '13%', 'Cabernet Sauvignon', 2, 1);
INSERT INTO vinho (id, nome, estoque, preco, descricao, teoralcoolico, tipoUva, produtor, tipovinho)
VALUES (3, 'Winemakers Selection', 45, 55, 'Produzido a partir das uvas Moscatel.', '12%', 'Moscatel e Chardonnay', 3, 2);
INSERT INTO vinho (id,nome, estoque, preco, descricao, teoralcoolico, tipoUva, produtor, tipovinho)
VALUES (4, 'Viñapeña Tempranillo', 35, 65, 'Produzido a partir das uvas Tempranillo.', '11,5%', 'Tempranillo', 4, 3);
INSERT INTO vinho (id, nome, estoque, preco, descricao, teoralcoolico, tipoUva, produtor, tipovinho)
VALUES (5, 'Comte de Chamberi Mousseux Brut', 85, 80, 'Produzido a partir das uvas Airén Viúra.', '10,5%', 'Airén Viúra', 5, 4);

-- Endereços:
INSERT INTO endereco (id, rua, numero, bairro, complemento, cep, cidade)
VALUES (1, 'Alameda 02', '33', '804 Sul', 'Perto do bar', '97859-256', 1);
INSERT INTO endereco (id, rua, numero, bairro, complemento, cep, cidade)
VALUES (2, 'Alameda 10', '13', '1004 Sul', 'Casa vermelha', '99789-741', 2);
INSERT INTO endereco (id, rua, numero, bairro, complemento, cep, cidade)
VALUES (3, 'Alameda 69', '98', '303 Norte', 'Perto da praça', '9741-456', 3);
INSERT INTO endereco (id, rua, numero, bairro, complemento, cep, cidade)
VALUES (4, 'Alameda 07', '85', '204 Sul', 'Enfrente ao supermercado', '99698-299', 4); 

-- Usuários:
INSERT INTO usuario (id, nome, cpf, senha, email, telefone_id)
VALUES (1, 'Duda Delo Russo', '001.002.003-78', 'TRwn0XU29Gwl2sagG00bvjrNJvLuYo+dbOBJ7R3xFpU4m/FAUc5q8OoGbVNwPF7F5713RaYkN4qyufNCDHm/mA==', 'dudadelo@gmail.com', 1);
INSERT INTO usuario (id, nome, cpf, senha, email, telefone_id )
VALUES (2, 'Bruno Alencar da Silva', '021.002.003-33', 'TRwn0XU29Gwl2sagG00bvjrNJvLuYo+dbOBJ7R3xFpU4m/FAUc5q8OoGbVNwPF7F5713RaYkN4qyufNCDHm/mA==', 'brunoalencar@gmail.com', 2);
INSERT INTO usuario (id, nome, cpf, senha, email, telefone_id )
VALUES (3, 'Amanda Bruna da Silva', '001.962.003-84', 'TRwn0XU29Gwl2sagG00bvjrNJvLuYo+dbOBJ7R3xFpU4m/FAUc5q8OoGbVNwPF7F5713RaYkN4qyufNCDHm/mA==', 'amandasilva@gmail.com', 3);
INSERT INTO usuario (id, nome, cpf, senha, email, telefone_id )
VALUES (4, 'Victor Soares da Silva', '001.002.003-00', 'TRwn0XU29Gwl2sagG00bvjrNJvLuYo+dbOBJ7R3xFpU4m/FAUc5q8OoGbVNwPF7F5713RaYkN4qyufNCDHm/mA==', 'victorsilva@gmail.com', 4); 

insert into perfis (id_usuario, perfil) values (1, 'Admin');
insert into perfis (id_usuario, perfil) values (1, 'User');
insert into perfis (id_usuario, perfil) values (2, 'User');


-- Lista de desejos: 
/* INSERT INTO listadesejos (usuario_id, vinho_id)
VALUES (1, 1);
INSERT INTO listadesejos (usuario_id, vinho_id)
VALUES (2, 1);
INSERT INTO listadesejos (usuario_id, vinho_id)
VALUES (3, 1);
INSERT INTO listadesejos (usuario_id, vinho_id)
VALUES (4, 1); */
