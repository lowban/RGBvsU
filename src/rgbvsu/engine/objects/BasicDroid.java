package rgbvsu.engine.objects;

import java.awt.Graphics2D;
import rgbvsu.ResourceManager;
import rgbvsu.engine.World;
import rgbvsu.engine.util.CollisionMath;
import rgbvsu.engine.ai.Enemy;
import rgbvsu.engine.util.AnimationSet;
import rgbvsu.engine.util.Sprite;


public class BasicDroid extends Enemy
{
        Sprite sprite;
	public BasicDroid(World world, float w, float h)
	{
		super(world,50,50);
                AnimationSet set = new AnimationSet();
                set.addAnimation(ResourceManager.getAnimation("basicdroid.normal"));   
                sprite = new Sprite(set, 50,50);
	}

        @Override
        public Sprite getSprite()
        {
            return sprite;
        }	
        
        @Override
	public void checkCollision(GameObject otherobj)
	{
		if(CollisionMath.Intersects(this, otherobj))
			getAI().collisionResponse(otherobj);
	}
	
        @Override
	public void update(long time)
	{
		sprite.update(time);
	}
	
        @Override
	public void draw(int x, int y, Graphics2D g)
	{
		sprite.draw(g, x+(int)getX(),y+(int)getY(), getRotation());
	}
	
}