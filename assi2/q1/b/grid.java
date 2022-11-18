// This applet program only works on java jdk 1.8.0_312 or Lower

import java.awt.*;
import java.applet.*;
import java.awt.event.*;
// import java.lang.Thread;

public class grid extends Applet implements MouseWheelListener{

    int gap = 2;
    public void init() {
        Color bgColor = new Color(255, 255, 255);
        setBackground(bgColor);
        addMouseWheelListener(this);
    }
    // for zoom using mouse wheel
    public void mouseWheelMoved(MouseWheelEvent e) {
        int i = e.getWheelRotation();
        if (i > 0)
            gap += gap / 10 + 1;
        else if (i < 0)
            gap -= gap / 10 + 1;
        if (gap < 0)
            gap = 100;

        repaint();
    }

    // plots a square at point (Square) x,y in grid
    public void plotpoint(Graphics g, int x, int y, Color c) {
        int originX = (getX() + getWidth()) / 2;
        int originY = (getY() + getHeight()) / 2;
        g.setColor(c);
        g.fillRect(originX + (gap * x) - (gap / 2), originY - (gap * y) - (gap / 2), gap, gap);
    }

    // plots a point in x,y (Circle)
    public void plotPoint(Graphics g, int x, int y, Color c) {
        int originX = (getX() + getWidth()) / 2;
        int originY = (getY() + getHeight()) / 2;
        g.setColor(c);
        g.fillOval(originX + (gap * x) - (gap / 2), originY - (gap * y) - (gap / 2), gap, gap);
    }
    
    public void plotLineBresenham(Graphics g, int x1,int y1,int x2,int y2)
    {
        int x = x1;
        int y = y1;
        int dy = y2 - y1;
        int dx = x2-x1;
        if(Math.abs(dx)>Math.abs(dy)){
            int p = 2*dx - dy;
            while(x<x2)
            {
                plotPoint(g,x,y, Color.MAGENTA);
                x++;
                if(p<0)
                    p = p+ 2*dy;
                else{
                    p = p+2*dy-2*dx;
                    y++;
                }
            }
        }
        else{
            int p = 2*dy - dx;
            while(y<y2)
            {
                plotPoint(g,x,y,Color.MAGENTA);
                y++;
                if(p<0)
                    p = p+ 2*dx;
                else{
                    p = p+ 2*dx - 2*dy;
                    x++;
                }
            }
        }
    }

    public void paint(Graphics g) {
        int originX = (getX() + getWidth()) / 2;
        int originY = (getY() + getHeight()) / 2;
        g.setColor(Color.green);
        g.drawLine(originX, originY - getHeight() / 2, originX, originY + getHeight() / 2);
        g.drawLine(originX - getWidth() / 2, originY, originX + getWidth() / 2, originY);
        g.setColor(Color.green);

        plotLineBresenham(g,0,0,300,300);
        plotLineBresenham(g,0,0,500,300);
        plotLineBresenham(g,0,0,300,500);
        
        plotpoint(g, 0, 0, Color.yellow);

    }
}