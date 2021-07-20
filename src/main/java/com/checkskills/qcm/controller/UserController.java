package com.checkskills.qcm.controller;

import com.checkskills.qcm.model.Mail;
import com.checkskills.qcm.model.Qcm;
import com.checkskills.qcm.model.User;
import com.checkskills.qcm.repository.UserRepository;
import com.checkskills.qcm.security.service.UserDetailsServiceImpl;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JavaMailSender javaMailSender;

    @GetMapping("/user/current-auth/")
    @PreAuthorize("hasRole('EMPLOYER') or hasRole('AUTHOR') or hasRole('ADMIN')")
    public ResponseEntity getCurrentAuthUser(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName());
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping("/user/picture")
    public ResponseEntity saveQcm(@RequestBody(required = false) byte[] picByte, Authentication authentication){
        User user = userRepository.findByUsername(authentication.getName());
        user.setPicture(picByte);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping("/user/description")
    public ResponseEntity saveDescription(@RequestBody(required = false) String description, Authentication authentication){
        User user = userRepository.findByUsername(authentication.getName());
        if(description != null){
            user.setDescription(description);
        }
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping("user/forgot_password")
    public ResponseEntity processForgotPassword(@RequestBody(required = false) String email) throws IOException, MessagingException {
        String token = RandomString.make(30);
        try{
            userDetailsService.updateResetPasswordToken(token, email);
            sendEmail(email, token);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    void sendEmail(String email, String token) throws MessagingException, IOException {

        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);

        helper.setTo(email);
        helper.setSubject("Jungle Skills : Mot de passe oublié");
        helper.setText("<h3>Veuillez redéfinir un mot de passe en suivant ce lien :</h3></br><a href='https://qcm-tests-front-prod.herokuapp.com/reset-password/"+token+"'> https://qcm-tests-front-prod.herokuapp.com/reset-password/</a>", true);

        javaMailSender.send(msg);

    }

    @PostMapping("user/reset_password")
    public ResponseEntity processResetPassword(@RequestBody Map<String, Object> data) {

        String token = data.get("token").toString();
        String password = data.get("password").toString();

        User user = userDetailsService.getByResetPasswordToken(token);

        if (user != null) {
            userDetailsService.updatePassword(user, password);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur introuvable");
        }


    }

}