package ra.run;

import ra.model.Catalog;
import ra.model.Product;
import ra.service.CatalogService;
import ra.service.ProductService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class BookManagement {

    private static List<String> productList = new ArrayList<>();
    private static List<Integer> cart = new ArrayList<>();
    private static CatalogService catalogService;
    private static ProductService productService;

    public static void main(String[] args) {
        catalogService = new CatalogService();
        productService = new ProductService();

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            printBasicMenu();
            System.out.print("Nhập lựa chọn của bạn: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    catalogManagement(scanner);
                    break;
                case 2:
                    productManagement(scanner);
                    break;
                case 3:
                    forUsers(scanner);
                    break;
                case 4:
                    System.out.println("Thoát khỏi chương trình. Tạm biệt!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
                    break;
            }
        } while (choice != 3);

        scanner.close();
    }

    private static void printBasicMenu() {
        System.out.println("**************************BASIC-MENU**************************");
        System.out.println("1. Quản lý danh mục");
        System.out.println("2. Quản lý sản phẩm");
        System.out.println("3. Trang của người dùng");
        System.out.println("4. Thoát");
        System.out.println("**************************************************************");
    }

    private static void catalogManagement(Scanner scanner) {
        int choice;

        do {
            printCatalogManagementMenu();
            System.out.print("Nhập lựa chọn của bạn: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    addNewCatalog(scanner);
                    break;
                case 2:
                    displayAllCatalogs();
                    break;
                case 3:
                    updateCatalogName(scanner);
                    break;
                case 4:
                    deleteCatalog(scanner);
                    break;
                case 5:
                    System.out.println("Quay lại menu.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
                    break;
            }
        } while (choice != 5);
    }

    private static void printCatalogManagementMenu() {
        System.out.println("********************CATALOG-MANAGEMENT********************");
        System.out.println("1. Nhập số danh mục thêm mới và nhập thông tin cho từng danh mục");
        System.out.println("2. Hiển thị thông tin tất cả các danh mục");
        System.out.println("3. Sửa tên danh mục theo mã danh mục");
        System.out.println("4. Xóa danh muc theo mã danh mục (lưu ý ko xóa khi có sản phẩm)");
        System.out.println("5. Quay lại");
        System.out.println("*********************************************************");
    }

    private static void forUsers(Scanner scanner) {
        int choice;

        do {
            printUserMenu();
            System.out.print("Nhập lựa chọn của bạn: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    displayAllProducts();
                    break;
                case 2:
                    addToCart(scanner);
                    break;
                case 3:
                    displayCartProducts();
                    break;
                case 4:
                    updateCartProductQuantity(scanner);
                    break;
                case 5:
                    removeProductFromCart(scanner);
                    break;
                case 6:
                    clearCart();
                    break;
                case 8:
                    System.out.println("Quay lại menu.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
                    break;
            }
        } while (choice != 8);
    }


    private static void printUserMenu() {
        System.out.println("***********************USER-MENU***********************");
        System.out.println("1. Xem danh sách sản phẩm");
        System.out.println("2. Thêm vào giỏ hàng");
        System.out.println("3. Xem tất cả sản phẩm giỏ hàng");
        System.out.println("4. Thay đổi số lượng sản phẩm trong giỏ hàng");
        System.out.println("5. Xóa 1 sản phẩm trong giỏ hàng");
        System.out.println("6. Xóa toàn bộ sản phẩm trong giỏ hàng");
        System.out.println("8. Quay lại");
        System.out.println("*******************************************************");
    }

    private static void addNewCatalog(Scanner scanner) {
        System.out.print("Nhập số lượng danh mục để thêm: ");
        int numCatalogs = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        for (int i = 0; i < numCatalogs; i++) {
            System.out.println("Nhập thông tin cho Catalogue " + (i + 1) + ":");
            System.out.print("Danh mục ID: ");
            int catalogId = scanner.nextInt();
            scanner.nextLine(); // Consume newline character
            System.out.print("Tên danh mục: ");
            String catalogName = scanner.nextLine();

            Catalog existingCatalog = catalogService.findById(catalogId);
            if (existingCatalog != null) {
                System.out.println("Danh mục với ID " + catalogId + " đã tồn tại. Không thể thêm danh mục mới.");
            } else {
                Catalog catalog = new Catalog(catalogId, catalogName);
                catalogService.save(catalog);
                System.out.println("Thêm danh mục thành công.");
            }
        }
    }


    private static void displayAllCatalogs() {
        List<Catalog> catalogs = catalogService.getAll();
        if (((List<?>) catalogs).isEmpty()) {
            System.out.println("Chưa có danh mục nào.");
        } else {
            System.out.println("Danh mục:");
            for (Catalog catalog : catalogs) {
                System.out.println(catalog.toString());
            }
        }
    }


    private static void updateCatalogName(Scanner scanner) {
        System.out.print("Nhập ID danh mục để cập nhật tên của nó: ");
        int catalogId = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        Catalog catalogToUpdate = null;
        for (Catalog catalog : catalogService.getAll()) {
            if (catalog.getCatalogId() == catalogId) {
                catalogToUpdate = catalog;
                break;
            }
        }

        if (catalogToUpdate != null) {
            System.out.print("Nhập tên mới cho danh mục: ");
            String newCatalogName = scanner.nextLine();
            catalogToUpdate.setCatalogName(newCatalogName);
            System.out.println("Tên danh mục đã được cập nhật thành công.");
        } else {
            System.out.println("Danh mục có ID " + catalogId + " không tìm thấy.");
        }
    }

    private static void deleteCatalog(Scanner scanner) {
        System.out.print("Nhập ID danh mục muốn xóa: ");
        int catalogId = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        Catalog catalogToDelete = null;
        for (Catalog catalog : catalogService.getAll()) {
            if (catalog.getCatalogId() == catalogId) {
                catalogToDelete = catalog;
                break;
            }
        }

        if (catalogToDelete != null) {
            boolean hasProducts = false;
            for (Product product : productService.getAll()) {
                if (product.getCatalog().getCatalogId() == catalogId) {
                    hasProducts = true;
                    break;
                }
            }

            if (!hasProducts) {
                catalogService.delete(catalogId);
                System.out.println("Danh mục có ID " + catalogId + " đã xóa thành công.");
            } else {
                System.out.println("Không thể xóa danh mục. Sản phẩm tồn tại dưới danh mục này.");
            }
        } else {
            System.out.println("dnah mục có ID " + catalogId + " không tìm thấy.");
        }
    }

    private static void productManagement(Scanner scanner) {
        int choice;

        do {
            printProductManagementMenu();
            System.out.print("Nhập lựa chọn của bạn: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    addNewProduct(scanner);
                    break;
                case 2:
                    displayAllProducts();
                    break;
                case 3:
                    sortProductsByPriceDescending();
                    break;
                case 4:
                    deleteProduct(scanner);
                    break;
                case 5:
                    searchProductByName(scanner);
                    break;
                case 6:
                    updateProductInformation(scanner);
                    break;
                case 7:
                    System.out.println("Quai lại menu.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
                    break;
            }
        } while (choice != 7);
    }

    private static void printProductManagementMenu() {
        System.out.println("********************PRODUCT-MANAGEMENT********************");
        System.out.println("1. Nhập số sản phẩm và nhập thông tin sản phẩm");
        System.out.println("2. Hiển thị thông tin các sản phẩm");
        System.out.println("3. Sắp xếp sản phẩm theo giá giảm dần");
        System.out.println("4. Xóa sản phẩm theo mã");
        System.out.println("5. Tìm kiếm sách theo tên sách");
        System.out.println("6. Thay đổi thông tin của sách theo mã sách");
        System.out.println("7. Quay lại");
        System.out.println("**********************************************************");
    }

    private static void addNewProduct(Scanner scanner) {
        System.out.print("Nhập số lượng sản phẩm cần thêm: ");
        int numProducts = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        for (int i = 0; i < numProducts; i++) {
            System.out.println("Nhập thông tin cho Sản phẩm " + (i + 1) + ":");

            System.out.print("ID sản phẩm (bắt đầu bằng 'P' theo sau là 4 chữ số): ");
            String productId = scanner.nextLine();
            while (!isValidProductId(productId)) {
                System.out.println("ID sản phẩm không hợp lệ. Vui lòng nhập lại.");
                System.out.print("ID sản phẩm (bắt đầu bằng 'P' theo sau là 4 chữ số): ");
                productId = scanner.nextLine();
            }

            System.out.print("Tên sản phẩm: ");
            String productName = scanner.nextLine();
            while (productName.isEmpty()) {
                System.out.println("Tên sản phẩm không được để trống. Vui lòng nhập lại.");
                System.out.print("Tên sản phẩm: ");
                productName = scanner.nextLine();
            }

            System.out.print("Gía sản phẩm: ");
            double productPrice = scanner.nextDouble();
            while (productPrice <= 0) {
                System.out.println("Gía sản phẩm phải lớn hơn 0. Vui lòng nhập lại.");
                System.out.print("Gía sản phẩm: ");
                productPrice = scanner.nextDouble();
            }
            scanner.nextLine(); // Consume newline character

            System.out.print("Miêu tả: ");
            String description = scanner.nextLine();

            System.out.print("Số lượng: ");
            int stock = scanner.nextInt();
            while (stock < 10) {
                System.out.println("Số lượng phải ít nhất là 10. Vui lòng nhập lại.");
                System.out.print("Số lượng: ");
                stock = scanner.nextInt();
            }
            scanner.nextLine(); // Consume newline character

            System.out.println("Chọn Danh mục cho sản phẩm:");
            displayAllCatalogs();
            System.out.print("Danh mục ID: ");
            int catalogId = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            Catalog catalog = null;
            for (Catalog c : catalogService.getAll()) {
                if (c.getCatalogId() == catalogId) {
                    catalog = c;
                    break;
                }
            }

            if (catalog != null) {
                Product product = new Product(productId, productName, productPrice, description, stock, catalog);
                productService.save(product);
                System.out.println("Thêm sản phẩm thành công.");
            } else {
                System.out.println("Danh mục có ID " + catalogId + " không tìm thấy. Sản phẩm chưa được thêm vào.");
            }
        }
    }

    private static boolean isValidProductId(String productId) {
        // Kiểm tra định dạng của productId (bắt đầu bằng 'P' theo sau là 4 chữ số)
        return productId.matches("^P\\d{4}$");
    }


    private static void displayAllProducts() {
        List<Product> products = productService.getAll();
        if (products.isEmpty()) {
            System.out.println("Không có sản phẩm nào.");
        } else {
            int count = 0;
            for (Product product : products) {
                System.out.println("*******************************************************");
                System.out.println("Sản phẩm: " + count);
                count += 1;
                System.out.println(product.toString());
            }
        }
    }

    private static void sortProductsByPriceDescending() {
        Collections.sort(productService.getAll(), (p1, p2) -> Double.compare(p2.getProductPrice(), p1.getProductPrice()));
        System.out.println("Sản phẩm được sắp xếp theo giá (giảm dần):");
        displayAllProducts();
    }

    private static void deleteProduct(Scanner scanner) {
        System.out.print("Nhập ID sản phẩm để xóa: ");
        String productId = scanner.nextLine();

        Product productToDelete = null;
        for (Product product : productService.getAll()) {
            if (product.getProductId().equals(productId)) {
                productToDelete = product;
                break;
            }
        }

        if (productToDelete != null) {
            productService.delete(productId);
            System.out.println("Sản phẩm có ID " + productId + " đã xóa thành công.");
        } else {
            System.out.println("Sản phẩm có ID " + productId + " Không tìm thấy.");
        }
    }

    private static void searchProductByName(Scanner scanner) {
        System.out.print("Nhập tên sản phẩm cần tìm: ");
        String productName = scanner.nextLine();

        List<Product> searchResults = new ArrayList<>();
        for (Product product : productService.getAll()) {
            if (product.getProductName().equalsIgnoreCase(productName)) {
                searchResults.add(product);
            }
        }

        if (searchResults.isEmpty()) {
            System.out.println("Không tìm thấy sản phẩm nào.");
        } else {
            System.out.println("Kết quả tìm kiếm:");
            for (Product product : searchResults) {
                System.out.println(product.toString());
            }
        }
    }


    private static void updateProductInformation(Scanner scanner) {
        System.out.print("Nhập ID sản phẩm để cập nhật thông tin: ");
        String productId = scanner.nextLine();

        Product productToUpdate = null;
        for (Product product : productService.getAll()) {
            if (product.getProductId().equals(productId)) {
                productToUpdate = product;
                break;
            }
        }

        if (productToUpdate != null) {
            System.out.println("Nhập thông tin mới cho sản phẩm:");
            System.out.print("Tên sản phẩm: ");
            String newProductName = scanner.nextLine();
            System.out.print("Giá sản phẩm: ");
            double newProductPrice = scanner.nextDouble();
            scanner.nextLine(); // Consume newline character
            System.out.print("Miêu tả: ");
            String newDescription = scanner.nextLine();
            System.out.print("Số Lượng: ");
            int newStock = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            productToUpdate.setProductName(newProductName);
            productToUpdate.setProductPrice(newProductPrice);
            productToUpdate.setDescription(newDescription);
            productToUpdate.setStock(newStock);

            System.out.println("Thông tin sản phẩm được cập nhật thành công.");
        } else {
            System.out.println("Sản phẩm có ID " + productId + " không tìm thấy.");
        }
    }

    private static void addToCart(Scanner scanner) {
        System.out.println("Nhập số thứ tự sản phẩm để thêm vào giỏ hàng:");
        int productIndex = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        if (productIndex >= 1 && productIndex <= productService.getAll().size()) {
            System.out.println("Nhập số lượng sản phẩm:");
            int quantity = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            if (quantity >= 1) {
                int cartIndex = productIndex - 1;
                if (cartIndex < cart.size()) {
                    cart.set(cartIndex, cart.get(cartIndex) + quantity);
                } else {
                    cart.add(quantity);
                }
                System.out.println("Sản phẩm đã được thêm vào giỏ hàng.");
            } else {
                System.out.println("Số lượng không hợp lệ. Vui lòng thử lại.");
            }
        } else {
            System.out.println("Sản phẩm không tồn tại. Vui lòng thử lại.");
        }
        System.out.println();
    }


    private static void displayCartProducts() {
        if (cart.isEmpty()) {
            System.out.println("Giỏ hàng trống.");
        } else {
            System.out.println("Sản phẩm trong giỏ hàng:");
            for (Integer product : cart) {
                System.out.println(product.toString());
            }
        }
    }


    private static void updateCartProductQuantity(Scanner scanner) {
        if (cart.isEmpty()) {
            System.out.println("Giỏ hàng trống. Không có sản phẩm để cập nhật.");
        } else {
            System.out.println("Nhập số thứ tự sản phẩm trong giỏ hàng để cập nhật số lượng:");
            int productIndex = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            if (productIndex >= 1 && productIndex <= cart.size()) {
                int cartIndex = productIndex - 1;
                int currentQuantity = cart.get(cartIndex);
                if (currentQuantity > 0) {
                    System.out.println("Số lượng hiện tại: " + currentQuantity);
                    System.out.println("Nhập số lượng mới:");
                    int newQuantity = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character

                    if (newQuantity >= 0) {
                        cart.set(cartIndex, newQuantity);
                        System.out.println("Số lượng sản phẩm đã được cập nhật.");
                    } else {
                        System.out.println("Số lượng không hợp lệ. Vui lòng thử lại.");
                    }
                } else {
                    System.out.println("Sản phẩm không tồn tại trong giỏ hàng. Vui lòng thử lại.");
                }
            } else {
                System.out.println("Sản phẩm không tồn tại trong giỏ hàng. Vui lòng thử lại.");
            }
        }
        System.out.println();
    }

    private static void removeProductFromCart(Scanner scanner) {
        if (cart.isEmpty()) {
            System.out.println("Giỏ hàng trống. Không có sản phẩm để xóa.");
        } else {
            System.out.println("Nhập số thứ tự sản phẩm trong giỏ hàng để xóa:");
            int productIndex = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            if (productIndex >= 1 && productIndex <= cart.size()) {
                int cartIndex = productIndex - 1;
                int currentQuantity = cart.get(cartIndex);
                if (currentQuantity > 0) {
                    cart.set(cartIndex, 0);
                    System.out.println("Sản phẩm đã được xóa khỏi giỏ hàng.");
                } else {
                    System.out.println("Sản phẩm không tồn tại trong giỏ hàng. Vui lòng thử lại.");
                }
            } else {
                System.out.println("Sản phẩm không tồn tại trong giỏ hàng. Vui lòng thử lại.");
            }
        }
        System.out.println();
    }

    private static void clearCart() {
        if (cart.isEmpty()) {
            System.out.println("Giỏ hàng trống.");
        } else {
            cart.clear();
            System.out.println("Giỏ hàng đã được xóa.");
        }
        System.out.println();
    }

}
