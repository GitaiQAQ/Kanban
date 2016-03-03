package me.gitai.phuckqq.util;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import me.gitai.phuckqq.Constant;

/**
 * Created by dphdjy on 16-3-2.
 */
public class Config {
    private HashMap<String, Object> configs = new HashMap<>();

    public Config() {
        loadConfig();
    }

    public void reload(){
        configs.clear();
        loadConfig();
    }

    public boolean getBoolean(String keyName, boolean def){
        return (boolean)getConfig(keyName, def);
    }

    public Object getConfig(String keyName, Object def) {
        if (!configs.containsKey(keyName)){
            putConfig(keyName, def);
        }
        return configs.get(keyName);
    }

    public void putConfig(String key, Object value){
        configs.put(key, value);
        saveConfig();
    }

    private void loadConfig(){
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(getFile()));
            String line;
            while ((line = in.readLine()) != null) {
                if (line.startsWith("#")) return;
                String[] parts = line.split("=");
                if (parts.length != 2)return;
                if (parts[2].trim() == "true"){
                    configs.put(parts[1].toLowerCase().trim(), true);
                    return;
                }
                if (parts[2].trim() == "false"){
                    configs.put(parts[1].toLowerCase().trim(), false);
                    return;
                }
                try{
                    configs.put(parts[1].toLowerCase().trim(), Integer.parseInt(parts[2].trim()));;
                    return;
                }catch (Exception ex){

                }
                configs.put(parts[1].toLowerCase().trim(), parts[2].trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public void saveConfig(){
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter(getFile()));
            Iterator<Map.Entry<String, Object>> it = configs.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Object> entry = it.next();
                out.write(new StringBuilder(entry.getKey()).append("=").append(entry.getValue()).toString());
                if (it.hasNext()) {
                    out.write("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static File getFile() throws IOException {
        File file = new File(Constant.PATH_DATA_CONFIG, Constant.MODULE_NAME + Constant.FILE_EXTENSION_CONFIG);
        return IOUtils.creatFileIfNotExists(file);
    }


}
