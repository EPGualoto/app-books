package com.programacion.distribuida.book.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@ToString
public class BookDto {
    private String isbn;
    private String title;
    private BigDecimal price;
    private Integer inventorySold;
    private Integer supplier;

    private List<String> authors;
}
