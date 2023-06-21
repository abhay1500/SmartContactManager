package com.smart.smartcontactmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.smartcontactmanager.dao.UserRepository;
import com.smart.smartcontactmanager.entities.User;
import com.smart.smartcontactmanager.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {
    
    @Autowired
    private UserRepository userRepository;


    @RequestMapping("/")
    public String home(Model model){
        model.addAttribute("title", "Home -Smart Contact Manager");
        return "home";
    }

    @RequestMapping("/about")
    public String about(Model model){
        model.addAttribute("title", "About -Smart Contact Manager");
        return "about";
    }
    @RequestMapping("/signup")
    public String signup(Model model){
        model.addAttribute("title", "Register -Smart Contact Manager");
        model.addAttribute("user", new User());
        return "signup";
    }
    @RequestMapping("/login")
    public String login(Model model){
        model.addAttribute("title", "Login -Smart Contact Manager");
        return "login";
    }

    //Registering user
    @PostMapping("/do_Register")
    public String registerUser(@Valid @ModelAttribute("user") User user,BindingResult result1,@RequestParam(value = "aggrement", defaultValue = "false") boolean aggrement,Model model,HttpSession session){

        try {
            if(!aggrement){
            System.out.println("You have not agreed the terms and conditions.");
            throw new Exception("You have not agreed the terms and conditions.");
          }
        if(result1.hasErrors()){
            System.out.println("ERROR "+result1.toString());
            model.addAttribute("user", user);
            return "signup"; 
        }
        
        user.setRole("ROLE_USER");
        user.setEnabled(true);
        user.setImageUrl("default.png");
        
        System.out.println("Agreement: "+aggrement);
        System.out.println("User: "+user); 

        //database main save kr dega isse
        this.userRepository.save(user);

        model.addAttribute("user", new User());

        session.setAttribute("message",new Message("Signup Successfully!! ", "alert-success"));
        return "signup";
        } 
        catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("message",new Message("Something went wrong !! " +e.getMessage(), " alert-danger"));
            return "signup";
        }

    }


}


