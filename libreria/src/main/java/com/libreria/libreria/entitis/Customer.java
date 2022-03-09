package com.libreria.libreria.entitis;

import com.libreria.libreria.enumeraciones.Roles;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Customer {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String name;
    private String lastname;
    private String email;
    private String password;

    private Boolean alta;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate nacimiento;

    @Enumerated(EnumType.STRING)
    private Roles rol;

    @OneToOne
    private Foto foto;

    public Customer() {
    }

    public Customer(String name, String lastname, String email, String password, Boolean alta, LocalDate nacimiento, Roles rol, Foto foto) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.alta = alta;
        this.nacimiento = nacimiento;
        this.rol = rol;
        this.foto = foto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAlta() {
        return alta;
    }

    public void setAlta(Boolean alta) {
        this.alta = alta;
    }

    public LocalDate getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(LocalDate nacimiento) {
        this.nacimiento = nacimiento;
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

}
