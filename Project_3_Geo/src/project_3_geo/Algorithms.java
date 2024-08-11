package project_3_geo;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
//package Prog07_01;

//import java.awt.Point;
//import SpecialPackage.Point; 
import java.util.ArrayList;
import java.util.Comparator;
/**
 *
 * @author kevinsanchez
 */
public class Algorithms {
    
    public static void closestPairOfPoints(Point[] points, int[] indices){
        
        double minDistance = Double.MAX_VALUE;
        
        for(int i =0; i<points.length;i++){
            
            for(int j=i+1; j<points.length;j++){
                
                double d = points[i].distance(points[j]); //do distance method in the Point class
                if(d<minDistance){
                    minDistance = d; 
                    indices[0] = i; 
                    indices[1] = j; 
                }
            }
        }
    }
    
    public static int isPointInSet(Rectangle[] rArray, Point p){
        
        for (int i=0; i<rArray.length; i++){
            if (rArray[i].isPointInRectangle (p))
                return i;
        }
                return -1;
        
    }
    
    
    public static ArrayList<Point> computeAllIntersections(LineSegment [] lsArray){
        
        ArrayList<Point> pointList = new ArrayList<>();
        for(int i = 0; i<lsArray.length; i++){
            for(int j =i+1; j<lsArray.length;j++){
                Point p= new Point(); 
                LineSegment ls = new LineSegment(); 
              if( lsArray[i].intersect(lsArray[j], p) == 1 )  {
                  pointList.add(p); 
            }
            
        }
        }
         
        
        return pointList;
        
    }
    
    
   public static ArrayList<Point> endpointSort(LineSegment [] lsArray){
       ArrayList<Point> sortedPoints= new ArrayList<>(); 
      for(int i=0; i<lsArray.length;i++){
          sortedPoints.add(lsArray[i].getBegin());
          sortedPoints.add(lsArray[i].getEnd());
      }
      
      sortedPoints.sort(new PointComparator());
       return sortedPoints; 
   } 
    
   /**
     * Sorts endpoints of line segments lexigraphically in (x, y).
     *
     * @param lsArray array of line segments
     * @return array list of points sorted lexigraphically in (x, y)
     */
//    public static ArrayList<Point> endpointSort(LineSegment[] lsArray)
//    {
//        ArrayList<Point> sortedPoints = new ArrayList<>();
//
//        //creates sorted list of endpoints
//        for (int i=0; i<lsArray.length; i++)
//        {
//            sortedPoints.add(lsArray[i].getBegin());
//            sortedPoints.add(lsArray[i].getEnd());
//        }
//        sortedPoints.sort(new PointComparator());
//
//        return sortedPoints;
//    }

    /**
     * Given a set of non-vertical line segments, determines whether any two
     * line segments intersect.
     *
     * Precondition: No two endpoints are equal.
     *
     * @param lsArray array of line segments
     * @return an intersection point if one exists, null otherwise
     */
    public static Point isThereAnIntersection(LineSegment[] lsArray)
    {
        //output: an intersection point, if one exists
        Point output = null;

        //data structure: sweep line
        SweepLine sweepLine = new SweepLine();

        //data structure: array list of events
        ArrayList<Event> eventList = new ArrayList<>();
        for (int i=0; i< lsArray.length; i++)
        {
            //each line segment generates two events
            Event e1 = new Event("left endpoint", lsArray[i]);
            eventList.add(e1);

            Event e2 = new Event("right endpoint", lsArray[i]);
            eventList.add(e2);
        }

        eventList.sort(new EventComparator());

        label:  //break to this 
        for(int i=0; i<eventList.size(); i++)
        {
            Event e = eventList.get(i);
            LineSegment s, pre, suc;
            Point p = new Point();
            switch (e.type)
            {
                case "left endpoint":
                    s = e.segment;
                    sweepLine.add(s);

                    pre = sweepLine.predecessor(s);
                    if (pre != null && s.intersect(pre, p) == 1)
                    {
                        output = p;
                        break label; //break from this 
                    }

                    suc = sweepLine.successor(s);
                    if (suc != null && s.intersect(suc, p) == 1)
                    {
                        output = p;
                        break label;
                    }

                    break;

                case "right endpoint":
                    s = e.segment;

                    pre = sweepLine.predecessor(s);
                    suc = sweepLine.successor(s);
                    if (pre != null && suc != null && pre.intersect(suc, p) == 1)
                    {
                        output = p;
                        break label;
                    }

                    sweepLine.remove(s);

                    break;
            }
        }

        return output;
    }
}

