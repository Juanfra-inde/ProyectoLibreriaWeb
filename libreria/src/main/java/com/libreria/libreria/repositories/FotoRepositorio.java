package com.libreria.libreria.repositories;

import com.libreria.libreria.entitis.Foto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FotoRepositorio extends JpaRepository<Foto, String> {
    
}
