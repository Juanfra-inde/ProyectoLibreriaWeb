
package com.libreria.libreria.service;

import com.libreria.libreria.entitis.Editorial;
import com.libreria.libreria.entitis.Libro;
import com.libreria.libreria.repositories.EditorialRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditorialService {
    
    private EditorialRepositorio editorialrepositorio;
    
    @Autowired
    public EditorialService(EditorialRepositorio editorialrepositorio) {
        this.editorialrepositorio = editorialrepositorio;
    }
    
    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = Exception.class)
    public List<Editorial> listarEditoriales(){
        return editorialrepositorio.findAll();
    }
    
    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = Exception.class)
    public Editorial guardarEditorial(Editorial editorial) throws Exception{
        
        validar(editorial);
        editorial.setAlta(true);
        return editorialrepositorio.save(editorial);
    }

    private void validar(Editorial editorial) throws Exception {
        String nombre = editorial.getNombre();
        if(nombre.contains("   ") || nombre.isEmpty() || nombre.length()<2){
            throw new Exception("El nombre cargado es invalido");
        }
    }
    
    public void modificarEditorial(String id, String nombre) throws Exception{
        Optional<Editorial> resultado = editorialrepositorio.findById(id);
        
        if (resultado.isPresent()) {
            Editorial editorial = resultado.get();
            validar(editorial);
            editorial.setNombre(nombre);        
            editorialrepositorio.save(editorial);
        } else {
            throw new Exception("Editorial no encontrada");
        }
    }
    
}
