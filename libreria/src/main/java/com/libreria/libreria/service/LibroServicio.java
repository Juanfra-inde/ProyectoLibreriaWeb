
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
    
    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn  = Exception.class)
    public void guardar(Libro libro) throws Exception{        
        libro.setEjemplaresRestantes(lPrestados(libro.getEjemplares(),libro.getEjemplaresPrestados()));
        validar(libro);        
        librorepositorio.save(libro);
    }
    
    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn  = Exception.class)
    public void modificarLibro(String id) throws Exception{
        
        Optional<Libro> respuesta = librorepositorio.findById(id);
            
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            validar(libro);
            librorepositorio.save(libro);
        }else{
            throw new Exception("Libro no encontrado");
        }
        
    }

    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn  = Exception.class)
    private void validar(Libro libro) throws Exception {
        
        if (libro.getIsbn()<0 || libro.getIsbn() == null) {
            throw new Exception("Isbn debe tener un valor valido");
        }
        
        if (libro.getTitulo() == null || libro.getTitulo().isEmpty() || libro.getTitulo().contains("  ")) {
            throw new Exception("Debe tener un nombre valido");
        }
        
        if(validarTitulo(libro.getTitulo())){
            throw new Exception("El titulo ingresado ya se encuentra previamente cargado");
        }
        
        if (libro.getAnio()<0 || libro.getAnio() == null || libro.getAnio()>2025) {
            throw new Exception("El Añio debe tener un valor valido");
        }
        
        if (libro.getEjemplares()<0 || libro.getEjemplares() == null) {
            throw new Exception("Los Ejemplares deben de tener un valor valido");
        }
        
        if (libro.getEjemplaresPrestados()<0 || libro.getEjemplaresPrestados() == null || libro.getEjemplaresPrestados()>libro.getEjemplares()) {
            throw new Exception("Los Ejemplares Prestados deben de tener un valor valido");
        }
        
        if (libro.getEjemplaresRestantes()<0 || libro.getEjemplaresRestantes() == null || libro.getEjemplaresRestantes()>libro.getEjemplares()) {
            throw new Exception("Los Ejemplares Restantes deben de tener un valor valido");
        }
       
    }
    
    public Boolean validarTitulo(String titulo){
        if(titulo.equals(librorepositorio.buscarelLibro(titulo))){
            return true;
        }else{
            return false;
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
    
     @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = Exception.class)
    public void Alta(String id){
        Libro libro = librorepositorio.findById(id).get();        
        if(libro.getAlta() == null || libro.getAlta() == false){
            libro.setAlta(true);
        }else{
            libro.setAlta(false);
        }
        librorepositorio.save(libro);        
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
