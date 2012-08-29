

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.swing.*;
import java.awt.*;
import java.util.*;
/**
 *
 * @author Reed
 */
public class Background extends JComponent {
    //Clase a la que le pertenece el fondo de Minecraft
    private Image icon;
    public Background() {
        icon = new ImageIcon(getClass().getResource("NWallMC1.jpg")).getImage();
    }
    @Override
    public void paintComponent (Graphics g){
        //Método para pintar el componente
        g.drawImage(icon, 0, 0, getWidth(), getHeight(), null);
        setOpaque(false);
    }
}
