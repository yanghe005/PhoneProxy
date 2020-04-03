package com.yhp.phoneproxy.utils;

import java.io.Closeable;
import java.io.IOException;

public class FileUtils {
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
