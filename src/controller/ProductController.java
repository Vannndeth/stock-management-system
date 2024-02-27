package controller;

import model.Product;
import service.ProductService;
import util.PaginationUtil;
import util.Singleton;
import view.ProductView;

import java.util.List;
import java.util.Scanner;
public class ProductController {
    private final Scanner scanner;
    private final ProductView productView;
    private final ProductService productService;
    private final PaginationUtil paginationUtil;
    public ProductController(){
        productView = Singleton.getProductView();
        productService = Singleton.getProductService();
        scanner = Singleton.getScanner();
        paginationUtil = Singleton.getPaginationUtil();
    }
    public void random(){
        System.out.print("Enter Number of Records: ");
        Long count = Long.parseLong(scanner.nextLine());
        productService.random(count);
    }
    public void display(){
        int rowPerPage = 10;
        int currentPage = 1;
        productView.displayProduct(productService.display(), rowPerPage, currentPage);
        do {
            productView.paginationOption();
            System.out.print("-> B)ack or Navigate page: ");
            String option = String.valueOf(scanner.nextLine());
            switch (option.toUpperCase()){
                case "F" -> {
                    currentPage = paginationUtil.first(productService.display(), rowPerPage, currentPage);
                }
                case "P" -> {
                    currentPage = paginationUtil.previous(productService.display(), rowPerPage, currentPage);
                }
                case "G" -> {
                    currentPage = paginationUtil.goTo(productService.display(), rowPerPage, currentPage, scanner);
                }
                case "N" -> {
                    currentPage = paginationUtil.next(productService.display(), rowPerPage, currentPage);
                }
                case "L" -> {
                    currentPage = paginationUtil.last(productService.display(), rowPerPage, currentPage);
                }
                case "S" -> {
                    rowPerPage = setRecord();
                    productView.displayProduct(productService.display(), rowPerPage, currentPage);
                }
                case "B" -> {
                    return;
                }
                default -> {
                    System.out.println("Please choose option above!");
                }
            }
        }while (true);
    }
    public void write(){
        Product product = new Product();
        String confirm = productView.write(product, scanner);
        productService.write(product,confirm);
    }
    public void read() {
        System.out.print("-> Enter Product's Code: ");
        String code = String.valueOf(scanner.nextLine());
        Product product = productService.read(code);
        if (product != null) {
            System.out.println("Product found:");
            System.out.printf("# Product detail of %s\n", code);
            productView.read(productService.read(code));
        } else {
            System.out.println("Product with code " + code + " not found.");
        }
    }
    public void update(){
        System.out.print("Enter product code to update: ");
        String productCode = String.valueOf(scanner.nextLine());
        Product product = productService.read(productCode);
        if (product != null) {
            System.out.printf("# Product detail of %s\n", productCode);
            productView.read(productService.read(productCode));
            System.out.println("Select option to update:");
            System.out.println("1. Update All");
            System.out.println("2. Update name");
            System.out.println("3. Update price");
            System.out.println("4. Update quantity");
            System.out.println("5. Back to menu");
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> {
                    productService.update(productCode, true, true, true);
                }
                case 2 -> {
                    productService.update(productCode, true, false, false);
                }

                case 3 -> {
                    productService.update(productCode, false, true, false);
                }

                case 4 -> {
                    productService.update(productCode, false, false, true);
                }
                case 5 -> {
                    return;
                }
                default -> {
                    System.out.println("Invalid option selected.");
                }
            }
        } else {
            System.out.println("Product with code " + product + " not found.");
        }
    }

    public void delete(){
        System.out.print("Enter product's code to delete: ");
        String code = String.valueOf(scanner.nextLine());
        System.out.print("Are you sure you want to delete?(Y/N):");
        String confirm = String.valueOf(scanner.nextLine());
        if(confirm.equalsIgnoreCase("Y") || confirm.equalsIgnoreCase("YES")){
            productService.delete(code);
        }else {
            System.out.println("You're canceled delete!");
        }
    }
    public void search(){
        int rowPerPage = 10;
        int currentPage = 1;
        System.out.print("-> Enter Product's Name: ");
        String name = String.valueOf(scanner.nextLine());
        List<Product> products = productService.search(name);
        if (!products.isEmpty()) {
            productView.displayProduct(products, rowPerPage, currentPage);
            do {
                productView.paginationOption();
                System.out.print("-> B)ack or Navigate page: ");
                String option = String.valueOf(scanner.nextLine());
                switch (option.toUpperCase()){
                    case "F" -> {
                        currentPage = paginationUtil.first(products, rowPerPage, currentPage);
                    }
                    case "P" -> {
                        currentPage = paginationUtil.previous(products, rowPerPage, currentPage);
                    }
                    case "G" -> {
                        currentPage = paginationUtil.goTo(products, rowPerPage, currentPage, scanner);
                    }
                    case "N" -> {
                        currentPage = paginationUtil.next(products, rowPerPage, currentPage);
                    }
                    case "L" -> {
                        currentPage = paginationUtil.last(products, rowPerPage, currentPage);
                    }
                    case "S" -> {
                        rowPerPage = setRecord();
                        productView.displayProduct(products, rowPerPage, currentPage);
                    }
                    case "B" -> {
                        return;
                    }
                    default -> {
                        System.out.println("Please choose option above!");
                    }
                }
            }while (true);
        } else {
            System.out.println("No products with name containing " + name + " found.");
        }
    }
    public int setRecord(){
        return paginationUtil.setRecord();
    }
    public void commit(){

    }
    public void backup(){

    }
    public void restore(){

    }

    public void help(){
        productView.helpView();
    }
    public void exit(){
        System.exit(0);
    }
}
