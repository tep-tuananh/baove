
import java.io.PrintStream;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Store {
    public static List<Category> categoryList = new ArrayList<>();
    public static List<Product> productList= new ArrayList<>();
    public static void main(String[] args) {
        try {
            categoryList = Category.readDataToFile();
            productList=Product.readDataToFile();
        }catch (Exception ex)
        {
            System.out.println("Lỗi : "+ex);
        }
        Scanner scanner = new Scanner(System.in);
        boolean checkOut = true;
        do {
            System.out.println("\n");
            System.out.println("---------QUẢN LÝ KHO-----------");
            System.out.println("1. Quản lý danh mục");
            System.out.println("2. Quản lý sản phẩm ");
            System.out.println("3. Thoát");
            System.out.println("Nhập vào lựa chọn của bạn: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        MenuCategory(scanner);
                        break;
                    case 2:
                        menuProduct(scanner);
                        break;
                    case 3:
                        checkOut = false;
                        break;
                }
            } catch (Exception ex) {
                System.out.println("Có lỗi nhập: " + ex);
            }
        } while (checkOut);

    }

    public static void MenuCategory(Scanner scanner) {
        boolean chekOut = true;
        Category category = new Category();
        do {
            System.out.println("\n");
            System.out.println("********** QUẢN LÝ DANH MỤC **************");
            System.out.println("1. Thêm mới danh mục");
            System.out.println("2. Cập nhật danh mục");
            System.out.println("3. Xóa danh mục");
            System.out.println("4. Tìm kiếm danh mục theo tên danh mục");
            System.out.println("5. Thống kê số lượng sản phẩm đang có trong danh mục ");
            System.out.println("6. Quay lại");
            System.out.println("0 . Để hiển thị thông tin trong file");
            System.out.println("Nhập vào lựa chọn của bạn: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        System.out.println("Nhập vào số danh mục cần thêm");
                        try {
                            int n = Integer.parseInt(scanner.nextLine());
                            for (int i = 0; i < n; i++) {
                                category.inputData(scanner);
                                Category.writeDataToFile(Store.categoryList);
                            }
                        } catch (Exception ex) {
                            System.out.println("Lỗi nhập " + ex);
                        }
                        break;
                    case 2:
                        category.update(scanner,categoryList);
                        break;
                    case 3:
                            category.deleteByIdCategory(scanner);
                        break;
                    case 4:
                        category.searchCategoryName(scanner);
                        break;
                    case 5:
                            category.statisticalCategory();
                        break;
                    case 6:
                        chekOut=false;
                        break;
                    case 0:
                        Store.categoryList=Category.readDataToFile();
                        if(Store.categoryList==null)
                        {
                            System.err.println("Lỗi đọc file");
                        }
                        else {
                            category.mold();
                            for (int i = 0; i < Store.categoryList.size(); i++) {
                                Store.categoryList.get(i).displayData();
                            }
                        }
                        break;
                    default:
                        System.out.println("Hãy lựa chọn từ 1-6");
                }
            } catch (Exception ex) {
                System.out.println("Có lỗi nhập lựa chọn: " + ex);
            }
        } while (chekOut);
    }
    public static void menuProduct(Scanner scanner)
    {
        boolean checkOut=true;
        Product product = new Product();
        do {
            System.out.println("\n============= QUẢN LÝ SẢN PHẨM ============");
            System.out.println("1. Thêm mới sản phẩm");
            System.out.println("2. Cập nhật sản phẩm");
            System.out.println("3. Xóa sản phẩm");
            System.out.println("4. Hiển thị sản phẩm theo tên A-Z");
            System.out.println("5. Hiển thị sản phẩm theo lợi nhuận từ cao -> thấp");
            System.out.println("6. Tìm kiếm sản phẩm");
            System.out.println("7. Quay lại");
            System.out.println("0 . Hiển thị danh sách trong file");
            System.out.println("Lựa chọn của bạn: ");
            try {
                int choice= Integer.parseInt(scanner.nextLine());
                switch (choice)
                {
                    case 1:
                        System.out.println("Nhập vào số sản phẩm  : ");
                        try {
                            int n=Integer.parseInt(scanner.nextLine());
                            for(int i= 0;i<n;i++)
                            {
                               product.inputData(scanner);
                               productList.add(product);
                               Product.writeDataToFile(Store.productList);
                               product.khuon();
                               product.displayData();
                            }
                        }catch (Exception ex)
                        {
                            System.err.println("Có lỗi nhập số lượng");
                        }
                        break;
                    case 2:
                        product.updateById(scanner);
                        break;
                    case 3:
                        product.deleteIdProduct(scanner,product);
                        break;
                    case 4:
                        product.khuon();
                            product.sortProductName();
                        break;
                    case 5:
                        product.khuon();
                        product.sortProductProfit();
                        break;
                    case 6:
                        product.searchProductname(scanner,product);
                        break;
                    case 7:
                        checkOut=false;
                        break;
                    case 0:
                       Store.productList=Product.readDataToFile();
                        if(Store.productList==null)
                        {
                            System.err.println("Lỗi đọc file hoặc file đang rỗng");
                        }
                        else {
                            product.khuon();
                            for (int i = 0; i < Store.productList.size(); i++) {
                                Store.productList.get(i).displayData();
                            }
                        }
                        break;
                    default:
                    {
                        System.out.println("Nhâp lựa chọn từ 1 - > 7 ");
                    }
                }
            }catch (Exception ex)
            {
                System.err.println("Lỗi nhập lựa chọn ");
            }
        }while (checkOut);
    }
}
