package com.pbo.enotes.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class HomeController {

    @GetMapping("/")
    public String Home() {
        return "index";
    }
    
    
}
