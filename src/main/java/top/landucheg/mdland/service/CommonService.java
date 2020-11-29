package top.landucheg.mdland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.landucheg.mdland.cmd.CmdCommonImpl;

@Service
public class CommonService {

    @Autowired
    CmdCommonImpl cmdCommonImpl;

    public String cp4CpOrder(String od){
        return cmdCommonImpl.setOrder(od).exec();
    }

    public String cp(String sourceFile, String toFile){
        String od = "cp -r " + sourceFile + " " + toFile;
        return cmdCommonImpl.setOrder(od).exec();
    }

}
