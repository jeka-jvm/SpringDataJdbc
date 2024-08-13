package org.example.springdatajdbc.repository;

import org.example.springdatajdbc.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@SpringJUnitConfig(BookDao.class)
public class BookDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private ApplicationContext applicationContext;

    @BeforeEach
    void setUp() throws Exception {
        jdbcTemplate.execute("DELETE FROM books");

        // Инициализируем базу данных из schema.sql
        Resource resource = applicationContext.getResource("classpath:schema.sql");
        String sql = new String(Files.readAllBytes(Paths.get(resource.getURI())));
        jdbcTemplate.execute(sql);
    }

    @Test
    void testCreateBook() {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setPublicationYear(2023);

        bookDao.createBook(book);

        List<Book> books = bookDao.getAllBooks();
        assertEquals(1, books.size());
        assertEquals("Test Book", books.get(0).getTitle());
        assertEquals("Test Author", books.get(0).getAuthor());
        assertEquals(2023, books.get(0).getPublicationYear());
    }

    @Test
    void testGetBookById() {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setPublicationYear(2023);

        bookDao.createBook(book);
        Long id = bookDao.getAllBooks().get(0).getId();

        Optional<Book> fetchedBook = bookDao.getBookById(id);
        assertTrue(fetchedBook.isPresent());
        assertEquals("Test Book", fetchedBook.get().getTitle());
        assertEquals("Test Author", fetchedBook.get().getAuthor());
        assertEquals(2023, fetchedBook.get().getPublicationYear());
    }

    @Test
    void testUpdateBook() {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setPublicationYear(2023);

        bookDao.createBook(book);
        Long id = bookDao.getAllBooks().get(0).getId();

        Book updatedBook = new Book();
        updatedBook.setTitle("Updated Book");
        updatedBook.setAuthor("Updated Author");
        updatedBook.setPublicationYear(2024);

        bookDao.updateBook(id, updatedBook);

        Optional<Book> fetchedBook = bookDao.getBookById(id);
        assertTrue(fetchedBook.isPresent());
        assertEquals("Updated Book", fetchedBook.get().getTitle());
        assertEquals("Updated Author", fetchedBook.get().getAuthor());
        assertEquals(2024, fetchedBook.get().getPublicationYear());
    }

    @Test
    void testDeleteBook() {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setPublicationYear(2023);

        bookDao.createBook(book);
        Long id = bookDao.getAllBooks().get(0).getId();

        bookDao.deleteBook(id);

        List<Book> books = bookDao.getAllBooks();
        assertTrue(books.isEmpty());
    }
}