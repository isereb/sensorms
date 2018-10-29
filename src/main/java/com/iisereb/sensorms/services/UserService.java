package com.iisereb.sensorms.services;

import com.iisereb.sensorms.entities.UserEntity;
import com.iisereb.sensorms.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository users;

    @Autowired
    public UserService(UserRepository users) {
        this.users = users;
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
        return users.findByEmailAndPassword(user.getEmail(), user.getPassword()).isPresent();
    }

    public boolean register(UserEntity user) {
        try {
            users.save(user);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

}
