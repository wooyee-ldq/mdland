package top.landucheg.mdland.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EditorController {

    @GetMapping("/mdland")
    public String index(){
        return "static/html/mdEditor.html";
    }
}
