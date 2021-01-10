package top.landucheg.mdland.cmd;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.landucheg.mdland.util.EnvironmentUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@NoArgsConstructor
@Component
public class CmdImpl implements ICmd {

    private String[] preOrder;

    private final static ThreadLocal<String> localOrders = new ThreadLocal<>();

    private Process pro;

    @Autowired
    EnvironmentUtil environmentUtil;

    {
        preOrder = new String[2];
        String system = System.getProperties().getProperty("os.name").toLowerCase();
        if(system != null && system.contains("windows")){
            preOrder[0] = "cmd";
            preOrder[1] = "/c";
        }else if(system != null && system.contains("linux")){
            preOrder[0] = "/bin/bash";
            preOrder[1] = "-c";
        }else{
            throw new RuntimeException("Get the System Environment Is Error!");
        }
    }

    public CmdImpl(String order) {
        setOrderVal(order);
    }

    private void setOrderVal(String od){
        localOrders.set(environmentUtil.getProperties("exec.cdinitpath") + " " + od);
    }

    public String getOrder() {
        return localOrders.get();
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
            this.pro = Runtime.getRuntime().exec(bulidOrders());
            try {
                this.pro.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.pro;
    }

    @Override
    public String exec() {
        String consoleLog = "";
        try {
            this.pro = this.exec2returnProcess();
            String line;
            StringBuilder strbr = new StringBuilder();
            BufferedReader buf = null;
            try{
                buf = new BufferedReader(new InputStreamReader(pro.getInputStream()));
                while ((line = buf.readLine()) != null){
                    strbr.append(line).append(System.lineSeparator());
                }
            } catch (IOException ioe){
                ioe.printStackTrace();
            } finally {
                if(null != buf){
                    buf.close();
                }
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

    /**
     * 构建orders
     *
     * @return String[]
     */
    private String[] bulidOrders(){
        String[] orders = new String[3];
        orders[0] = this.preOrder[0];
        orders[1] = this.preOrder[1];
        orders[2] = this.localOrders.get();
        return orders;
    }
}
