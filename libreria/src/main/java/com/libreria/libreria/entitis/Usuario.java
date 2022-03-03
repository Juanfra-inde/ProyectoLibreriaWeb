
package com.libreria.libreria.entitis;

import com.libreria.libreria.enumeraciones.Roles;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;


@Entity
public class Usuario{
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    private String mail;
    private String contraseña;
    private String nombre;
    private String apellido;
    
    private Boolean alta;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date nacimiento;
    
    @Enumerated(EnumType.STRING)
    private Roles rol;
    
    @OneToOne
    private Foto foto;
    
    public Usuario() {
    }

    public Usuario(String mail, String contraseña, String nombre, String apellido, Boolean alta, Date nacimiento, Roles rol, Foto foto) {
        this.mail = mail;
        this.contraseña = contraseña;
        this.nombre = nombre;
        this.apellido = apellido;
        this.alta = alta;
        this.nacimiento = nacimiento;
        this.rol = rol;
        this.foto = foto;
    }

    public Roles getRol() {
        return rol;
    }

    public void setRol(Roles rol) {
        this.rol = rol;
    }
   
    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Boolean getAlta() {
        return alta;
    }

    public void setAlta(Boolean alta) {
        this.alta = alta;
    }

    public Date getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(Date nacimiento) {
        this.nacimiento = nacimiento;
    }
            
    
    
    
    
    
}
