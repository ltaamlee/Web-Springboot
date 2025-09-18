package vn.iostar.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.iostar.entity.User;
import vn.iostar.service.UserService;

@Controller
public class LoginController {
	@Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginForm() {
        return "web/login";
    }

    @PostMapping("/login")
    public String loginSubmit(
            @RequestParam String username,
            @RequestParam String password,
            Model model) {

        Optional<User> user = userService.login(username, password);

        if (user.isPresent()) {
            User u = user.get();
            model.addAttribute("user", u);

            switch (u.getRoleid()) {
                case "admin":
                    return "redirect:/admin/layout-admin";
                case "staff":
                    return "redirect:/staff/layout-staff";
                default:
                    return "redirect:/";
            }
        } else {
            model.addAttribute("error", "Sai tài khoản hoặc mật khẩu!");
            return "web/login";
        }
    }
}
