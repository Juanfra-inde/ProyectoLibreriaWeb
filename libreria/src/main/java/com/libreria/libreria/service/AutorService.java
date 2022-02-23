
package com.libreria.libreria.service;

import com.libreria.libreria.entitis.Autor;
import com.libreria.libreria.repositories.AutorRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@Service
public class AutorService {
    
    
    private AutorRepositorio autortepositorio;
    
    @Autowired
    public AutorService(AutorRepositorio autortepositorio) {
        this.autortepositorio = autortepositorio;
    }
     
    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = Exception.class)
    public List<Autor> listarAutores(){
        return autortepositorio.findAll();
    }
    
    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = Exception.class)
    public Autor guardarAutor(Autor autor) throws Exception{
        
        validar(autor);     
//        autor.setAlta(true);
        return autortepositorio.save(autor);
    }
    
    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = Exception.class)
    private void validar(Autor autor) throws Exception {
        
        String nombre = autor.getNombre();
        
        if(nombre.isEmpty()||nombre.contains("  ")||nombre.length()<2){
            throw new Exception("El nombre ingresado es invalido");
        }
    }
    
    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = Exception.class)
    public void modificarAutor(String id,String nombre) throws Exception{
        
        Optional<Autor> resultado = autortepositorio.findById(id);
        
        if(resultado.isPresent()){
            Autor autor = resultado.get();            
            validar(autor);
            autor.setNombre(nombre);
            autortepositorio.save(autor);
        }else{
            throw new Exception("Autor no ha sido encontrado");
        }
    }
    
    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = Exception.class)
    public Optional buscarAutor(String id){
        return autortepositorio.findById(id);
    }
    
    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = Exception.class)
    public Autor buscarAutor2(String id){
        return autortepositorio.findById(id).get();
    }
    
    @Transactional(value = Transactional.TxType.REQUIRED, rollbackOn = Exception.class)
    public void Alta(String id){
        Autor autor = autortepositorio.findById(id).get();        
        if(autor.getAlta() == null || autor.getAlta() == false){
            autor.setAlta(true);
            autortepositorio.save(autor);
        }else{
            autor.setAlta(false);
            autortepositorio.save(autor);
        }
        autortepositorio.save(autor);        
    }
    
    
    
    
}
