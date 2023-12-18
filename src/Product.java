import java.io.*;
import java.sql.SQLOutput;
import java.util.*;
import java.util.regex.Pattern;

public class Product implements IProduct, Serializable {
    private static final long serialVersionUID = -7756228864547736802L;
    private String id;
    private String name;
    private double importPrice;// giá nhập vào
    private double exportPrice;// giá bán ra
    private double profit;//lợi nhuận
    private String description;
    private boolean status;
    private int categoryId;

    public Product() {
    }

    public Product(String id, String name, double importPrice, double exportPrice,
                   double profit, String description, boolean status, int categoryId) {
        this.id = id;
        this.name = name;
        this.importPrice = importPrice;
        this.exportPrice = exportPrice;
        this.profit = profit;
        this.description = description;
        this.status = status;
        this.categoryId = categoryId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(double importPrice) {
        this.importPrice = importPrice;
    }

    public double getExportPrice() {
        return exportPrice;
    }

    public void setExportPrice(double exportPrice) {
        this.exportPrice = exportPrice;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public void inputData(Scanner scanner) {
        this.id = checkId(scanner);
        this.name = checkName(scanner);
        this.importPrice = checkImportPrice(scanner);
        this.exportPrice = checkExportPrice(scanner);
        this.description = checkDescription(scanner);
        this.status = checkStatus(scanner);
        this.categoryId = checkCategoryId(scanner);
    }

    @Override
    public void displayData() {
//        System.out.println(" mã: " + this.id);
//        System.out.println(" tên : " + this.name);
//        System.out.println("Giá nhập: " + this.importPrice);
//        System.out.println("Giá bán: " + this.exportPrice);
//        System.out.println("Lợi nhuận" + calProfit());
//        System.out.println("Mổ tả: " + this.description);
//        System.out.println("Trạng thái: " + this.status);
//        System.out.println("Mã danh mục: " + this.categoryId);
        String status = this.status ? "Còn hàng" : "Ngừng kinh doanh";
        PrintStream out = System.out;
        out.printf("| %10s | %20s | %15f | %15f | | %15f | | %10s | | %10b | | %10d |%n", this.id, this.name, this.importPrice, this.exportPrice,
                calProfit(), this.description, status, this.categoryId);
        out.printf("------------------------------------------------------------------------------------------------------------------------------------------\n");

    }

    public void khuon() {
        PrintStream out = System.out;
        out.printf("------------------------------------------------------------------------------------------------------------------------------------------\n");
        out.printf("| %-10s | %-20s | %-15s | %-15s | | %-15s | | %-10s | | %-10s | | %-5s |%n", "ID", "Tên", "Giá Nhập ", "Giá Bán", "Lợi Nhuận", "Mô Tả", "Trạng Thái", "Mã Danh Mục");
        out.printf("------------------------------------------------------------------------------------------------------------------------------------------\n");
    }

    public void sortProductName() {
        Store.productList = Product.readDataToFile();
        if (Store.productList.isEmpty()) {
            System.err.println("Danh sách đang rỗng");
            return;
        }
        Store.productList.stream().sorted(Comparator.comparing(Product::getName)).forEach(Product::displayData);
        System.out.println("Sắp xếp thành công");
    }

    public void sortProductProfit() {
        Store.productList = Product.readDataToFile();
        if (Store.productList.isEmpty()) {
            System.err.println("Danh sách đang rỗng");
            return;
        }
        Store.productList.stream().sorted(Comparator.comparing(Product::calProfit).reversed()).forEach(Product::displayData);
        System.out.println("Sắp xếp thành công");
    }

    public void searchProductname(Scanner scanner, Product product) {
        Store.productList = Product.readDataToFile();
        System.out.println("Nhập vào tên cần tìm: ");
        try {
            String deleteName = scanner.nextLine();
            for (int i = 0; i < Store.productList.size(); i++) {
                if (Store.productList.get(i).getName().equals(deleteName)) {
                    product.khuon();
                    Store.productList.get(i).displayData();
                    return;
                }
            }
            System.err.println("Không có tên trong danh sách");
        } catch (Exception ex) {
            System.err.println("Có lỗi khi nhập tên cần xóa " + ex);
        }
    }

    public int checkCategoryId(Scanner scanner) {
        boolean checkOut = true;
        do {
            Store.categoryList = Category.readDataToFile();
            for (int i = 0; i < Store.categoryList.size(); i++) {
                System.out.println(" " + Store.categoryList.get(i).getId() + " --  " + Store.categoryList.get(i).getName());
            }
            System.out.println("Nhập vào mã danh mục : ");
            try {
                int categoryId = Integer.parseInt(scanner.nextLine());
                for (int j = 0; j < Store.categoryList.size(); j++) {
                    if (categoryId != Store.categoryList.get(j).getId()) {
                        checkOut = false;// bị sai
                    }
                }
                if (checkOut) {
                    System.err.println("Bạn đã chọn sai mã danh mục");
                } else {
                    return categoryId;
                }
            } catch (Exception ex) {
                System.err.println("Có lỗi trong lúc nhập mã danh mục");
            }
        } while (checkOut);
        return -1;
    }

    public boolean checkStatus(Scanner scanner) {
        do {
            System.out.println("Nhập vào trạng thái sản phẩm: ");
            try {
                String status = scanner.nextLine();
                if (status.trim().equals("true") || status.trim().equals("false")) {
                    return Boolean.parseBoolean(status);
                } else {
                    System.err.println("Chỉ nhận gi trị true | false");
                }
            } catch (Exception ex) {

            }
        } while (true);
    }

    public void deleteIdProduct(Scanner scanner, Product product) {
        Store.productList = Product.readDataToFile();
        do {
            System.out.println("Nhập vào ID cần xóa: ");
            try {
                String idDelete = scanner.nextLine();
                for (int i = 0; i < Store.productList.size(); i++) {
                    if (Store.productList.get(i).getId().equals(idDelete)) {
                        Store.productList.remove(i);
                        Product.writeDataToFile(Store.productList);
                        System.out.println("Đã xóa thành công");
                        product.khuon();
                        product.displayData();
                        return;
                    }
                }
                System.err.println("Không tìm thấy ID cần xóa");
            } catch (Exception ex) {
                System.err.println("Có lỗi khi nhập vào ID");
            }
        } while (true);
    }

    public void updateById(Scanner scanner) {
        Product product = new Product();
        Store.productList = Product.readDataToFile();
        System.out.println("Nhập vào ID cần cập nhật: ");
        try {
            String idUpdate = scanner.nextLine();
            for (int i = 0; i < Store.productList.size(); i++) {
                if (Store.productList.get(i).getId().equals(idUpdate)) {
                    boolean checkOutUpdate = true;
                    do {
                        System.out.println("************* MENU CẬP NHẬT *************");
                        System.out.println("1. Cập nhật tên sản phẩm mới");
                        System.out.println("2. Cập nhật giá nhập mới");
                        System.out.println("3. Cập nhật giá bán mới");
                        System.out.println("4. Cập nhật mô tả sản phẩm mới");
                        System.out.println("5 . Thoát cập nhật ");
                        System.out.println("Nhập vào lựa chọn :  ");
                        try {
                            int choice = Integer.parseInt(scanner.nextLine());
                            switch (choice) {
                                case 1:
                                    System.out.println("Nhập vào tên mới");
                                    String newName = scanner.nextLine();
                                    if (newName.trim().isEmpty()) {
                                        System.err.println("Không để tên sản phẩm trống");
                                    } else if (newName.trim().length() >= 6 && newName.trim().length() <= 30) {
                                        for (int j = 0; j < Store.productList.size(); j++) {
                                            if (Store.productList.get(j).getName().equals(newName)) {
                                                System.err.println("Đã có tên này trong danh sách rồi! Nhâp lại tên mới");
                                            } else {
                                                Store.productList.get(i).setName(newName);
                                                Product.writeDataToFile(Store.productList);
                                                System.out.println("Cập nhật thành công");
                                                    break;
                                                }
                                            }
                                    } else {
                                        System.err.println("Nhập tên sản phảm từ 6-> 30 ký tự");
                                    }
                            product.khuon();
                            for (int k = 0; k < Store.productList.size(); k++) {
                                Store.productList.get(k).displayData();
                            }
                                    break;
                                case 2:
                                    do {
                                        System.out.println("Nhập vào giá mới: ");
                                        try {
                                            double newImportPrice = Double.parseDouble(scanner.nextLine());
                                            if (newImportPrice > 0) {
                                                Store.productList.get(i).setImportPrice(newImportPrice);
                                                Product.writeDataToFile(Store.productList);
                                                System.out.println("Cập nhật thành công");
                                                break;// thoát khi hợp lệ
                                            } else {
                                                System.err.println("Giá trị nhập vào phải lớn hơn 0");
                                            }
                                        } catch (Exception ex) {
                                            System.err.println("Lỗi khi nhập giá mới");
                                        }
                                    } while (true);
                                    product.khuon();
                                    for (int k = 0; k < Store.productList.size(); k++) {
                                        Store.productList.get(k).displayData();
                                    }
                                    System.out.println("Cập nhật thành công ");
                                    break;
                                case 3:
                                    do {
                                        System.out.println("Nhập vào giá bán mới: ");
                                        try {
                                            double newExportPrice = Double.parseDouble(scanner.nextLine());
                                            if (newExportPrice < 0) {
                                                System.err.println("Nhập vào giá bán lớn hơn 0 ");
                                            } else if (newExportPrice <= this.importPrice && newExportPrice < (this.importPrice + (this.importPrice * MIN_INTEREST_RATE))) {
                                                System.err.println("Giá bán phải lơn hơn ít nhất 0.2 lần giá nhập v");
                                            } else {
                                                Store.productList.get(i).setExportPrice(newExportPrice);
                                                Product.writeDataToFile(Store.productList);
                                                System.out.println("Cập nhật thành công");
                                                break;
                                            }
                                        } catch (Exception ex) {
                                            System.err.println("Lỗi nhập giá bán");
                                        }
                                    } while (true);
                                    for (int k = 0; k < Store.productList.size(); k++) {
                                        Store.productList.get(k).displayData();
                                    }
                                    break;
                                case 4:
                                    do {
                                        System.out.println("Nhập vào mô tả danh mục mới: ");
                                        try {
                                            String newDescription = scanner.nextLine();
                                            if (newDescription.trim().isEmpty()) {
                                                System.err.println("Khộng để trống mô tả danh mục ");
                                            } else {
                                                Store.productList.get(i).setDescription(newDescription);
                                                Product.writeDataToFile(Store.productList);

                                                break;
                                            }
                                        } catch (Exception ex) {
                                            System.err.println("Có lỗi khi nhập mô tả danh mục");
                                        }
                                    } while (true);
                                    for (int k = 0; k < Store.productList.size(); k++) {
                                        Store.productList.get(k).displayData();
                                    }
                                    break;
                                case 5:
                                    checkOutUpdate = false;
                                   return;
                                default:
                                    System.err.println("Hãy chọn từ 1 - > 5");
                            }
                        } catch (Exception ex) {
                            System.err.println("Lỗi khi nhập vào lựa chọn cần cập nhật");
                        }
                    } while (checkOutUpdate);
                }
            }
            System.err.println("Không tìm thấy ID cần câp nhât");
        } catch (Exception ex) {
            System.err.println("Lỗi khi nhập vào ID cần xóa");
        }
    }

    public String checkDescription(Scanner scanner) {
        do {
            System.out.println("Nhập vào mô tả sản phẩm: ");
            try {
                String descriptionn = scanner.nextLine();
                if (descriptionn.trim().isEmpty()) {
                    System.err.println("Không được bỏ trống mô tả");
                    continue;
                } else {
                    return descriptionn;
                }
            } catch (Exception ex) {
                System.err.println("Có lỗi trong lúc nhập mô tả sản phẩm");
            }
        } while (true);
    }

    public String checkId(Scanner scanner) {
        Store.productList = Product.readDataToFile();
        do {
            boolean checkOut = false;// chưa xuất hiện
            System.out.println("Nhập vào ID cần thêm: ");
            try {
                String id = scanner.nextLine();
                if (id.trim().isEmpty()) {
                    System.err.println("Không để trống ID");
                    break;
                }
                String regax = "P[a-zA-Z0-9]{3}";
                boolean result = Pattern.matches(regax, id);
                if (result == true) {
                    for (int i = 0; i < Store.productList.size(); i++) {
                        if (Store.productList.get(i).getId().equals(id)) {
                            checkOut = true;
                            break;// thoát for
                        }
                    }
                    if (checkOut == true) {
                        System.err.println("Đã có mã này!! Nhập lại ");
                    } else {
                        return id;
                    }
                }

            } catch (Exception e) {
                System.err.println("Có lỗi nhập mã ID");
            }
        } while (true);
        return null;
    }

    public double checkImportPrice(Scanner scanner) {
        do {
            System.out.println("Nhập vào giá nhập: ");
            try {
                double importPrice = Double.parseDouble(scanner.nextLine());
                if (importPrice > 0) {
                    return importPrice;
                } else {
                    System.err.println("Nhập giá lơn hơn 0 ");
                }
            } catch (Exception ex) {
                System.err.println("Có lỗi khi nhập giá ");
            }
        } while (true);
    }

    public String checkName(Scanner scanner) {
        Store.productList = Product.readDataToFile();
        do {
            System.out.println("Nhập vào tên sản phẩm: ");
            try {
                String nameProduct = scanner.nextLine();
                if (!nameProduct.trim().isEmpty() && nameProduct.trim().length() >= 6 && nameProduct.trim().length() < 30) {
                    if (Store.productList == null || Store.productList.isEmpty()) {
                        return nameProduct;
                    }
                    for (int i = 0; i < Store.productList.size(); i++) {
                        if (Store.productList.get(i).getName().equals(nameProduct)) {
                            System.err.println("Tên đã bị trùng");
                        } else {
                            return nameProduct;
                        }
                    }
                } else {
                    System.err.println("Tên không để trống và từ 6-> 30 ký tự ! Nhập lại ");
                }
            } catch (Exception ex) {
                System.err.println("Lỗi khi nhập tên sản phẩm");
            }
        } while (true);
    }

    public double checkExportPrice(Scanner scanner) {
        do {
            System.out.println("Nhập vào giá bán: ");
            try {
                double exportPricee = Double.parseDouble(scanner.nextLine());
                if (exportPricee <= 0) {
                    System.err.println("Giá trị nhập vào lớn hơn 0 ");
                } else if (exportPricee <= this.importPrice) {
                    System.err.println("Giá trị bán ra phải lơn hơn giá trị nhập vào");
                } else if (exportPricee < (this.importPrice + (this.importPrice * MIN_INTEREST_RATE))) {
                    System.err.println("Giá bán ra lơn hơn ít nhất 0.2 lần nhập vào");
                } else {
                    return exportPricee;
                }
            } catch (Exception ex) {
                System.err.println("Có lôi trong lúc nhập giá");
            }
        } while (true);
    }

    @Override
    public double calProfit() {
        return this.exportPrice - this.importPrice;
    }

    public static void writeDataToFile(List<Product> productList) {
        // Đọc file
        File file = new File("product.txt");
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(productList);
            oos.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static List<Product> readDataToFile() {
        List<Product> productListRead = null;
        File file = new File("product.txt");
        if (!file.exists()) {
            return new ArrayList<>();
        }
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            productListRead = (List<Product>) ois.readObject();
            return productListRead;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }
}
