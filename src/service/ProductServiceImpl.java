package service;

import model.Product;
import util.Singleton;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class ProductServiceImpl implements ProductService{
    private final Scanner scanner;
    public ProductServiceImpl(){
        scanner = Singleton.getScanner();
    }
    private final String FILEPATH = "src/transaction/transaction.bak";
    private final String TEMP = "src/transaction/temp.bak";
    @Override
    public void random(Long count) {
        long startTime = System.currentTimeMillis(); // Record start time
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILEPATH, true))) {
            for (int i = 0; i < count; i++) {
                String productCode = String.format("CSTAD-%08d", (i+1));
                String productName = "Product::" + (i+1);
                int quantity = 1000;
                double unitPrice = 99.9;
                LocalDate importedDate = LocalDate.now();
                String productLine = productCode + "," + productName + "," + quantity + "," + unitPrice + "," + importedDate;
                writer.write(productLine);
                writer.newLine(); // Add a newline after each product
            }
            System.out.println("Products written to file successfully!");
        } catch (IOException e) {
            System.err.println("Error writing products to file: " + e.getMessage());
        }
        long endTime = System.currentTimeMillis();
        double duration = (endTime - startTime) / 1000.0;
        System.out.println("Time taken for writing: " + duration + " seconds");
    }
    @Override
    public List<Product> display() {
        long startTime = System.currentTimeMillis();
        List<Product> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILEPATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Product product = parseProduct(line);
                if (product != null) {
                    products.add(product);
                }
            }
        } catch (IOException e) {
            System.err.println("Products unavailable in stock!");
        }
        long endTime = System.currentTimeMillis(); // Record end time
        double duration = (endTime - startTime) / 1000.0; // Convert to seconds
        System.out.println("Time taken for reading: " + duration + " seconds");
        return products;
    }
    private static Product parseProduct(String line) {
        String[] parts = line.split(",");
        if (parts.length == 5) {
            String productCode = parts[0].trim();
            String productName = parts[1].trim();
            int quantity = Integer.parseInt(parts[2].trim());
            double unitPrice = Double.parseDouble(parts[3].trim());
            LocalDate importedDate = LocalDate.parse(parts[4].trim());
            return new Product(productCode, productName, quantity, unitPrice, importedDate);
        }
        return null;
    }

    private static String serializeProduct(Product product) {
        return product.getCode() + "," +
                product.getName() + "," +
                product.getQuantity() + "," +
                product.getUnitPrice() + "," +
                product.getImportedDate();
    }
    private static Product deserializeProduct(String line) {
        String[] parts = line.split(",");
        if (parts.length == 5) {
            String code = parts[0];
            String name = parts[1];
            int quantity = Integer.parseInt(parts[2]);
            double unitPrice = Double.parseDouble(parts[3]);
            LocalDate importedDate = LocalDate.parse(parts[4]);
            return new Product(code, name, quantity, unitPrice, importedDate);
        }
        return null;
    }
    @Override
    public Product read(String code) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILEPATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Product product = deserializeProduct(line);
                if (product != null && product.getCode().equals(code)) {
                    return product;
                }
            }
        } catch (IOException e) {
            System.err.println("Product unavailable in stock!");
        }
        return null; // Product not found
    }
    @Override
    public Product write(Product product, String confirm) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILEPATH, true))) {
            if (confirm.equalsIgnoreCase("Y") || confirm.equalsIgnoreCase("YES")) {
                String productLine = serializeProduct(product);
                writer.write(productLine);
                writer.newLine();
                System.out.println("Product written to file successfully.");
            } else {
                System.out.println("You've canceled creating a new product!");
            }
        } catch (IOException e) {
            System.err.println("Error writing product to file: " + e.getMessage());
        }
        return product;
    }
    @Override
    public void update(String productCode, boolean updateName, boolean updatePrice, boolean updateQuantity) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILEPATH));
             BufferedWriter writer = new BufferedWriter(new FileWriter(TEMP))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 1 && parts[0].equals(productCode)) {
                    if (updateName) {
                        System.out.print("Enter new name: ");
                        parts[1] = new Scanner(System.in).nextLine();
                    }
                    if (updatePrice) {
                        System.out.print("Enter new price: ");
                        parts[3] = String.valueOf(new Scanner(System.in).nextDouble());
                    }
                    if (updateQuantity) {
                        System.out.print("Enter new quantity: ");
                        parts[2] = String.valueOf(new Scanner(System.in).nextInt());
                    }
                }
                writer.write(String.join(",", parts));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error updating product: " + e.getMessage());
            return; // Exit method on error
        }

        File originalFile = new File(FILEPATH);
        File tempFile = new File(TEMP);

        // Delete the original file
        if (!originalFile.delete()) {
            System.err.println("Failed to delete the original file.");
            return; // Exit method
        }

        // Rename the temporary file to the original file name
        if (!tempFile.renameTo(originalFile)) {
            System.err.println("Failed to rename the temporary file.");
            return; // Exit method
        }

        System.out.println("Product with code " + productCode + " updated successfully.");
    }

    @Override
    public void delete(String code) {
        File inputFile = new File(FILEPATH);
        File tempFile = new File(TEMP);
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 1 && !parts[0].equals(code)) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading or writing file: " + e.getMessage());
            return;
        }
        if (!inputFile.delete()) {
            System.err.println("Failed to delete the original file.");
            return;
        }
        if (!tempFile.renameTo(inputFile)) {
            System.err.println("Failed to rename the temporary file.");
            return;
        }
        System.out.println("Product with code " + code + " deleted successfully.");
    }

    @Override
    public List<Product> search(String name) {
        List<Product> foundProducts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILEPATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Product product = deserializeProduct(line);
                if (product != null && product.getName().contains(name)) {
                    foundProducts.add(product);
                }
            }
        } catch (IOException e) {
            System.err.println("Product unavailable in stock!");
        }
        return foundProducts;
    }

    @Override
    public Integer setRecord(int record) {
        return null;
    }

    @Override
    public List<Product> backup() {
        return null;
    }

    @Override
    public List<Product> restore() {
        return null;
    }

}

