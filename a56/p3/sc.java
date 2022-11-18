// This applet program only works on java jdk 1.8.0_312 or Lower

import java.awt.*;
import java.applet.*;
import java.awt.event.*;

public class sc extends Applet implements ActionListener ,MouseWheelListener{
    Button b1 = new Button(" + ");
    Button b2 = new Button(" - ");
    Button gridb = new Button("GRID");
    
    int gap = 10;
    boolean gridon = true;
 
    
    public void init() {
        Color buttonColor1 = new Color(0,255,0);
        Color buttonColor2 = new Color(255,0,0);
        Color bgColor = new Color(0,0,0);

        b1.setBackground(buttonColor1);
        b2.setBackground(buttonColor2);
        gridb.setBackground(Color.lightGray);
        
        add(gridb);
        add(b1);
        add(b2);
        
        addMouseWheelListener(this);
        b1.addActionListener(this);
        b2.addActionListener(this);
        gridb.addActionListener(this);
       
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
        else if(e.getSource()==gridb)
        {
            gridon= !gridon;
            repaint();
        }
        
    }
    
    //function to make a grid with respect to standard cartesian coordinates
    public void makeGrid(Graphics g, int gap)
    {
        if(gridon==false)
            return;
        if(gap<=0|| gap>getHeight())
            return ;
        int originX = (getX() + getWidth()) / 2;
        int originY = (getY() + getHeight()) / 2;
        g.setColor(Color.DARK_GRAY);
        g.drawLine(originX, originY - getHeight() / 2, originX, originY + getHeight() / 2);
        g.drawLine(originX - getWidth() / 2, originY, originX + getWidth() / 2, originY);
        g.setColor(Color.DARK_GRAY);
        for (int x = gap; x <= getWidth(); x += gap) {
            g.drawLine(originX + x, 0, originX + x, getHeight());
            g.drawLine(originX - x, 0, originX - x, getHeight());
        }
        for (int y = gap; y <= getHeight(); y += gap) {
            g.drawLine(0, originY + y, getWidth(), originY + y);
            g.drawLine(0, originY - y, getWidth(), originY - y);
        }
    }
    //for zoom feature
    public void zoom(int i)
    {
        if(i>0)
            gap+=gap/10+1;
        else if(i<0)
            gap-=gap/10+1;
        if(gap<0)
            gap = 1000;
        if(gap>1600)
            gap = 2;
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

    public void plotLine(Graphics g,int x1,int y1 ,int x2, int y2,Color col)
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
            plotpoint(g,(int)x,(int)y,col);
            x  = x+ (double)dx/step;
            y = y + (double)dy/step;
        }
    }  
    // public void Bezier(int x[],int y[])
    // {
    //     float u;
    //     for (u=0;u<1;u=u+0.0005)
    //     {
    //         float xu=f(x,u);
    //         float yu=f(y,u);
    //     }
    //     plot_point(round(x(u)),round(y(u)),COLOR);
    // }     
    public void plotPoints(int[] x ,int[] y) 
    {
        for(int i = 0 ;i< x.length ;i++)
        {
            plotpoint(getGraphics(),x[i],y[i],Color.blue);
        }
    } 
    public void bezierCurve(int[] x , int[] y)
    {
        double xu = 0.0 , yu = 0.0 , u = 0.0 ;
        
        for(u = 0.0 ; u <= 1.0 ; u += 0.0001)
        {
            xu = Math.pow(1-u,3)*x[0]+3*u*Math.pow(1-u,2)*x[1]+3*Math.pow(u,2)*(1-u)*x[2]
                 +Math.pow(u,3)*x[3];
            yu = Math.pow(1-u,3)*y[0]+3*u*Math.pow(1-u,2)*y[1]+3*Math.pow(u,2)*(1-u)*y[2]
                +Math.pow(u,3)*y[3];
            plotpoint(getGraphics(), (int)xu , (int)yu,Color.orange) ;
        }
    }
    public void paint(Graphics g) 
    {
        makeGrid(g,gap);
        int[] x1 = {-40 , -20 , 0 , 20};
        int[] y1 = {-10 , 20 , 25 , -10};
        int[] x2 = {20 , 40 , 80 , 60};
        int[] y2 = {-10 , -40 , -40 , 10};
        // plotPoints(x,y);
        bezierCurve(x1,y1);
        bezierCurve(x2,y2);
        plotCircle(g,0,0,Color.yellow);
    }
}