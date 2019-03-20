package rgbvsu.engine.objects;

import java.awt.*;
import rgbvsu.engine.util.Sprite;

public abstract class RobotForm
{
        Sprite sprite;
	GameObject owner;
	Color color;
	boolean poweredUp;
	long poweredUpTimer=0;
	
	public RobotForm(GameObject owner, Color color)
	{
		this.owner = owner;
		this.color = color;
	}
	
        public Sprite getSprite(){ return sprite;}
        public abstract Image getIcon();
	
	public void setPoweredUp(long duration)
	{
		poweredUpTimer = duration;
		poweredUp = true;
	}
	
	public boolean isPoweredUp(){ return poweredUp; }
	
	public void update(long time)
	{            
            sprite.update(time);
            if( poweredUpTimer > 0 )
                poweredUpTimer -= time;
            else
                poweredUp = false;
	}
	
	public abstract void draw(int x, int y,Graphics2D g);
	public abstract void action();
}