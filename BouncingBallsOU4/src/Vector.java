
public class Vector {
	private double x;
	private double y;

	public Vector(double x, double y){
		this.x = x;
		this.y = y;		
	}	

	public Vector add(Vector v){
		return new Vector(this.x + v.x, this.y + v.y);
	}

	public Vector sub(Vector v){
		return new Vector(this.x - v.x, this.y - v.y);
	}

	public double dot(Vector v){
		return this.x*v.x + this.y*v.y;
	}

	public Vector scale(double d){
		return new Vector(this.x*d, this.y*d);
	}

	public double distance(Vector v){
		return Math.sqrt(Math.pow(this.x-v.x,2) +
				Math.pow(this.y-v.y,2));
	}

	public double length(){
		return Math.sqrt(Math.pow(this.x,2) +
				Math.pow(this.y,2));
	}

	public double getX(){
		return this.x;
	}

	public double getY(){
		return this.y;
	}

	public Vector flipSignX(){
		return new Vector(-this.x, this.y);
	}

	public Vector flipSignY(){
		return new Vector(this.x, -this.y);
	}

	public static Vector randomVector(double len){
		//Generate random angle in radians
		double angle = 2*Math.PI*Math.random(); 
		return new Vector(len*Math.cos(angle), len*Math.sin(angle));
	}

	public double angle(){//WRONG
		return	Math.atan2(this.x,this.y) + Math.PI;
	}

	public static Vector polar(double length, double angle){
		return new Vector(length*Math.cos(angle), length*Math.sin(angle));
	}

	public String toString(){
		return "<" + this.x + " ," + this.y + ">";
	}
}
