package model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Product implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String code;
    private String name;
    private Integer quantity;
    private Double unitPrice;
    private LocalDate importedDate;

    public Product() {
    }

    public Product(String code, String name, Integer quantity, Double unitPrice, LocalDate importedDate) {
        this.code = code;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.importedDate = importedDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public LocalDate getImportedDate() {
        return importedDate;
    }

    public void setImportedDate(LocalDate importedDate) {
        this.importedDate = importedDate;
    }

    @Override
    public String toString() {
        return "Product{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", importedDate=" + importedDate +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return Objects.equals(getCode(), product.getCode()) && Objects.equals(getName(), product.getName()) && Objects.equals(getQuantity(), product.getQuantity()) && Objects.equals(getUnitPrice(), product.getUnitPrice()) && Objects.equals(getImportedDate(), product.getImportedDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode(), getName(), getQuantity(), getUnitPrice(), getImportedDate());
    }
}