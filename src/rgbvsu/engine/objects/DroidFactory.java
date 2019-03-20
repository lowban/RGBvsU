package rgbvsu.engine.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import rgbvsu.ResourceManager;
import rgbvsu.engine.World;
import rgbvsu.engine.ai.Enemy;
import rgbvsu.engine.ai.StupidDroidAI;
import rgbvsu.engine.util.AnimationSet;
import rgbvsu.engine.util.Sprite;

public class DroidFactory extends Enemy
{
	long spawnInterval, timer;
        Sprite sprite;
	
	public DroidFactory(World world, long spawnInterval)
	{
		super(world,50,50);
		this.spawnInterval = spawnInterval;
		timer = 0;
                sprite = new Sprite(new AnimationSet(),50,50);
                sprite.addAnimation(ResourceManager.getAnimation("factory.idle"));
	}
	
	public void spawn()
	{
            StupidDroid droid = new StupidDroid(getWorld(),50,50);
            droid.translate(getX(),getY()-50);
            StupidDroidAI ai = new StupidDroidAI(droid);
            getWorld().getCurrentRoom().addAI(ai);
	}
	
        @Override
        public Sprite getSprite() {
           return sprite;
        }
        
	@Override
	public void checkCollision(GameObject otherObj)
	{
	}
	
	@Override
	public void update(long time)
	{
            sprite.update(time);
            timer += time;
            if(timer >= spawnInterval)
            {
                timer = 0;
                spawn(); 	
            }
	}
	
	@Override
	public void draw(int x, int y, Graphics2D g)
	{
            sprite.draw(g,x+getDrawX(),y+getDrawY(),0);
	}


}