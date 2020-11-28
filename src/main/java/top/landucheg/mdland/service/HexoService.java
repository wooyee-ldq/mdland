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

    public String hexoNew() throws Exception {
        return this.hexoExec("hexo.new");
    }

    public String hexoRelease() throws Exception {
        StringBuilder strbd = new StringBuilder();
        strbd.append(this.hexoClean()).append(System.lineSeparator()).append(this.hexoGenerate());
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
        cmd.setOrder(new CmdImpl(environmentUtil.getProperties(orderKey)));
        String result =  cmd.exec();
        if(!cmd.isExecSuccess()){
            throw new Exception(orderKey + " running error!");
        }
        return result;
    }
}
