
package com.libreria.libreria.service;

import com.libreria.libreria.entitis.Prestamo;
import com.libreria.libreria.repositories.PrestamoRepositorio;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrestamoServicio {
 
    private PrestamoRepositorio pr;
    
    @Autowired    
    public PrestamoServicio(PrestamoRepositorio pr) {
        this.pr = pr;
    }
    
    @Transactional
    public void guardar(Prestamo prestamo) throws Exception{
        validar(prestamo);
        activar(prestamo);
        
        pr.save(prestamo);
    }
    
    @Transactional
    public void modificar(Prestamo prestamo) throws Exception{
        validar(prestamo);
        altabaja(prestamo);
        
        pr.save(prestamo);
    }

    private void validar(Prestamo prestamo) throws Exception{
        
        if (prestamo.getLibro() == null) {
            throw new Exception("El libro no se ha encontrado");
        }
        
        if (prestamo.getCustomer() == null) {
            throw new Exception("El usuario seleccionado no se ha encotnrado");
        }
        
    }   

    private void activar(Prestamo prestamo) {
        if(prestamo.getAlta() == null){
            prestamo.setAlta(true);            
        }
    }

    private void altabaja(Prestamo prestamo) {
        if(prestamo.getAlta() == true){
            prestamo.setAlta(false);
        }else{
            prestamo.setAlta(true);
        }
    }
    
}
