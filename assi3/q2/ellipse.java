import java.awt.*;
import java.applet.*;
import java.awt.event.*;
public class ellipse extends Applet implements MouseWheelListener{
    int gap = 8;
 
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
    public void plotArcs(Graphics g , int x ,int y,int cx,int cy)
    {
        plotPoint(g,cx+x,cy+y);
        plotPoint(g,cx-x,cy+y);
        plotPoint(g,cx-x,cy-y);
        plotPoint(g,cx+x,cy-y);
    }
    public void makeEllipse(Graphics g  ,int cx, int cy , int rx , int ry )
    {
        g.setColor(Color.red);
        int x = 0;
        int y = ry;
        
        double p = ry*ry - rx*rx*ry +0.25*rx*rx;
        while(x*ry*ry<=y*rx*rx)
        {
            plotArcs(g, x, y,cx,cy);
            x = x+1;
            if(p<0)
            {
                plotArcs(g, x, y,cx,cy);
                p = p +2.0*x*ry*ry + ry*ry; 
            }
            else{
                y = y-1;
                plotArcs(g, x, y,cx,cy);
                p = p + 2.0*x*ry*ry - 2*y*rx*rx +ry*ry; 
            }
        } 
        p = ry*ry*Math.pow(x+0.5,2) + rx*rx*Math.pow(y-1,2) - Math.pow(rx*ry,2);
        while(x*ry*ry>=y*rx*rx)
        {
            y = y-1;
            if(p>0)
            {
                plotArcs(g, x, y,cx,cy);
                p = p - 2.0*y*rx*rx + rx*rx; 
            }
            else{
                x= x+1;
                plotArcs(g, x, y,cx,cy);
                p = p + 2.0*x*ry*ry - 2*y*rx*rx +rx*rx; 
            }
            if(y==0)
                break;
        } 
    }
    public void paint(Graphics g){
        makeGrid(g);
        // makeEllipse(g,40,40,30 ,15);
        // makeEllipse(g,-40,40,30 ,15);
        // makeEllipse(g,0,0,100 ,100);
        // makeEllipse(g,0,-50,30 ,40);
        makeEllipse(g,0,0,20,40);
    }
}