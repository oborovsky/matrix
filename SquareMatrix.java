import java.util.ArrayList;

/**
 * Created by oborovsky on 21.10.15.
 */
public class SquareMatrix extends RectMatrix {
    public SquareMatrix(int n, ArrayList<Double> list)
    {
        super(n, n, list);
    }
    public SquareMatrix(int n, double[] array)
    {
        super(n,n);
        ArrayList<Double> list = new ArrayList<Double>();
        for (int i = 0; i < n*n; i++)
        {
            list.add(array[i]);
        }
        mList = list;
    }
    public SquareMatrix(int n)
    {
        super(n,n);
    }
    public SquareMatrix transpose()
    {
        int n = mWidth;
        ArrayList<Double> list = new ArrayList<Double>();
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                list.add(mList.get(j * n + i));
            }
        }
        return new SquareMatrix(n, list);
    }
    public double determine()
    {
        ArrayList<Double> list = new ArrayList<Double>(mList);
        int n = mWidth;
        double r = 0;
        for (int i = 0; i < n; i++)
        {
            r += sign(i) * minor(n-1, subList(i, n, list));
        }
        return r;
    }
    private int sign(int n)
    {
        if ( n % 2 == 0) return 1;
        return -1;
    }
    private ArrayList<Double> subList(int i, int width, ArrayList<Double> list)
    {
        if ( width == 1) return list;
        ArrayList<Double> l = new ArrayList<Double>();
        for (int j = 1; j < width; j++)
        {
            for (int k = 0; k < width; k++)
            {
                if ( k == i) continue;
                l.add(list.get(j * width + k));
            }
        }
        return l;
    }
    private double minor(int n, ArrayList<Double> list)
    {
        if( n == 0) return list.get(0);
        double r = 0;
        for (int i = 0; i < n; i++)
        {
            r += sign(i) * minor(n-1, subList(i, n, list));
        }
        return r;
    }
    public static SquareMatrix generateOneMatrix(int n)
    {
        ArrayList<Double> list = new ArrayList<Double>();
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                if ( j == i)
                {
                    list.add(1.0);
                }
                else
                {
                    list.add(0.0);
                }
            }
        }
        return  new SquareMatrix(n, list);
    }
    public static void main(String[] args)
    {
        ArrayList<Double> l1 = new ArrayList<Double>();
        for (int i = 0; i < 9; i++)
        {
            l1.add((double)i);
        }
        SquareMatrix m = new SquareMatrix(3,l1);
        SquareMatrix one = SquareMatrix.generateOneMatrix(3);

        System.out.println(m);
        System.out.println((SquareMatrix)m.transpose());
        System.out.println(one);
        System.out.println(SquareMatrix.generateOneMatrix(5).determine());

        System.out.println((RectMatrix)m.mult(one));
    }
}
