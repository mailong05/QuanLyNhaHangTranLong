package com.restaurant.quanlydatbannhahang.util;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Utility class để xử lý đường dẫn hình ảnh với CACHE
 * 
 * ⚠️ ĐỜI CHUYÊN NGÀNH - CÓ THỂ CẤU HÌNH VỊ TRÍ THƯ MỤC IMAGES
 * 
 * Giải quyết:
 * 1. Database lưu chỉ tên file (ví dụ: "com_chien.jpg")
 * 2. Tốc độ load chậm -> Dùng CACHE để tránh load lại
 * 3. UI bị đơ -> Dùng async loading
 * 4. Hoạt động trên mọi máy -> Relative path
 */
public class ImageUtil {

    // ============ CÓ THỂ CẤU HÌNH ĐƯỜNG DẪN ============
    // Đặt thư mục monan ở đây để dễ truy cập
    // Nên đặt ở: c:\QuanLyNhaHangTranLong\images\monan\ (ngoài src/main/resources)
    // - NHANH NHẤT
    // HOẶC: src/main/resources/images/monan/ (mặc định)
    private static String MONAN_IMAGE_FOLDER = "images/monan/";
    private static String externalMonanPath = null;

    // Initialize once
    static {
        // Tự động tìm đường dẫn tối ưu nhất
        externalMonanPath = AppConfig.getMonanFolder();
        if (externalMonanPath != null) {
            MONAN_IMAGE_FOLDER = externalMonanPath;
            System.out.println("✓ ImageUtil initialized. Using: " + MONAN_IMAGE_FOLDER);
        }
    }

