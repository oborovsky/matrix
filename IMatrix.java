import java.util.ArrayList;

/**
 * Created by oborovsky on 08.10.15.
 */
public interface IMatrix {
    int getWidth();
    int getHight();
    ArrayList<Double> getList();
    void setWidth(final int width);
    void setHight(final int hight);
    IMatrix mult(double k);
    IMatrix mult(IMatrix m);
    IMatrix add(IMatrix m);
    double sum();

}
