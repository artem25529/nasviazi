package com.es.nasviazi.validator;

import com.es.nasviazi.model.User;
import com.es.nasviazi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class UserValidator implements Validator {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        Optional<User> userOptional = userRepository.findById(user.getUsername());

        if (userOptional.isPresent()) {
            errors.rejectValue("username", "", "Пользователь уже существует");
        }
    }
}
