
package com.libreria.libreria.service;

import com.libreria.libreria.entitis.Customer;
import com.libreria.libreria.entitis.Libro;
import com.libreria.libreria.entitis.Prestamo;
import com.libreria.libreria.repositories.PrestamoRepositorio;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrestamoServicio {
 
    private PrestamoRepositorio pr;
    private LibroServicio ls;
    private UsuarioServicio us;
    
    @Autowired    
    public PrestamoServicio(PrestamoRepositorio pr, LibroServicio ls, UsuarioServicio us) {
        this.pr = pr;
        this.ls = ls;
        this.us = us;
    }
    
    @Transactional
    public void guardar(Prestamo prestamo,String customerId,String libroId) throws Exception{
        Libro libro = ls.buscarPorId(libroId);
        Customer customer = us.buscarPorId(libroId);
        
        prestamo.setCustomer(customer);
        prestamo.setLibro(libro);
        altabaja(prestamo);
        
        validar(prestamo);
        
        pr.save(prestamo);
    }   
    
    @Transactional
    public void modificar(Prestamo prestamo) throws Exception{
        validar(prestamo);
        altabaja(prestamo);
        
        pr.save(prestamo);
    }
    private void altabaja(Prestamo prestamo){
        if(prestamo.getAlta() == null || prestamo.getAlta() == false){
            prestamo.setAlta(true);
        }else{
            prestamo.setAlta(false);
        }
    }
    private void validar(Prestamo prestamo) throws Exception{
        
        if (prestamo.getLibro() == null) {
            throw new Exception("El libro no se ha encontrado");
        }
        
        if (prestamo.getCustomer() == null) {
            throw new Exception("El usuario seleccionado no se ha encotnrado");
        }
        
    }   
    
    public Prestamo buscarPorId(String id){
        return pr.findById(id).get();
    }
    
    public List<Prestamo> listarPrestamos(){
        return pr.findAll();
    }


    
}
