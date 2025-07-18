package com.programacion.distribuida.book.service.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
public class AuthorDto {

    private Integer id;
    private String name;
}
