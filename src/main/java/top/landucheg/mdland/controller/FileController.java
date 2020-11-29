package top.landucheg.mdland.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.landucheg.mdland.model.Result;
import top.landucheg.mdland.service.FileService;
import top.landucheg.mdland.service.HexoService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FileController {

    @Autowired
    HexoService hexoService;

    @Autowired
    FileService fileService;

    /**
     * 获取文章列表
     *
     * @return json
     */
    @GetMapping("/filelist")
    public Result<List<String>> fileList(){
        try {
            List<String> fileList = fileService.getFileList();
            return new Result<>(Result.SUCCESS_CODE, Result.SUCCESS_MSG, fileList);
        } catch (Exception e) {
            List<String> list = new ArrayList<>(2);
            list.add(e.getMessage());
            return new Result<>(Result.ERROR_CODE, Result.ERROR_MSG, list);
        }
    }

    /**
     * 打开文章
     *
     * @param fileName 文件名
     * @return json
     */
    @GetMapping("/readfile")
    public Result<String> readFile(@RequestParam("filename") String fileName){
        try {
            String context = fileService.readFile(fileName);
            return new Result<>(Result.SUCCESS_CODE, Result.SUCCESS_MSG, context);
        } catch (Exception e) {
            return new Result<>(Result.ERROR_CODE, Result.ERROR_MSG, e.getMessage());
        }
    }

    /**
     * 保存文章
     *
     * @param fileName 文件名
     * @param context 文件内容
     * @return json
     */
    @GetMapping("/savefile")
    public Result<String> saveFile(@RequestParam("filename") String fileName, @RequestParam("context") String context){
        try {
            boolean bl = fileService.saveFile(fileName, context);
            if(bl){
                return new Result<>(Result.SUCCESS_CODE, Result.SUCCESS_MSG, bl + "");
            }else{
                return new Result<>(Result.ERROR_CODE, Result.ERROR_MSG, "file io saving error!");
            }
        } catch (Exception e) {
            return new Result<>(Result.ERROR_CODE, Result.ERROR_MSG, e.getMessage());
        }
    }

    /**
     * 创建新的文章
     *
     * @return json
     */
    @GetMapping("/newpost")
    public Result<String> newPost(@RequestParam("filename") String fileName){
        try {
            String ret =  hexoService.hexoNew(fileName);
            return new Result<>(Result.SUCCESS_CODE, Result.SUCCESS_MSG, ret);
        } catch (Exception e) {
            return new Result<>(Result.ERROR_CODE, Result.ERROR_MSG, e.getMessage());
        }
    }

    /**
     * 发布文章到博客
     *
     * @return json
     */
    @GetMapping("/release")
    public Result<String> release(){
        try {
            String ret = hexoService.hexoRelease();
            return new Result<>(Result.SUCCESS_CODE, Result.SUCCESS_MSG, ret);
        } catch (Exception e) {
            return new Result<>(Result.ERROR_CODE, Result.ERROR_MSG, e.getMessage());
        }
    }

}
