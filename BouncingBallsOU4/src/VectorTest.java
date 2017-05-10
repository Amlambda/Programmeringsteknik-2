
import junit.framework.TestCase;

/**
 * J-Unit tests for the Vector class.
 * @author Johan Öfverstedt
 */
public class VectorTest
  extends TestCase {
  
  final double EPSILON = 1E-14; // Error tolerance
 
  @Override
  public void setUp() {
  }
    
  public void testConstructorAndGetters() {
    Vector v = new Vector(0.5, -0.5);
    assertEquals("X-coordinate", 0.5, v.getX(), EPSILON);    
    assertEquals("Y-coordinate", -0.5, v.getY(), EPSILON);    
  }
  
  public void testAdd() {
    Vector v = new Vector(0.5, 0.5);
    Vector u = new Vector(-0.5, 0.5);
    Vector result = v.add(u);

    // test immutability of addition
    assertEquals("[v] X-coordinate", 0.5, v.getX(), EPSILON);
    assertEquals("[v] Y-coordinate", 0.5, v.getY(), EPSILON);
    assertEquals("[u] X-coordinate", -0.5, u.getX(), EPSILON);
    assertEquals("[u] Y-coordinate", 0.5, u.getY(), EPSILON);
    
    assertEquals("[result] X-coordinate", 0.0, result.getX(), EPSILON);
    assertEquals("[result] Y-coordinate", 1.0, result.getY(), EPSILON);
  }
  
  public void testSub() {
    Vector v = new Vector(0.5, 0.5);
    Vector u = new Vector(-0.5, 0.5);
    Vector result = v.sub(u);

    // test immutability of subtraction
    assertEquals("[v] X-coordinate", 0.5, v.getX(), EPSILON);
    assertEquals("[v] Y-coordinate", 0.5, v.getY(), EPSILON);
    assertEquals("[u] X-coordinate", -0.5, u.getX(), EPSILON);
    assertEquals("[u] Y-coordinate", 0.5, u.getY(), EPSILON);
    
    assertEquals("X-coordinate", 1.0, result.getX(), EPSILON);
    assertEquals("Y-coordinate", 0.0, result.getY(), EPSILON);
  }
  
  public void testScale() {
    Vector v = new Vector(0.5, -0.5);
    Vector result = v.scale(-4.0);

    // test immutability of scaling
    assertEquals("[v] X-coordinate", 0.5, v.getX(), EPSILON);
    assertEquals("[v] Y-coordinate", -0.5, v.getY(), EPSILON);
    
    assertEquals("X-coordinate", -2.0, result.getX(), EPSILON);
    assertEquals("Y-coordinate", 2.0, result.getY(), EPSILON);
  }
  
  public void testFlipSign() {
    Vector v = new Vector(2.0, 2.0);
    Vector flippedX = v.flipSignX();

    // test immutability of sign flip x
    assertEquals("[v] X-coordinate", 2.0, v.getX(), EPSILON);
    assertEquals("[v] Y-coordinate", 2.0, v.getY(), EPSILON);

    Vector flippedY = v.flipSignY();

    // test immutability of sign flip y
    assertEquals("[v] X-coordinate", 2.0, v.getX(), EPSILON);
    assertEquals("[v] Y-coordinate", 2.0, v.getY(), EPSILON);
    
    assertEquals("flipSignX - X-coordinate", -2.0, flippedX.getX(), EPSILON);
    assertEquals("flipSignX - Y-coordinate", +2.0, flippedX.getY(), EPSILON);
    assertEquals("flipSignX - X-coordinate", +2.0, flippedY.getX(), EPSILON);
    assertEquals("flipSignY - Y-coordinate", -2.0, flippedY.getY(), EPSILON);
  }
  
  public void testDot() {
    Vector v = new Vector(0.8, 0.75);
    Vector u = new Vector(-0.5, 0.5);
    double result = v.dot(u);
    
    assertEquals("Dot Product", 0.8 * (-0.5) + 0.75 * 0.5, result, EPSILON);
  }
  
  public void testLength() {
    Vector v = new Vector(1.0, -1.0);
    double result = v.length();
    
    assertEquals("Length", Math.sqrt(2.0), result, EPSILON);
  }
  
  public void testDistance() {
    Vector v = new Vector(-1.0, -1.0);
    Vector u = new Vector(1.0, 1.0);
    double result = v.distance(u);
    double expected = Math.sqrt(8.0);
    
    assertEquals("Distance", expected, result, EPSILON);
  }
    
  public void testAngle() {
    double x = 1.0;
    double y = 0;
    
    int steps = 90;
    double angle = (2.0 * Math.PI) / steps;
    double a00 = Math.cos(angle);
    double a01 = -Math.sin(angle);
    double a10 = Math.sin(angle);
    double a11 = Math.cos(angle);
    
    //Test canonical comparison method
    assertTrue("Error", compareCanonicalAngles(Math.PI - 0.5*EPSILON, -Math.PI + 0.25*EPSILON));
    assertTrue("Error", compareCanonicalAngles(-Math.PI + 0.25*EPSILON, Math.PI - 0.5*EPSILON));
    
    for(int i = 0; i < steps; ++i) {
      Vector v = new Vector(x, y);
      double result = canonicalAngle(v.angle());
      double expected = canonicalAngle(i * angle);
    
      assertTrue("Angle expected: <" + expected + "> but was: <" + result + ">",
                 compareCanonicalAngles(result, expected));
      
      double newX = a00 * x + a01 * y;
      double newY = a10 * x + a11 * y;
      
      x = newX;
      y = newY;
    }
  }
  
  //Helper methods for testAngle
  
  private double canonicalAngle(double angle) {
    int wholePI = (int)(Math.abs(angle) / (Math.PI));
    if(wholePI <= 1) //If angle is within canonical range
      return angle; //We are done
    
    double result;
    if(angle < 0) {
      result = angle + (wholePI-1) * (2.0 * Math.PI);
    } else {
      result = angle - (wholePI-1) * (2.0 * Math.PI);
    }
    
    return result;
  }
  
  private boolean compareCanonicalAngles(double angle1, double angle2) {
    double diff1 = Math.abs(angle1 - angle2);
    double diff2 = Math.abs((2.0 * Math.PI + angle1) - angle2);
    double diff3 = Math.abs((2.0 * Math.PI + angle2) - angle1);
    double minDiff = Math.min(diff1, Math.min(diff2, diff3));
    final double ANGLE_EPSILON = 1E-9;

    return minDiff <= ANGLE_EPSILON;
  }
  
  //Test vector with specified length but randomized angle
  
  public void testRandomVector() {
    Vector v = Vector.randomVector(2.0);
    double result = v.length();
    
    assertEquals("Random Vector", 2.0, result, EPSILON);
  }
  
  public void testPolar() {
    Vector v = Vector.polar(1.0, 3.0 * (Math.PI / 4.0));
    
    assertEquals("X-coordinate", -Math.sqrt(0.5), v.getX(), EPSILON);
    assertEquals("Y-coordinate", Math.sqrt(0.5), v.getY(), EPSILON);
  }
}
