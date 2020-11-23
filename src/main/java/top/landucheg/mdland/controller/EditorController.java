package top.landucheg.mdland.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import top.landucheg.mdland.service.HexoService;

@Controller
public class EditorController {

    @Autowired
    HexoService hexoService;

    @GetMapping("")
    public String index(){
        return "static/html/mdEditor.html";
    }

    @GetMapping("/release")
    public String release(){
        return "static/html/mdEditor.html";
    }
}
