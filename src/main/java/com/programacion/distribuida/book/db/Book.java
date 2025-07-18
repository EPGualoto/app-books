package com.programacion.distribuida.book.db;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;


@Entity
@Data
@NoArgsConstructor
@Table(name = "books")
public class Book {

    @Id
    @Column(length = 128)
    private String isbn;

    @OneToOne(mappedBy = "book")
    private Inventory inventory;

    @Column(length = 128)
    private String title;

    @Column(precision = 12, scale = 2)
    private BigDecimal price;

    private Integer version;
}
