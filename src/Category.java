import java.io.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Category implements ICategory, Serializable {
    private static final long serialVersionUID = 1616235331741790636L;
    private int id;//phải lớn hơn 0 và duy nhất
    private String name;//không trùng lặp và duy nhất
    private String description;// không để trống
    private boolean status;

    public Category() {
    }

    public Category(int id, String name, String description, boolean status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public void inputData(Scanner scanner) {
        this.id = checkID(scanner, Store.categoryList);
        this.name = checkName(scanner, Store.categoryList);
        this.description = checkDescription(scanner);
        this.status = checkStatus(scanner);
        Category category = new Category(this.id, this.name, this.description, this.status);
        Store.categoryList.add(category);
    }

    public void update(Scanner scanner, List<Category> categoryList) {
        Store.categoryList = Category.readDataToFile();
        System.out.println("Nhập mã cần cập nhật: ");
        try {
            int idUpdate = Integer.parseInt(scanner.nextLine());
            boolean checkOut = true;
            for (int i = 0; i < Store.categoryList.size(); i++) {
                if (Store.categoryList.get(i).getId() == idUpdate) {
                    do {
                        System.out.println("************ Menu Cập Nhât **************");
                        System.out.println("1. Cập nhật tên danh muc");
                        System.out.println("2. Cập nhật mô tả danh mục");
                        System.out.println("3. Cập nhật lại trạng thái");
                        System.out.println("4. Thoát cập nhật");
                        System.out.println("Nhập vào lựa chọn của bạn: ");
                        try {
                            int luachon = Integer.parseInt(scanner.nextLine());
                            switch (luachon) {
                                case 1:
                                    String newName = checkName(scanner, Store.categoryList);
                                    for (int k = 0; k < Store.categoryList.size(); k++) {
                                        if (Store.categoryList.get(k).equals(newName)) {
                                            System.err.println("Tên đã tồn tại");
                                        }
                                    }
                                    Store.categoryList.get(i).setName(newName);
                                    Category.writeDataToFile(Store.categoryList);
                                    System.out.println("Cập nhật thành công");
                                    break;
                                case 2:
                                    System.out.println("Nhập vào mô tả danh mục mới: ");
                                    String newDescription = scanner.nextLine();
                                    if (newDescription.trim().isEmpty()) {
                                        System.err.println("Không được để trống");
                                    } else {
                                        Store.categoryList.get(i).setDescription(newDescription);
                                        Category.writeDataToFile(Store.categoryList);
                                        System.out.println("Cập nhật thành công");
                                    }
                                    break;
                                case 4:
                                    checkOut = false;
                                    return;
                                default:
                                    System.err.println("Nhập từ 1-> 4 ");
                            }
                        } catch (Exception ex) {
                            System.err.println("Lỗi khi nhập mục chọn");
                        }
                    } while (checkOut);
                }
            }
            System.err.println("Không có mã này");
        } catch (Exception ex) {
            System.err.println("Lỗi nhập mã cập nhật");
        }
    }

    public void searchCategoryName(Scanner scanner) {
        Store.categoryList = Category.readDataToFile();
        System.out.println("Nhập vào tên danh mục cần tìm: ");
        try {
            String nameCategory = scanner.nextLine();
            for (int i = 0; i < Store.categoryList.size(); i++) {
                if (nameCategory.trim().startsWith(Store.categoryList.get(i).getName())) {
                    PrintStream out = System.out;
                    out.printf("----------------------------------------------------------------------------------\n");
                    out.printf("| %-10s | %-20s | %-30s | %-10s |%n", "ID", "Tên", "Mô tả", "Trạng thái");
                    out.printf("----------------------------------------------------------------------------------\n");
                    Store.categoryList.get(i).displayData();
                    return;
                }
            }
            System.out.println("Không có danh mục này");
        } catch (Exception ex) {
            System.out.println("Lỗi khi nhập tên danh mục ");
        }
    }

    public void deleteByIdCategory(Scanner scanner) {
        Store.categoryList = Category.readDataToFile();
        Store.productList = Product.readDataToFile();
        System.out.println("Nhập vào ID cần xóa: ");
        try {
            int deleteId = Integer.parseInt(scanner.nextLine());
            boolean checkDelete = false;
            boolean check=true;
            for (Category category : Store.categoryList) {
                if (category.getId() == deleteId) {
                    for (int i = 0; i < Store.categoryList.size(); i++) {
                        if (Store.categoryList.get(i).getId() == deleteId) {
                            for (int j = 0; j < Store.productList.size(); j++) {
                                if (deleteId == Store.productList.get(j).getCategoryId()) {
                                    checkDelete = true;
                                    break;
                                }
                            }
                            if (checkDelete == true) {
                                System.err.println("Đang có sản phẩm không thể xóa");
                                break;
                            } else {
                                Store.categoryList.remove(i);
                                System.out.println("Xóa thành công\n");
                                Category.writeDataToFile(Store.categoryList);
                                mold();
                                for (int k = 0; k < Store.categoryList.size(); k++) {
                                    Store.categoryList.get(k).displayData();
                                }
                                return;
                            }

                        }
                    }
                }
            }
            if(!checkDelete) {
                System.err.println("Không có mã này");
                return;
            }
        } catch (Exception ex) {
            System.err.println("Lỗi trong lúc nhập ID ");
        }
    }

    public void statisticalCategory() {
        Store.categoryList = Category.readDataToFile();
        Store.productList = Product.readDataToFile();
        int count = 0;
        moldCategory();
        for (int i = 0; i < Store.categoryList.size(); i++) {
            int coutIndex = Store.categoryList.get(i).getId();
            for (int j = 0; j < Store.productList.size(); j++) {
                if (coutIndex == Store.productList.get(j).getCategoryId()) {
                    count += 1;
                }
            }

            PrintStream out = System.out;
            out.printf("| %20s | | %11d |\n", Store.categoryList.get(i).getName(), count);
            out.printf("|--------------------------------------|\n");
            count = 0;
        }

    }

    public void moldCategory() {
        PrintStream out = System.out;
        out.printf("|--------------------------------------|\n");
        out.printf("| %-20s | | %-5s |%n", "Tên danh mục", "Số sản phẩm");
        out.printf("|--------------------------------------|\n");
    }

    public int checkID(Scanner scanner, List<Category> categorie) {
        do {
            System.out.println("Nhập vào mã danh mục: ");
            try {
                boolean check = false;
                int id = Integer.parseInt(scanner.nextLine());
                for (int i = 0; i < categorie.size(); i++) {
                    {
                        if (categorie.get(i).getId() == id) {
                            check = true;// xuất hiện
                        }
                    }
                }
                if (check) {
                    System.err.println("Đã  trùng ID.Nhập lại");
                } else {
                    return id;
                }
            } catch (Exception ex) {
                System.err.println("Hãy Nhập vào số");
            }
        } while (true);
    }

    public String checkName(Scanner scanner, List<Category> categories) {
        do {
            System.out.println("Nhập vào tên danh mục: ");
            try {
                boolean check = false;// chưa xuất hiện tên này
                String name = scanner.nextLine();
                if (name.trim().length() >= 6 && name.trim().length() <= 30) {
                    for (int i = 0; i < categories.size(); i++) {
                        if (categories.get(i).getName().equals(name)) {
                            check = true;// đã xuất hiện tên này
                        }
                    }
                    if (check) {
                        System.err.println("Đã có tên này! Nhập lại");
                    } else {
                        return name;
                    }
                } else {
                    System.err.println("Tên danh mục từ 6-30 ký tự");
                }
            } catch (Exception ex) {
                System.out.println("Có lỗi");
            }
        } while (true);
    }

    public String checkDescription(Scanner scanner) {
        do {
            System.out.println("Nhập vào mô tả danh mục: ");
            try {
                String descriptionn = scanner.nextLine();
                if (descriptionn.trim().isEmpty()) {
                    System.err.println("Không được để trống");
                } else {
                    return descriptionn;
                }
            } catch (Exception ex) {
                System.out.println("Có lỗi ");
            }
        } while (true);
    }

    public boolean checkStatus(Scanner scanner) {
        do {
            System.out.println("Nhập vào trạng thái: ");
            try {
                String status = scanner.nextLine();
                if (status.equals("true") || status.equals("false")) {
                    return Boolean.parseBoolean(status);
                } else {
                    System.err.println("Chỉ nhận giá trị true | false");
                }
            } catch (Exception ex) {
                System.err.println("Lỗi nhập trạng thái");
            }
        } while (true);
    }

    @Override
    public void displayData() {
        PrintStream out = System.out;
        out.printf("|%10d | %20s | %30s | %11b |%n", this.id, this.name, this.description, ((this.status = true) ? "Hoạt động" : "Dừng hoạt đông"));
        out.printf("|---------------------------------------------------------------------------------|\n");

    }

    public static void mold() {
        PrintStream out = System.out;
        out.printf("|---------------------------------------------------------------------------------|\n");
        out.printf("| %-10s | %-20s | %-30s | %-10s |%n", "ID", "Tên", "Mô tả", "Trạng thái");
        out.printf("|---------------------------------------------------------------------------------|\n");

    }

    public static void writeDataToFile(List<Category> categoryList) {
        // Đọc file
        File file = new File("category.txt");
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(categoryList);
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

    public static List<Category> readDataToFile() {
        List<Category> categoryListRead = null;
        File file = new File("category.txt");
        if (!file.exists()) {
            return new ArrayList<>();
        }
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            categoryListRead = (List<Category>) ois.readObject();
            return categoryListRead;
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
