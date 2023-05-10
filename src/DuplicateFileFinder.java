import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DuplicateFileFinder {

    public static void main(String[] args) {
        String directoryPath = "files";
        Map<String, List<String>> fileMap = new HashMap<>();

        // Рекурсивно обходим файловую систему, начиная с указанной директории
        scanDirectory(new File(directoryPath), fileMap);

        // Печатаем дубликаты файлов
        printDuplicates(fileMap);
    }

    public static void scanDirectory(File directory, Map<String, List<String>> fileMap) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        // Рекурсивно сканируем поддиректории
                        scanDirectory(file, fileMap);
                    } else {
                        // Получаем MD5-хеш файла
                        String md5Hash = getMD5Hash(file);
                        if (md5Hash != null) {
                            List<String> fileList = fileMap.get(md5Hash);
                            if (fileList == null) {
                                fileList = new ArrayList<>();
                                fileMap.put(md5Hash, fileList);
                            }
                            fileList.add(file.getAbsolutePath());
                        }
                    }
                }
            }
        }
    }

    private static String getMD5Hash(File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                md.update(buffer, 0, bytesRead);
            }
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void printDuplicates(Map<String, List<String>> fileMap) {
        for (List<String> fileList : fileMap.values()) {
            if (fileList.size() > 1) {
                System.out.println("Duplicates found:");
                for (String fileName : fileList) {
                    System.out.println(fileName);
                }
                System.out.println();
            }
        }
    }
}