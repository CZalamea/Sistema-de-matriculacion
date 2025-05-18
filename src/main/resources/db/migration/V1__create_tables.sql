-- V1__create_tables.sql

CREATE TABLE estudiantes (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             nombres VARCHAR(45) NOT NULL,
                             apellidos VARCHAR(45) NOT NULL,
                             email VARCHAR(255) NOT NULL UNIQUE,
                             password VARCHAR(15) NOT NULL,
                             curso VARCHAR(45) NOT NULL,
                             matricula VARCHAR(45) NOT NULL,
                             enabled BOOLEAN NOT NULL
);

CREATE TABLE profesores (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            nombres VARCHAR(45) NOT NULL,
                            apellidos VARCHAR(45) NOT NULL,
                            email VARCHAR(255) NOT NULL UNIQUE,
                            password VARCHAR(15) NOT NULL,
                            curso VARCHAR(45) NOT NULL,
                            matricula VARCHAR(45) NOT NULL,
                            enabled BOOLEAN NOT NULL
);