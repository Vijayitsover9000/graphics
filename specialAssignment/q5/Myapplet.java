import java.applet.*;
import java.awt.*;
import java.awt.event.*;
public class Myapplet extends Applet implements ActionListener{
	Button b;
	newShape S;
	int c = 0;
	public void init() {
		setBackground(Color.blue);
		b = new Button("Press Here!!");
		add(b);
		b.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e)
	{
		if(c==0)c=1;
		else c=0;
		repaint();
	}
	public void paint(Graphics g) {
		S = new newShape(g,getWidth(),getHeight());
		if(c==0)
			S.Rect();
		else
			S.Square();
	}
}
