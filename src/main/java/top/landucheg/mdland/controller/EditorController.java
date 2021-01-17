package top.landucheg.mdland.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EditorController {

    /**
     * 编辑器主页面
     *
     * @return html page
     */
    @GetMapping("/editor")
    public String index(){
        return "static/html/mdEditor.html";
    }

    /**
     * GET请求返回登录页面
     *
     * @return html page
     */
    @GetMapping("/login")
    public String login(){
        return "static/html/login.html";
    }

}
