package com.test.bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*******************************************************************************
 *
 * @author : <a href="mailto:borislepeshenkov@gmail.com">Boris Lepeshenkov</a>
 * @since : 16.03.2021
 */
@Controller
public class HomeController {

    @GetMapping(path = "/")
    public String home() {
        return "redirect:/swagger-ui/index.html";
    }

}
