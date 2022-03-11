
package com.libreria.libreria.controlers;

import com.libreria.libreria.entitis.Customer;
import com.libreria.libreria.entitis.Libro;
import com.libreria.libreria.entitis.Prestamo;
import com.libreria.libreria.service.LibroServicio;
import com.libreria.libreria.service.PrestamoServicio;
import com.libreria.libreria.service.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/prestamos")
public class PrestamoController {
    
    private final PrestamoServicio ps;
    private final LibroServicio ls;
    private final UsuarioServicio us;
    
    @Autowired
    public PrestamoController(PrestamoServicio ps, LibroServicio ls, UsuarioServicio us) {
        this.ps = ps;
        this.ls = ls;
        this.us = us;
    }
    
    @GetMapping
    public String listarPrestamos(ModelMap model){
        try {
            List<Prestamo> prestamos = ps.listarPrestamos();
            model.addAttribute("prestamos",prestamos);
            return "/prestamos/prestamos.html";
        } catch (Exception e) {
            return "/prestamos/prestamos.html";
        }
    }
    
    @GetMapping("/registro")
    public String registro(ModelMap model,@RequestParam(required = false) String id){
        List<Libro> libros = ls.listarLibros();
        List<Customer> customers = us.buscarUsuarios();
        try{
            if (id != null) {
                Prestamo prestamo = ps.buscarPorId(id);
                model.addAttribute("prestamo", prestamo);
                model.addAttribute("customers", customers);
                model.addAttribute("libros", libros);
                return "/prestamos/form.html";
            }else{
                model.addAttribute("prestamo",new Prestamo());
                model.addAttribute("customers", customers);
                model.addAttribute("libros", libros);
                return "/prestamos/form.html";
            }
           
        }catch(Exception e){
            model.addAttribute("error",e.getMessage());
            model.addAttribute("customers", customers);
            model.addAttribute("libros", libros);
            return "/prestamos/form.html";
        }
    }
    
    @PostMapping("/registro")
    public String registrar(@ModelAttribute Prestamo prestamo,ModelMap model,
            @RequestParam String customerId,
            @RequestParam String libroId){
        try {            
            ps.guardar(prestamo,customerId,libroId);
            return "redirect:/prestamos";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.put("prestamo", prestamo);
            return "/prestamos/form";
        }
        
    }
    
}
