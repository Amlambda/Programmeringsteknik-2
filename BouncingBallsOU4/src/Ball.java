import java.awt.Color;
import java.awt.Graphics;

public class Ball {
//private Color color = blue;
private Vector position;
private Vector velocity;
private int size; //Radius of ball
private Color c;
private Box box;

public Ball(Box box, int size){
	this.position = Vector.randomVector(Math.random());
	this.velocity = Vector.randomVector(Math.random());
	//this.position = pos;
	//this.velocity = speed;
	this.size = size;
	this.box = box;
    c = new Color((float)Math.random(),
            (float)Math.random(),
            (float)Math.random()); // Random color
}

public Vector getPos(){
	return this.position;
}

public Vector getVel(){
	return this.velocity;
}

public double getSize(){
	return this.size;
}

public void setPos(Vector v){
	this.position = v;
}

public void setVel(Vector v){
	this.velocity = v;
}

public void setSize(int d){
	this.size = d;
}

public void move(double delay){
	this.position = this.position.add(this.velocity.scale(delay));
}
 
public void collide(Ball b){
	Vector velDiff1 = b.getVel().sub(this.velocity);
	Vector velDiff2 = this.velocity.sub(b.getVel());
	Vector posDiff = b.getPos().sub(this.position);
	Vector exp1 = posDiff.scale((velDiff1.dot(posDiff))/Math.pow(posDiff.length(),2));
	Vector exp2 = posDiff.scale((velDiff2.dot(posDiff))/Math.pow(posDiff.length(),2));
	
	this.velocity = this.velocity.add(exp1); 
	b.setVel(b.getVel().add(exp2));						
}

public void paint(Graphics g) {
    g.setColor(c);
    double x = this.position.getX()*box.getWidth()-size;
    double y = this.position.getY()*box.getHeight()-size;
   g.fillOval((int)x,(int)y , 2*size, 2*size);
 }
}

