package service;

import model.Product;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    public void random(Long count);
    public List<Product> display();
    public Product read(String code);
    public Product write(Product product, String confirm);
    public void update(String productCode, boolean updateName, boolean updatePrice, boolean updateQuantity);
    public void delete(String code);
    public List<Product> search(String name);
    public Integer setRecord(int record);
    public List<Product> backup();
    public List<Product> restore();
}
