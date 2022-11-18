import java.awt.*;
import java.applet.*;
import java.awt.event.*;
public class circle extends Applet implements MouseWheelListener{
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
    //function to make a grid with respect to standard cartesian coordinates
    public void makeGrid1(Graphics g)
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
            g.drawLine(originX + x, 0, originX + x, getHeight());
            g.drawLine(originX - x, 0, originX - x, getHeight());
        }
        for (int y = gap; y <= getHeight(); y += gap) {
            g.drawLine(0, originY + y, getWidth(), originY + y);
            g.drawLine(0, originY - y, getWidth(), originY - y);
        }
    }    
    public void plotPoint(Graphics g , int x,int y){
        int originX = (getX()+getWidth())/2;
        int originY = (getY()+getHeight())/2;
        g.fillOval(originX+x*gap-gap/2 , originY-y*gap-gap/2 , gap, gap);
    }
    public void plotArcs(Graphics g , int x ,int y,int cx,int cy)
    {
        plotPoint(g, x+cx, y+cy);
        plotPoint(g,-x+cx, y+cy);
        plotPoint(g,-x+cx,-y+cy);
        plotPoint(g, x+cx,-y+cy);
        plotPoint(g, y+cx, x+cy);
        plotPoint(g,-y+cx, x+cy);
        plotPoint(g,-y+cx,-x+cy);
        plotPoint(g, y+cx,-x+cy);
    }
    public void makeCircle(Graphics g  ,int cx, int cy , int r)
    {
        g.setColor(Color.red);
        int x = 0;
        int y = r;
        plotArcs(g, x, y,cx,cy);
        double p = 5.0/4.0 - r;
        while(x<y)
        {
            x = x+1;
            if(p<0)
            {
                plotArcs(g, x, y,cx,cy);
                p = p +2.0*x + 1.0; 
            }
            else{
                y = y-1;
                plotArcs(g, x, y,cx,cy);
                p = p + 2*x +1.0 - 2*y; 
            }
        } 
    }
    public void level1(Graphics g)
    {
        double a,x,b , r , pc ;
        // x= 10;
        x = 50;
        r = x*Math.cos(Math.toRadians(30));
        a = x*Math.cos(Math.toRadians(30));
        b = x*Math.sin(Math.toRadians(30));

        makeCircle(g,0, -1*(int)x, (int)r);
        makeCircle(g,   (int)a, (int)b, (int)r);
        makeCircle(g,-1*(int)a, (int)b, (int)r);
        makeCircle(g,-1*(int)a, (int)b, (int)r);
        makeCircle(g,0,0, (int)(x-r));
        makeCircle(g,0,0, (int)(r+x));

        pc = r+x-x*Math.sin(Math.toRadians(30));
        double r1 = (pc)*(pc)/(2*(pc+r));
        double cy1 = pc-r1 +x*Math.sin(Math.toRadians(30));
        double cx2 = cy1*Math.cos(Math.toRadians(30));
        double cy2 = cy1*Math.sin(Math.toRadians(30));
        makeCircle(g,0,(int)cy1,(int)r1  );
        makeCircle(g,  -1*(int)cx2, -1*(int)cy2, (int)r1);
        makeCircle(g,     (int)cx2, -1*(int)cy2,(int)r1);
    }
    public void paint(Graphics g){
        makeGrid(g);
        makeGrid1(g);
        level1(g);
    }
}