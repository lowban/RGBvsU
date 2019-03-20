package rgbvsu.engine.objects;

import java.awt.*;
import rgbvsu.engine.World;

public abstract class Projectile extends GameObject
{
	float vx, vy;
	long life;	
	
	public Projectile(World world, float w, float h, long life)
	{
		super(world,w,h);
		this.life = life;
                setPassable(true);
	}
	
	public void update(long time)
	{
            super.update(time);
            if(alive())
            {
                translate(vx*time,vy*time);
                life -= time;
                if(life <= 0 || x < 0 || y < 0 || x > world.getWidth() || y > world.getHeight())
                    kill();
            }
	}
	
	public float getVX(){ return vx; }
	public float getVY(){ return vy; }
	public void setVX(float vx){ this.vx = vx; }
	public void setVY(float vy){ this.vy = vy; }
	
	public abstract void checkCollision(GameObject otherObject);
	public abstract void draw(int x, int y,Graphics2D g);
	
}