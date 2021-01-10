package top.landucheg.mdland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.landucheg.mdland.util.EnvironmentUtil;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    @Autowired
    EnvironmentUtil environmentUtil;

    public List getFileList() throws Exception {
        String fileDirPath = environmentUtil.getProperties("post.path");
        File fileDir = new File(fileDirPath);
        if(null == fileDir){
            throw new Exception("file path is error, please check the properties file about post.path setting!");
        }
        File[] files = fileDir.listFiles(file -> !file.isDirectory());
        List<String> filesName = new ArrayList<>();
        for(File file : files){
            filesName.add(file.getName());
        }
        return filesName;
    }

    public String getLastFile() throws Exception {
        URL url = this.getClass().getResource("/lsfiln.txt");
        File file = new File(url.getPath());
        if(!file.exists()){
            throw new Exception("lsfiln file is not to be create.");
        }
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader(file));
            String fileName = reader.readLine();
            return fileName;
        } finally {
            if(null != reader){
                try{
                    reader.close();
                } catch (IOException e){
                    throw new IOException("close file error.");
                }
            }
        }
    }


    public String readFile(String fileName) throws Exception {
        File file = getFile(fileName);
        String context = this.read(file);
        saveLastFileName(fileName);
        return context;
    }

    private void saveLastFileName(String fileName) throws Exception {
        URL url = this.getClass().getResource("/lsfiln.txt");
        File file = new File(url.getPath());
        if(!file.exists()){
            throw new Exception("lsfiln file is not to be create.");
        }
        FileWriter writer = null;
        try{
            writer = new FileWriter(file);
            writer.write(fileName);
        } finally {
            if(null != writer){
                try{
                    writer.close();
                } catch (IOException e){
                    throw new IOException("close file error.");
                }
            }
        }
    }

    private File getFile(String fileName) throws Exception {
        String prePath = environmentUtil.getProperties("post.path");
        if(prePath != null && !prePath.endsWith("/")){
            prePath = prePath + File.separator;
        }
        if(fileName.startsWith("/")){
            fileName = fileName.substring(1);
        }
        String filePath = prePath + fileName;
        File file = new File(filePath);
        if(!file.exists()){
            throw new Exception(fileName + " is not found!");
        }
        if(file.isDirectory()){
            throw new Exception(fileName + " is a directory!");
        }
        return file;
    }

    private String read(File file){
        BufferedReader reader = null;
        StringBuilder sbd = new StringBuilder();
        String line;
        try{
            reader = new BufferedReader(new FileReader(file));
            while((line = reader.readLine()) != null){
                sbd.append(line).append(System.lineSeparator());
            }
        } catch (IOException ioe){

        } finally {
            if(null != reader){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sbd.toString();
    }

    public boolean saveFile(String fileName, String fileContext) throws Exception {
        File file = this.getFile(fileName);
        OutputStream writer = null;
        try{
            writer = new FileOutputStream(file);
            writer.write(fileContext.getBytes());
        } catch (IOException ioe){
            return false;
        } finally {
            if(null != writer){
                writer.close();
            }
        }
        return true;
    }
}
