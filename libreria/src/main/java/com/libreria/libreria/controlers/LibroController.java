
package com.libreria.libreria.controlers;

import com.libreria.libreria.entitis.Libro;
import com.libreria.libreria.service.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @GetMapping("/formulario")
    public String mostrarFormulario(ModelMap model,@RequestParam(required = false) String id){
        
        try {
//            if(id != null){
//                Libro libro = libroservicio.buscarPorId(id);
//                model.addAttribute("libro",libro);
//                return "libros/form";
//            }else{
//                model.addAttribute("libro", new Libro());
//                return "libros/form";
//            }
            return "libros/form";
        } catch (Exception e) {
            model.put("Error",e.getMessage());
            return "libros/form";
        }
    }
    
    @PostMapping("/formulario")
    public String saveLibro(@RequestParam Libro libro, ModelMap model){
        try {
            libroservicio.guardar(libro);
            return "redirect:libros/form";
        } catch (Exception e) {
            model.put("Error", e.getMessage());
            model.addAttribute("libro", libro);
            return "libros/form";
        }        
    }
    
    @GetMapping("/alta/{id}")
    public String altabaja(@PathVariable("id") String id) {
        try {
            libroservicio.Alta(id);
            return "redirect:/libros";
        } catch (Exception e) {
            return "redirect:/libros";

        }

    }
    
    
}
