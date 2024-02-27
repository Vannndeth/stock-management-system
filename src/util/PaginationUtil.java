package util;

import model.Product;
import view.ProductView;

import java.util.List;
import java.util.Scanner;

public class PaginationUtil {
    private final ProductView productView;
    public PaginationUtil(){
        productView = Singleton.getProductView();
    }
    public int first(List<Product> products, int rowPerPage, int currentPage) {
        if (currentPage == 1) {
            System.out.println("Now you stand on the first page...!");
            productView.displayProduct(products, rowPerPage, currentPage);
        } else {
            currentPage = 1;
            productView.displayProduct(products, rowPerPage, currentPage);
        }
        return currentPage;
    }
    public int previous(List<Product> products, int rowPerPage, int currentPage) {
        if (currentPage > 1) {
            currentPage--;
            productView.displayProduct(products, rowPerPage, currentPage);
        }else{
            currentPage = (int) Math.ceil((double) products.size() / rowPerPage);
            productView.displayProduct(products, rowPerPage, currentPage);
        }
        return currentPage;
    }
    public int goTo(List<Product> products, int rowPerPage, int currentPage, Scanner scanner) {
        System.out.print("Enter Page: ");
        currentPage = Integer.parseInt(scanner.next());
        scanner.nextLine();
        productView.displayProduct(products, rowPerPage, currentPage);
        return currentPage;
    }

    public int next(List<Product> products, int rowPerPage, int currentPage) {
        int totalPages = (int) Math.ceil((double) products.size() / rowPerPage);
        if (currentPage < totalPages) {
            currentPage++;
            productView.displayProduct(products, rowPerPage, currentPage);
        }else {
            currentPage = 1;
            productView.displayProduct(products, rowPerPage, currentPage);
        }
        return currentPage;
    }
    public int last(List<Product> products, int rowPerPage, int currentPage) {
        int totalPages = (int) Math.ceil((double) products.size() / rowPerPage);
        if (currentPage == totalPages) {
            System.out.println("Now you stand on the last page...!");
            productView.displayProduct(products, rowPerPage, currentPage);
        } else {
            currentPage = totalPages;
            productView.displayProduct(products, rowPerPage, currentPage);
        }
        return currentPage;
    }
    public int setRecord(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter record for display: ");
        int record = Integer.parseInt(sc.nextLine());
        System.out.printf("    Set page to %d record successfully...!    ",record);
        System.out.println();
        return record;
    }
}
