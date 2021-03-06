package rgbvsu.engine.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import rgbvsu.ResourceManager;
import rgbvsu.engine.World;
import rgbvsu.engine.ai.Enemy;
import rgbvsu.engine.util.CollisionMath;
import rgbvsu.engine.util.Sprite;
import rgbvsu.gui.GUI;

public class ProjectilePhotonBolt extends Projectile
{
	int dirX,dirY;
	Image image;
        
	public ProjectilePhotonBolt(World world, float x, float y, int dirX, int dirY, float speed, long life)
	{
            super(world,10,10, life);
            setX(x-width/2);setY(y-height/2);
            this.dirX = dirX;this.dirY = dirY;
            setColor( Color.YELLOW);
            setVX(dirX*speed);setVY(dirY*speed);
            image = ResourceManager.getImage("objects.projectiles.photonBolt");
	}
	
        @Override
        public Sprite getSprite()
        {
            return null;
        }
        
        @Override
	public void checkCollision(GameObject otherObject)
	{
            if(otherObject instanceof Enemy)
                otherObject.checkCollision(otherObject);
            else if(otherObject instanceof Player)
            {
                Player player = (Player)otherObject;
                player.damage(5, 50*dirX,50*dirY,700);
                GUI.getSoundEngine().playSound("impact4");
                kill();
            }
            else if(otherObject instanceof Block)
                if(CollisionMath.Intersects(this, otherObject))
                    kill();
	}
	
        @Override
	public void draw(int x, int y, Graphics2D g)
	{
            g.drawImage(image,x+getDrawX()-5,y+getDrawY()-5,null );
	}
}