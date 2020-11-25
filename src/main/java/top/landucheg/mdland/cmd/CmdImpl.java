package top.landucheg.mdland.cmd;

import org.springframework.beans.factory.annotation.Autowired;
import top.landucheg.mdland.util.EnvironmentUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CmdImpl implements ICmd {

    private String[] order;

    private Process pro;

    @Autowired
    EnvironmentUtil environmentUtil;

    {
        order = new String[3];
        String system = System.getProperties().getProperty("os.name").toLowerCase();
        if(system != null && system.contains("windows")){
            order[0] = "cmd";
            order[1] = "/c";
        }else if(system != null && system.contains("linux")){
            order[0] = "/bin/bash";
            order[1] = "-c";
        }else{
            throw new RuntimeException("Get the System Environment Is Error!");
        }
    }

    public CmdImpl() {
    }

    public CmdImpl(String order) {
        setOrderVal(order);
    }

    private void setOrderVal(String od){
        order[2] = environmentUtil.getProperties("exec.cdinitpath") + " " + od;
    }

    public String getOrder() {
        return order[2];
    }

    public CmdImpl setOrder(String order) {
        this.setOrderVal(order);
        return this;
    }

    /**
     *
     * @return
     */
    @Override
    public Process exec2returnProcess() {
        try {
            this.pro = Runtime.getRuntime().exec(order);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.pro;
    }

    @Override
    public String exec() {
        String consoleLog = "";
        try {
            this.pro = Runtime.getRuntime().exec(order);
            String line;
            StringBuilder strbr = new StringBuilder();
            BufferedReader buf = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            while ((line = buf.readLine()) != null){
                strbr.append(line).append(System.lineSeparator());
            }
            consoleLog = strbr.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return consoleLog;
    }

    @Override
    public boolean isExecSuccess() throws Exception {
        if(null != this.pro){
            if(this.pro.exitValue() == 0){
                return true;
            }else{
                return false;
            }
        }else{
            throw new Exception("The commond is running!");
        }
    }
}