/**
 * An object of this class is used whenever lexicographical comparison
 * of two Point objects is needed, as in sorting points in an ArrayList with
 * the sort method.
 */
//class PointComparator implements Comparator<Point>
//{
//    /**
//     * Compares, using lexicographical order, given points.
//     *
//     * @param p point
//     * @param q point
//     * @return -1 if p<q, 0 if p==q, 1 if p>q
//     */
//    public int compare(Point p, Point q)
//    {
//        if (p.getX() < q.getX()) return -1;
//        else
//            if (p.getX() > q.getX()) return 1;
//            else //p.getX() == q.getX()
//                if (p.getY() < q.getY()) return -1;
//                else
//                    if (p.getY() == q.getY()) return 0;
//                    else //p.getY() > q.getY()
//                        return 1;
//    }
//}

/**
 * Represents an event point, namely, a left endpoint or a right endpoint of
 * a non-vertical line segment.
 *
 * Precondition: begin and end vertices of the line segment satisfy that
 *               begin.getX() is less than end.getX()
 */
class Event
{
    public String type; //assumed to be either "left endpoint" or "right endpoint"
    public LineSegment segment; //if type = "left endpoint", event point =
                                //segment.getBegin(), else segment.getEnd()

    Event(String t, LineSegment s)
    {
        type = t;
        segment = s;
    }
}

/**
 * Represent sweep line, i.e., data structure storing line segments intersecting
 * vertical line at an event point. Implemented as a sorted array list.
 */
class SweepLine
{
    private ArrayList<LineSegment> sl;

    SweepLine()
    {
        sl = new ArrayList<>();
    }

    /**
     * Adds a line segment. Uses sorted-search to add line segment so that
     * sweep line remains sorted.
     *
     * @param segm line segment
     */
    public void add(LineSegment segm)
    {
        int i=0;
        int n = sl.size();
        double x = segm.getBegin().getX();
        double y = segm.getBegin().getY();
        while (i<n &&
               y>new Line(sl.get(i).getBegin(), sl.get(i).getEnd()).getY(x))
        {
            i++;
        }

        sl.add(i, segm);
    }

    /**
     * Determines predecessor on the sweep line of given line segment.
     *
     * @param s line segment
     * @return predecessor if given line segment is not the first in the order,
     *         null otherwise
     */
    public LineSegment predecessor (LineSegment s)
    {
        int loc = sl.indexOf(s);

        if (loc == -1) //s not found on this sweep line
            throw new IllegalArgumentException();

        if (loc != 0) //it's not the first one (no predecessor)
            return sl.get(loc-1);
        else //first line segment, no predecessor
            return null;
    }

    /**
     * Removes given line segment from sweep line data structure.
     *
     * @param s line segment
     */
    public void remove(LineSegment s)
    {
        if (!sl.remove(s)) throw new IllegalArgumentException();
    }

    /**
     * @return String description of status of sweep line
     */
    public String toString()
    {
        String str = "";
        for (int i=0; i<sl.size(); i++)
        {
            str += sl.get(i) + "\n";
        }

        return str;
    }

    /**
     * Determines successor on the sweep line of given line segment.
     *
     * @param s line segment
     * @return successor if given line segment is not the last in the order,
     *         null otherwise
     */
    public LineSegment successor (LineSegment s)
    {
        int loc = sl.indexOf(s);

        if (loc == -1) //s not found on this sweep line
            throw new IllegalArgumentException();

        if (loc != sl.size()-1) //it's not the last one (no successor)
            return sl.get(loc+1);
        else //last line segment, no predecessor
            return null;
    }

}

/**
 * An object of this class is used whenever comparison of two Event objects
 * is needed, as in sorting points in the list of event points.
 */
class EventComparator implements Comparator<Event>
{
    public int compare(Event e1, Event e2)
    {
            Point p1, p2;
            if (e1.type.equals("left endpoint"))
                p1 = e1.segment.getBegin();
            else
                p1 = e1.segment.getEnd();

            if (e2.type.equals("left endpoint"))
                p2 = e2.segment.getBegin();
            else
                p2 = e2.segment.getEnd();

            if (p1.getX() < p2.getX()) return -1;
            else
                if (p1.getX() == p2.getX()) return 0;
                else return 1;
    }
       
   

}

 class PointComparator implements Comparator<Point>{
   public int compare(Point p, Point q){
       if(p.getX()< q.getX()){
           return -1;
       } else {
           if(p.getX() > q.getX()){
               return 1;
           } else {
               if(p.getY() < q.getY()){
                   return 1;
               } else {
                   if(p.getY() == q.getY()){
                       return 0;
                   }else {
                       return 1;
                       }
               } 
           }
       }
   }  
 }