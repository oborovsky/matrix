import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by oborovsky on 20.10.15.
 */
public class RectMatrix implements IMatrix {
    protected List<Double> mList;
    @Override
    public int getWidth()
    {
        return mWidth;
    }
    @Override
    public void setWidth(final int width)
    {
        mWidth = width;
    }
    @Override
    public int getHeight()
    {
        return mHight;
    }

    @Override
    public List<Double> getList()
    {
        return new ArrayList<Double>(mList);
    }

    @Override
    public void setHeight(final int hight)
    {
        mHight = hight;
    }

    protected int mWidth;
    protected int mHight;

    public RectMatrix(int w, int h)
    {
        mWidth = w;
        mHight = h;
        mList = new ArrayList<Double>(w*h);
        Random rnd = new Random();
        for (int i = 0; i < h*w; i++)
        {
            mList.set(i,rnd.nextDouble());
        }

    }
    public RectMatrix(int w, int h, List<Double> list )
    {
        mList = list;
        mWidth = w;
        mHight = h;
    }
    @Override
    public IMatrix mult(final double k)
    {
        List<Double> list = new ArrayList<Double>(mList);
        for (int i = 0; i < list.size(); i++)
        {
            list.set(i, list.get(i)*k);
        }
        return new RectMatrix(mWidth, mHight, list);

    }

    @Override
    public IMatrix mult(final IMatrix m)
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
        return new RectMatrix(w, mHight, list );
    }

    @Override
    public IMatrix add(final IMatrix m)
    {
        if (mWidth != m.getWidth() || mHight != m.getHeight())
        {
            throw new IllegalArgumentException("width or hight isn't equals");
        }
        List<Double> list = new ArrayList<Double>(mList);
        for (int i = 0; i < list.size(); i++)
        {
            list.set(i,list.get(i) + m.getList().get(i));
        }
        return  new RectMatrix(mWidth, mHight, list);
    }

    @Override
    public void setCell(final int i, final int j, final double a)
    {
        mList.set(i*mWidth+j, a);
    }

    @Override
    public IMatrix generateEij(final int i, final int j)
    {
        return null;
    }

    @Override
    public double sum()
    {
        double sum = 0;
        for (int i = 0; i < mList.size(); i++)
        {
            sum += mList.get(i);
        }
        return sum;
    }
    public static RectMatrix generateZeroMatrix(int w, int h)
    {
        List<Double> list =  new ArrayList<Double>();
        for (int i = 0; i < w*h; i++)
        {
            list.add(0.0);
        }
       return new RectMatrix(w, h,list);
    }

    @Override
    public String toString()
    {
        String str = "";
        for ( int i = 0; i < mHight; i++)
        {
            for (int j = 0; j < mWidth; j++)
            {
                str += mList.get(i*mWidth + j) + " ";
            }
            str += "\n";
        }
        return str;
    }

    public  static void main(String[] args)
    {
        double[] l1 = {1,1,2,2,3,3};
        double[] l2 = {1,1,1,1,1,1};
        List<Double> list1 = new ArrayList<Double>();
        List<Double> list2 = new ArrayList<Double>();
        for (int i = 0; i < 6; i++)
        {
            list1.add(l1[i]);
            list2.add(l2[i]);
        }

        System.out.println(list1);
        System.out.println(list2);
        try
        {
            RectMatrix m1 = new RectMatrix(2, 3, list1);
            System.out.println(m1);
            RectMatrix m2 = new RectMatrix(3, 2, list2);
            System.out.println(m2);
            RectMatrix m4 = new RectMatrix(3, 2, list1);
            System.out.println(m4);
            RectMatrix m3 = (RectMatrix) m1.mult(m2);
            System.out.println(m3);
            RectMatrix m5 = (RectMatrix) m1.mult(m4);
            System.out.println(m5);
            System.out.println(m5.mult(RectMatrix.generateZeroMatrix(3,3)));
            System.out.println(RectMatrix.generateZeroMatrix(3,2).add(m2).mult(2.0).sum());
        }
        catch ( Exception e)
        {
            System.out.println("error:" + e.getCause()+ " ->" + e.getMessage());
        }
    }
}
