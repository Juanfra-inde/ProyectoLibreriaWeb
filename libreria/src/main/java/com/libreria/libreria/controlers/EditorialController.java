
package com.libreria.libreria.controlers;

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
        return "/editoriales/lista-editorial";
    }
    
    @GetMapping("/form")
    public String showForm(ModelMap model, @RequestParam (required = false) String id) {
        try {            
            if(id != null){
                Editorial editorial = editorialservice.buscarEditorial(id);
                model.addAttribute("editorial",editorial);
                return "/editoriales/form";
            }else{
                model.addAttribute("editorial", new Editorial());
                return "/editoriales/form";
            }            
        } catch (Exception e) {
            model.put("Error", e.getMessage());            
            return "/editoriales/form";
        }
    }

    @PostMapping("/form")
    public String saveAutor(ModelMap model,@ModelAttribute Editorial editorial) {
        try {
            editorialservice.guardarEditorial(editorial);
            return "redirect:/editoriales";
        } catch (Exception e) {
            model.put("Error",e.getMessage());
            model.addAttribute("editorial",editorial);
            return "/editoriales/from";
        }
    }
    
    @GetMapping("/alta/{id}")
    public String altabaja(@PathVariable("id") String id) {
        try {
            editorialservice.Alta(id);
            return "redirect:/editoriales";
        } catch (Exception e) {
            return "redirect:/editorailes";

        }

    }
    
    
    
    
}
