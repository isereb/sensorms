package com.iisereb.sensorms.services;

import com.iisereb.sensorms.entities.UserEntity;
import com.iisereb.sensorms.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository users;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository users, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    public List<String> validate(UserEntity user) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<UserEntity>> emailErrors = validator.validate(user);
        List<String> errors = new ArrayList<>();
        emailErrors.forEach(err -> {
            errors.add(err.getMessage());
        });
        return errors;
    }

    public boolean authenticate(UserEntity user) {
        Optional<UserEntity> potential = users.findByEmail(user.getEmail());
        if (potential.isPresent()) {
            UserEntity aUserInDatabase = potential.get();
            return passwordEncoder.matches(user.getPassword(), aUserInDatabase.getPassword());
        }
        return true;
    }

    public boolean register(UserEntity user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            users.save(user);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public String hashPassword(String passwordToBeHashed) {
        return passwordEncoder.encode(passwordToBeHashed);
    }

}
