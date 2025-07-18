package com.programacion.distribuida.book.repo;

import com.programacion.distribuida.book.db.Book;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
@Transactional
public class BooksRepository implements PanacheRepositoryBase<Book, String> {


}
