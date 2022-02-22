
package com.libreria.libreria.controlers;

import com.libreria.libreria.entitis.Libro;
import com.libreria.libreria.service.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/libros")
public class LibroController {
    
    private LibroServicio libroservicio;
    
    @Autowired
    public LibroController(LibroServicio libroservicio) {
        this.libroservicio = libroservicio;
    }
    
    @GetMapping
    public String listarLibros(ModelMap model){
        List<Libro> libros = libroservicio.listarLibros();
        model.addAttribute("libros",libros);
        return "/libros/lista-libros";
    }
    
    @GetMapping("/form")
    public String mostrarFormulario(){
        return "libros/form";
    }
    
    @PostMapping("/form")
    public String nombreUsuario(@RequestParam String nombre,@RequestParam String apellido, ModelMap model){
            
        model.addAttribute("nombre", nombre);
        model.addAttribute("apellido", apellido);
        return "libros/saludo";
    }
    
    
}
