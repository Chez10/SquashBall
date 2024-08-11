/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project_3_geo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Random; 
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 *
 * @author kevinsanchez
 */
public class GraphDisplay extends JPanel implements KeyListener{
    
   Polygon pol;
   Polygon pol2;
   double colorChangeThreshold; 
   Color[]color; 
   int currentColor; 
   private BufferedImage image; //background image
   
   
   public GraphDisplay(int width, int height, Polygon pol, Polygon pol2){
       this.pol = pol; 
       this.pol2 = pol2;
       setBackground(Color.white); 
       Dimension d = new Dimension(width, height); 
       setPreferredSize(d); 
       setFocusable(true); 
       addKeyListener(this); 
       
       
       try
        {
            image = ImageIO.read(new File("blueBack2.jpeg"));
        }
        catch (IOException ex)
        {
            System.out.println("Image file not found!");
        }  
       
       try {
            // Load images for polygons
            Image squashBallImage = ImageIO.read(new File("ball.jpeg"));
            Image playerImage = ImageIO.read(new File("player2.jpeg"));

            // Set images for polygons
            pol.setImage(squashBallImage);
            pol2.setImage(playerImage);
        } catch (IOException ex) {
            System.out.println("Image file not found!");
        }
   }
   
   
   @Override
   public void keyPressed(KeyEvent e){
           Vector v; 
           switch(e.getKeyCode()){    
                  case KeyEvent.VK_LEFT:
                        v = new Vector(-20, 0);
                        pol2.translate(v);
                        if(pol2.greatestX() >= Globals.WIDTH || pol2.smallestX() <= 0){
                            v = v.neg();
                            pol2.translate(v);
                        }
                        break;
                  case KeyEvent.VK_RIGHT:
                        v= new Vector (20, 0);
                        pol2.translate(v);
                         if(pol2.greatestX() >= Globals.WIDTH || pol2.smallestX() <= 0){
                            v = v.neg();
                            pol2.translate(v);
                        }
                        break;
           }
           repaint(); 
             
   }
   
   @Override 
   public void keyReleased(KeyEvent e){}
                                                //Remmber that KeyListener even requier three methods to be implemented. 
   //These two need to be implemented but are not used in this class as they are not neede but still need to be implemented
   @Override
   public void keyTyped(KeyEvent e){}
       
   
   @Override
   public void paint(Graphics g){
       super.paint(g);
       
       Dimension d = getSize();
       g.drawImage(image, 0, 0, d.width, d.height, this);
    
       pol.draw(g);
       pol2.draw(g);
       
   }
    public boolean changeColor(double prob){
        Random rnd = new Random(); 
        return rnd.nextDouble() < prob;
    }
  
    
    //addded method
    private boolean canMovePolygon(Vector v) {
        Polygon tempPolygon = new Polygon(pol.getVertices());
        tempPolygon.translate(v);
        return tempPolygon.isAtBoundary(getWidth(), getHeight());
    }
    
}
