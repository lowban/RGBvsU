package rgbvsu.engine.objects;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import rgbvsu.ResourceManager;
import rgbvsu.engine.World;
import rgbvsu.engine.util.CollisionMath;
import rgbvsu.engine.util.Sprite;

public class Block extends GameObject
{
	int step;
	boolean movable, breakable;
        Image image;
        Color colorAlphad;
	
	public Block(World world, Color color, boolean movable, boolean breakable)
	{
            super(world,world.getCellWidth(),world.getCellHeight());
            step = 5;
            this.movable = movable;
            this.breakable = breakable;
            setPushable(movable);
            if(!color.equals(Color.BLACK))
                colorAlphad = new Color(color.getRed(),color.getGreen(),color.getBlue(),180);
            else
                colorAlphad = new Color(color.getRed(),color.getGreen(),color.getBlue(),80);
            setColor(color);
            String imagePath = "";
            if(movable)
                imagePath = "objects.misc.boxMovable";
            else if(breakable)
                imagePath = "objects.misc.boxBreakable";
            else
                imagePath = "objects.misc.boxStatic";
            image = ResourceManager.getImage(imagePath);
	}	       
	
        @Override
        public Sprite getSprite()
        {
            return null;
        }
        
        @Override
        public void checkCollision(GameObject otherobj)
	{
            if( otherobj instanceof Player)
                otherobj.checkCollision(this);
            if( otherobj instanceof ProjectilePhotonBolt)
                otherobj.kill();
            
            if(!onDestination() || !otherobj.alive() || !alive())
                return;
            
            if( otherobj instanceof PushProjectile )
            {			
                otherobj.kill();
                if(movable)
                {
                    PushProjectile p = (PushProjectile)otherobj;
                    if( CollisionMath.Intersects(this, otherobj))				
                    {
                        float checkX = (getX()+getWidth()*(0.5f+p.getPushX()));
                        float checkY = (getY()+getHeight()*(0.5f+p.getPushY()));

                        if(world.isSpaceAvailable(checkX, checkY))
                        {
                            destinationX = (int)(getX()+getWidth()*p.getPushX());
                            destinationY = (int)(getY()+getHeight()*p.getPushY());
                            push(destinationX,destinationY);
                        }
                    }
                }

            }
            else if( otherobj instanceof CrushProjectile)
            {
                if(breakable)
                {
                    kill();
                    otherobj.kill();
                }
            }
            else
                otherobj.checkCollision(this);                
		
	}
        
        @Override
	public void update(long time)
	{
            super.update(time);
	}
        @Override
	public void draw(int x, int y, java.awt.Graphics2D g)
	{
            g.drawImage(image, x+getDrawX(),y+getDrawY(),null);
            if(!getColor().equals(Color.BLACK))
            {
                g.setColor(colorAlphad);
		g.fillRect(x+getDrawX()+5,y+getDrawY()+5, 5, 40);
                g.fillRect(x+getDrawX()+40,y+getDrawY()+5, 5, 35);
                g.fillRect(x+getDrawX()+10,y+getDrawY()+5, 35, 5);
                g.fillRect(x+getDrawX()+10,y+getDrawY()+40, 35, 5);
            }
	}
	
}