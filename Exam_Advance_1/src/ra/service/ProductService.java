package ra.service;

import ra.model.Catalog;
import ra.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductService implements IService<Product, String> {
    private List<Product> productList;

    public ProductService() {
        this.productList = new ArrayList<>();
    }

    @Override
    public List<Product> getAll() {
        return productList;
    }

    @Override
    public void save(Product product) {
        productList.add(product);
    }

    @Override
    public Catalog findById(String productId) {
        for (Product product : productList) {
            if (product.getProductId().equals(productId)) {
                System.out.println("Tìm thấy sản phẩm: " + product.toString());
                return null;
            }
        }
        System.out.println("Sản phẩm có ID " + productId + " không tìm thấy.");
        return null;
    }

    @Override
    public void delete(String productId) {
        for (Product product : productList) {
            if (product.getProductId().equals(productId)) {
                productList.remove(product);
                System.out.println("Sản phẩm có ID " + productId + " đã xóa.");
                return;
            }
        }
        System.out.println("Sản phẩm có ID " + productId + " không tìm thấy.");
    }

    public Product getProductByName(String productName) {
        for (Product product : productList) {
            if (product.getProductName().equals(productName)) {
                return product;
            }
        }
        return null;
    }
}
