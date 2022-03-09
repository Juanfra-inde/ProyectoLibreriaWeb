package com.libreria.libreria.controlers;

import com.libreria.libreria.entitis.Autor;
import com.libreria.libreria.entitis.Editorial;
import com.libreria.libreria.entitis.Libro;
import com.libreria.libreria.service.AutorService;
import com.libreria.libreria.service.EditorialService;
import com.libreria.libreria.service.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/libros")
public class LibroController {

    private final LibroServicio libroServicio;
    private final AutorService autorServicio;
    private final EditorialService editorialServicio;

    @Autowired
    public LibroController(LibroServicio libroServicio,
            AutorService autorServicio,
            EditorialService editorialServicio) {
        this.libroServicio = libroServicio;
        this.autorServicio = autorServicio;
        this.editorialServicio = editorialServicio;

    }

    @GetMapping
    public String listarLibros(ModelMap model) {
        List<Libro> libros = libroServicio.listarLibros();
        model.addAttribute("libros", libros);
        return "/libros/lista-libros";
    }

    @GetMapping("/formulario")
    public String mostrarFormulario(ModelMap model, @RequestParam(required = false) String id) {

        List<Autor> autores = autorServicio.listarAutores();
        model.addAttribute("autores", autores);
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        model.addAttribute("editoriales", editoriales);
        try {
            if (id != null) {
                Libro libro = libroServicio.buscarPorId(id);
                model.addAttribute("libro", libro);
                return "libros/form";
            } else {
                model.addAttribute("libro", new Libro());
                return "libros/form";
            }
        } catch (Exception e) {
            model.put("Error", e.getMessage());
            model.put("editorailes",editoriales);
            model.put("auotres",autores);
            return "libros/formulario";
        }
    }

    @PostMapping("/formulario")
    public String saveLibro(@ModelAttribute Libro libro, ModelMap model,
            @RequestParam String autorId,
            @RequestParam String editorialId) {
        try {

            Editorial editorial = editorialServicio.buscarEditorial(editorialId);
            Autor autor = autorServicio.buscarAutor2(autorId);

            libro.setEditorial(editorial);
            libro.setAutor(autor);
            
            libroServicio.guardar(libro);
            return "redirect:/libros";
        } catch (Exception e) {
            model.put("Error", e.getMessage());
            model.put("autores", autorServicio.buscarAutor2(autorId));            
            model.addAttribute("libro", libro);
            return "libros/formulario";
        }
    }

    @GetMapping("/alta/{id}")
    public String altabaja(@PathVariable("id") String id) {
        try {
            libroServicio.Alta(id);
            return "redirect:/libros";
        } catch (Exception e) {
            return "redirect:/libros";

        }

    }


}
