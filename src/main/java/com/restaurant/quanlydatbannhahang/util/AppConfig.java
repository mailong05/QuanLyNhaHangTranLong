package com.restaurant.quanlydatbannhahang.util;

import java.io.File;
import java.nio.file.Paths;

/**
 * Application Configuration
 * 
 * Quản lý các cài đặt global của ứng dụng
 * Dễ dàng cấu hình đường dẫn hình ảnh
 */
public class AppConfig {

    /**
     * Tìm thư mục monan theo độ ưu tiên:
     * 1. c:\QuanLyNhaHangTranLong\images\monan\ (ngoài project)
     * 2. src/main/resources/images/monan/ (trong project)
     * 3. target/classes/images/monan/ (compiled resources)
     */
    public static void initializeImagePaths() {
        String projectRoot = getProjectRoot();

        // Độ ưu tiên 1: Thư mục riêng ngoài src (NHANH NHẤT)
        File externalFolder = new File(projectRoot + File.separator + "images" + File.separator + "monan");
        if (externalFolder.exists()) {
            System.out.println("✓ Found images folder at: " + externalFolder.getAbsolutePath());
            return;
        }

        // Độ ưu tiên 2: Inside resources (mặc định)
        File resourcesFolder = new File(projectRoot + File.separator + "src" + File.separator + "main"
                + File.separator + "resources" + File.separator + "images" + File.separator + "monan");
        if (resourcesFolder.exists()) {
            System.out.println("✓ Found images folder at: " + resourcesFolder.getAbsolutePath());
            return;
        }

        // Độ ưu tiên 3: Inside target (compiled)
        File targetFolder = new File(projectRoot + File.separator + "target" + File.separator + "classes"
                + File.separator + "images" + File.separator + "monan");
        if (targetFolder.exists()) {
            System.out.println("✓ Found images folder at: " + targetFolder.getAbsolutePath());
            return;
        }

        System.err.println("⚠ WARNING: Không tìm thấy thư mục images/monan!");
        System.err.println("  Hãy tạo thư mục: " + externalFolder.getAbsolutePath());
    }

    /**
     * Lấy đường dẫn project root
     */
    public static String getProjectRoot() {
        String userDir = System.getProperty("user.dir");
        return userDir;
    }

    /**
     * Lấy đường dẫn đầy đủ của thư mục monan
     * 
     * @return Đường dẫn absolute hoặc null nếu không tìm thấy
     */
    public static String getMonanFolder() {
        String projectRoot = getProjectRoot();

        // Thử thứ tự ưu tiên
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

    /**
     * Kiểm tra xem ứng dụng chạy từ JAR hay từ IDE
     */
    public static boolean isRunningFromJar() {
        String classPath = AppConfig.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        return classPath.endsWith(".jar");
    }
}
