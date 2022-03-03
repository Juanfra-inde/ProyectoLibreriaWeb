
package com.libreria.libreria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificacionServicio {
        
//    private final JavaMailSender mailSender;
//    
//    @Autowired
//    public NotificacionServicio(JavaMailSender mailSender) {
//        this.mailSender = mailSender;
//    }
//    
//    @Async
//    public void enviar(String cuerpo,String titulo,String mail){
//        SimpleMailMessage mensaje = new SimpleMailMessage();
//        mensaje.setTo(mail);
//        mensaje.setFrom("noreplay@libreria-piposneverdie.com");
//        mensaje.setSubject(titulo);
//        mensaje.setText(cuerpo);
//        
//        mailSender.send(mensaje);        
//    }
    
    
    
}
