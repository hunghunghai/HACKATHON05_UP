package ra.service;

import ra.model.CartItem;
import ra.model.Product;

import java.util.ArrayList;
import java.util.List;

public class CartService {
    private List<CartItem> cartItems;

    public CartService() {
        cartItems = new ArrayList<>();
    }

    public void displayAllProducts() {
        // Hiển thị danh sách tất cả sản phẩm đang được bán của cửa hàng
        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            System.out.println("Product ID: " + product.getProductId());
            System.out.println("Name: " + product.getProductName());
            System.out.println("Price: " + product.getProductPrice());
            System.out.println("-----------------------");
        }
    }

    public void addProductToCart(int productId) {
        // Thêm mới 1 sản phẩm vào giỏ hàng dựa theo mã sản phẩm
        Product product = getProductById(productId);
        if (product != null) {
            boolean found = false;
            for (CartItem cartItem : cartItems) {
                if (cartItem.getProduct().getProductId().equals(String.valueOf(productId))) {
                    int newQuantity = cartItem.getQuantity() + 1;
                    if (checkStockAvailability(cartItem.getProduct(), newQuantity)) {
                        cartItem.setQuantity(newQuantity);
                        System.out.println("Product added to cart.");
                    } else {
                        System.out.println("Insufficient stock.");
                    }
                    found = true;
                    break;
                }

            }
            if (!found) {
                if (checkStockAvailability(product, 1)) {
                    CartItem newCartItem = new CartItem(cartItems.size() + 1, product, product.getProductPrice(), 1);
                    cartItems.add(newCartItem);
                    System.out.println("Product added to cart.");
                } else {
                    System.out.println("Insufficient stock.");
                }
            }
        } else {
            System.out.println("Invalid product ID.");
        }
    }

    public void displayCartItems() {
        // Hiển thị danh sách giỏ hàng
        if (cartItems.isEmpty()) {
            System.out.println("Cart is empty.");
        } else {
            for (CartItem cartItem : cartItems) {
                Product product = cartItem.getProduct();
                System.out.println("Cart Item ID: " + cartItem.getCartItemId());
                System.out.println("Product ID: " + product.getProductId());
                System.out.println("Name: " + product.getProductName());
                System.out.println("Price: " + product.getProductPrice());
                System.out.println("Quantity: " + cartItem.getQuantity());
                System.out.println("-----------------------");
            }
        }
    }

    public void updateCartItemQuantity(int cartItemId, int newQuantity) {
        // Cập nhật số lượng sản phẩm muốn mua theo cartItemId
        boolean found = false;
        for (CartItem cartItem : cartItems) {
            if (cartItem.getCartItemId() == cartItemId) {
                Product product = cartItem.getProduct();
                if (checkStockAvailability(product, newQuantity)) {
                    cartItem.setQuantity(newQuantity);
                    System.out.println("Quantity updated successfully.");
                } else {
                    System.out.println("Insufficient stock.");
                }
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Invalid cart item ID.");
        }
    }

    public void removeCartItem(int cartItemId) {
        // Xóa 1 sản phẩm ra khỏi giỏ hàng theo cartItemId
        boolean found = false;
        for (CartItem cartItem : cartItems) {
            if (cartItem.getCartItemId() == cartItemId) {
                Product product = cartItem.getProduct();
                int removedQuantity = cartItem.getQuantity();
                cartItems.remove(cartItem);
                adjustStock(product, removedQuantity);
                System.out.println("Product removed from cart.");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Invalid cart item ID.");
        }
    }

    public void clearCart() {
        // Xóa toàn bộ sản phẩm trong giỏ hàng
        cartItems.clear();
        System.out.println("Cart cleared.");
    }

    private boolean checkStockAvailability(Product product, int quantity) {
        // Kiểm tra stock của sản phẩm
        return product.getStock() >= quantity;
    }

    private void adjustStock(Product product, int quantity) {
        // Cập nhật stock của sản phẩm khi có sự thay đổi trong giỏ hàng
        product.setStock(product.getStock() + quantity);
    }

    private Product getProductById(int productId) {
        // Giả định có một phương thức để lấy thông tin sản phẩm từ database hoặc nguồn dữ liệu khác
        // Trả về đối tượng Product dựa trên productId, null nếu không tìm thấy
        //return product;
        // Hoặc có thể sử dụng danh sách sản phẩm tĩnh nếu không có database
        return null;
    }
}
