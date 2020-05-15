package com.pediy.blog.web.admin;


import com.pediy.blog.po.User;
import com.pediy.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String loginPage(){
        return "admin/login";
    }


    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        User user = userService.checkUser(username,password);
        if(user != null){
            user.setPassword(null);
            session.setAttribute("user",user);
            return "redirect:/admin/listallblog";
        }else{
            attributes.addFlashAttribute("tip","用户名或者密码错误");
            return "redirect:/admin";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.setAttribute("user",null);
        return "redirect:/admin";
    }
}
