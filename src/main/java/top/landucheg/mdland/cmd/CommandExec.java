package top.landucheg.mdland.cmd;

public class CommandExec implements ICmd {

    private static final CommandExec INSTANCE = new CommandExec();

    private CmdImpl order;

    private CommandExec(){}

    public CommandExec getInstance(){
        return CommandExec.INSTANCE;
    }

    public void setOrder(CmdImpl order){
        this.order = order;
    }

    @Override
    public Process exec2returnProcess() {
        return order.exec2returnProcess();
    }

    @Override
    public String exec() {
        return order.exec();
    }

    @Override
    public boolean isExecSuccess() throws Exception {
        return order.isExecSuccess();
    }
}
