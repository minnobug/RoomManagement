package tools;

import java.io.*;
import java.util.List;
import java.util.Map;

public class FileUtils {

    // Lưu đối tượng List vào file
    public static <T> boolean saveListToFile(List<T> list, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(list);
            return true;
        } catch (IOException e) {
            System.out.println("Error saving list: " + e.getMessage());
            return false;
        }
    }

    // Đọc đối tượng List từ file
    @SuppressWarnings("unchecked")
    public static <T> List<T> readListFromFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) return null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading list: " + e.getMessage());
            return null;
        }
    }

    // Lưu đối tượng Map vào file
    public static <K, V> boolean saveMapToFile(Map<K, V> map, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(map);
            return true;
        } catch (IOException e) {
            System.out.println("Error saving map: " + e.getMessage());
            return false;
        }
    }

    // Đọc đối tượng Map từ file
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> readMapFromFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) return null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Map<K, V>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading map: " + e.getMessage());
            return null;
        }
    }
}
