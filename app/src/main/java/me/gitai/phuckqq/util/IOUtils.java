package me.gitai.phuckqq.util;

import java.io.File;
import java.io.IOException;

/**
 * Created by dphdjy on 16-3-2.
 */
public class IOUtils {
    /**
     * @return null if IOException
     */
    public static File creatFileIfNotExists(File file) throws IOException {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        return file;
    }
}
