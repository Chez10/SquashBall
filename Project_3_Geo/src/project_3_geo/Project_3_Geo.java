/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project_3_geo;

import java.awt.Color;
import javax.swing.JFrame;

/**
 *
 * @author Marcos Aldana
 */
public class Project_3_Geo {
    
    public static void main(String[] args){
        Point point1 = new Point(250, 480);
        Point point2 = new Point(250, 468);
        Point point3 = new Point(300, 468);
        Point point4 = new Point(300, 480);
       

        Point[] points = { point1, point2, point3, point4};
        Polygon player = new Polygon(points);
        player.setInteriorColor(Color.RED);
        
        
        Point point5 = new Point(270, 250);
        Point point6 = new Point(264, 264);
        Point point7 = new Point(250, 270);
        Point point8 = new Point(236, 264);
        Point point9 = new Point(230, 250);
        Point point10 = new Point(236, 236);
        Point point11 = new Point(250, 230);
        Point point12 = new Point(264, 236);
        
        
        Point[] points2 = { point5, point6, point7, point8, point9, point10, point11, point12};
        Polygon ball = new Polygon(points2); 
        ball.setInteriorColor(Color.yellow);
        
        
//        Polygon[] polyList = new Polygon[2]; 
//        polyList[0] = ball; 
//        polyList[1] = Globals.player;
        
       SquashBall d1 = new SquashBall(ball,player ); 
       FrameDisplay frame = new FrameDisplay(ball,player); 
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
       frame.setVisible(true);
      Thread thread = new Thread(d1);
      thread.start();
      while(true){
          frame.repaint(); 
      }
    }
    
}
