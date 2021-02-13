package top.landucheg.mdland.cmd.git;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;

public class GitExecutor {

    private String repoDirectory = "";

    private Git git;

    private AddCommand addCmd;

    public GitExecutor(){

    }

    public GitExecutor(String repoDirectory) {
        this.repoDirectory = repoDirectory;
    }

    public void setRepoDirectory(String repoDirectory) {
        this.repoDirectory = repoDirectory;
    }

    public void setGit(Git git) {
        this.git = git;
    }

    public String getRepoDirectory() {
        return repoDirectory;
    }

    public Git getGit() throws IOException {
        if(null == git){
            this.git = Git.open(new File(repoDirectory));
        }
        return git;
    }

    public AddCommand getAddCmd() throws IOException {
        if(null == addCmd){
            addCmd = new AddCommand(this.getGit().getRepository());
        }
        return addCmd;
    }

    public void noGC2Close(){
        this.git.close();
    }

    public void GC2Close(){
        if(null != git){
            git.gc();
            git.close();
        }
    }

    public void Gc(){
        if(null != git){
            git.gc();
        }
    }

    public void add(String filePath) throws IOException, GitAPIException {
        this.getAddCmd().addFilepattern(filePath).call();
    }

    public void addAll() throws IOException, GitAPIException {
        this.getAddCmd().setUpdate(true).call();
    }

    public void commit() throws GitAPIException {
        this.commit("commit");
    }

    public void commit(String context) throws GitAPIException {
        git.commit().setMessage(context).call();
    }

    public void push() throws GitAPIException {
        git.push().call();
    }



}
