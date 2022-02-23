
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
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @GetMapping("/form")
    public String showForm(ModelMap model, @RequestParam (required = false) String id) {
        try {            
            if(id != null){
                Editorial editorial = editorialservice.buscarEditorial(id);
                model.addAttribute("editorial",editorial);
                return "/editoriales/form.html";
            }else{
                model.addAttribute("editorial", new Editorial());
                return "/editoriales/form.html";
            }            
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "/editoriales/form.html";
        }
    }

    @PostMapping("/form")
    public String saveAutor(ModelMap model,@ModelAttribute Editorial editorial) {
        try {
            editorialservice.guardarEditorial(editorial);
            return "redirect:/editoriales.html";
        } catch (Exception e) {
            model.put("error",e.getMessage());
            model.addAttribute("editoriales",editorial);
            return "/editoriales/from.html";
        }
    }
    
    @GetMapping("/alta/{id}")
    public String altabaja(@PathVariable("id") String id){
        try {               
            
            Editorial editorial = editorialservice.buscarEditorial(id); 
            if(editorial.getAlta() == true){
                editorial.setAlta(false);
            }else if(editorial.getAlta() == null || editorial.getAlta() == false){
                editorial.setAlta(true);
            }                  
            editorialservice.guardarEditorial(editorial);
            return "redirect:";
        } catch (Exception e) {            
            return "/editoriales/lista-editorial";
            
        }
        
    }
    
    
    
    
}
