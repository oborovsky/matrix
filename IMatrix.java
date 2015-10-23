import java.util.List;

/**
 * Created by oborovsky on 08.10.15.
 */
public interface IMatrix {
    int getWidth();
    int getHight();
    List<Double> getList();
    void setWidth(final int width);
    void setHight(final int hight);
    IMatrix mult(double k);
    IMatrix mult(IMatrix m);
    IMatrix add(IMatrix m);
    void setCell(int i, int j, double a);
    IMatrix generateEij(int i, int j);
    double sum();

}
