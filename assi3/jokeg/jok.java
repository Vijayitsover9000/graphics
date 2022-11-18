import java.awt.*;
import java.applet.*;
import java.awt.event.*;
public class jok extends Applet implements MouseWheelListener{
    int gap = 2;
    
    public void init(){
        setBackground(Color.white);
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
    
    public void gradient(Color c){


    }
    void delay(int d){
        try{
            Thread.sleep(d);
        }catch(Exception e)
        {
            //to do :handle exception
        }
    }
    
    public void paint(Graphics g){
        int r = 255;
        int gr = 255;
        int b = 255;
        int k = 0;
        Color c;
        for(int i = 0;i<getHeight();i++)
        {
            int t = i;
            r = t%255;
            t-=r;

            gr = t%255;
            t-= gr;
            b = t%255;
            if(r+b+gr>250*3)
            
            if(k==0)
                c = new Color(r,gr,b);
            if(k==0)
                c = new Color(gr,r,b);
            else
                c = new Color(b,r,gr);
            g.setColor(c);
            g.drawLine(0,i,getHeight(),i);
            delay(10);
        }
        
    }
}