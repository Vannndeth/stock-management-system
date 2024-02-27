package util;

import controller.ProductController;
import service.ProductService;
import service.ProductServiceImpl;
import view.ProductView;

import java.io.*;
import java.util.Scanner;

public class Singleton {
    private static Scanner scanner;
    private static ProductService productService;
    private static ProductView productView;
    private static ProductController productController;
    private static PaginationUtil paginationUtil;

    public static Scanner getScanner(){
        if (scanner == null){
            scanner = new Scanner(System.in);
        }
        return scanner;
    }
    public static ProductService getProductService() {
        if (productService == null){
            productService = new ProductServiceImpl();
        }
        return productService;
    }

    public static ProductView getProductView(){
        if (productView == null){
            productView = new ProductView();
        }
        return productView;
    }
    public static ProductController getProductController(){
        if (productController == null){
            productController = new ProductController();
        }
        return productController;
    }
    public static PaginationUtil getPaginationUtil(){
        if (paginationUtil == null){
            paginationUtil = new PaginationUtil();
        }
        return paginationUtil;
    }
}
