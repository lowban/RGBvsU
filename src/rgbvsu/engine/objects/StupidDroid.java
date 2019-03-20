package rgbvsu.engine.objects;

import java.awt.Graphics2D;
import rgbvsu.ResourceManager;
import rgbvsu.engine.World;
import rgbvsu.engine.ai.Enemy;
import rgbvsu.engine.util.AnimationSet;
import rgbvsu.engine.util.CollisionMath;
import rgbvsu.engine.util.Sprite;
import rgbvsu.gui.GUI;

/**
 *
 * @author Robin Horneman
 */
public class StupidDroid extends Enemy {
        Sprite sprite;
        public StupidDroid(World world, float w, float h)
	{
            super(world,w,h);
            sprite = new Sprite(new AnimationSet(),50,50);
            sprite.addAnimation(ResourceManager.getAnimation("stupiddroid.idle"));
            sprite.addAnimation("death",ResourceManager.getAnimation("misc.explosion"));
            setDeathTime(250);
	}

        @Override
        public Sprite getSprite()
        {
            return sprite;
        }	
        
        @Override
	public void checkCollision(GameObject otherobj)
	{
            if(otherobj instanceof CrushProjectile)
            {
                sprite.setAnimation("death");
                GUI.getSoundEngine().playSound("explosion2");
                this.kill();
                otherobj.kill();
            }
            else if(CollisionMath.Intersects(this, otherobj))
                getAI().collisionResponse(otherobj);
	}
	
        @Override
	public void update(long time)
	{            
            super.update(time);
            sprite.update(time);
	}
	
        @Override
	public void draw(int x, int y, Graphics2D g)
	{
            sprite.draw(g, x+getDrawX(),y+getDrawY(),getRotation());            
	}
}
