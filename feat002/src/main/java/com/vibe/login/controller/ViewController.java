package com.vibe.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/login")
    public String loginPage() {
        return "forward:/login.html";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "forward:/register.html";
    }

    @GetMapping("/mypage")
    public String myPage() {
        return "forward:/mypage.html";
    }

    @GetMapping("/page/vip")
    public String vipPage() {
        return "forward:/vip.html";
    }

    @GetMapping("/page/premium")
    public String premiumPage() {
        return "forward:/premium.html";
    }

    @GetMapping("/page/basic")
    public String basicPage() {
        return "forward:/basic.html";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "forward:/admin.html";
    }
}
