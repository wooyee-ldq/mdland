package top.landucheg.mdland.cmd;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.landucheg.mdland.util.EnvironmentUtil;

@NoArgsConstructor
@Component
public class CmdCommonImpl implements ICmd {

    @Autowired
    EnvironmentUtil environmentUtil;

    @Autowired
    CmdImpl cmdImpl;

    public String getOrder() {
        return cmdImpl.getOrder();
    }

    public CmdCommonImpl setOrder(String order) {
        cmdImpl.setOrder(order);
        return this;
    }

    public Process exec2returnProcess(String orderKey) {
        return this.setOrder(environmentUtil.getProperties(orderKey)).exec2returnProcess();
    }

    public String exec(String orderKey) {
        return this.setOrder(environmentUtil.getProperties(orderKey)).exec();
    }

    /**
     *
     * @return
     */
    @Override
    public Process exec2returnProcess() {
        return cmdImpl.exec2returnProcess();
    }

    @Override
    public String exec() {
        return cmdImpl.exec();
    }

    @Override
    public boolean isExecSuccess() throws Exception {
        return cmdImpl.isExecSuccess();
    }
}
