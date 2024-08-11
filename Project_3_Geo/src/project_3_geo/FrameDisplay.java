package project_3_geo;
import java.awt.Color;
import javax.swing.*;

/**
 * Defines a frame to which a panel with drawings will be added.
 * 
 * @author Prof. Antonio Hernandez
 */
public class FrameDisplay extends JFrame
{
    int WIDTH = 500;
    int HEIGHT = 500;
    
    private GraphDisplay panel;

    public FrameDisplay(Polygon pol, Polygon pol2 )
    {
        setTitle("Pong game");
        setSize(WIDTH, HEIGHT);

        panel = new GraphDisplay(WIDTH, HEIGHT, pol, pol2);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEtchedBorder());
        
        add(panel);
        
        pack(); //sizes the frame so that the panel is at, or above,
                //the preferred size
    }

}
