package com.libreria.libreria.controlers;

import com.libreria.libreria.entitis.Customer;
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
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/clientes")

public class CustomersController {

    private final UsuarioServicio us;

    @Autowired
    public CustomersController(UsuarioServicio us) {
        this.us = us;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String listaUsuarios(ModelMap model) {
        List<Customer> customers = us.buscarUsuarios();
        model.addAttribute("customers", customers);
        return "/customers/lista.html";
    }

    @GetMapping("/registro")
    public String registro(ModelMap model, @RequestParam(required = false) String error,@RequestParam(required = false) String id) {
        
        try {
            if(id==null){
                model.addAttribute("customer", new Customer());
                return "/customers/customer-form.html";
            }else{
                Customer customer = us.buscarPorId(id);
                model.addAttribute("customer", customer);
                return "/customers/customer-form.html";
            }
            
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/customers/customer-form.html";
        }
    }

    @PostMapping("/registro")
    public String restriar(MultipartFile archivo, @ModelAttribute Customer customer, ModelMap model) {
        
        try {
            us.registrar(archivo, customer);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.put("customer", customer);
            return "/customers/customer-form.html";
        }
    }

    @GetMapping("/altabaja/{id}")
    public String altabaja(@PathVariable String id) {
        try {
            us.altabaja(id);
            return "redirect:/clientes";
        } catch (Exception e) {
            return "redirect:/cientes";
        }
    }

}
