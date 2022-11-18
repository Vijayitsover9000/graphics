package P1;
import P1.P2.test2;
import P1.P2.P3.test3;
import java.awt.*;
import java.applet.*;
public class test1 extends Applet
{
    public void init()
    {
        setBackground(Color.green);

    }
    public void paint(Graphics g)
    {
        test2 a = new test2();
        a.plotPoint(g,50,100);
        test3 b = new test3();
        b.drawRect(g);  
    }
}
