package top.landucheg.mdland.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EditorController {

    /**
     * 编辑器主页面
     *
     * @return html
     */
    @GetMapping("")
    public String index(){
        return "static/html/mdEditor.html";
    }

}
