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
