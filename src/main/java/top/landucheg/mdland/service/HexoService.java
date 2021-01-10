package top.landucheg.mdland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.landucheg.mdland.cmd.CmdImpl;
import top.landucheg.mdland.cmd.CommandExec;
import top.landucheg.mdland.util.EnvironmentUtil;

@Service
public class HexoService {

    @Autowired
    EnvironmentUtil environmentUtil;

    @Autowired
    CmdImpl cmdImpl;

    @Autowired
    CommonService commonService;

    @Autowired
    FileService fileService;

    public String hexoNew(String filename) throws Exception {
        if(filename.trim() == "" || filename == null){
            throw new Exception("error: filename is null.");
        }
        this.hexoExec("hexo.new", filename.trim());
        if(!filename.trim().endsWith(".md")){
            filename = filename + ".md";
        }
        return fileService.readFile(filename);
    }

    public String hexoRelease() throws Exception {
        String sourceFile = environmentUtil.getProperties("blog.path");
        String toFile = environmentUtil.getProperties("www.path");
        StringBuilder strbd = new StringBuilder();
        strbd.append(this.hexoClean())
                .append(System.lineSeparator())
                .append(this.hexoGenerate())
                .append(System.lineSeparator())
                .append(commonService.cp(sourceFile, toFile));
        return strbd.toString();
    }

    public String hexoClean() throws Exception {
        return this.hexoExec("hexo.clean");
    }

    public String hexoGenerate() throws Exception {
        return this.hexoExec("hexo.generate");
    }

    private String hexoExec(String orderKey) throws Exception {
        CommandExec cmd = CommandExec.getInstance();
        cmd.setOrder(cmdImpl.setOrder(environmentUtil.getProperties(orderKey)));
        String result =  cmd.exec();
        if(!cmd.isExecSuccess()){
            throw new Exception(orderKey + " running error!");
        }
        return result;
    }

    private String hexoExec(String orderKey, String param) throws Exception {
        CommandExec cmd = CommandExec.getInstance();
        cmd.setOrder(cmdImpl.setOrder(environmentUtil.getProperties(orderKey) + " " + param));
        String result =  cmd.exec();
        if(!cmd.isExecSuccess()){
            throw new Exception(orderKey + " running error!");
        }
        return result;
    }
}
