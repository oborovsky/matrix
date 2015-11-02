import java.util.List;

/**
 * Created by oborovsky on 08.10.15.
 */
public interface IMatrix {
    int getWidth();
    int getHeight();
    List<Double> getList();
    IMatrix mult(double k);
    IMatrix mult(IMatrix m);
    IMatrix add(IMatrix m);
    void setCell(int i, int j, double a);
    double getCell(int i, int j);
    IMatrix generateEij(int i, int j);
    double sum();

}
