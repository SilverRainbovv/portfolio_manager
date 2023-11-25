package com.didenko.http.controller;

import com.didenko.dto.UserCreateEditDto;
import com.didenko.dto.UserReadDto;
import com.didenko.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("registration")
@RequiredArgsConstructor
@Controller
public class RegistrationController {

    private final UserService userService;

    @GetMapping
    public String registrationPage(){
        return "/registration";
    }

    @PostMapping
    public String register(@ModelAttribute @Validated UserCreateEditDto user, BindingResult bindingResult,
                         RedirectAttributes redirectAttributes){
        UserReadDto createdUser = null;

        if(userService.findByEmail(user.getEmail()).isPresent()) {
            bindingResult.addError(new ObjectError(user.getEmail(), "email has been used already"));
        } else {
            createdUser = userService.create(user);
        }
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/registration";
        } else {
            return "redirect:/user/" + createdUser.getId();
        }
    }
}
