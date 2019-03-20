package rgbvsu.engine.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import rgbvsu.ResourceManager;
import rgbvsu.engine.World;
import rgbvsu.engine.util.Animation;
import rgbvsu.engine.util.CollisionMath;
import rgbvsu.engine.util.Sprite;
import rgbvsu.gui.GUI;

/**
 *
 * @author Dauid
 */
public class ElectrifiedFloor extends PressurePlatform{

    int width,height;
    Animation animation;
    
    public ElectrifiedFloor(World world, int width, int height)
    {
	super(world,width*world.getCellWidth(),height*world.getCellHeight());
        this.width = width;
        this.height = height;
        animation = ResourceManager.getAnimation("floors.electric");
    }
    
    @Override
    public Sprite getSprite()
    {
        return null;
    }
    
    @Override
    public void action(GameObject presser) 
    {
        System.out.println("ACTION");
        if(presser instanceof Player)
            if(presser.isEnabled())
            {
                Player player = (Player)presser;
                float pushX = player.getX();
                float pushY = player.getY();
                CollisionMath.MinimumTranslation(presser, this);
                if(Math.abs(pushX - player.getX()) < Math.abs(pushY - player.getY()))
                {
                    pushX = 0;
                    pushY = 50*Math.signum(player.getY() - pushY);
                }
                else
                {                    
                    pushX = 50*Math.signum(player.getX() - pushX);
                    pushY = 0;
                }
                
                player.damage(5,pushX,pushY,300);
                GUI.getSoundEngine().playSound("electrichit");
            }
        pressed = false;
    }

    @Override
    public void revertAction(GameObject releaser) 
    {
        //NOTHING!
    }
    
    @Override
    public void update(long time)
    {
        super.update(time);
        animation.update(time);
    }
    @Override
    public void draw(int x, int y, Graphics2D g) {
        g.drawImage(animation.getImage(),x+getDrawX(),y+getDrawY(),null);
        //g.setColor( new Color(100,100,255));
        //g.drawLine(x+(int)getX(),y+(int)getY(),x+(int)(getX()+getWidth()), y+(int)(getY()+getHeight()));
        //g.drawLine(x+(int)(getX()+getWidth()),y+(int)getY(),x+(int)getX(), y+(int)(getY()+getHeight()));
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
