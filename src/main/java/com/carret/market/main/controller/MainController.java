package com.carret.market.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    @GetMapping("/")
    public ModelAndView main(ModelAndView mav) {
        mav.setViewName("main/main");

        return mav;
    }

}
