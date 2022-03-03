package com.libreria.libreria.service;

import com.libreria.libreria.entitis.Foto;
import com.libreria.libreria.entitis.Usuario;
import com.libreria.libreria.enumeraciones.Roles;
import com.libreria.libreria.repositories.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UsuarioServicio implements UserDetailsService{
    
    
    private final UsuarioRepositorio usuariorepositorio;
    private final FotoServicio fotorepositorio;
    private final NotificacionServicio notificacionservicio;
    
    @Autowired
    public UsuarioServicio(UsuarioRepositorio usuariorepositorio, FotoServicio fotorepositorio, NotificacionServicio notificacionservicio) {
        this.usuariorepositorio = usuariorepositorio;
        this.fotorepositorio = fotorepositorio;
        this.notificacionservicio = notificacionservicio;
    }
    
    @Transactional
    public void registrar(MultipartFile archivo,Usuario usuario) throws Exception{
        
        validar(usuario);
        activaralta(usuario);
        Foto foto = fotorepositorio.guardar(archivo);
        usuario.setFoto(foto);
        
        usuario.setContraseña(new BCryptPasswordEncoder().encode(usuario.getContraseña()));
        
        usuariorepositorio.save(usuario);
        
//        notificacionservicio.enviar("Bienvenido a la libreria virutal","Libreria PipoNeverDie", usuario.getMail());
    }

    public void activaralta(Usuario usuario){
        if (usuario.getRol() != null) {
            usuario.setRol(Roles.USER);
            usuario.setAlta(true);
        }
    }
    
    public void validar(Usuario usuario) throws Exception{
        
        if(usuario.getNombre() == null || usuario.getNombre().isEmpty() || usuario.getNombre().equals(" ")){
            throw new Exception("El nombre ingresado es incorrecto");
        }
        
        if (usuario.getApellido() == null || usuario.getApellido().isEmpty() || usuario.getApellido().equals(" ")) {
            throw new Exception("El apellido ingressado es invalido");
        }
        
        if (usuario.getMail() == null || usuario.getMail().isEmpty() || usuario.getMail().contains(" ")) {
            throw new Exception("El mail ingresado es invalido");
        }
        
        if (usuario.getContraseña() == null || usuario.getContraseña().isEmpty() || usuario.getContraseña().contains(" ") || usuario.getContraseña().length()<6) {
            throw new Exception("La contraseña ingresado es invalido");
        }       
        
    }
    @Transactional
    public void modificar(MultipartFile archivo, String id)throws Exception{
        Optional<Usuario> respuesta = usuariorepositorio.findById(id);        
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            validar(usuario);
            
            String idFoto = null;
            if (usuario.getFoto() != null) {
                idFoto = usuario.getFoto().getId();                
            }
            
            Foto foto = fotorepositorio.acutalizar(idFoto, archivo);
            usuario.setFoto(foto);
            
            usuario.setContraseña(new BCryptPasswordEncoder().encode(usuario.getContraseña()));
            
            usuariorepositorio.save(usuario);
        }else{
            throw new Exception("El usuario no ha sido encontrado");
        } 
    }
    @Transactional
    public void altabaja(String id) throws Exception{
        Optional<Usuario> respuesta = usuariorepositorio.findById(id);        
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            if(usuario.getAlta() == false){
                usuario.setAlta(true);
            }else{
                usuario.setAlta(false);
            }            
            validar(usuario);
            usuariorepositorio.save(usuario);
        }else{
            throw new Exception("El usuario no ha sido encontrado");
        } 
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Usuario usuario = usuariorepositorio.buscarPorMail(mail);
        
        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();
//            GrantedAuthority p1 = new SimpleGrantedAuthority("MODULO_FOTO");
//            permisos.add(p1);
//            GrantedAuthority p2 = new SimpleGrantedAuthority("MODULO_LIBROS");
//            permisos.add(p2);
//            GrantedAuthority p3 = new SimpleGrantedAuthority("MODULO_AUTORES");
//            permisos.add(p3);
//            GrantedAuthority p4 = new SimpleGrantedAuthority("MODULO_EDITORIALES");
//            permisos.add(p4);
            GrantedAuthority rolePermissions = new SimpleGrantedAuthority("ROLE_"+usuario.getRol());
            permisos.add(rolePermissions);
            User user = new User(usuario.getMail(), usuario.getContraseña(), permisos);
            return user;
        } else {
            throw new UsernameNotFoundException("El usuario no ha sido encontrado");
        }
        
    }
}
