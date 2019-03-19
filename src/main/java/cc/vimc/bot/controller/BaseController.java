package cc.vimc.bot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class BaseController {

    @GetMapping("/")
    public String main(Model model){
        model.addAttribute("messages","test");
        return "pages/layout/fixed";
    }
}
