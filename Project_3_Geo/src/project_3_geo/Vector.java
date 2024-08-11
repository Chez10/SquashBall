/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project_3_geo;

/**
 *
 * @author kevinsanchez
 */
public class Vector {
    
    private double x;
    private double y;
    
    public Vector(){
        x=y=0;
    }
    
    public Vector(double x, double y){
        this.x = x;
        this.y = y;
    }
    
    public Vector add(Vector v){
        return new Vector(this.x + v.x, this.y + v.y);
    }
    
    public double direction(){
        double angle = Math.toDegrees(Math.atan2(y, x));
        if (angle<0) angle += 360;
        
        return angle;  
    }
    
    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    
    public double magnitude(){
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }
    
    public Vector neg(){
        return new Vector(-x, -y);
    }
    
    public void SetX(double x){
        this.x =x;
    }
    public void SetY(double y){
        this.y =y;
    }
    
    public Vector subtract(Vector v){
        return new Vector(this.x - v.x, this.y - v.y);
    }
    
    public String toString(){
        return "VECTOR (" + x + ", " + y + ")";
    }
    public void translate(double angleInDegrees) {
        // Define the radius of the circular path (you can change this as needed)
        double radius = 10.0; // Example radius value
        
        // Convert the angle to radians
        double radians = Math.toRadians(angleInDegrees);
        
        // Calculate the new x and y coordinates based on circular motion
        double newX = x + radius * Math.cos(radians);
        double newY = y + radius * Math.sin(radians);
        
        // Update the point's coordinates
        x = newX;
        y = newY;
    }
    
}