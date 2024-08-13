package org.example.springdatajdbc.repository;

import org.example.springdatajdbc.entity.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Repository
public class BookDao {

    private final JdbcTemplate jdbcTemplate;

    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String INSERT_BOOK = "INSERT INTO books (title, author, publication_year) VALUES (?, ?, ?)";
    private static final String SELECT_BOOK_BY_ID = "SELECT * FROM books WHERE id = ?";
    private static final String SELECT_ALL_BOOKS = "SELECT * FROM books";
    private static final String UPDATE_BOOK = "UPDATE books SET title = ?, author = ?, publication_year = ? WHERE id = ?";
    private static final String DELETE_BOOK = "DELETE FROM books WHERE id = ?";

    private final RowMapper<Book> rowMapper = new RowMapper<>() {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Book book = new Book();
            book.setId(rs.getLong("id"));
            book.setTitle(rs.getString("title"));
            book.setAuthor(rs.getString("author"));
            book.setPublicationYear(rs.getInt("publication_year"));
            return book;
        }
    };

    public void createBook(Book book) {
        jdbcTemplate.update(INSERT_BOOK, book.getTitle(), book.getAuthor(), book.getPublicationYear());
    }

    public Optional<Book> getBookById(Long id) {
        return jdbcTemplate.query(SELECT_BOOK_BY_ID, new Object[]{id}, rowMapper).stream().findFirst();
    }

    public List<Book> getAllBooks() {
        return jdbcTemplate.query(SELECT_ALL_BOOKS, rowMapper);
    }

    public void updateBook(Long id, Book book) {
        jdbcTemplate.update(UPDATE_BOOK, book.getTitle(), book.getAuthor(), book.getPublicationYear(), id);
    }

    public void deleteBook(Long id) {
        jdbcTemplate.update(DELETE_BOOK, id);
    }
}
