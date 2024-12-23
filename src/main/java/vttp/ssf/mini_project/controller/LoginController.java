package vttp.ssf.mini_project.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vttp.ssf.mini_project.model.LoginUser;
import vttp.ssf.mini_project.model.User;
import vttp.ssf.mini_project.service.UserService;

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
    @PostMapping("/process-login")
    public String processLogin(@Valid @ModelAttribute("user") LoginUser loginuser,
                               BindingResult bindingResult, Model model) {


        if (bindingResult.hasErrors()) {
            System.out.println(">>> Binding errors: " + bindingResult.getAllErrors());
            model.addAttribute("user", loginuser); // Retain the user input
            return "login"; // Reload the login page
        }

       /* System.out.println(">>> No binding errors, proceeding with authentication...");
        System.out.println(">>> Raw password from form: " + loginuser.getPassword());
        System.out.println(">>> Email from form: " + loginuser.getEmail());*/
        boolean isAuthenticated = userSvc.authenticate(loginuser.getEmail(), loginuser.getPassword());
       /* System.out.println(">>User email:" + loginuser.getEmail());
        System.out.println(">>User password:" + loginuser.getPassword());
        System.out.println(">>> Authentication result: " + isAuthenticated);
*/
        if (isAuthenticated) {
            model.addAttribute("message", "Login successful!");
            return "home";
        } else {
            model.addAttribute("error", "Invalid email or password!");
            return "login";
        }

    }

}
