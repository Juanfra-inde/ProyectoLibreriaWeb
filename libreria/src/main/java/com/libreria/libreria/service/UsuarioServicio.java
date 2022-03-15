package com.libreria.libreria.service;

import com.libreria.libreria.entitis.Foto;
import com.libreria.libreria.entitis.Customer;
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
public class UsuarioServicio implements UserDetailsService {

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
    public void registrar(MultipartFile archivo, Customer customer) throws Exception {

        validar(customer);
        activaralta(customer);

        Foto foto = fotorepositorio.guardar(archivo);
        customer.setFoto(foto);

        customer.setPassword(new BCryptPasswordEncoder().encode(customer.getPassword()));
        usuariorepositorio.save(customer);

//        notificacionservicio.enviar("Bienvenido a la libreria virutal","Libreria PipoNeverDie", usuario.getMail());
    }

    public void activaralta(Customer customer) {
        if (customer.getRol() == null) {
            customer.setRol(Roles.USER);
            customer.setAlta(true);
        }
    }

    public void validar(Customer customer) throws Exception {

        if (customer.getName() == null || customer.getName().isEmpty() || customer.getName().equals(" ")) {
            throw new Exception("El nombre ingresado es incorrecto");
        }

        if (customer.getLastname() == null || customer.getLastname().isEmpty() || customer.getLastname().equals(" ")) {
            throw new Exception("El apellido ingressado es invalido");
        }

        if (customer.getEmail() == null || customer.getEmail().isEmpty() || customer.getEmail().contains(" ")) {
            throw new Exception("El mail ingresado es invalido");
        }

        if (customer.getPassword() == null || customer.getPassword().isEmpty() || customer.getPassword().contains(" ") || customer.getPassword().length() < 6) {
            throw new Exception("La contraseña ingresado es invalido");
        }

        if (customer.getEmail().equals(usuariorepositorio.findByEmail(customer.getEmail()))) {
            throw new Exception("El e-mail ingresado ya se encuentra registrado");
        }

    }

    @Transactional
    public void modificar(MultipartFile archivo, String id) throws Exception {
        Optional<Customer> respuesta = usuariorepositorio.findById(id);
        if (respuesta.isPresent()) {
            Customer customer = respuesta.get();
            validar(customer);

            String idFoto = null;
            if (customer.getFoto() != null) {
                idFoto = customer.getFoto().getId();
            }

            Foto foto = fotorepositorio.acutalizar(idFoto, archivo);
            customer.setFoto(foto);

            customer.setPassword(new BCryptPasswordEncoder().encode(customer.getPassword()));

            usuariorepositorio.save(customer);
        } else {
            throw new Exception("El usuario no ha sido encontrado");
        }
    }

    @Transactional
    public Customer buscarPorId(String id) throws Exception {
        return usuariorepositorio.findById(id).orElseThrow(() -> new Exception("Usuario no encontrado"));
    }

    @Transactional
    public List<Customer> buscarUsuarios() {
        return usuariorepositorio.findAll();
    }

    @Transactional
    public void altabaja(String id) throws Exception {
        Optional<Customer> respuesta = usuariorepositorio.findById(id);
        if (respuesta.isPresent()) {
            Customer customer = respuesta.get();
            if (customer.getAlta() == false) {
                customer.setAlta(true);
            } else {
                customer.setAlta(false);
            }
            validar(customer);
            usuariorepositorio.save(customer);
        } else {
            throw new Exception("El usuario no ha sido encontrado");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Customer customer = usuariorepositorio.findByEmail(mail);
        if (customer != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority rolePermissions = new SimpleGrantedAuthority("ROLE_" + customer.getRol());
            permisos.add(rolePermissions);
            return new User(customer.getEmail(), customer.getPassword(), permisos);
        } else {
            throw new UsernameNotFoundException("El usuario no ha sido encontrado");
        }

    }
}
