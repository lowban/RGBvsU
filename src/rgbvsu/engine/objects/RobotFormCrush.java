package rgbvsu.engine.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import rgbvsu.ResourceManager;
import rgbvsu.engine.util.AnimationSet;
import rgbvsu.engine.util.Sprite;

public class RobotFormCrush extends RobotForm
{
        boolean disabledBlink;
        Image icon;
	public RobotFormCrush(GameObject owner)
	{
            super(owner, new Color(0.5f,0.5f,0.5f));
            AnimationSet animationSet = new AnimationSet();
            animationSet.addAnimation( ResourceManager.getAnimation("player.crush.idle") );
            animationSet.addAnimation( ResourceManager.getAnimation("player.crush.move"));
            animationSet.addAnimation( "death", ResourceManager.getAnimation("misc.explosion"));
            icon = ResourceManager.getImage("objects.player.playerCrushIdle");
            sprite = new Sprite(animationSet,50,50);
            disabledBlink = true;
	}
        @Override
        public Image getIcon() {
            return icon;
        }
        @Override public void action()
	{
            Point facingOffset = owner.getFacingOffset();
            CrushProjectile crushProjectile = new CrushProjectile(owner.getWorld(), owner.getFacing());
            float x = owner.getCenterX() - crushProjectile.getWidth()*0.5f + crushProjectile.getWidth()*1.5f*facingOffset.x;
            float y = owner.getCenterY() - crushProjectile.getHeight()*0.5f + crushProjectile.getHeight()*1.5f*facingOffset.y;
            crushProjectile.translate(x, y);
            owner.getWorld().addProjectile( crushProjectile);
	}
        
	@Override public void draw(int x, int y, Graphics2D g)
	{
            sprite.draw(g,x+owner.getDrawX(), y+owner.getDrawY(),owner.getRotation());
            /*if(owner.isEnabled())
                sprite.draw(g,x+owner.getDrawX(), y+owner.getDrawY(),owner.getRotation());	
            else
            {
                disabledBlink = !disabledBlink;
                if(disabledBlink)
                    sprite.draw(g,x+owner.getDrawX(), y+owner.getDrawY(),owner.getRotation());	
            } */                                      
	}

    
}