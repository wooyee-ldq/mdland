package top.landucheg.mdland.cmd;

public class CommandExec implements ICmd {

    private static final CommandExec INSTANCE = new CommandExec();

    private CmdImpl order;

    private CommandExec(){}

    public static CommandExec getInstance(){
        return CommandExec.INSTANCE;
    }

    public CommandExec setOrder(CmdImpl order){
        this.order = order;
        return this;
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

    public static void main(String[] args) {
        CommandExec cmd = CommandExec.getInstance();
        String ret = cmd.setOrder(new CmdImpl("dir")).exec();
        System.out.println(ret);
    }
}