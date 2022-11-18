// This applet program only works on java jdk 1.8.0_312 or Lower

import java.awt.*;
import java.applet.*;
import java.awt.event.*;

public class grid extends Applet implements ActionListener ,MouseWheelListener{
    
    Button b1 = new Button();
    Button b2 = new Button(" - ");
    int gap =50;
    public void init() {
        b1.setLabel(" + ");
        b1.setSize(300, 300);
        Color buttonColor1 = new Color(0,255,0);
        Color buttonColor2 = new Color(255,0,0);
        Color bgColor = new Color(0,0,0);
        b1.setBackground(buttonColor1);
        b2.setBackground(buttonColor2);
        
        add(b1);
        add(b2);
        addMouseWheelListener(this);
        b1.addActionListener(this);
        b2.addActionListener(this);
        
        setBackground(bgColor);
    }
    //for zoom using mouse wheel
    public void mouseWheelMoved(MouseWheelEvent e)
    {
        int z = e.getWheelRotation();
        zoom(z);
    }
    //button action listener
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1)
            zoom(+10);
        else if(e.getSource()==b2)
            zoom(-10);
    }
    //function to make a grid with respect to standard cartesian coordinates
    public void makeGrid(Graphics g, int gap)
    {
        if(gap<=0|| gap>getHeight())
            return ;
        int originX = (getX() + getWidth()) / 2;
        int originY = (getY() + getHeight()) / 2;
        g.setColor(Color.green);
        g.drawLine(originX, originY - getHeight() / 2, originX, originY + getHeight() / 2);
        g.drawLine(originX - getWidth() / 2, originY, originX + getWidth() / 2, originY);
        g.setColor(Color.green);
        for (int x = gap; x <= getWidth(); x += gap) {
            // g.drawLine(originX+x,originY-height,originX+x, originY+height);
            g.drawLine(originX + x, 0, originX + x, getHeight());
            g.drawLine(originX - x, 0, originX - x, getHeight());
            // g.drawLine(originX-x,originY-height,originX-x, originY+height);
        }
        for (int y = gap; y <= getHeight(); y += gap) {
            // g.drawLine(originX-width,originY+y,originX+width,originY+y);
            // g.drawLine(originX-width,originY-y,originX+width,originY-y);
            g.drawLine(0, originY + y, getWidth(), originY + y);
            g.drawLine(0, originY - y, getWidth(), originY - y);
        }
        
        // try {
        //     Thread.sleep(100);
        // } catch (Exception e) {
        //     System.out.println("Error occurred");
        // }
        // g.clearRect(0, 0, getWidth(), getHeight());
        
    }
    public void zoom(int i)
    {
        if(i>0)
            gap+=gap/10+1;
        else if(i<0)
            gap-=gap/10+1;
        if(gap<0)
            gap = 100;
    
        System.out.println(gap);
        repaint();
        
    }
    //plots a square at point (Square) x,y in grid
    public void plotpoint(Graphics g, int x,int y ,Color c){
        int originX = (getX() + getWidth()) / 2;
        int originY = (getY() + getHeight()) / 2;
        g.setColor(c);
        g.fillRect(originX+(gap*x)-(gap/2), originY-(gap*y)-(gap/2),gap ,gap);
    }
    //plots a point in x,y (Circle)
    public void plotCircle(Graphics g, int x,int y ,Color c){
        int originX = (getX() + getWidth()) / 2;
        int originY = (getY() + getHeight()) / 2;
        g.setColor(c);
        g.fillOval(originX+(gap*x)-(gap/8), originY-(gap*y)-(gap/8),gap/4 ,gap/4 );
    }

    public void plotLineMidPoint(Graphics g, int x1, int y1 , int x2,int y2)
    {
        double m = Math.abs((double)(y2-y1)/(x2-x1));
        
        int x = x1;
        int y = y1;
        plotpoint(g,x,y,Color.red);
        if(m<1){
            double p = 0.5 - m;
            while(x<=x2)
            {
                x = x+1;
                if(p<0){
                    y=y+1;
                    plotpoint(g,x,y,Color.red);
                     p = p+1-m;
                }
                else{
                    plotpoint(g,x,y,Color.red);
                    p = p-m;
                }
            }
        }
        
    }

    public void paint(Graphics g) 
    {
        
        makeGrid(g,gap);
        plotCircle(g,0,0,Color.yellow);
        plotLineMidPoint(g,0,0,400, 300);

    }
}
