
package com.libreria.libreria.repositories;

import com.libreria.libreria.entitis.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String>{
    
    @Query("SELECT l FROM Libro l")
    public List<Libro> buscarLibros();
    
    @Query("SELECT l FROM Libro l WHERE l.titulo = :titulo")
    public List<Libro> buscarelLibros(@Param("titulo") String titulo);
    
    @Query("SELECT l FROM Libro l WHERE l.titulo = :titulo")
    public Libro buscarelLibro(@Param("titulo") String titulo);
    
    /*
    @Query("SELECT l FROM Libro l WHERE l.isbn = :isbn")
    public Libro buscarelLibroPorIsbn(@Param("isbn") Long isbn);
    */
}
