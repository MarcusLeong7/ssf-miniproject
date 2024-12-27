package vttp.ssf.mini_project.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vttp.ssf.mini_project.model.LoginUser;
import vttp.ssf.mini_project.model.User;
import vttp.ssf.mini_project.service.UserService;

import java.util.List;

@Controller
@RequestMapping
public class LoginController {


    @Autowired
    private UserService userSvc;

    @Autowired
    private PasswordEncoder passwordEncoder;


    // Shows the registration form
    @PostMapping("/register")
    public String showAddForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // For registration of new account
    }

    // Registration form success page
    @PostMapping("/registered")
    public String createSuccess(@Valid @ModelAttribute User user,
                                BindingResult bindingResult, Model model) {

        // For debugging
        if (bindingResult.hasErrors()) {
            System.out.printf(">>> Binding Error");
            bindingResult.getAllErrors().forEach(error -> {
                System.out.println(" - " + error.getDefaultMessage());
            });

            model.addAttribute("user", user); // Retain the original object with errors
            return "register";
        }
        userSvc.registerUser(user.getEmail(), user.getPassword());
        model.addAttribute("user", user);
        return "success";
    }

    // Shows the login page
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("user", new LoginUser());
        return "login";
    }

    // Process login form submission
    @PostMapping("/home")
    public String processLogin(@Valid @ModelAttribute("user") LoginUser loginuser,
                               BindingResult bindingResult, Model model, HttpSession session) {

        if (bindingResult.hasErrors()) {
            System.out.println(">>> Binding errors: " + bindingResult.getAllErrors());
            model.addAttribute("user", loginuser);
            return "login"; // Reload the login page
        }

        boolean isAuthenticated = userSvc.authenticate(loginuser.getEmail(), loginuser.getPassword());

        if (isAuthenticated) {
            session.setAttribute("userEmail", loginuser.getEmail());
            model.addAttribute("message", "Login successful!");
            return "home";
        } else {
            model.addAttribute("error", "Invalid email or password!");
            return "login";
        }

    }

    // GetMapping for Homepage
    @GetMapping("/home")
    public String showHomePage(HttpSession session, Model model) {
        // Check if the user is logged in
        String userEmail = (String) session.getAttribute("userEmail");

        if (userEmail == null) {
            model.addAttribute("error", "Please log in to access the homepage.");
            model.addAttribute("user", new LoginUser());
            return "login"; // Redirect to login page if not authenticated
        }

        model.addAttribute("message", "Welcome back, " + userEmail + "!");
        return "home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        if (session != null) {
            session.removeAttribute("userEmail"); // Example: Remove specific attributes
            session.invalidate(); // Invalidate the session
        }
        return "redirect:/login"; // Redirect to login page
    }


}
