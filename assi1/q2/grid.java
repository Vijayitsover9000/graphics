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
        g.setColor(Color.red);
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
        g.fillRect(originX+(gap*x)-(gap/4), originY-(gap*y)-(gap/4),gap/2 ,gap/2 );
    }
    //plots a point in x,y (Circle)
    public void plotCircle(Graphics g, int x,int y ,Color c){
        int originX = (getX() + getWidth()) / 2;
        int originY = (getY() + getHeight()) / 2;
        g.setColor(c);
        g.fillOval(originX+(gap*x)-(gap/8), originY-(gap*y)-(gap/8),gap/4 ,gap/4 );
    }
    public void PlotSquare(Graphics g , int x ,int y,int a, int b)
    {
        for(int i = 0 ;i<a ;i++)
        {
            plotpoint(g,x+i,y,Color.pink);
            plotpoint(g,x+i,y+b,Color.pink);
            
        }
        for(int i = 0 ;i<=b ;i++)
        {
            plotpoint(g,x,y+i,Color.pink);
            plotpoint(g,x+a,y+i,Color.pink);
            
        }
        
    }
    public void PlotLine(Graphics g , int x1,int y1 , int x2,int y2)
    {
        for(int i = x1;i<= x2;i++)
        {
            for(int j = y1;j<= y2; j++)
            if(i==j)
                plotpoint(g,i,j,Color.blue);
        }
    }
    public void paint(Graphics g) 
    {
       
        makeGrid(g,gap);
        Color c = new Color(100,100,200);
        plotCircle(g,0,0,Color.yellow);
        plotpoint(g,2,1 , c);
        PlotSquare(g,2,2,10,10);
        PlotLine(g, 1,1 , 9,9);
    
    }
}
