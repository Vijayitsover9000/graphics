import java.awt.*;
import java.applet.*;
import java.awt.event.*;
public class circle extends Applet implements MouseWheelListener{
    int gap = 10;
    
    public void init(){
        setBackground(Color.black);
        addMouseWheelListener(this);
    }
    public void mouseWheelMoved(MouseWheelEvent e)
    {
        int z = e.getWheelRotation();
        zoom(z);
    }
    public void zoom(int i)
    {
        if(i>0)
            gap+=gap/10+1;
        else if(i<0)
            gap-=gap/10+1;
        if(gap<0)
            gap = 100;
        repaint();
    }
    public void makeGrid(Graphics g)
    {
        int originX = (getX()+getWidth())/2;
        int originY = (getY()+getHeight())/2;
        g.setColor(Color.green);
        g.drawLine(0,originY,getWidth(),originY);
        g.drawLine(originX,0,originX ,getHeight());
    }
    public void plotPoint(Graphics g , int x,int y){
        int originX = (getX()+getWidth())/2;
        int originY = (getY()+getHeight())/2;
        g.fillOval(originX+x*gap-gap/2 , originY-y*gap-gap/2 , gap, gap);
    }
    public void plotArcs(Graphics g , int x ,int y)
    {
        plotPoint(g,x,y);
        plotPoint(g,-x,y);
        plotPoint(g,-x,-y);
        plotPoint(g,x,-y);
        plotPoint(g,y,x);
        plotPoint(g,-y,x);
        plotPoint(g,-y,-x);
        plotPoint(g,y,-x);
    }
    public void makeCircle(Graphics g  ,int cx, int cy , int r)
    {
        int x = 0;
        int y = r;
        plotPoint(g, x, y);
        double p = 5.0/4.0 - r;
        while(x<y)
        {
            x = x+1;
            if(p<0)
            {
                plotArcs(g, x, y);
                p = p +2.0*x + 1.0; 
            }
            else{
                y = y-1;
                plotArcs(g, x, y);
                p = p + 2*x +1.0 - 2*y; 
            }
        } 
    }
    public void paint(Graphics g){
        makeGrid(g);
        makeCircle(g,20,20,20);
    }
}