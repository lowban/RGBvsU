package rgbvsu.engine.objects;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Image;
import rgbvsu.ResourceManager;
import rgbvsu.engine.objects.*;
import rgbvsu.engine.util.AnimationSet;
import rgbvsu.engine.util.Sprite;

public class RobotFormBasic extends RobotForm
{
        boolean disabledBlink;
        Image icon;
	public RobotFormBasic(GameObject owner)
	{
            super(owner, new Color(0.5f,0.5f,0.5f));
            AnimationSet animationSet = new AnimationSet();
            animationSet.addAnimation( ResourceManager.getAnimation("player.basic.idle") );
            animationSet.addAnimation( ResourceManager.getAnimation("player.basic.move"));
            animationSet.addAnimation( "death", ResourceManager.getAnimation("misc.explosion"));
            icon = ResourceManager.getImage("objects.player.playerBaseIdle");
            sprite = new Sprite(animationSet,50,50);      
            disabledBlink = true;
	}
        
        @Override public void action()
	{

	}
        @Override
        public Image getIcon() {
            return icon;
        }
	@Override public void draw(int x, int y, Graphics2D g)
	{
            if(owner.isEnabled())
                sprite.draw(g,x+owner.getDrawX(), y+owner.getDrawY(),owner.getRotation());	
            else
            {
                disabledBlink = !disabledBlink;
                if(disabledBlink)
                    sprite.draw(g,x+owner.getDrawX(), y+owner.getDrawY(),owner.getRotation());	
            }                                       
	}

    
}