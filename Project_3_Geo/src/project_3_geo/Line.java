package project_3_geo;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Graphics;

/**
 * This class represents a line in the plane, given by the equation
 * ax + by + c = 0.
 */
public class Line extends GeometricObject
{

    private double a;   //x coefficient
    private double b;   //y coefficient
    private double c;   //constant term

    /**
     * Instantiates a line object as -x + y = 0 or y = x.
     */
    public Line()
    {
        a = -1;
        b = 1;
        c = 0;
    }

    /**
     * Instantiates a line object as ax + by + c = 0 or y = (-a/b) x + (-c/b).
     */
    public Line(double a, double b, double c) throws IllegalArgumentException
    {
        //a=0 and b=0 does not define a line
        if (a==0 && b==0) throw new IllegalArgumentException();
        else
        {
            this.a = a;
            this.b = b;
            this.c = c;
        }
        
    }
       
    /**
     * Draws this line.
     * 
     * @param g graphic context 
     */
    @Override
    public void draw(Graphics g)
    {
        //finds the window size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double maxX = screenSize.width;
        double maxY = screenSize.height;

        //obtain two points on this line, (x1, y1) and (x2, y2)
        double x1, y1, x2, y2;
        
        if (!isVertical()) //line is not vertical
        {
            x1 = 0.0;
            y1 = getY(x1);
            x2 = maxX;
            y2 = getY(x2);
        }
        else //line is vertical
        {
            y1 = 0.0;
            x1 = getX(y1);
            y2 = maxY;
            x2 = getX(y2);
        }

        g.setColor(getBoundaryColor());
        g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
    }    
    
    /**
     * Returns the coefficient of the X variable in the equation of this line
     * object.
     * 
     * @return coefficient of the X variable in the equation of this line object
     */
    public double getA()
    {
        return a;
    }

    /**
     * Returns the coefficient of the Y variable in the equation of this line
     * object.
     * 
     * @return coefficient of the Y variable in the equation of this line object
     */
    public double getB()
    {
        return b;
    }

    /**
     * Returns the independent term of the equation of this line object.
     * 
     * @return independent term of the equation of this line object
     */
    public double getC()
    {
        return c;
    }

    /**
     * Given x, calculates the value of y.
     *
     * @param x x value
     * @return value of y
     * @throws IllegalStateException 
     */
    public double getY(double x) throws IllegalStateException
    {
        if (b != 0)
            return (-a / b) * x + (-c / b);
        else
            throw new IllegalStateException("Vertical line cannot be used here.");
    }

    /**
     * Given y, calculates the value of x.
     *
     * @param y y value
     * @return value of x
     * @throws IllegalStateException 
     */
    public double getX(double y) throws IllegalStateException
    {
        if (a != 0)
            return (-b / a) * y + (-c / a);
        else throw new IllegalStateException("Horizontal line cannot be used here.");
    }
    
    /**
     * Determines if this line is vertical.
     * 
     * @return true if this line is vertical, false otherwise
     */
    boolean isVertical()
    {
        return b == 0;
    }
        
    /**
     * Sets the coefficient of the X variable in the equation of this line
     * object.
     * 
     * @param a value of the coefficient
     * 
     * @throws IllegalStateException if a=0 and b=0
     */
    public void setA(double a) throws IllegalStateException
    {
        //a=0 and b=0 does not define a line
        if (a==0 && this.b==0) throw new IllegalStateException();
        else
        {
            this.a = a;
        }
    }

    /**
     * Sets the coefficient of the Y variable in the equation of this line
     * object
     * 
     * @param b value of the coefficient
     * 
     * @throws IllegalStateException if a=0 and b=0
     */
    public void setB(double b) throws IllegalStateException
    {
        //a=0 and b=0 does not define a line
        if (this.a==0 && b==0) throw new IllegalStateException();
        else
        {
            this.b = b;
        }
    }

    /**
     * Sets the independent term of the equation of this line object.
     * 
     * @param c independent term
     */
    public void setC(double c)
    {
        this.c = c;
    }

    /**
     * Sets this line object as ax + by + c = 0 or y = (-a/b) x + (-c/b).
     */
    public void setLine(double a, double b, double c) throws IllegalStateException
    {
        //a=0 and b=0 does not define a line
        if (a==0 && b==0) throw new IllegalStateException();
        else
        {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }
    
    /**
     * Constructs a String description of this line.
     * 
     * @return String containing a description of this line
     */
    @Override
    public String toString()
    {
        String str = "LINE " + a + " " + b + " " + c + " " + super.toString();

        return str;
    } 
    public Line(Point p, Point q) throws IllegalArgumentException
    {
        
        if ((q.getX() == p.getX()) && (q.getY() == p.getY())) throw new IllegalArgumentException();
        else
        {
            a = q.getY() - p.getY();
            b = p.getX() - q.getX();
            c = p.getX() * q.getY() - p.getY() * q.getX(); 
        }
        
    }
    public double distanceFromPoint(Point p){
        
        double numerator = Math.abs(a * p.getX() + b * p.getY() + c);  
        double denominator = Math.sqrt(a * a + b * b);
        double distance = numerator / denominator;
        return distance; 
    }
    
    public int pointRelativePosition(Point p){
        
      int relativePosition = 0; 
      if(!isVertical()){
          double yCoord = getY(p.getX()); 
          if(yCoord > p.getY()) relativePosition = -1; 
          else
          if (yCoord == p.getY()) relativePosition = 0;
           else relativePosition = 1;
      } else{
          double xCoord = -c/a; 
          if(xCoord > p.getX())
             
            if (xCoord == p.getX()) relativePosition = 0;
               else relativePosition = 1;
      }
      return relativePosition; 
    }
    
    
    public boolean intersectsLineSegment(LineSegment ls){
        
        Point p = ls.getBegin();
        Point q = ls.getEnd();
        int x = pointRelativePosition(p); 
        int y = pointRelativePosition(q); 
        
        return (x==0 || y==0 || 
                (x>0 && y<0) ||
                (x<0 && y>0));
        
     
       }
    
   //09_02 //i think that the for loop is good but the return not
    public boolean intersectsPolygon(Polygon pol) {
    LineSegment[] edges = pol.getEdges(); // Assuming getEdges() returns the edges of the polygon

    for (LineSegment edge : edges) {
        if (intersectsLineSegment(edge)) {
            return true; // If any edge intersects, return true
        }
    }

    return false; // No edges intersected
}
    
    
    
}
