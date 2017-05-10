import javax.swing.JPanel;
import java.awt.*;
import javax.swing.*;

public class Box extends JPanel{
	private int height;
	private int width;
	private Ball b1;
	private Ball b2;
	private int seconds;
	private double delay = 0.01;

	public Box(int heigth,int width){
		this.width = width;
		this.height = height;
		setPreferredSize(new Dimension(height,width));
		setBackground(Color.white);
		b1 = new Ball(this, 10);
		b2 = new Ball(this, 20);
	}

	public void set(int sec) {
		seconds = sec;
		repaint();
	}
	
	public int getHeight(){
		return this.height;
	}

	public int getWidth(){
		return this.width;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		b1.paint(g);
		b2.paint(g);
	}

	public void step() {
	       b1.move(delay);
	       b2.move(delay);
	       repaint();
	    }    
}
