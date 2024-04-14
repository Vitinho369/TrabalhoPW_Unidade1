CREATE TABLE Lojista(
    email TEXT PRIMARY KEY,
    nome TEXT,
    senha TEXT
);

CREATE TABLE Cliente(
    email TEXT PRIMARY KEY,
    nome TEXT,
    senha TEXT
);

CREATE TABLE Produto(
    id SERIAL PRIMARY KEY,
    nome TEXT,
    preco FLOAT,
    estoque INTEGER,
    descricao TEXT
);

--Adicionando clientes default
INSERT INTO Cliente(nome, email, senha)VALUES
('João Pedro', 'jp2017@uol.com.br','12345jaum'),
('Amara Silva', 'amarasil@bol.com.br','amara82'),
('Maria Pereira', 'mariape@terra.com.br','145aektm');

--Adicionando lojistas default
INSERT INTO Lojista(nome, email, senha) VALUES
('Taniro Rodrigues', 'tanirocr@gmail.com','123456abc'),
('Lorena Silva', 'lore_sil@yahoo.com.br','12uhuuu@');

--Adicionando produtos default
INSERT INTO Produto(nome, preco, estoque, descricao)  VALUES
('Mesa',500,10,'Uma mesa de computador'),
('Lápis', 2,50, 'Lápis B2 grafite'),
('Computador', 1500,2, 'Computador I5 16Gb de RAM');