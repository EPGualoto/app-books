package com.programacion.distribuida.books.repo;

import com.programacion.distribuida.books.db.Book;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Optional;

@ApplicationScoped
public class BooksRepository implements PanacheRepositoryBase<Book, String> {

    public Optional<Book> findByIsbnBook(String isbn) {
        return this.find("isbn", isbn).firstResultOptional();
    }

    @Transactional
    public void deleteInventoryByIsbn(String isbn) {
        getEntityManager()
                .createNativeQuery("DELETE FROM inventory WHERE isbn = :isbn")
                .setParameter("isbn", isbn)
                .executeUpdate();
    }

    @Transactional
    public void deleteBookAuthorsById(String isbn) {
        getEntityManager()
                .createNativeQuery("DELETE FROM books_authors WHERE books_isbn = :isbn")
                .setParameter("isbn", isbn)
                .executeUpdate();
    }

    @Transactional
    public void deleteLineItemsByIsbn(String isbn) {
        getEntityManager()
                .createNativeQuery("DELETE FROM line_items WHERE isbn = :isbn")
                .setParameter("isbn", isbn)
                .executeUpdate();
    }

    @Transactional
    public void update(String isbn, Book book) {
        this.findByIdOptional(isbn)
                .ifPresent(existingBook -> {
                    existingBook.setTitle(book.getTitle());
                    existingBook.setPrice(book.getPrice());
                    existingBook.setVersion(existingBook.getVersion() + 1);
                });
    }
}
