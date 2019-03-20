package rgbvsu.engine.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import rgbvsu.ResourceManager;
import rgbvsu.engine.World;
import rgbvsu.engine.util.AnimationSet;
import rgbvsu.engine.util.Sprite;
import rgbvsu.gui.GUI;

/**
 *
 * @author Dauid
 */
public class TeleportPlatform extends PressurePlatform 
{    
    float teleportX;
    float teleportY;
    boolean teleporting;
    long renderTimer;
    final long renderTimeLimit = 600;
    GameObject teleportingObject;
    Sprite sprite;
    
    public TeleportPlatform(World world, float w, float h, float teleportX, float teleportY)
    {
        super(world,w*world.getCellWidth(),h*world.getCellHeight());
        this.teleportX = teleportX;
        this.teleportY = teleportY;
        this.teleporting = false;
        this.renderTimer = 0;
        sprite = new Sprite(new AnimationSet(), 50,50);
        sprite.addAnimation(ResourceManager.getAnimation("floors.teleport"));
    }
    
    public void teleport(GameObject obj)
    {
        renderTimer = renderTimeLimit;
        teleporting = true;
        this.teleportingObject = obj;
        obj.setDisabledTimer(renderTimeLimit);
        GUI.getSoundEngine().playSound("teleport");
        //obj.setVisibleTimer((long)(renderTimeLimit*0.5f));        
    }
    
    @Override
    public Sprite getSprite()
    {
        return null;
    }
    
    @Override
    public void action(GameObject presser) {
        if(presser.isEnabled())
            teleport(presser);       
    }

    @Override
    public void update(long time)
    {        
        sprite.update(time);
        super.update(time);
        if(renderTimer > 0)
            renderTimer -= time;
    }
    
    @Override
    public void revertAction(GameObject releaser) {
        //Do nothing
    }

    @Override
    public void draw(int x, int y, Graphics2D g) {
        //g.setColor(Color.CYAN);
        //g.fillOval(x+getDrawX(),y+getDrawY(), (int)getWidth(),(int)getHeight());
        sprite.draw(g, x+getDrawX(),y+getDrawY(),0);
        if(teleporting)
        {
            g.setColor(Color.BLUE);
            if(renderTimer > renderTimeLimit*0.5f)
            {                
                g.fillOval(x+(int)getX()+5,y+(int)getY()+5, (int)getWidth()-10,(int)getHeight()-10);
            }
            else if(renderTimer > 0)
            {
                g.fillOval(x+(int)teleportX+5,y+(int)teleportY+5, (int)getWidth()-10,(int)getHeight()-10);
                teleportingObject.setX(teleportX);
                teleportingObject.setY(teleportY);
            }
            else
            {
                teleporting = false;
            }
        }
    }    
}
