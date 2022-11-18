// This applet program only works on java jdk 1.8.0_312 or Lower

import java.awt.*;
import java.applet.*;
import java.awt.event.*;

public class candle extends Applet implements MouseWheelListener, ActionListener {
    int gap = 1;
    int flameState = 0;
    Button on = new Button("ON");
    Button off = new Button("OFF");

    public void init() {

        on.setBackground(Color.green);
        off.setBackground(Color.red);
        add(on);
        add(off);

        Color bgColor = new Color(0, 0, 0);
        on.addActionListener(this);
        off.addActionListener(this);
        addMouseWheelListener(this);
        setBackground(bgColor);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == on)
            flameOn();
        if (e.getSource() == off)
            flameOff();
    }

    // for zoom using mouse wheel
    public void mouseWheelMoved(MouseWheelEvent e) {
        int i = e.getWheelRotation();
        if (i > 0)
            gap += gap / 10 + 1;
        else if (i < 0)
            gap -= gap / 10 + 1;
        if (gap < 0)
            gap = 100;
        repaint();
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
        
    }

    public void plotLineDDA(Graphics g, int x1, int y1, int x2, int y2) {
        double x = x1;
        double y = y1;
        int dx = x2 - x1;
        int dy = y2 - y1;
        int step;
        if (Math.abs(dx) > Math.abs(dy))
            step = Math.abs(dx);
        else
            step = Math.abs(dy);

        for (int i = 0; i < step; i++) {
            Color c = new Color(255, 255, 255);

            plotPoint(g, (int) x, (int) y, c);
            x = x + (double) Math.abs(dx) / step;
            y = y + (double) Math.abs(dy) / step;
        }
    }

    public void plotPoint(Graphics g, int x, int y, Color c) {
        if (y < 0)
            return;
        int gr = 0;
        int b = 0;
        int r = 0;
        gr = (int) (Math.sqrt( Math.abs(x)*x * x +  y * y)*1.2 );
        
        if (gr > 255) {
            b = gr - 255;
            gr = 255;
        }
        if (b > 255) {
            r =  b-255;
            b = 255;

        }
        if (r > 255) {
            r = 255;
        }

        int oX = (getX() + getWidth()) / 2;
        int oY = (getY() + getHeight()) / 2;

        Color c1 = new Color(c.getRed() - r, c.getGreen() - b, c.getBlue() - gr);
        g.setColor(c1);
        g.fillOval(oX + (gap * x) - (gap), oY - (gap * y) - (gap), gap * 2, gap * 2);
    }

    public void infiniteLoop() {
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            // TODO: handle exception
        }
        repaint();
    }
    public void flameOn()
    {
        flameState = 1;
        repaint();
    }
    public void flameOff()
    {
        flameState = 0;
        gap = 1;
        repaint();
    }
    public void paint(Graphics g) {
        
        // int oY = (getY() + getHeight()) / 2;
        // int oX = (getX() + getWidth()) / 2;
        Color baseColor = new Color(200, 200, 200);
        g.setColor(baseColor);
        // g.fillRect(oX - 40, oY, 80, 300);
        for(int i = 0 ;i>=-50;i--)
            plotLineDDA(g,-10,i,10,i);
        plotLineDDA(g,-100,-100,100,100);
        if (flameState == 1) {
            // makeGrid(g, gap);
            plotPoint(g, 0, 0, Color.yellow);
            for (int i = -100; i <= 100; i++) {
                int y = 200 - Math.abs(i) - (int)(Math.random()*40);
                if(Math.abs(y)>Math.abs(i/2))
                plotLineDDA(g, 0, 0, i, y);
            }
            infiniteLoop();
        }
    }
}