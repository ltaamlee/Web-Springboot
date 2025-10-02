package vn.iostar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class AccessDeniedController {

    @GetMapping("/access-denied")
    public String accessDenied(HttpSession session, Model model) {
        Object role = session.getAttribute("role");

        if (role == null) {
            model.addAttribute("message", "Bạn chưa đăng nhập, vui lòng đăng nhập lại!");
            return "web/login";
        }

        model.addAttribute("message", "Bạn không có quyền truy cập chức năng này!");
        return "web/access-denied";

    }
}