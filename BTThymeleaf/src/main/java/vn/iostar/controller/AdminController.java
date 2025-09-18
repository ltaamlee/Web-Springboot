package vn.iostar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

	@GetMapping("/admin/layout-admin")
    public String adminDashboard() {
        return "admin/layout-admin";
    }
}
