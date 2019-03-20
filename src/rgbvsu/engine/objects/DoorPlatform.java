package rgbvsu.engine.objects;

import java.awt.*;
import rgbvsu.ResourceManager;
import rgbvsu.engine.World;
import rgbvsu.engine.util.Sprite;

public class DoorPlatform extends PressurePlatform
{
	int door;
        Image image;
	
	public DoorPlatform(World world, float w, float h, Color colorCode,int door)
	{
		super(world,w,h);
		this.door = door;
		setColor(colorCode);
                image = ResourceManager.getImage("objects.misc.doorPlatform");
	}
	
        @Override
        public Sprite getSprite()
        {
            return null;
        }
        
        @Override
	public void action(GameObject presser)
	{
            getWorld().getCurrentRoom().openDoor(door);
	}
	
        @Override
	public void revertAction(GameObject releaser)
	{
            getWorld().getCurrentRoom().closeDoor(door);
	}
	
        @Override
	public void draw(int x, int y, Graphics2D g)
	{
            g.drawImage(image, x+getDrawX(),y+getDrawY(),null);
            g.setColor(getColor());
            g.fillRect(x+getDrawX()+20,y+getDrawY()+20,10,10);
		//g.setPaint(getColor() == null ? getWorld().getCurrentRoom().getColor().darker() : getColor().darker());
		//g.fillOval((int)(x+getX()),(int)(y+getY()), (int)getWidth(), (int)getHeight());
		//g.setPaint(getColor() == null ? getWorld().getCurrentRoom().getColor() : getColor());
		//g.fillOval((int)(x+getX()+10),(int)(y+getY()+10), (int)getWidth()-20, (int)getHeight()-20);		
	}
}