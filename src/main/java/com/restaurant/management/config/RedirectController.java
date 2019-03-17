package com.restaurant.management.config;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/")
public class RedirectController {

    @GetMapping(value = "/")
    public ModelAndView redirectSwagger() {
         return new ModelAndView("redirect:/swagger-ui.html");
    }
}
