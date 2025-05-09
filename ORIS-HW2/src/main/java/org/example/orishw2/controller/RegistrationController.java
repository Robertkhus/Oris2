package org.example.orishw2.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.orishw2.dto.RegistrationForm;
import org.example.orishw2.service.RegistrationService;
import org.example.orishw2.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequiredArgsConstructor
@RequestMapping("/registration")
public class RegistrationController {

    private final RegistrationService service;

    private final UserService userService;

    @GetMapping
    public String getPage(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "registration";
    }

    @PostMapping
    public String createUser(
            @Valid RegistrationForm registrationForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        service.createUser(registrationForm);

        return "redirect:/confirm";
    }

    @GetMapping("/confirm")
    public String confirm() {
        return "/confirm_site";
    }

    @GetMapping("/confirm/{code}")
    public String confirmEmail(@PathVariable String code) {
        if (userService.confirmUser(code)) {
            return "redirect:/login";
        }
        return "redirect:/registration";
    }


}
