package init;

import controller.ProductController;
import service.ProductService;
import util.Singleton;
import view.ProductView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class MainApplication {
    private final ProductView productView;
    private final ProductController productController;
    private final ProductService productService;
    public MainApplication(){
        productController = Singleton.getProductController();
        productView = Singleton.getProductView();
        productService = Singleton.getProductService();
    }
    public void initialize(){
        Scanner scanner = new Scanner(System.in);
        do {
            productView.menu();
            System.out.print("Please choose option: ");
            String option = String.valueOf(scanner.nextLine());
            switch (option.toLowerCase()){
                case "l" -> {
                    productController.display();
                }
                case "m" -> {
                    productController.random();
                }
                case "w" -> {
                    productController.write();
                }
                case "r" -> {
                    productController.read();
                }
                case "u" -> {
                    productController.update();
                }
                case "d" -> {
                    productController.delete();
                }
                case "s" -> {
                    productController.search();
                }
                case "c" -> {
                    productController.commit();
                }
                case "b" -> {
                    productController.backup();
                }
                case "t" -> {
                    productController.restore();
                }
                case "h" -> {
                    productController.help();
                }
                case "x" -> {
                    productController.exit();
                }
                default -> {
                    System.out.println("Please choose option above!");
                }
            }
        }while (true);
    }
    public static void main(String[] args) {
        new MainApplication().initialize();
    }
}
