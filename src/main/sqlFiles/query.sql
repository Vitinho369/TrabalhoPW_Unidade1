CREATE TABLE Lojista(
    id SERIAL PRIMARY KEY,
    nome TEXT,
    senha TEXT,
    email TEXT
);

CREATE TABLE Cliente(
    id SERIAL PRIMARY KEY,
    nome TEXT,
    senha TEXT,
    email TEXT
);

CREATE TABLE Produto(
    id SERIAL PRIMARY KEY,
    id_lojista INTEGER REFERENCES Lojista(id),
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
INSERT INTO Produto(id_lojista, nome, preco, estoque, descricao)  VALUES
(1, 'Mesa',500,10,'Uma mesa de computador'),
(1, 'Lápis', 2,50, 'Lápis B2 grafite'),
(1, 'Computador', 1500,2, 'Computador I5 16Gb de RAM');