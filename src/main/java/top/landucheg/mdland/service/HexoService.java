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

    public String hexoClean(){
        CommandExec cmd = CommandExec.getInstance();
        cmd.setOrder(new CmdImpl(environmentUtil.getProperties("hexo.clean")));
        return cmd.exec();
    }

    public String hexoGenerate(){
        CommandExec cmd = CommandExec.getInstance();
        cmd.setOrder(new CmdImpl(environmentUtil.getProperties("hexo.generate")));
        return cmd.exec();
    }
}
