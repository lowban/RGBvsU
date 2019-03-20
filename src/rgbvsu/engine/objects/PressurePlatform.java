package rgbvsu.engine.objects;

import java.awt.*;
import rgbvsu.engine.util.CollisionMath;
import rgbvsu.engine.World;
import rgbvsu.engine.ai.Enemy;
import rgbvsu.engine.objects.*;

public abstract class PressurePlatform extends GameObject
{
	boolean pressed, last, check;
        GameObject lastPresser;
	public PressurePlatform(World world, float width, float height)
	{
            super(world,width,height);
            setPassable(true);
	}
	
	public abstract void action(GameObject obj);
	public abstract void revertAction(GameObject obj);
	
	public void update(long time)
	{   
            if(!pressed && last)
            {
                revertAction(lastPresser);
                last = pressed = false;
            }
            
            pressed = false;            
	}
	
	public void checkCollision(GameObject otherObj)
	{            
            if(pressed)
                return;
            if( !otherObj.isPassable() || otherObj instanceof Player )
            {	
                //if(CollisionMath.Intersects(this,otherObj)&& (getColor() == null || otherObj.getColor().equals(getColor())))
                if(CollisionMath.Inside(this,otherObj.getCenterX(), otherObj.getCenterY())&& (getColor().equals(Color.BLACK) || otherObj.getColor().equals(getColor())))
                {
                    pressed = true;
                    lastPresser = otherObj;
                }
            }
            if(pressed && !last)
            {
                action(otherObj);
            }
            last = pressed;		
	}
        
	public abstract void draw(int x, int y, Graphics2D g);
}