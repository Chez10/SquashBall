
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project_3_geo;
import java.awt.*;

/**
 *
 * @author kevinsanchez
 */
public class Point extends GeometricObject{
    private double x;
    private double y;
    
    public Point(){
        x=y=0;
    }
    
    public Point(double x, double y){
        this.x=x;
        this.y=y;
    }
    
    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }
    
    public void setX(double x){
        this.x=x;
    }
    
    public void setY(double y){
        this.y=y;
    }
    
    public String toString(){
        return "POINT (" + x + ", " + y + ") " + super.toString();
    }
    
    public void draw(Graphics g){
        int radius = 5;
        
        g.setColor(getInteriorColor());
        g.fillOval((int)x - radius, (int)y - radius, 2 * radius, 2 * radius);
        g.setColor(getBoundaryColor());
        g.drawOval((int)x - radius, (int)y - radius, 2 * radius, 2 * radius);
    }
    public void translate(Vector v){
        x += v.getX(); 
        y += v.getY(); 
    }
    public void rotate(double angle){
        double angleRadians = Math.toRadians(angle);
        double sine = Math.sin(angleRadians); 
        double cosine = Math.cos(angleRadians); 
        
        double x1 = x*cosine - y*sine; 
        double y1 = x*sine + y*sine; 
        
        x = x1; 
        y = y1; 
    }
   public double distance(Point otherPoint) {
    double dx = this.x - otherPoint.getX();
    double dy = this.y - otherPoint.getY();
    double distance = Math.sqrt(dx * dx + dy * dy);
    return distance;
    }
    
}
