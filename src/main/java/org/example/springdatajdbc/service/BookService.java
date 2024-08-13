package org.example.springdatajdbc.service;

import org.example.springdatajdbc.entity.Book;
import org.example.springdatajdbc.repository.BookDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class BookService {

    private final BookDao bookDao;

    public BookService(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public void createBook(Book book) {
        bookDao.createBook(book);
    }

    public Optional<Book> getBookById(Long id) {
        return bookDao.getBookById(id);
    }

    public List<Book> getAllBooks() {
        return bookDao.getAllBooks();
    }

    public void updateBook(Long id, Book book) {
        bookDao.updateBook(id, book);
    }

    public void deleteBook(Long id) {
        bookDao.deleteBook(id);
    }
}
