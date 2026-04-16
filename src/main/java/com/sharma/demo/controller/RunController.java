package com.sharma.demo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class RunController {
    
    @Value("${class.cr}")
    private String classCR;

    @Value("${class.Name}")
    private String className;

    @GetMapping("/")
    @PreAuthorize("hasRole('USER')")
    public String Home() {
        System.out.println(classCR + " " + className);
        return "RunController [classCR=" + classCR + ", className=" + className + "]";
    }
    
    @GetMapping("/about")
    @PreAuthorize("hasRole('USER')")
    public String About() {
        return "This is about my page";
    }
    
    @GetMapping("/help")
    @PreAuthorize("hasRole('USER')")
    public String Help() {
        return "This is a help page";
    }
    
    @GetMapping("/contact")
    @PreAuthorize("hasRole('USER')")
    public String Contact() {
        return "This is a Contact page";
    }
    
    @GetMapping("/gender")
    @PreAuthorize("hasRole('USER')")
    public String Gender() {
        return "This is a Gender page";
    }

    @GetMapping("/logout-now")
    public void logoutNow(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        new SecurityContextLogoutHandler().logout(request, response, authentication);
        response.sendRedirect("/login?logout");
    }
}