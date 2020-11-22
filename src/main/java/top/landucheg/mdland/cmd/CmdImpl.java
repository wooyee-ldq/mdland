package top.landucheg.mdland.cmd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CmdImpl implements ICmd {

    private String order;

    private Process pro;

    public CmdImpl() {
    }

    public CmdImpl(String order) {
        this.order = order;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
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
