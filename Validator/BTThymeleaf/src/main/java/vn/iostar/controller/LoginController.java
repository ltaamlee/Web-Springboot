package vn.iostar.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vn.iostar.dto.LoginDTO;
import vn.iostar.entity.User;
import vn.iostar.service.UserService;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginDTO());
        return "web/login";
    }

    @PostMapping("/login")
    public String loginSubmit(
            @Valid @ModelAttribute("loginForm") LoginDTO loginForm,
            BindingResult result,
            HttpSession session,
            Model model) {

        if (result.hasErrors()) {
            return "web/login";
        }

        Optional<User> userOpt = userService.login(loginForm);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            session.setAttribute("userId", user.getId());
            session.setAttribute("role", user.getRoleid().toUpperCase());

            switch (user.getRoleid().toLowerCase()) {
                case "admin":
                    return "redirect:/admin/dashboard";
                case "user":
                    return "redirect:/user/dashboard";
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
