package view;

import model.Product;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;
import service.ProductService;
import util.Singleton;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class ProductView {
    private static Table table;
    static final CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.CENTER);
    private final ProductService productService;
    public ProductView(){
        productService = Singleton.getProductService();
    }

    public void menu(){
        table = new Table(3, BorderStyle.UNICODE_BOX_DOUBLE_BORDER_WIDE, ShownBorders.SURROUND);
        table.addCell("     Disp(l)ay   ");
        table.addCell("     Rando(m)    ");
        table.addCell("     (W)rite     ");
        table.addCell("     (R)ead     ");
        table.addCell("     (U)pdate     ");
        table.addCell("     (D)elete     ");
        table.addCell("     (S)earch     ");
        table.addCell("     (C)ommit     ");
        table.addCell("     (B)ackup     ");
        table.addCell("     Res(t)ore     ");
        table.addCell("     (H)elp     ");
        table.addCell("     E(x)it     ");
        System.out.println(table.render());
    }

    public void read(Product product){
        table = new Table(1, BorderStyle.UNICODE_BOX_DOUBLE_BORDER_WIDE, ShownBorders.SURROUND);
        table.addCell("     CODE            : "+product.getCode()+"     ");
        table.addCell("     NAME            : "+product.getName()+"     ");
        table.addCell("     QUANTITY        : "+product.getQuantity().toString()+"     ");
        table.addCell("     UNIT_PRICE      : "+product.getUnitPrice().toString()+"     ");
        table.addCell("     IMPORTED_AT     : "+product.getImportedDate().toString()+"     ");
        System.out.println(table.render());
    }

    public void helpView(){
        System.out.println("# Help Instruction");
        table = new Table(1, BorderStyle.CLASSIC_COMPATIBLE_LIGHT_WIDE, ShownBorders.SURROUND);
        table.addCell("1.    Press   l : Display product as table");
        table.addCell("2.    Press   w : Create a new product");
        table.addCell("3.    Press   r : View product detail by code");
        table.addCell("4.    Press   u : Update an existing product by code");
        table.addCell("5.    Press   d : Delete an existing product by code");
        table.addCell("6.    Press   s : Search an existing product by name");
        table.addCell("7.    Press   c : Commit transactional data");
        table.addCell("8.    Press   b : Backup data");
        table.addCell("9.    Press   t : Restore data");
        table.addCell("10.   Press   f : Navigate pagination to the first page");
        table.addCell("11.   Press   p : Navigate pagination to the previous page");
        table.addCell("12.   Press   n : Navigate pagination to the next page");
        table.addCell("13.   Press   l : Navigate pagination to the last page");
        table.addCell("14.   Press   h : Help");
        table.addCell("15.   Press   b : Step back of the application");
        table.addCell("16.   Press   x : Exit the application");
        System.out.println(table.render());
    }
    public void displayProduct(List<Product> products, int rowPerPage, int currentPage){
        int size = products.size();
        int startIndex = (currentPage - 1) * rowPerPage;
        int endIndex = Math.min(startIndex + rowPerPage, size);
        table = new Table(5, BorderStyle.UNICODE_BOX_DOUBLE_BORDER_WIDE, ShownBorders.SURROUND_HEADER_AND_COLUMNS);
        table.addCell("        CODE       ", cellStyle);
        table.addCell("        NAME       ", cellStyle);
        table.addCell("        QUANTITY        ", cellStyle);
        table.addCell("        UNIT_PRICE       ", cellStyle);
        table.addCell("        IMPORTED_AT        ", cellStyle);
        for (int i = startIndex; i < endIndex; i++) {
            table.addCell(products.get(i).getCode(), cellStyle);
            table.addCell(products.get(i).getName(), cellStyle);
            table.addCell(products.get(i).getQuantity().toString(), cellStyle);
            table.addCell(products.get(i).getUnitPrice().toString(), cellStyle);
            table.addCell(products.get(i).getImportedDate().toString(), cellStyle);
        }
        System.out.println(table.render());
        table = new Table(2, BorderStyle.DESIGN_CURTAIN, ShownBorders.HEADER_ONLY);
        int totalPages = (int) Math.ceil((double) size / rowPerPage);
        table.addCell("  Page : %d of %s".formatted(currentPage, totalPages + " ".repeat(80)));
        table.addCell("Total Record : %d".formatted(size));
        System.out.println(table.render());
    }
    public void paginationOption(){
        Table tableMenu = new Table(7, BorderStyle.DESIGN_CURTAIN);
        tableMenu.addCell("Page Navigation:"+" ".repeat(20));
        tableMenu.addCell("     (F)irst");
        tableMenu.addCell("     (P)revious");
        tableMenu.addCell("     (G)oto ");
        tableMenu.addCell("     (N)ext");
        tableMenu.addCell("     (L)ast   ");
        tableMenu.addCell("     (S)et Record   ");
        System.out.println(tableMenu.render());
    }

    public String write(Product product, Scanner scanner){
        String productCode = "";
        for (int i = 1; i <= 10; i++) {
            productCode = String.format("CSTAD-%08d", i);
            product.setCode(productCode);
        }
        System.out.print("Enter Product's Name: ");
        product.setName(scanner.nextLine());
        System.out.print("Enter Product's Quantity: ");
        product.setQuantity(Integer.parseInt(scanner.nextLine()));
        System.out.print("Enter Unit Price: ");
        product.setUnitPrice(Double.parseDouble(scanner.nextLine()));
        product.setImportedDate(LocalDate.now());
        System.out.print("Are you sure to create a new product?(Y/N):");
        String confirm = scanner.nextLine();
        return confirm;
    }
}
