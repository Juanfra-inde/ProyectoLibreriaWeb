
package com.libreria.libreria.service;

import com.libreria.libreria.entitis.Autor;
import com.libreria.libreria.entitis.Editorial;
import com.libreria.libreria.entitis.Libro;
import com.libreria.libreria.repositories.LibroRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

@Service
public class LibroServicio {
    
    private LibroRepositorio librorepositorio;
    
    @Autowired
    public LibroServicio(LibroRepositorio librorepositorio) {
        this.librorepositorio = librorepositorio;
    }
       
//    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = Exception.class)
//    public void guardar(Libro libro){
//        
//        if(libro.getTitulo().trim().isEmpty()){
//            
//        }
//        librorepositorio.save(libro);
//    }
    
    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn  = Exception.class)
    public Libro guardar(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Autor autor, Editorial editorial, Boolean alta, Boolean baja) throws Exception{
        
        Integer librosprestantes = lPrestados(ejemplares, ejemplaresPrestados);
        
        validar(isbn, titulo, anio, ejemplares, ejemplaresPrestados,librosprestantes);        
        
        Libro libro = new Libro();
        
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(ejemplaresPrestados);
        libro.setEjemplaresRestantes(librosprestantes);
        libro.setEditorial(editorial);
        libro.setAutor(autor);
        libro.setAlta(true);
        libro.setBaja(false);
        
        
        return librorepositorio.save(libro);
        
    }
    
    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn  = Exception.class)
    public void modificarLibro(String id,String titulo,Long isbn, Integer anio, Integer ejemplares,Integer ejemplaresPrestados,Autor autor,Editorial editorial) throws Exception{
        
        validar(isbn, titulo, anio, ejemplares, ejemplaresPrestados, ejemplaresPrestados);
        
        Optional<Libro> respuesta = librorepositorio.findById(id);
        
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setTitulo(titulo);
            libro.setIsbn(isbn);
            libro.setEjemplares(ejemplares);
            libro.setEjemplaresPrestados(ejemplaresPrestados);
            libro.setAutor(autor);
            libro.setEditorial(editorial);

            librorepositorio.save(libro);
        }else{
            throw new Exception("Libro no encontrado");
        }
        
    }

    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn  = Exception.class)
    private void validar(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes) throws Exception {
        
        if (isbn<0 || isbn == null) {
            throw new Exception("Isbn debe tener un valor valido");
        }
        
        if (titulo == null || titulo.isEmpty() || titulo.contains("  ")) {
            throw new Exception("Debe tener un nombre valido");
        }
        
        if (anio<0 || anio == null || anio>2025) {
            throw new Exception("El Añio debe tener un valor valido");
        }
        
        if (ejemplares<0 || ejemplares == null) {
            throw new Exception("Los Ejemplares deben de tener un valor valido");
        }
        
        if (ejemplaresPrestados<0 || ejemplaresPrestados == null || ejemplaresPrestados>ejemplares) {
            throw new Exception("Los Ejemplares Prestados deben de tener un valor valido");
        }
        
        if (ejemplaresRestantes<0 || ejemplaresRestantes == null || ejemplaresRestantes>ejemplares) {
            throw new Exception("Los Ejemplares Restantes deben de tener un valor valido");
        }
        
    }
    
    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn  = Exception.class)
    private Integer lPrestados(Integer e, Integer eP) throws Exception{
        Integer lprestados;
        if(e<eP){
            throw new Exception("Error de valores");
        }else{
            lprestados = e-eP;
        }
        return lprestados;
    }
    
    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn  = Exception.class)
    public Libro alta(String id){
        
        Libro l = librorepositorio.findById(id).get();
        
        l.setBaja(false);
        l.setAlta(true);
        
        return librorepositorio.save(l);
    }
    
    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn  = Exception.class)
    public Libro baja(String id){
        
        Libro l = librorepositorio.findById(id).get();
        
        
        l.setBaja(true);
        l.setAlta(false);
        
        return librorepositorio.save(l);
    }
    
    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn  = Exception.class)
    public List<Libro> listarLibros(){
        return librorepositorio.findAll();
    }
    
    @Transactional
    public Libro buscarPorId(String id){
        return librorepositorio.findById(id).get();
    }
}
