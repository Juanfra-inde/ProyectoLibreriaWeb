
package com.libreria.libreria.controlers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class MainController {
    
//    private UsuarioServicio us;
//    
//    @Autowired
//    public MainController(UsuarioServicio us) {
//        this.us = us;
//    }
    
    @GetMapping
    public String start(){
        return "index.html";
    }   

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap model){        
        if (error != null) {
            model.put("error", "Nombre de usuario o Calve incorrecto.");            
        }        
        return "/login.html";
    }
          
   
        
    
    
}
