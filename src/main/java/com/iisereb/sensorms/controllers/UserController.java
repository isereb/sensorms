package com.iisereb.sensorms.controllers;

import com.iisereb.sensorms.entities.UserEntity;
import com.iisereb.sensorms.repositories.UserRepository;
import com.iisereb.sensorms.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("signin")
    public String signInPage(Model model) {
        return "signin";
    }

    @PostMapping("signin")
    public String signInHandler(
        HttpServletRequest request,
        HttpSession session,
        Model model,
        @RequestParam(value = "email") String email,
        @RequestParam(value = "password") String password,
        @RequestParam(value = "rememberMe", defaultValue = "false") String rememberMe
    ) {
        UserEntity userEntity = new UserEntity(email, password);
        List<String> errors = userService.validate(userEntity);
        if(errors.isEmpty()) {
            if (userService.authenticate(userEntity)) {
                if (session.isNew()) session.invalidate();
                session = request.getSession();
                session.setAttribute("user", userRepository.findByEmail(email));
                return "redirect:/panel";
            }
            errors.add("Incorrect email or password");
        }
        model.addAttribute("email", email);
        model.addAttribute("errors", errors);
        return "/signin";
    }

    @GetMapping("signup")
    public String signUpPage() {
        return "signup";
    }

    @PostMapping("signup")
    public String signUpHandler(
        Model model,
        @RequestParam(value = "firstName") String firstName,
        @RequestParam(value = "lastName") String lastName,
        @RequestParam(value = "email") String email,
        @RequestParam(value = "password") String password,
        @RequestParam(value = "passwordAgain") String passwordAgain,
        @RequestParam(value = "phone") String phone
    ) {
        UserEntity newUser = new UserEntity();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setPhone(phone);

        List<String> errors = userService.validate(newUser);
        if (!password.equals(passwordAgain)) errors.add("Passwords do not match");
        if(errors.isEmpty()) {
            if (userService.register(newUser)) {
                model.addAttribute("email", email);
                return "redirect:/signin";
            }
        }

        model.addAttribute("user", newUser);
        model.addAttribute("errors", errors);
        return "/signup";
    }



}
