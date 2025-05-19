CREATE TABLE cursos (
                        id INT NOT NULL AUTO_INCREMENT,
                        nombre VARCHAR(100) NOT NULL,
                        descripcion VARCHAR(255),
                        creditos INT NOT NULL,
                        enabled BIT(1) NOT NULL,
                        PRIMARY KEY (id),
                        UNIQUE (nombre)
);