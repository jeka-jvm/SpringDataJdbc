CREATE TABLE IF NOT EXISTS books (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    publication_year INT NOT NULL
    );

INSERT INTO books (title, author, publication_year) VALUES
                                                        ('Effective Java', 'Joshua Bloch', 2008),
                                                        ('Clean Code', 'Robert C. Martin', 2008),
                                                        ('Spring in Action', 'Craig Walls', 2018);