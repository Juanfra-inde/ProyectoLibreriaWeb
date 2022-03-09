package com.libreria.libreria.repositories;

import com.libreria.libreria.entitis.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Customer, String> {

//    @Query("SELECT c FROM Customer c WHERE c.email = :email")
//    public Customer buscarPorMail(@Param("email") String email);
    public Customer findByEmail(String email);
}
