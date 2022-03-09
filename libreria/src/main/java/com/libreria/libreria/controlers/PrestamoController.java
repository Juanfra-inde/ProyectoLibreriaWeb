
package com.libreria.libreria.controlers;

import com.libreria.libreria.service.PrestamoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/prestamos")
public class PrestamoController {
    
    private final PrestamoServicio ps;
    
    @Autowired
    public PrestamoController(PrestamoServicio ps) {
        this.ps = ps;
    }
    
    @GetMapping
    public String listarPrestamos(){
        try {
            return "/";
        } catch (Exception e) {
            return "/";
        }
    }
    
}
