package project_3_geo;
import java.awt.Graphics;

/**
 * This class represents a line segment given by end points;
 * end points might be equal.
 */
public class LineSegment extends GeometricObject
{

    //begin and end are the end points of line segment; no guaranties on order
    private Point begin;
    private Point end;

    /**
     * Instantiates a LineSegment object with endpoints (0, 0) and (1, 1).
     */
    public LineSegment()
    {
        begin = new Point(0, 0);
        end = new Point(1, 1);
    }

    /**
     * Instantiates a LineSegment object with endpoints b and e.
     */
    public LineSegment(Point b, Point e)
    {
        begin = b;
        end = e;
    }

    /**
     * Draws this line segment.
     * 
     * @param g graphic context 
     */
    @Override
    public void draw(Graphics g)
    {
        //line segment is drawn by using its boundary color, not its interior
        //color (width of line=0)
        
        g.setColor(getBoundaryColor());
        g.drawLine((int)begin.getX(), (int)begin.getY(),
                   (int)end.getX(), (int)end.getY());
    }

    /**
     * Returns the "begin" point of this line segment.
     * 
     * @return the "begin" point of this line segment
     */
    public Point getBegin()
    {
        return begin;
    }

    /**
     * Sets the "begin" point of this line segment.
     * 
     * @param b new "begin" point
     */
    public void setBegin(Point b)
    {
        begin = b;
    }

    /**
     * Returns the "end" point of this line segment.
     * 
     * @return the "end" point of this line segment
     */
    public Point getEnd()
    {
        return end;
    }

    /**
     * Sets the "end" point of this line segment.
     * 
     * @param e new "end" point
     */
    public void setEnd(Point e)
    {
        end = e;
    }

    /**
     * Determines the greatest x.
     *
     * @return the greatest x
     */
    public double greatestX()
    {
        return begin.getX() > end.getX() ? begin.getX() : end.getX();
    }

    /**
     * Determines the greatest y.
     *
     * @return the greatest y
     */
    public double greatestY()
    {
        return begin.getY() > end.getY() ? begin.getY() : end.getY();
    }

    /**
     * Determines the smallest x.
     *
     * @return the smallest x
     */
    public double smallestX()
    {
        return begin.getX() < end.getX() ? begin.getX() : end.getX();
    }

    /**
     * Determines the smallest y.
     *
     * @return the smallest y
     */
    public double smallestY()
    {
        return begin.getY() < end.getY() ? begin.getY() : end.getY();
    }

    /**
     * Constructs a String description of this line segment.
     * 
     * @return String containing a description of this line segment
     */
    @Override
    public String toString()
    {
        String str = "LINE_SEGMENT " + super.toString() + "\n";
        str += begin + "\n" + end;

        return str;
    }

    /**
     * Translates this line segment by given vector.
     *
     * @param v given vector
     */
    public void translate(Vector v)
    {
        begin.translate(v);
        end.translate(v);
    }
    
    
     public boolean intersectsLineSegment(LineSegment ls){
        Point p = ls.getBegin();
        Point q = ls.getEnd();

        Line ln = new Line(p, q);

        int x = ln.pointRelativePosition(this.begin); 
        int y = ln.pointRelativePosition(this.end); 

        return (x == 0 || y == 0 || (x > 0 && y < 0) || (x < 0 && y > 0));
    }

    
    
    private boolean sameSign(double a, double b)
    {
        return (a>0 &&  b>0) || (a<0) && (b<0);
    }

    /**
     * Determines if this line segment intersects another given line segment. If so,
     * it returns intersection point. If line segments are collinear, possibility of
     * intersection if not analyzed and simply a value of 'COLLINEAR' is returned.
     *
     * Precondition: for each line segment, endpoints are different
     *
     * @param ls given line segment to find intersection with
     * @param p intersection point, if intersection point exists and it's unique
     *          (if line segments are collinear and intersection is not empty,
     *           no point is returned); it must be instantiated in caller method
     * @return -1: segments are not collinear and do not intersect, OR
     *          1: line segments are not collinear and intersect, OR
     *          0: line segments are collinear; intersection might be empty or not.
     */
    public int intersect(LineSegment ls, Point p)
    {
        //Line defined by this line segment object
        Point p0 = getBegin();
        Point q0 = getEnd();
        Line line0 = new Line(p0, q0);

        //Line defined by line segment ls
        Point p1 = ls.getBegin();
        Point q1 = ls.getEnd();
        Line line1 = new Line(p1, q1);

        //relative positions of endpoints of this line segment about line1
        int p0Line1 = line1.pointRelativePosition(p0);
        int q0Line1 = line1.pointRelativePosition(q0);

        //relative positions of endpoints of ls about line0
        int p1Line0 = line0.pointRelativePosition(p1);
        int q1Line0 = line0.pointRelativePosition(q1);

        if (p0Line1 == 0 && q0Line1 == 0)
            return 0; //COLLINEAR
        else
            if (sameSign(p0Line1, q0Line1) ||
                sameSign(p1Line0, q1Line0))
                return -1; //same halfplanes, NO INTERSECTION
            else
            {
                double a0 = line0.getA();
                double b0 = line0.getB();
                double c0 = line0.getC();

                double a1 = line1.getA();
                double b1 = line1.getB();
                double c1 = line1.getC();

                double denominator = a0*b1 - a1*b0;
                double x = (b0*c1 - b1*c0) / denominator;
                double y = (a1*c0 - a0*c1) / denominator;

                p.setX(x);
                p.setY(y);

                return 1; //different halfplanes, INTERSECTION;
            }
    }

}
