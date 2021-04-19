package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class NodeConfig {

    private Properties prop;
    private String nodeConfigPath;

    public NodeConfig(String nodeConfigFileName) {
        this.nodeConfigPath = "node_config/capability/" + nodeConfigFileName + ".properties";
        this.prop = new Properties();
    }

    public String getCapability(String key){
        InputStream input = null;
        try {
            input = new FileInputStream(this.nodeConfigPath);
            prop.load(input);
            return prop.getProperty(key);
        } catch (IOException ex) {
            try {
                throw new Exception("File not found in the path : " + nodeConfigPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }  finally {
            if(input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    try {
                        throw new Exception("File not found in the path : " + nodeConfigPath);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

}
