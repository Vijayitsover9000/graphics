import java.awt.*;
import java.applet.*;
public class newShape extends Applet{
    Graphics g;
    int width ;
    int height;
    newShape(Graphics g1 , int w,int h)
    {
        g = g1;
        width = w;
        height = h;
    }
    public void Rect(){
        g.setColor(Color.black);
	    int x = width/2;
	    int y  = height/2;
        g.fillRect(x-150, y-50, 300, 100);
    }
    public void Square(){
        g.setColor(Color.gray);
        int x = width/2;
        int y = height/2;
        g.fillRect(x-100,y-100,200,200);
    }
    
}
