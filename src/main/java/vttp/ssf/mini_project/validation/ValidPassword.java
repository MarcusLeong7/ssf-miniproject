package vttp.ssf.mini_project.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

// Custom validation annotation
@Documented
//Associates the validation logic in PasswordValidator class
@Constraint(validatedBy = PasswordValidator.class)
// Specify that this annotation can be applied in either class field or method
@Target({ElementType.FIELD, ElementType.METHOD})
// Specifies that the annotation is available at runtime
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    // Default message if validation fails
    String message() default "Password must be at least 8 characters long and include uppercase, lowercase, digits, and special characters";
    int minlength() default 8;
    boolean requireSpecialCharacter() default true;  // Require special character
    boolean requireUppercase() default true;  // Require uppercase letter
    boolean requireNumber() default true; // Require number

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
