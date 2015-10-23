import java.util.ArrayList;
import java.util.List;

/**
 * Created by oborovsky on 21.10.15.
 */
public class SquareMatrix extends RectMatrix implements IMatrix {
    public SquareMatrix(int n, List<Double> list)
    {
        super(n, n, list);
    }
    public SquareMatrix(int n, double[] array)
    {
        super(n,n);
        List<Double> list = new ArrayList<Double>();
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
        List<Double> list = new ArrayList<Double>();
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
        List<Double> list = new ArrayList<Double>(mList);
        int n = mWidth;
        double r = 0;
        for (int i = 0; i < n; i++)
        {
            r += sign(i) * list.get(i)*minor(n-1, subList(i, n, list));
        }
        return r;
    }
    private int sign(int n)
    {
        if ( n % 2 == 0) return 1;
        return -1;
    }
    private List<Double> subList(int i, int width, List<Double> list)
    {
        if ( width == 1) return list;
        List<Double> l = new ArrayList<Double>();
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
    private double minor(int n, List<Double> list)
    {
        if( n == 1) return list.get(0);
        double r = 0;
        for (int i = 0; i < n; i++)
        {
            r += sign(i) * list.get(i) * minor(n-1, subList(i, n, list));
        }
        return r;
    }
    public static SquareMatrix generateOneMatrix(int n)
    {
        List<Double> list = new ArrayList<Double>();
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

    @Override
    public IMatrix mult(double k)
    {
        List<Double> list = new ArrayList<Double>(mList);
        for (int i = 0; i < list.size(); i++)
        {
            list.set(i, list.get(i)*k);
        }
        return new SquareMatrix(mWidth, list);

    }

    @Override
    public IMatrix mult(IMatrix m)
    {
        if (mWidth != m.getHeight())
        {
            throw new IllegalArgumentException("the width of the first matrix isn't equals to the hight of the second");
        }
        List<Double> list = new ArrayList<Double>();
        List<Double> m1 = getList();
        List<Double> m2 = m.getList();
        int w = m.getWidth();
        for (int i = 0; i < mHight; i++)
        {
            for (int j = 0; j < m.getWidth(); j++)
            {
                double r = 0;
                for (int k = 0; k < mWidth; k++)
                {
                    r += m1.get(i*mWidth+k) * m2.get(w*k + j);
                }
                list.add(r);
            }
        }
        return new SquareMatrix(w, list );
    }

    @Override
    public IMatrix add(IMatrix m)
    {
        return new SquareMatrix(mWidth,super.add(m).getList());
    }

    //@Override
    public IMatrix generateEij(final int i, final int j)
    {
        List<Double> list = (RectMatrix.generateZeroMatrix(mWidth, mWidth)).getList();
        list.set(i*mWidth + j,1.0);
        return new SquareMatrix(mWidth, list);
    }

    public static void main(String[] args)
    {
        List<Double> l1 = new ArrayList<Double>();
        for (int i = 0; i < 9; i++)
        {
            l1.add((double)i);
        }
        SquareMatrix m = new SquareMatrix(3,l1);
        SquareMatrix one = SquareMatrix.generateOneMatrix(3);

        System.out.println(m);
        System.out.println(m.transpose());
        System.out.println(one);
        SquareMatrix e2 = SquareMatrix.generateOneMatrix(3);
        IMatrix mm = e2.mult(2.0);
//        e2 = (SquareMatrix) e2.mult(2.0);
        mm.setCell(0,0,1.0);
        mm.setCell(0,1,2.0);
        mm.setCell(0, 2, 3.0);
        mm.setCell(1, 0, 1.0);
        mm.setCell(1, 1, 2.0);
        mm.setCell(1, 2, 3.0);
        mm.setCell(2, 0, 1.0);
        mm.setCell(2, 1, 2.0);
        mm.setCell(2, 2, 3.0);
        System.out.println(mm);
        System.out.println(((SquareMatrix)mm).determine());
        System.out.println(m);
        System.out.println(m.determine());
        SquareMatrix E = SquareMatrix.generateOneMatrix(3);
        IMatrix m3 = E.generateEij(0, 0).mult(2).add(E.generateEij(0, 1).mult(3)).add(E.generateEij(0,2).mult(5));
        m3 = m3.add(E.generateEij(1,0).mult(3)).add(E.generateEij(1,1).mult(6)).add(E.generateEij(1,2).mult(7));
        m3 = m3.add(E.generateEij(2,0).mult(9)).add(E.generateEij(2,1).mult(11)).add(E.generateEij(2,2).mult(3));
        System.out.println(m3);
        System.out.println(((SquareMatrix) m3).determine());

    }
}
