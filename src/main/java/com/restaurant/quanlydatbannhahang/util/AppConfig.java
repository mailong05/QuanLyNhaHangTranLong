package com.restaurant.quanlydatbannhahang.util;

import java.io.File;

public class AppConfig {

    public static void initializeImagePaths() {
        String projectRoot = getProjectRoot();

        
        File externalFolder = new File(projectRoot + File.separator + "images" + File.separator + "monan");
        if (externalFolder.exists()) {
            System.out.println("✓ Found images folder at: " + externalFolder.getAbsolutePath());
            return;
        }

        
        File resourcesFolder = new File(projectRoot + File.separator + "src" + File.separator + "main"
                + File.separator + "resources" + File.separator + "images" + File.separator + "monan");
        if (resourcesFolder.exists()) {
            System.out.println("✓ Found images folder at: " + resourcesFolder.getAbsolutePath());
            return;
        }

        
        File targetFolder = new File(projectRoot + File.separator + "target" + File.separator + "classes"
                + File.separator + "images" + File.separator + "monan");
        if (targetFolder.exists()) {
            System.out.println("✓ Found images folder at: " + targetFolder.getAbsolutePath());
            return;
        }

        System.err.println("⚠ WARNING: Không tìm thấy thư mục images/monan!");
        System.err.println("  Hãy tạo thư mục: " + externalFolder.getAbsolutePath());
    }

    


    public static String getProjectRoot() {
        String userDir = System.getProperty("user.dir");
        return userDir;
    }

    public static String getMonanFolder() {
        String projectRoot = getProjectRoot();
  
        String[] paths = {
                projectRoot + File.separator + "images" + File.separator + "monan",
                projectRoot + File.separator + "src" + File.separator + "main" + File.separator + "resources"
                        + File.separator + "images" + File.separator + "monan",
                projectRoot + File.separator + "target" + File.separator + "classes" + File.separator + "images"
                        + File.separator + "monan"
        };

        for (String path : paths) {
            File folder = new File(path);
            if (folder.exists() && folder.isDirectory()) {
                return folder.getAbsolutePath() + File.separator;
            }
        }

        return null;
    }


    public static boolean isRunningFromJar() {
        String classPath = AppConfig.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        return classPath.endsWith(".jar");
    }
}
