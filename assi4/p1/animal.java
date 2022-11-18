import java.awt.*;
import java.applet.*;
import java.awt.event.*;
public class animal extends Applet implements MouseWheelListener{
    int gap = 4;
    
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
    public void plotArcs(Graphics g , Point p)
    {
        int x = p.x;
        int y = p.y;
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
                Point po = new Point(x,y);
                plotArcs(g,po);
                p = p +2.0*x + 1.0; 
            }
            else{
                y = y-1;
                Point po = new Point(x,y);
                plotArcs(g,po);
                p = p + 2*x +1.0 - 2*y; 
            }
        } 
    }
    
    public Point rotate (Point p,double deg)
    {
        double x = p.x*Math.cos(Math.toRadians(deg))  - p.y*Math.sin(Math.toRadians(deg));
        double y = p.x*Math.sin(Math.toRadians(deg))  + p.y*Math.cos(Math.toRadians(deg));
        
        Point np = new Point((int)x,(int)y);
        return np;
    }
    public void makeEllipse(Graphics g  ,int cx, int cy , int rx , int ry ,double rot)
    {
        g.setColor(Color.red);
        int x = 0;
        int y = ry;
        
        double p = ry*ry - rx*rx*ry +0.25*rx*rx;
        while(x*ry*ry<=y*rx*rx)
        {
            plotArcs(g, x, y,cx,cy ,rot);
            x = x+1;
            if(p<0)
            {
                plotArcs(g, x, y,cx,cy,rot);
                p = p +2.0*x*ry*ry + ry*ry; 
            }
            else{
                y = y-1;
                plotArcs(g, x, y,cx,cy,rot);
                p = p + 2.0*x*ry*ry - 2*y*rx*rx +ry*ry; 
            }
        } 
        p = ry*ry*Math.pow(x+0.5,2) + rx*rx*Math.pow(y-1,2) - Math.pow(rx*ry,2);
        while(x*ry*ry>=y*rx*rx)
        {
            y = y-1;
            if(p>0)
            {
                plotArcs(g, x, y,cx,cy,rot);
                p = p - 2.0*y*rx*rx + rx*rx; 
            }
            else{
                x= x+1;
                plotArcs(g, x, y,cx,cy,rot);
                p = p + 2.0*x*ry*ry - 2*y*rx*rx +rx*rx; 
            }
            if(y==0)
                break;
        } 
    }
    public void plotPoint(Graphics g, int x, int y, Color c) {
        int originX = (getX() + getWidth()) / 2;
        int originY = (getY() + getHeight()) / 2;
        g.setColor(c);
        g.fillOval(originX + (gap * x) - (gap / 2), originY - (gap * y) - (gap / 2), gap, gap);
    }
    public void makeTriangle(Graphics g,Point p1,Point p2, Point p3 , double rot)
    {
        plotLine(g,p1.x,p1.y, p2.x,p2.y,rot);
        plotLine(g,p1.x,p1.y, p3.x,p3.y,rot) ;   
        plotLine(g,p2.x,p2.y, p3.x,p3.y,rot);
    }
    public void plotArcs(Graphics g , int x ,int y,int cx,int cy,double rot)
    {
        Point p = new Point(x+x,cy+y);
        Point np = rotate(p,rot);
        plotPoint(g,np.x,np.y);

        p = new Point(cx-x,cy+y);
        np = rotate(p,rot);
        plotPoint(g,np.x,np.y);

        p = new Point(cx-x,cy-y);
        np = rotate(p,rot);
        plotPoint(g,np.x,np.y);

        p = new Point(cx+x,cy-y);
        np = rotate(p,rot);
        plotPoint(g,np.x,np.y);
    }
    public void plotLine(Graphics g,int x1,int y1 ,int x2, int y2,double rot)
    {
        double x = x1;
        double y = y1;
        int dx = x2-x1;
        int dy = y2-y1;
        int step;
        if(Math.abs(dx) > Math.abs(dy))
            step = Math.abs(dx);
        else 
            step = Math.abs(dy);

        for(int i  = 0; i<step; i++)
        {
            Point p = new Point((int)x,(int)y);
            Point np = rotate(p,rot);
            plotPoint(g,np.x,np.y,Color.red );
            x  = x+ (double)dx/step;
            y = y + (double)dy/step;
        }
    }
    public Point translation(Point p, int tx, int ty)
    {
        Point np = new Point(p.x-tx, p.y-ty);
        return np;
    }
    
    public void paint(Graphics g)
    {
        makeGrid(g);
        // makeCircle(g,0,0,50);
        double rot = 30.0;
        makeEllipse(g,0,0,20,30 , rot);
        // Point p1 = new Point(0,0);
        // Point p2 = new Point(10,100);
        // Point p3 = new Point(30,100);
        // makeTriangle(g,p1,p2,p3,rot);

    }
}
