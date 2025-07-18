package com.programacion.distribuida.book.service.dto;

import jakarta.enterprise.context.ApplicationScoped;
import org.modelmapper.ModelMapper;

@ApplicationScoped
public class MapperService {

    @ApplicationScoped
    public ModelMapper mapper() {
        return new ModelMapper();
    }

}
