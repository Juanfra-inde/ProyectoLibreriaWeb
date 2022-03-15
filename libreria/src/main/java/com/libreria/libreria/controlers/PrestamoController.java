
package com.libreria.libreria.controlers;

import com.libreria.libreria.entitis.Prestamo;
import com.libreria.libreria.service.LibroServicio;
import com.libreria.libreria.service.PrestamoServicio;
import com.libreria.libreria.service.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/prestamos")
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
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
        
        try{
            if (id != null) {                
                model.addAttribute("prestamo", ps.buscarPorId(id));
                model.addAttribute("customers", us.buscarUsuarios());
                model.addAttribute("libros", ls.listarLibros());
                return "/prestamos/form.html";
            }else{
                model.addAttribute("prestamo",new Prestamo());
                model.addAttribute("customers", us.buscarUsuarios());
                model.addAttribute("libros", ls.listarLibros());
                return "/prestamos/form.html";
            }
           
        }catch(Exception e){
            model.addAttribute("error",e.getMessage());
            model.addAttribute("customers", us.buscarUsuarios());
            model.addAttribute("libros", ls.listarLibros());
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
            model.addAttribute("prestamo", prestamo);
            return "/prestamos/form.html";
        }
        
    }
    
    @GetMapping("/altabaja/{id}")
    public String altabaja(@PathVariable("id") String id){
        try {
            ps.cambiaralta(id);
            return "redirect:/prestamos";
        } catch (Exception e) {            
            return "redirect:/prestamos";
        }
    }
    
}
