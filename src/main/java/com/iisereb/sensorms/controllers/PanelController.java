package com.iisereb.sensorms.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/panel")
public class PanelController {

    @GetMapping()
    public String panelPage() {
        return "panel";
    }



}
