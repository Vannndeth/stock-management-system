package util;

import java.io.File;
import java.io.IOException;

public class CreateFile {
    public static void transactionFile() {
        String directoryPath = "src/transaction";
        String fileName = "transaction.bak";
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            System.err.println("Directory does not exist: " + directory.getAbsolutePath());
            return;
        }
        File file = new File(directory, fileName);
        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getAbsolutePath());
            } else {
                System.out.println("File already exists: " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("Failed to create file: " + file.getAbsolutePath());
            e.printStackTrace();
        }
    }
}
