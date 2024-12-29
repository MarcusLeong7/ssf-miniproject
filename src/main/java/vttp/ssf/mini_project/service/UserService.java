package vttp.ssf.mini_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vttp.ssf.mini_project.model.User;
import vttp.ssf.mini_project.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(String email, String rawPassword) {
        User user = new User();
        user.setEmail(email);
        // Encode password before saving
        user.setPassword(passwordEncoder.encode(rawPassword)); // Hash the password
        userRepo.save(user);
    }

    public boolean authenticate(String email, String rawPassword) {
        System.out.println(">>> authenticate triggered for email: " + email);
        User user = userRepo.findByEmail(email);
        if (user == null) {
            System.out.println("User not found for email: " + email);
            return false;} // User not found
        System.out.println("Raw password: " + rawPassword);
        System.out.println("Stored hashed password: " + user.getPassword());
        // verify password during login
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

    // Random key for health check
    public String checkHealth() {
        String randomKey = userRepo.getRandomKey();
        if (randomKey == null) {
            return "Redis Health Check: Unhealthy (No keys found or Redis is down)";
        }
        return "Redis Health Check: Healthy (Random Key: " + randomKey + ")";
    }
}
