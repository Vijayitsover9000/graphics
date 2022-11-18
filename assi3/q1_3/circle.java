import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import java.util.*;

public class circle extends Applet implements MouseWheelListener{
    int gap = 2;
    int globalx = 100;
    int wid, hei;
    int k = 1000;
    Circle bigc;
    ArrayList<Circle> arr = new ArrayList<Circle>(k+10);
    public void init(){
        setBackground(Color.white);
        addMouseWheelListener(this);
        level1();
        randlevel();
    }
    double dist(int x1,int y1, int x2, int y2)
    {
        double d = Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
        return d;
    }
    public int touch(Circle c1, Circle c2)
    {
        int d = (int)dist(c1.cx,c1.cy , c2.cx,c2.cy);
        if(d>c1.r+c2.r)
            return 1;//not touching
        else
            return 0;
    }
    public void mouseWheelMoved(MouseWheelEvent e){
        int z = e.getWheelRotation();
        zoom(z);
    }
    public void zoom(int i)
    {
        if(i>0){
            gap+=gap/10+1;
        }
        else if(i<0){
            gap-=gap/10+1;
        }
        if(gap<0){
            gap = 100;
        }
        repaint();
    }
    public void makeGrid(Graphics g){
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
    public void plotArcs(Graphics g , int x ,int y,int cx,int cy){
        plotPoint(g, x+cx, y+cy);
        plotPoint(g,-x+cx, y+cy);
        plotPoint(g,-x+cx,-y+cy);
        plotPoint(g, x+cx,-y+cy);
        plotPoint(g, y+cx, x+cy);
        plotPoint(g,-y+cx, x+cy);
        plotPoint(g,-y+cx,-x+cy);
        plotPoint(g, y+cx,-x+cy);
    }
    public void makeCircle(Graphics g  ,Circle c)
    {
        g.setColor(Color.red);
        int x = 0;
        int y = c.r;
        plotArcs(g, x, y,c.cx,c.cy);
        double p = 5.0/4.0 - c.r;
        while(x<y)
        {
            x = x+1;
            if(p<0)
            {
                plotArcs(g, x, y,c.cx,c.cy);
                p = p +2.0*x + 1.0; 
            }
            else{
                y = y-1;
                plotArcs(g, x, y,c.cx,c.cy);
                p = p + 2*x +1.0 - 2*y; 
            }
        } 
    }
    public void level1(){
        double a,x,b , r , pc ;
        x= globalx;
        r = x*Math.cos(Math.toRadians(30));
        a = x*Math.cos(Math.toRadians(30));
        b = x*Math.sin(Math.toRadians(30));
        Circle c = new Circle(0, -1*(int)x, (int)r);
        arr.add(c);
        c = new Circle((int)a, (int)b, (int)r);
        arr.add(c);
        c = new Circle(-1*(int)a, (int)b, (int)r);
        arr.add(c);
        c = new Circle(-1*(int)a, (int)b, (int)r);
        arr.add(c);
        c = new Circle(0,0, (int)(x-r));
        arr.add(c);
        c = new Circle(0,0, (int)(r+x));
        bigc = c;
        
        pc = r+x-x*Math.sin(Math.toRadians(30));

        double r1 = (pc)*(pc)/(2*(pc+r));
        double cy1 = pc-r1 +x*Math.sin(Math.toRadians(30));
        double cx2 = cy1*Math.cos(Math.toRadians(30));
        double cy2 = cy1*Math.sin(Math.toRadians(30));
        c = new Circle(0,(int)cy1,(int)r1 );
        arr.add(c);
        c = new Circle(-1*(int)cx2, -1*(int)cy2, (int)r1);
        arr.add(c);
        c = new Circle((int)cx2, -1*(int)cy2,(int)r1);
        arr.add(c);
    }
    public void randlevel(){
        int i = 0;
        while(i<400)
        {
            int x1 = (int)(Math.random()*bigc.r*2)-bigc.r;
            int y1 = (int)(Math.random()*bigc.r*2)-bigc.r;
            
            Circle c = new Circle(x1,y1,0);
            i+=circleGrow(c);
        }
    }   
    public void delay(int d)
    {
        try {
            Thread.sleep(d);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    public int circleGrow( Circle c){
        int retv= 0;
        int r = 1;
        hei = getHeight()/gap;
        wid = getWidth()/gap;
        // while(c.cx+r< wid/2 && c.cx-r>-wid/2 && c.cy+r<hei/2 && c.cy-r>-hei/2){
        while(bigc.r-c.r>(int)dist(bigc.cx ,bigc.cy , c.cx,c.cy)){
            int f = 0;
            for(int i = 0 ;i<arr.size();i++)
            {
                if(touch(arr.get(i),c)==0)
                {
                    f= 1;
                    break;
                }
            }
            if(f==1)
                break;
            c.r = r;
            r++;
        }
        if(r>2){
            arr.add(c);
            retv++;
        }
        return retv;
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
    public void paint(Graphics g){
        makeGrid(g);
        makeGrid1(g);
        makeCircle(g,bigc);
        for(int i = 0;i<arr.size();i++)
        {
            Circle c = arr.get(i);
            makeCircle(g,c);
        }
                
    }
}