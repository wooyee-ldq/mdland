package top.landucheg.mdland.cmd;

public interface ICmd {

    Process exec2returnProcess();

    String exec();

    boolean isExecSuccess() throws Exception;

}
