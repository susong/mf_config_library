package com.mf.config;

import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于对本地文件的读写操作工具类
 */
class FileIOUtils {
    public static final String SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String MF_ROOT = SDCARD + "/mf";
    public static final String MF_CONFIG = MF_ROOT + "/config/";

    /**
     * 读取文件行过滤器
     */
    interface OnLineFilterListener {
        /**
         * 读取文件时行过滤器
         *
         * @param line -- 文件行内容
         * @return 返回false，表示忽略该行内容
         */
        boolean onLineFilter(String line);
    }

    static boolean hasFile(String path) {
        File file = new File(path);
        return file.exists() && file.isFile() && file.length() > 0;
    }

    static void write(String path, String content) {
        try {
            FileWriter fw = new FileWriter(path);
            fw.write(content);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取指定文件
     *
     * @param path
     * @return
     */
    static String readFile(String path) {
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            String line = "";
            while (!TextUtils.isEmpty((line = reader.readLine()))) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    static List<String> read(String filename, OnLineFilterListener l) {
        if (hasFile(filename)) {
            try {
                InputStream stream = new FileInputStream(filename);
                return read(stream, l);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

    static List<String> read(InputStream stream, OnLineFilterListener l) {
        List<String> list = new ArrayList<>();
        BufferedReader reader = null;
        String line = null;
        try {
            reader = new BufferedReader(new InputStreamReader(stream));
            while (null != (line = reader.readLine())) {
                if (null != l && l.onLineFilter(line)) {
                    continue;
                }
                list.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return list;
    }

    /**
     * 创建路径
     *
     * @param path
     */
    static void createDir(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    static void checkFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            File parentFile = file.getParentFile();
            if (parentFile != null && !parentFile.exists()) {
                parentFile.mkdirs();
            }
        }
    }

    static File[] list(String dir) {
        return list(new File(dir));
    }

    static File[] list(File dir) {
        if (dir.exists() && dir.canRead() && dir.isDirectory()) {
            return dir.listFiles();
        }
        return null;
    }
}
