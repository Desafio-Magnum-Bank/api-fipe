-- Tabela Marca
CREATE TABLE marca
(
    codigo_marca BIGINT       NOT NULL PRIMARY KEY,
    nome         VARCHAR(255) NOT NULL
);

ALTER TABLE marca OWNER TO "user";

-- Tabela Usuario
CREATE TABLE usuario
(
    id_usuario VARCHAR(255) NOT NULL PRIMARY KEY,
    nome       VARCHAR(255) NOT NULL,
    senha      VARCHAR(255) NOT NULL,
    user_name  VARCHAR(255) NOT NULL UNIQUE
);

ALTER TABLE usuario OWNER TO "user";

-- Tabela Veiculo
CREATE TABLE veiculo
(
    codigo_marca   BIGINT       NOT NULL,
    codigo_veiculo BIGINT       NOT NULL PRIMARY KEY,
    nome           VARCHAR(255) NOT NULL,
    observacao     VARCHAR(255),
    CONSTRAINT fk_veiculo_marca FOREIGN KEY (codigo_marca)
        REFERENCES marca (codigo_marca)
);

ALTER TABLE veiculo OWNER TO "user";

INSERT INTO usuario (id_usuario, nome, senha, user_name)
VALUES ('09a3698b-27d8-4d14-a9d1-05d91c624885', 'Admin', '$2a$10$FbJjEtIZYOwVtIUKDgTNMOm6cpCCHbqR1ewpKFGc3xyZc9o7oHmDi', 'admin');

CREATE INDEX idx_veiculo_marca ON veiculo (codigo_marca);
CREATE INDEX idx_veiculo_nome ON veiculo (nome);
CREATE INDEX idx_marca_nome ON marca (nome);