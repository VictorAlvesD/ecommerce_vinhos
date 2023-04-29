-- Estados:

insert into estado (nome,sigla)
values('Tocantins','TO');
insert into estado (nome,sigla)
values('Goias','GO');
insert into estado (nome,sigla)
values('Maranhão','MA');
insert into estado (nome,sigla)
values('Pará','PA');
insert into estado (nome,sigla)
values('Bahia','BA');

-- cidades:
insert into cidade (nome,estado)
values('Palmas',1);
insert into cidade (nome,estado)
values('Anapolis',2);
insert into cidade (nome,estado)
values('Imperatriz',3);
insert into cidade (nome,estado)
values('Belém',4);
insert into cidade (nome,estado)
values('Salvador',5); 


-- telefones:
insert into telefone (codigoArea,numero)
values('(63)','99999-0044');
insert into telefone (codigoArea,numero)
values('(62)','98888-0964');
insert into telefone (codigoArea,numero)
values('(61)','97779-8044');
insert into telefone (codigoArea,numero)
values('(99)','99889-1234');
insert into telefone (codigoArea,numero)
values('(63)','99865-0951');


-- produtor:
insert into produtor (nome,pais)
values('Marqués del Atrio','Espanha');
insert into produtor (nome,pais)
values('Viña Santa Alicia','Chile');
insert into produtor (nome,pais)
values('DFJ Vinhos','Portugal');
insert into produtor (nome,pais)
values('J. García Carrión','Espanha');
insert into produtor (nome,pais)
values('Felix Solis','Espanha');

-- vinho:
insert into vinho (nome,estoque,preco,descricao,teoralcoolico,tipoUva,produtor,tipovinho)
values('Valtier Reserva Utiel-Requena DOP',15,50,'Produzido a partir das uvas Tempranillo Bobal.','13%','Tempranillo Bobal',1,1);
insert into vinho (nome,estoque,preco,descricao,teoralcoolico,tipoUva,produtor,tipovinho)
values('Santa Alicia Cabernet Sauvignon',35,35,'Produzido a partir das uvas Cabernet Sauvignon.','13%','Cabernet Sauvignon',2,1);
insert into vinho (nome,estoque,preco,descricao,teoralcoolico,tipoUva,produtor,tipovinho)
values('Winemakers Selection',45,55,'Produzido a partir das uvas Moscatel.','12%','Moscatel e Chardonnay',3,2);
insert into vinho (nome,estoque,preco,descricao,teoralcoolico,tipoUva,produtor,tipovinho)
values('Viñapeña Tempranillo',35,65,'Produzido a partir das uvas Tempranillo.','11,5%','Tempranillo',4,3);
insert into vinho (nome,estoque,preco,descricao,teoralcoolico,tipoUva,produtor,tipovinho)
values('Comte de Chamberi Mousseux Brut',85,80,'Produzido a partir das uvas Airén Viúra.','10,5%','Airén Viúra',5,4);

-- usuários:
insert into usuario (nome,cpf,senha,email,telefone_id)
values('Duda Delo Russo','001.002.003-78','DudaRusoo95844','dudadelo@gmail.com',1);
insert into usuario (nome,cpf,senha,email,telefone_id)
values('Bruno Alencar da Silva','021.002.003-33','Bruno12345678','brunoalencar@gmail.com',2);
insert into usuario (nome,cpf,senha,email,telefone_id)
values('Amanda Bruna da Silva','001.962.003-84','Ab12345678','amandasilva@gmail.com',3);
insert into usuario (nome,cpf,senha,email,telefone_id)
values('Victor Soares da Silva','001.002.003-00','Vi12345678','victorsilva@gmail.com',4);

--enderecos:
insert into endereco (rua,numero,bairro,complemento,cep,cidade,usuario_id)
values('Alameda 02','33','804 Sul','Perto do bar','97859-256',1,1);
insert into endereco (rua,numero,bairro,complemento,cep,cidade,usuario_id)
values('Alameda 10','13','1004 Sul','Casa vermelha','99789-741',2,2);
insert into endereco (rua,numero,bairro,complemento,cep,cidade,usuario_id)
values('Alameda 69','98','303 Norte','Perto da praça','9741-456',3,3);
insert into endereco (rua,numero,bairro,complemento,cep,cidade,usuario_id)
values('Alameda 07','85','204 Sul','Enfrente ao supermercado','99698-299',4,4);
insert into endereco (rua,numero,bairro,complemento,cep,cidade,usuario_id)
values('Avenida d','33','Aureny I','Perto do ponto de onibus','91234-911',5,2);

--lista de desejos:
insert into listadesejos (usuario,vinho)
values(1,1);
insert into listadesejos (usuario,vinho)
values(2,1);
insert into listadesejos (usuario,vinho)
values(3,1);
insert into listadesejos (usuario,vinho)
values(4,1);
insert into listadesejos (usuario,vinho)
values(2,2);
insert into listadesejos (usuario,vinho)
values(3,3);

