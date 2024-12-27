package vttp.ssf.mini_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class ApiController {

    @GetMapping
    public String showApiDoc() {
        return "api";
    }
}
