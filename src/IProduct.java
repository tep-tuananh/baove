import java.util.Scanner;

public interface IProduct {
    public final  float MIN_INTEREST_RATE = 0.2F;// lãi suất nhỏ trên từng sản phẩm
    void inputData(Scanner scanner);
    void displayData();
    double calProfit();

}
