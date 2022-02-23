package com.libreria.libreria.controlers;

import com.libreria.libreria.entitis.Autor;
import com.libreria.libreria.service.AutorService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String showForm(ModelMap model,@RequestParam(required = false) String id) throws Exception {
        try {
            
            if(id != null){
                Optional autor = autorservicio.buscarAutor(id);
                model.addAttribute("autor",autor.get());
                return "/autores/autor-formulario";
            }else{
                model.addAttribute("autor", new Autor());
                return "/autores/autor-formulario";
            }
        } catch (Exception e) {
            model.put("Error", e.getMessage());
            return "/autores/autor-formulario";
        }
        
    }

    @PostMapping("/formulario")
    public String saveAutor(ModelMap model,@ModelAttribute Autor autor) {
        try {
            autorservicio.guardarAutor(autor);
            return "redirect:/autores";
        } catch (Exception e) {
            model.put("Error",e.getMessage());
            model.addAttribute("autor", autor);
            return "/autores/autor-formulario";
        }
    }
    
//    @GetMapping
//    public String baja(@RequestParam String id){
//        try {
//            Autor autor = autorservicio.buscarAutor2(id); 
//            autor.setAlta(false);
//            autorservicio.guardarAutor(autor);
//            return "/autores/lista-autores";
//        } catch (Exception e) {            
//            return "/autores/lista-autores";
//            
//        }
//        
//    }
//    
//    @GetMapping
//    public String alta(@RequestParam String id){
//        try {
//            Autor autor = autorservicio.buscarAutor2(id); 
//            autor.setAlta(true);
//            autorservicio.guardarAutor(autor);
//            return "/autores/lista-autores";
//        } catch (Exception e) {            
//            return "/autores/lista-autores";            
//        }        
//    }
    
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