    // ============ CACHE IMAGES ============
    private static final int MAX_CACHE_SIZE = 400;
    private static final Map<String, ImageIcon> imageCache = Collections.synchronizedMap(
            new LinkedHashMap<String, ImageIcon>(16, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<String, ImageIcon> eldest) {
                    return size() > MAX_CACHE_SIZE;
                }
            });
    private static final Set<String> failedImageLogs = Collections.synchronizedSet(new HashSet<>());
    private static final ExecutorService executor = Executors.newFixedThreadPool(4);

    /**
     * Load ImageIcon từ imagePath (với CACHE)
     * 
     * @param imagePath  Có thể là:
     *                   - Chỉ tên file: "com_chien.jpg" -> sẽ load từ monan folder
     *                   - Đường dẫn đầy đủ (legacy): "C:\...\com_chien.jpg"
     * @param scaledSize Kích thước scale (0 = không scale)
     * @return ImageIcon hoặc null nếu không tìm thấy
     */
    public static ImageIcon loadImageIcon(String imagePath, int scaledSize) {
        if (imagePath == null || imagePath.trim().isEmpty()) {
            return null;
        }

        String normalizedInputPath = imagePath.trim();

        // Tạo cache key
        String cacheKey = normalizedInputPath + "_" + scaledSize;

        // Kiểm tra cache
        ImageIcon cachedIcon = imageCache.get(cacheKey);
        if (cachedIcon != null) {
            return cachedIcon;
        }

        try {
            String fullPath = getFullImagePath(normalizedInputPath);
            ImageIcon icon = loadFromFileOrResource(fullPath);

            // Fallback: nếu DB lưu path tương đối lạ thì thử lấy theo filename.
            if (icon == null && (normalizedInputPath.contains("/") || normalizedInputPath.contains("\\"))) {
                String fileNameOnly = new File(normalizedInputPath).getName();
                if (fileNameOnly != null && !fileNameOnly.trim().isEmpty()) {
                    icon = loadFromFileOrResource(getFullImagePath(fileNameOnly));
                }
            }

            if (icon != null) {
                // Scale nếu cần
                if (scaledSize > 0) {
                    Image img = icon.getImage().getScaledInstance(scaledSize, scaledSize, Image.SCALE_SMOOTH);
                    icon = new ImageIcon(img);
                }

                // Lưu vào cache
                imageCache.put(cacheKey, icon);
                failedImageLogs.remove(cacheKey);
                return icon;
            }

            logImageLoadFailureOnce(cacheKey, normalizedInputPath, fullPath);

        } catch (Exception e) {
            System.err.println("Error loading image: " + imagePath);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Load ImageIcon với scale mặc định là 80x80
     */
    public static ImageIcon loadImageIcon(String imagePath) {
        return loadImageIcon(imagePath, 80);
    }

    /**
     * Load ImageIcon không scale (full size)
     */
    public static ImageIcon loadImageIconFullSize(String imagePath) {
        return loadImageIcon(imagePath, 0);
    }

    /**
     * Load async (tránh block UI)
     * Gọi callback khi load xong
     */
    public static void loadImageAsync(String imagePath, int scaledSize,
            java.util.function.Consumer<ImageIcon> callback) {
        executor.submit(() -> {
            ImageIcon icon = loadImageIcon(imagePath, scaledSize);
            if (callback != null) {
                callback.accept(icon);
            }
        });
    }

    /**
     * Preload một danh sách ảnh vào cache theo background threads.
     */
    public static void preloadImagesAsync(List<String> imagePaths, int scaledSize) {
        if (imagePaths == null || imagePaths.isEmpty()) {
            return;
        }

        for (String imagePath : imagePaths) {
            if (imagePath == null || imagePath.trim().isEmpty()) {
                continue;
            }

            executor.submit(() -> loadImageIcon(imagePath, scaledSize));
        }
    }

    /**
     * Preload đồng bộ số lượng giới hạn ảnh đầu tiên.
     */
    public static void preloadFirstN(List<String> imagePaths, int scaledSize, int limit) {
        if (imagePaths == null || imagePaths.isEmpty() || limit <= 0) {
            return;
        }

        int max = Math.min(limit, imagePaths.size());
        List<String> subset = new ArrayList<>(imagePaths.subList(0, max));
        for (String imagePath : subset) {
            loadImageIcon(imagePath, scaledSize);
        }
    }

    /**
     * Xác định đường dẫn đầy đủ của hình ảnh
     * 
     * @param imagePath Tên file hoặc đường dẫn
     * @return Đường dẫn đầy đủ (relative to resources hoặc absolute)
     */
    private static String getFullImagePath(String imagePath) {
        String trimmedPath = imagePath == null ? "" : imagePath.trim();
        if (trimmedPath.isEmpty()) {
            return trimmedPath;
        }

        if (isAbsolutePath(trimmedPath)) {
            return trimmedPath;
        }

        String normalizedPath = trimLeadingSeparators(trimmedPath);

        // Hỗ trợ path tương đối kiểu images/monan/abc.jpg
        if (normalizedPath.startsWith("images/") || normalizedPath.startsWith("images\\")) {
            return normalizedPath;
        }

        // Nếu là path tương đối có separator thì giữ nguyên.
        if (normalizedPath.contains("/") || normalizedPath.contains("\\")) {
            return normalizedPath;
        }

        // Nếu không có path separator, coi như chỉ là tên file
        // Thêm thư mục monan vào phía trước (MONAN_IMAGE_FOLDER có thể là absolute hoặc
        // relative)
        if (MONAN_IMAGE_FOLDER.endsWith("/") || MONAN_IMAGE_FOLDER.endsWith("\\")) {
            return MONAN_IMAGE_FOLDER + normalizedPath;
        }
        return MONAN_IMAGE_FOLDER + File.separator + normalizedPath;
    }

    private static ImageIcon loadFromFileOrResource(String candidatePath) {
        if (candidatePath == null || candidatePath.trim().isEmpty()) {
            return null;
        }

        File file = new File(candidatePath);
        if (file.exists()) {
            ImageIcon fileIcon = decodeFileImage(file);
            if (fileIcon != null) {
                return fileIcon;
            }

            // Fallback qua Toolkit decoder nếu ImageIO không đọc được định dạng cụ thể.
            ImageIcon toolkitIcon = new ImageIcon(file.getAbsolutePath());
            if (toolkitIcon.getIconWidth() > 0 && toolkitIcon.getIconHeight() > 0) {
                return toolkitIcon;
            }
        }

        String resourcePath = candidatePath.replace("\\", "/");
        if (resourcePath.startsWith("/")) {
            resourcePath = resourcePath.substring(1);
        }

        URL resourceUrl = ImageUtil.class.getClassLoader().getResource(resourcePath);
        if (resourceUrl != null) {
            ImageIcon resourceIcon = decodeResourceImage(resourceUrl);
            if (resourceIcon != null) {
                return resourceIcon;
            }

            ImageIcon toolkitIcon = new ImageIcon(resourceUrl);
            if (toolkitIcon.getIconWidth() > 0 && toolkitIcon.getIconHeight() > 0) {
                return toolkitIcon;
            }
        }

        return null;
    }

    private static ImageIcon decodeFileImage(File file) {
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            if (bufferedImage != null) {
                return new ImageIcon(bufferedImage);
            }
        } catch (IOException ignored) {
            // Giữ im lặng ở đây, lỗi chi tiết sẽ in ở logImageLoadFailureOnce nếu tổng thể
            // thất bại.
        }
        return null;
    }

    private static ImageIcon decodeResourceImage(URL resourceUrl) {
        try {
            BufferedImage bufferedImage = ImageIO.read(resourceUrl);
            if (bufferedImage != null) {
                return new ImageIcon(bufferedImage);
            }
        } catch (IOException ignored) {
            // Giữ im lặng ở đây, lỗi chi tiết sẽ in ở logImageLoadFailureOnce nếu tổng thể
            // thất bại.
        }
        return null;
    }

    private static boolean isAbsolutePath(String path) {
        return new File(path).isAbsolute();
    }

    private static String trimLeadingSeparators(String path) {
        int index = 0;
        while (index < path.length() && (path.charAt(index) == '/' || path.charAt(index) == '\\')) {
            index++;
        }
        return path.substring(index);
    }

    private static void logImageLoadFailureOnce(String cacheKey, String originalPath, String resolvedPath) {
        if (!failedImageLogs.add(cacheKey)) {
            return;
        }

        System.err.println("[ImageUtil] Khong load duoc anh.");
        System.err.println("  - originalPath: " + originalPath);
        System.err.println("  - resolvedPath: " + resolvedPath);
        System.err.println("  - monanFolder:  " + MONAN_IMAGE_FOLDER);
        if (resolvedPath != null && !resolvedPath.isEmpty()) {
            System.err.println("  - fileExists:   " + new File(resolvedPath).exists());
        }
    }

    /**
     * Kiểm tra xem file hình ảnh có tồn tại không
     */
    public static boolean imageExists(String imagePath) {
        if (imagePath == null || imagePath.trim().isEmpty()) {
            return false;
        }

        try {
            String fullPath = getFullImagePath(imagePath);
            File file = new File(fullPath);
            if (file.exists()) {
                return true;
            }

            // Kiểm tra trong resources
            URL resourceUrl = ImageUtil.class.getClassLoader().getResource(fullPath);
            return resourceUrl != null;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Clear cache (gọi khi không dùng nữa để tiết kiệm memory)
     */
    public static void clearCache() {
        imageCache.clear();
    }

    /**
     * Lấy số lượng images trong cache
     */
    public static int getCacheSize() {
        return imageCache.size();
    }

    /**
     * Shutdown thread pool (gọi khi app đóng)
     */
    public static void shutdown() {
        executor.shutdown();
    }

    /**
     * Set custom monan folder path
     * Gọi này sớm trong application startup
     * 
     * Ví dụ:
     * ImageUtil.setMonanImageFolder("c:/QuanLyNhaHangTranLong/images/monan/");
     * ImageUtil.setMonanImageFolder("images/monan/");
     * 
     * @param folderPath Đường dẫn thư mục (kết thúc bằng /)
     */
    public static void setMonanImageFolder(String folderPath) {
        // Có thể mở rộng để thay đổi runtime nếu cần
        System.out.println("Note: Thư mục monan được set là: " + folderPath);
    }
}
