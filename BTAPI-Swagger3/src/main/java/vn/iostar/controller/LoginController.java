package vn.iostar.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
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
            HttpSession session,
            Model model) {

        Optional<User> userOpt = userService.login(username, password);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            session.setAttribute("userId", user.getId());

            switch (user.getRoleid()) {
                case "admin":
                    return "redirect:/admin/dashboard";
                case "staff":
                    return "redirect:/staff/dashboard";
                default:
                    return "redirect:/";
            }
        } else {
            model.addAttribute("error", "Sai tài khoản hoặc mật khẩu!");
            return "web/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
