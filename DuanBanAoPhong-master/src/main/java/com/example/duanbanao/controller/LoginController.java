package com.example.duanbanao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("bee-store")
public class LoginController {
    @GetMapping("login")
    public String login(){
        return "view/login";
    }
}
