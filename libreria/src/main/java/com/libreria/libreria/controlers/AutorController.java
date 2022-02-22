package com.libreria.libreria.controlers;

import com.libreria.libreria.entitis.Autor;
import com.libreria.libreria.service.AutorService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/autores")
public class AutorController {

    private AutorService autorservicio;

    @Autowired
    public AutorController(AutorService autorservicio) {
        this.autorservicio = autorservicio;
    }

    @GetMapping
    public String listarAutroes(ModelMap model) {
        List<Autor> autores = autorservicio.listarAutores();
        model.addAttribute("autores", autores);
        return "/autores/lista-autores";
    }

    @GetMapping("/formulario")
    public String showForm(ModelMap model) {
        model.addAttribute("autor", new Autor());
        return "/autores/autor-formulario";
    }

    @PostMapping("/formulario")
    public String saveAutor(@ModelAttribute Autor autor) {
        try {
            autorservicio.guardarAutor(autor);
            return "redirect:/autores";
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            return "/autores/autor-formulario";
        }
    }
    
    
    
    /*
    
    METODO PETICION DE NOMBRE:
    
    @GetMapping("/autoresnombre")
    @ResponseBody
    public String nombresAutores(){
        return "Pipo, Santiago, JR";
    }
    
    @GetMapping("/autornombre")
    @ResponseBody
    public String nombrarAutor(@RequestParam String nombre) {
        return "Hola " + nombre + "!";
    }
    
    
    @GetMapping("/autorpath/{nombre}")
    @ResponseBody
    public String autorPathParameter(@PathVariable String nombre){
        return "Hola " + nombre + "!";
    }
    
    1. Hacer que el nombre llege  a la plantilla saludo
    2. ModelMap nos perimete engolbar objetos
    
    @PostMapping("/form")
    public String hola(@RequestParam String nombre, MoldeMap model){
     String apellido = "asd";
     model.addAttribute("nombre",nombre);
     model.addAttribute("apellido",apellido);
     return "saludo/saludo";
    }
    
    
    
    
    */
    
    
}
