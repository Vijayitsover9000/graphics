// This applet program only works on java jdk 1.8.0_312 or Lower

import java.awt.*;
import java.applet.*;

public class grid extends Applet{

    int gap =50;
    public void init() {
        int a = 0, b = 0, c = 0;
        Color bgColor = new Color(a,b,c);
        setBackground(bgColor);
    }
   
    //function to make a grid with respect to standard cartesian coordinates
    public void makeGrid(Graphics g, int gap)
    {
        if(gap<=0|| gap>getHeight())
            return ;
        int originX = (getX() + getWidth()) / 2;
        int originY = (getY() + getHeight()) / 2;
        g.setColor(Color.green);
        for (int x = gap; x <= getWidth(); x += gap) {
            // g.drawLine(originX+x,originY-height,originX+x, originY+height);
            g.drawLine(originX + x, 0, originX + x, getHeight());
            g.drawLine(originX - x, 0, originX - x, getHeight());
         
        }
        for (int y = gap; y <= getHeight(); y += gap) {
            g.drawLine(0, originY + y, getWidth(), originY + y);
            g.drawLine(0, originY - y, getWidth(), originY - y);
        }
    }
   
    //plots a square at point (Square) x,y in grid
    public void plotpoint(Graphics g, int x,int y ,Color c){
        int originX = (getX() + getWidth()) / 2;
        int originY = (getY() + getHeight()) / 2;
        g.setColor(c);
        g.fillRect(originX+(gap*x)-(gap/4), originY-(gap*y)-(gap/4),gap/2 ,gap/2 );
    }
    //plots a point in x,y (Circle)
    public void plotCircle(Graphics g, int x,int y ,Color c){
        int originX = (getX() + getWidth()) / 2;
        int originY = (getY() + getHeight()) / 2;
        g.setColor(c);
        g.fillOval(originX+(gap*x)-(gap/8), originY-(gap*y)-(gap/8),gap/4 ,gap/4 );
    }
    public void paint(Graphics g) 
    {
        int originX = (getX() + getWidth()) / 2;
        int originY = (getY() + getHeight()) / 2;
        g.setColor(Color.red);
        g.drawLine(originX, originY - getHeight() / 2, originX, originY + getHeight() / 2);
        g.drawLine(originX - getWidth() / 2, originY, originX + getWidth() / 2, originY);
        
        makeGrid(g,gap);
        
        Color c = new Color(100,100,200);
        plotCircle(g,0,0,Color.yellow);
        plotpoint(g,2,1 , c);

    }
}
