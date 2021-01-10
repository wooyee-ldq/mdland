package top.landucheg.mdland.cmd;

public class CommandExec implements ICmd {

    private static final CommandExec INSTANCE = new CommandExec();

    private final static ThreadLocal<CmdImpl> orders = new ThreadLocal<>();

    private CommandExec(){}

    public static CommandExec getInstance(){
        return CommandExec.INSTANCE;
    }

    public CommandExec setOrder(CmdImpl order){
        orders.set(order);
        return this;
    }

    @Override
    public Process exec2returnProcess() {
        return orders.get().exec2returnProcess();
    }

    @Override
    public String exec() {
        return orders.get().exec();
    }

    @Override
    public boolean isExecSuccess() throws Exception {
        return orders.get().isExecSuccess();
    }

    public static void main(String[] args) {
        CommandExec cmd = CommandExec.getInstance();
        String ret = cmd.setOrder(new CmdImpl("dir")).exec();
        System.out.println(ret);
    }
}