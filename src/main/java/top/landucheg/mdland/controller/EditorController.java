package top.landucheg.mdland.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import top.landucheg.mdland.service.HexoService;

@Controller
public class EditorController {

    @Autowired
    HexoService hexoService;

    /**
     * 编辑器主页面
     *
     * @return html
     */
    @GetMapping("")
    public String index(){
        return "static/html/mdEditor.html";
    }

    /**
     * 创建新的文章
     *
     * @return json
     */
    @PostMapping("/newpost")
    public String newPost(){
        return hexoService.hexoNew();
    }

    /**
     * 发布文章到博客
     *
     * @return json
     */
    @PostMapping("/release")
    public String release(){
        return hexoService.hexoRelease();
    }
}
