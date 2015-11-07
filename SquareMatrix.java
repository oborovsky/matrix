import org.w3c.dom.css.Rect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by oborovsky on 21.10.15.
 */
public class SquareMatrix  implements IMatrix {
    private IMatrix mMatrix;

    public SquareMatrix(IMatrix matrix)
    {
        int min = Math.min(matrix.getHeight(), matrix.getWidth());
        List<Double> list = matrix.getList();
        List<Double> nlist = new ArrayList<>();
        for (int i = 0; i < min; i++)
        {
            for (int j = 0; j < min; j++)
            {
                nlist.add(matrix.getCell(i,j));
            }
        }
        mMatrix = new RectMatrix(min, min, nlist);
    }
    public SquareMatrix(int n, List<Double> list)
    {
        mMatrix = new RectMatrix(n , n, list);
    }
    public SquareMatrix(int n, double[] array)
    {
        List<Double> list = new ArrayList<Double>();
        for (int i = 0; i < n*n; i++)
        {
            list.add(array[i]);
        }
        mMatrix = new RectMatrix(n, n, list);
    }
    public SquareMatrix(int n)
    {
        mMatrix = new RectMatrix(n,n);
    }
    public SquareMatrix transpose()
    {
        int n = mMatrix.getWidth();
        List<Double> cur = mMatrix.getList();
        List<Double> list = new ArrayList<Double>();
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                list.add(cur.get(j * n + i));
            }
        }
        return new SquareMatrix(n, list);
    }
    public double determine()
    {
        List<Double> list = mMatrix.getList();
        int n = mMatrix.getWidth();
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
    public static IMatrix generateOneMatrix(int n)
    {
        IMatrix tmp = RectMatrix.generateZeroMatrix(n,n);
        for (int i = 0; i < n; i++)
        {
            tmp.setCell(i,i, 1.0);
        }
        IMatrix e = new SquareMatrix(n, tmp.getList());
        return  e;
    }

    @Override
    public int getWidth()
    {
        return mMatrix.getWidth();
    }

    @Override
    public int getHeight()
    {
        return mMatrix.getHeight();
    }

    @Override
    public List<Double> getList()
    {
        return mMatrix.getList();
    }

    @Override
    public IMatrix mult(double k)
    {
        return new SquareMatrix(mMatrix.mult(k));
    }

    @Override
    public IMatrix mult(IMatrix m)
    {
        IMatrix rectMatrix = mMatrix.mult(m);
        if (rectMatrix.getHeight() == rectMatrix.getWidth()) return new SquareMatrix(rectMatrix);
        return rectMatrix;
    }

    @Override
    public IMatrix add(IMatrix m)
    {
        return new SquareMatrix(mMatrix.add(m));
    }

    @Override
    public void setCell(final int i, final int j, final double a)
    {
        mMatrix.setCell(i, j, a);
    }

    @Override
    public double getCell(final int i, final int j)
    {
        return mMatrix.getCell(i, j);
    }

    //@Override
    public IMatrix generateEij(final int i, final int j)
    {
        return new SquareMatrix(mMatrix.generateEij(i, j));
    }

    @Override
    public double sum()
    {
        return mMatrix.sum();
    }

    @Override
    public String toString()
    {
        return mMatrix.toString();
    }

    public static void main(String[] args)
    {
        List<Double> l1 = new ArrayList<Double>();
        for (int i = 0; i < 9; i++)
        {
            l1.add((double)i);
        }

        try
        {
            IMatrix m = new SquareMatrix(3,l1);
            IMatrix one = SquareMatrix.generateOneMatrix(3);

            System.out.println(m);
            System.out.println(((SquareMatrix) m).transpose());
            System.out.println(one);
            IMatrix e2 = SquareMatrix.generateOneMatrix(3);
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
            System.out.println(((SquareMatrix) m).determine());
            IMatrix E = SquareMatrix.generateOneMatrix(3);
            IMatrix m3 = E.generateEij(0, 0).mult(2).add(E.generateEij(0, 1).mult(3)).add(E.generateEij(0,2).mult(5));
            m3 = m3.add(E.generateEij(1,0).mult(3)).add(E.generateEij(1,1).mult(6)).add(E.generateEij(1,2).mult(7));
            m3 = m3.add(E.generateEij(2,0).mult(9)).add(E.generateEij(2,1).mult(11)).add(E.generateEij(2,2).mult(3));
            System.out.println(m3);
            System.out.println(((SquareMatrix) m3).determine());

//              IMatrix rectM = new RectMatrix(3,2);
//              int a = 1;
//              for (int i = 0; i < 2; i++)
//              {
//                  for (int j = 0; j < 3; j++)
//                  {
//                      rectM.setCell(i,j,a++);
//                  }
//              }
//              System.out.println(rectM);
//              SquareMatrix sM = new SquareMatrix(rectM);
//            System.out.println(sM);

        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
