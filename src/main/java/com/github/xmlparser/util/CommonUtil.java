package com.github.xmlparser.util;

import java.io.File;

/**
 * @author Ram Alapure
 * @version 1.0
 * @since 17/02/2020
 */
public class CommonUtil {

    private CommonUtil() {
    }

    public static String getFileNamePrefix(File file) {
        return file.getName().substring(0, file.getName().indexOf("."));
    }

}
