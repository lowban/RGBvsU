package rgbvsu.engine.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import rgbvsu.ResourceManager;
import rgbvsu.engine.World;
import rgbvsu.engine.util.Sprite;
import rgbvsu.gui.GUI;

/**
 *
 * @author Dauid
 */
public class KeyCard extends GameObject
{
    Image image;
    Color colorTransparent;
    public KeyCard(World world, Color color)
    {
       super(world, 50,50);
       setColor(color);
       colorTransparent = new Color(color.getRed(),color.getGreen(),color.getBlue(),100);
       setPassable(true);
       image = ResourceManager.getImage("objects.misc.keyCard");
    }
    
    @Override
    public Sprite getSprite()
    {
        return null;
    }
    
    @Override
    public void update(long time) {
        //do nothing
    }   

    @Override
    public void checkCollision(GameObject otherobj) {
        if( otherobj instanceof Player )
        {
            ((Player)otherobj).addKeyCard( this );
            world.getCurrentRoom().removeGameObject(this);
            GUI.getSoundEngine().playSound("pickup2");
        }
    }
    
    @Override
    public void draw(int x, int y, Graphics2D g) {        
        g.drawImage(image, x+getDrawX(),y+getDrawY(),null);
        g.setColor(colorTransparent);
        g.fillRect( x+getDrawX()+10,y+getDrawY()+10,30,25);
    }
}
