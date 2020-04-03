package com.yhp.phoneproxy.util;

import java.io.Closeable;
import java.io.IOException;

public class FileUtil {
    public static void closeQuietly(Closeable input) {
        try {
            if (input != null) {
                input.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }
}
