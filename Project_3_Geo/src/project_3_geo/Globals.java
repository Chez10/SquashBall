/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project_3_geo;

/**
 *
 * @author marcosaldana
 */
public class Globals {
    public static int WIDTH = 500; 
    public static int HEIGHT = 500;
    public static Polygon player; 
    
    
    public static void delay(int ms){
        try{
            Thread.sleep(ms);
            
        } catch(InterruptedException e){
            
        }
    }
}
