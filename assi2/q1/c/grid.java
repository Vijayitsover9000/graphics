// This applet program only works on java jdk 1.8.0_312 or Lower

import java.awt.*;
import java.applet.*;
import java.awt.event.*;

public class grid extends Applet implements MouseWheelListener {

    int gap = 50;

    public void init() {

        Color bgColor = new Color(255, 255, 255);
        addMouseWheelListener(this);
        setBackground(bgColor);
    }

    // for zoom using mouse wheel
    public void mouseWheelMoved(MouseWheelEvent e) {
        int z = e.getWheelRotation();
        zoom(z);
    }

    // function to make a grid with respect to standard cartesian coordinates
    // public void makeGrid(Graphics g, int gap) {
    // if (gap <= 0 || gap > getHeight())
    // return;
    // int originX = (getX() + getWidth()) / 2;
    // int originY = (getY() + getHeight()) / 2;
    // g.setColor(Color.green);
    // g.drawLine(originX, originY - getHeight() / 2, originX, originY + getHeight()
    // / 2);
    // g.drawLine(originX - getWidth() / 2, originY, originX + getWidth() / 2,
    // originY);
    // g.setColor(Color.green);
    // for (int x = gap; x <= getWidth(); x += gap) {
    // g.drawLine(originX + x, 0, originX + x, getHeight());
    // g.drawLine(originX - x, 0, originX - x, getHeight());
    // }
    // for (int y = gap; y <= getHeight(); y += gap) {
    // g.drawLine(0, originY + y, getWidth(), originY + y);
    // g.drawLine(0, originY - y, getWidth(), originY - y);
    // }
    // }

    public void zoom(int i) {
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

    public void plotLineMidPoint(Graphics g, int x1, int y1, int x2, int y2) {
        double m = (double) (y2 - y1) / (x2 - x1);

        int x = x1;
        int y = y1;
        plotPoint(g, x, y, Color.red);
        if (m >= 0) {
            if (Math.abs(m) < 1) {
                double p = 0.5 - m;
                while (x < x2) {
                    x = x + 1;
                    if (p < 0.0) {
                        y = y + 1;
                        plotPoint(g, x, y, Color.red);
                        p = p + 1.0 - m;
                    } else {
                        plotPoint(g, x, y, Color.red);
                        p = p - m;
                    }
                }
            }

            if (Math.abs(m) >= 1) {
                double p = 1.0 - m / 2.0;
                while (y < y2) {
                    y = y + 1;
                    if (p < 0) {

                        plotPoint(g, x, y, Color.red);
                        p = p + 1;
                    } else {
                        x = x + 1;
                        plotPoint(g, x, y, Color.red);
                        p = p - m + 1.0;
                    }
                }
            }
        }
        
        if (m < 0) {
            
            if (Math.abs(m) < 1) {
                x = x1<x2?x1:x2;
                if(x==x2)
                {
                    y = y2;
                    y2 = y1;
                    x2 = x1;
                }
                double p = 0.5 + m;
                while (x < x2) {
                    x = x + 1;
                    if (p < 0.0) {
                        y = y - 1;
                        plotPoint(g, x, y, Color.red);
                        p = p + 1.0 + m;
                    } else {
                        plotPoint(g, x, y, Color.red);
                        p = p + m;
                    }
                }
            }
            
            if (Math.abs(m) >= 1) {

                y = y1<y2?y1:y2;
                if(y==y2)
                {
                    x = x2;
                    y2 = y1;
                    x2 = x1;
                }
                double p = 1.0 + m / 2.0;
                while (y < y2) {
                    y = y + 1;
                    if (p < 0) {

                        plotPoint(g, x, y, Color.red);
                        p = p + 1;
                    } else {
                        x = x - 1;
                        plotPoint(g, x, y, Color.red);
                        p = p + m + 1.0;
                    }
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

        plotLineMidPoint(g, 0, 0, 50, 100);
        plotLineMidPoint(g, 0, 0, 100, 50);
        plotLineMidPoint(g, 0, 0, 75, 75);
        plotLineMidPoint(g, -100, -100, 200, 100);
        plotLineMidPoint(g,0 , 0, -100 ,100);
        plotLineMidPoint(g,0 , 0, -100 ,200);
        plotLineMidPoint(g,0 , 0, -200 ,100);

        plotLineMidPoint(g,-100 , -300, 0 ,0);
        plotpoint(g, 0, 0, Color.yellow);

    }
}