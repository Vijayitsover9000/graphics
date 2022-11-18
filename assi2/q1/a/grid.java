// This applet program only works on java jdk 1.8.0_312 or Lower
import java.awt.*;
import java.applet.*;
import java.awt.event.*;

public class grid extends Applet implements ActionListener, MouseWheelListener {
    Button b1 = new Button();
    Button b2 = new Button(" - ");
    int gap = 50;

    public void init() {
        b1.setLabel(" + ");
        b1.setSize(300, 300);
        Color buttonColor1 = new Color(0, 255, 0);
        Color buttonColor2 = new Color(255, 0, 0);
        Color bgColor = new Color(255, 255, 255);
        b1.setBackground(buttonColor1);
        b2.setBackground(buttonColor2);

        add(b1);
        add(b2);
        addMouseWheelListener(this);
        b1.addActionListener(this);
        b2.addActionListener(this);

        setBackground(bgColor);
    }

    // for zoom using mouse wheel
    public void mouseWheelMoved(MouseWheelEvent e) {
        int z = e.getWheelRotation();
        zoom(z);
    }

    // button action listener
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1)
            zoom(+10);
        else if (e.getSource() == b2)
            zoom(-10);
    }

    // function to make a grid with respect to standard cartesian coordinates
    public void makeGrid(Graphics g, int gap) {
        if (gap <= 0 || gap > getHeight())
            return;
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

    public void plotLineDDA(Graphics g,int x1,int y1 ,int x2, int y2)
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
            plotPoint(g,(int)x,(int)y,Color.black);
            x  = x+ (double)dx/step;
            y = y + (double)dy/step;
        }
    }
    public void paint(Graphics g) {
        int originX = (getX() + getWidth()) / 2;
        int originY = (getY() + getHeight()) / 2;
        g.setColor(Color.green);
        g.drawLine(originX, originY - getHeight() / 2, originX, originY + getHeight() / 2);
        g.drawLine(originX - getWidth() / 2, originY, originX + getWidth() / 2, originY);
        g.setColor(Color.green);
        plotpoint(g, 0, 0, Color.yellow);
        plotLineDDA(g,0,0,40,80);
        plotLineDDA(g,0,0,60,60);
        plotLineDDA(g,0,0,80,40);
        plotLineDDA(g,-80,20,80,-20);
        plotLineDDA(g,-80,20,80,100);
    }
}