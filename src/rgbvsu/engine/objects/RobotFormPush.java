package rgbvsu.engine.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import rgbvsu.ResourceManager;
import rgbvsu.engine.util.AnimationSet;
import rgbvsu.engine.util.Sprite;

public class RobotFormPush extends RobotForm
{
        boolean disabledBlink;
        Image icon;
        
	public RobotFormPush(GameObject owner)
	{
            super(owner, new Color(0.5f,0.5f,0.5f));
            AnimationSet animationSet = new AnimationSet();
            animationSet.addAnimation( ResourceManager.getAnimation("player.push.idle") );
            animationSet.addAnimation( ResourceManager.getAnimation("player.push.move"));
            animationSet.addAnimation( "death", ResourceManager.getAnimation("misc.explosion"));
            icon = ResourceManager.getImage("objects.player.playerPushIdle");
            sprite = new Sprite(animationSet,50,50);      
            disabledBlink = true;
	}
        @Override
        public Image getIcon() {
            return icon;
        }
        @Override public void action()
	{
            int vx=0 , vy = 0;
            if(owner.getFacing() == GameObject.Facing.LEFT ) vx = -1;
            else if(owner.getFacing() == GameObject.Facing.RIGHT) vx = 1;
            else if(owner.getFacing() == GameObject.Facing.UP) vy = -1;
            else if(owner.getFacing() == GameObject.Facing.DOWN) vy = 1;

            PushProjectile pushProjectile = new PushProjectile(owner.getWorld(), vx,vy);
            float x = owner.getCenterX() - pushProjectile.getWidth()*0.5f + pushProjectile.getWidth()*1.5f*vx;
            float y = owner.getCenterY() - pushProjectile.getHeight()*0.5f + pushProjectile.getHeight()*1.5f*vy;
            pushProjectile.translate(x, y);
            owner.getWorld().addProjectile( pushProjectile);
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