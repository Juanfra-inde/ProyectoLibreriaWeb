
package com.libreria.libreria.controlers;

import com.libreria.libreria.entitis.Autor;
import com.libreria.libreria.entitis.Editorial;
import com.libreria.libreria.service.EditorialService;
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
@RequestMapping("/editoriales")
public class EditorialController {
    
    private EditorialService editorialservice;
    
    @Autowired
    public EditorialController(EditorialService editorialservice) {
        this.editorialservice = editorialservice;
    }
    
    @GetMapping
    public String listarLibros(ModelMap model){
        List<Editorial> editoriales = editorialservice.listarEditoriales();
        model.addAttribute("editoriales",editoriales);
        return "/editoriales/lista-editorial.html";
    }
    
      @GetMapping("/formulario")
    public String showForm(ModelMap model) {
        model.addAttribute("editorial", new Editorial());
        return "/editoriales/form";
    }

    @PostMapping("/formulario")
    public String saveAutor(@ModelAttribute Editorial editorial) {
        try {
            editorialservice.guardarEditorial(editorial);
//            return "redirect:/editoriales";
            return "/editoriales";
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            //return "redirect:/autor-formulario";
            return "/form";
        }
    }
    
    
    
    
}
