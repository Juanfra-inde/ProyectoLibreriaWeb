
package com.libreria.libreria.service;

import com.libreria.libreria.entitis.Foto;
import com.libreria.libreria.repositories.FotoRepositorio;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FotoServicio {
    
    private final FotoRepositorio fotorepositorio;
    
    @Autowired
    public FotoServicio(FotoRepositorio fotorepositorio) {
        this.fotorepositorio = fotorepositorio;
    }
    @Transactional
    public Foto guardar(MultipartFile archivo) throws Exception{
        if (archivo != null) {
            Foto foto = new Foto();
            foto.setMime(archivo.getContentType());
            foto.setNombre(archivo.getName());
            foto.setContenido(archivo.getBytes());
            
            return fotorepositorio.save(foto);            
        } else {
            return null;
        }
    }
    @Transactional
    public Foto acutalizar(String idFoto, MultipartFile archivo) throws Exception{
        if (archivo != null) {
            Foto foto = new Foto();
            if (idFoto != null) {
                Optional<Foto> respuesta = fotorepositorio.findById(idFoto);
                if (respuesta.isPresent()) {
                    foto = respuesta.get();
                }
            }
            foto.setMime(archivo.getContentType());
            foto.setNombre(archivo.getName());
            foto.setContenido(archivo.getBytes());
            
            return fotorepositorio.save(foto);            
        } else {
            return null;
        }
    }
    
    
}
