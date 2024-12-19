package vttp.ssf.mini_project.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    // Stores the required values from the annotation in User model
    private int minLength;
    private boolean requireSpecialCharacter;
    private boolean requireUppercase;
    private boolean requireNumber;

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        this.minLength = constraintAnnotation.minlength();
        this.requireSpecialCharacter = constraintAnnotation.requireSpecialCharacter();
        this.requireUppercase = constraintAnnotation.requireUppercase();
        this.requireNumber = constraintAnnotation.requireNumber();
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {

        // Null password is invalid
        if (password == null) {return false;}
        // Check minimum length
        if (password.length() < minLength) {return false;}
        // Check for uppercase letter
        if (requireUppercase && !password.matches(".*[A-Z].*")) {return false;}
        // Check for a special character
        if (requireSpecialCharacter && !password.matches(".*[@$!%*?&].*")) {return false;}
        // Check for a number
        if (requireNumber && !password.matches(".*\\d.*")) {return false;}

        return true; // Password is valid
    }
}
