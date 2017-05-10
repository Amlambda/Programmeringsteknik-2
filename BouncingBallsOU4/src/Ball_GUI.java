import javax.swing.JFrame;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Ball_GUI extends JFrame implements ActionListener{
	   private Box box;
	   private Timer timer;

	   public Ball_GUI(int width, int height,
	                      int delay, int dx, int dy) {
	      box = new Box(width, height);
	      timer = new Timer(delay, this);
	      add(box);
	      pack();
	      setVisible(true);
	      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      timer.start();
	   }

	   public void actionPerformed(ActionEvent e) {
	      box.step();
	   }

	   public static void main(String[] args) { 
	      Ball_GUI gui = new Ball_GUI(1000, 600, 30, 10, -30);  
	   }
}
