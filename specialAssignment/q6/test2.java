package P1.P2;
import java.applet.*;
import java.awt.*;
public class test2 extends Applet{
    public void plotPoint(Graphics g , int x, int y)
    {
        g.setColor(Color.black);
        g.fillOval(x,y,20,20);
    }
}
