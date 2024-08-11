package project_3_geo;
import java.awt.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author kevinsanchez
 */
public class Rectangle extends GeometricObject{
    
    private Point begin;
    private Point end;
    
    public Rectangle(){
        begin = new Point(0,0);
        end = new Point(1,1);
    }
    
    public Rectangle(Point b, Point e){
        begin = new Point(b.getX(), b.getY());
        end = new Point(e.getX(), e.getY());
    }
    
    //@Override
    public void draw(Graphics g){
        int x = (int)smallestX();
        int y = (int)smallestY();
        int w = (int)width();
        int h = (int)height();
        
        g.setColor(getInteriorColor());
        g.fillRect(x, y, w, h);
        
        g.setColor(getBoundaryColor());
        g.drawRect(x, y, w, h);
    }
    
    public Point getBegin(){
        return new Point(begin.getX(), begin.getY());
    }
    
    public Point getEnd(){
        return new Point(end.getX(), end.getY());
    }
    
    public double greatestX(){
        return begin.getX() > end.getX() ? begin.getX():end.getX();
    }
    
    public double greatestY(){
        return begin.getY() > end.getY() ? begin.getY():end.getY();
    }
    
    public double height(){
        return Math.abs(begin.getY() - end.getY());
    }
    
    public void setBegin(Point p){
        begin = new Point(p.getX(), p.getY());
    }
    
    public void setEnd(Point p){
        end = new Point(p.getX(), p.getY());
    }
    
    public double smallestX(){
        return begin.getX() < end.getX() ? begin.getX():end.getX();
    }
    
    public double smallestY(){
        return begin.getY() < end.getY() ? begin.getY():end.getY();
    }
    
    @Override
    public String toString(){
        String str = "Rectangle "+ super.toString()+ "\n";
        str += begin + "\n" + end;
        
        return str;
    }
    
    public void translate(Vector v){
        begin.translate(v);
        end.translate(v);
    }
    
    public double width(){
        return Math.abs(begin.getX() - end.getX());
    }
    
    boolean isPointInRectangle(Point p){
        double x = p. getX();
        double y = p.getY();
        if ( (smallestX() <= x && x <= greatestX()) && (smallestY() <= y && y <= greatestY()) )
            return true;
        else 
            return false;
    }
    
}
