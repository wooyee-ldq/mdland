package top.landucheg.mdland.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentUtil {

    @Autowired
    private Environment environment;

    public String getProperties(String key){
        return environment.getProperty(key);
    }

}
