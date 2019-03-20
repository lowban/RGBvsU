package rgbvsu.engine;

import java.awt.*;
import java.util.ArrayList;
import rgbvsu.ResourceManager;
import rgbvsu.engine.ai.AI;
import rgbvsu.engine.objects.GameObject;
import rgbvsu.engine.objects.KeyCard;
import rgbvsu.engine.util.AnimationSet;
import rgbvsu.engine.util.CollisionMath;
import rgbvsu.gui.GUI;

public class Room
{
        final long BLINK_INTERVAL=25;
        public final static int LEFT=0,RIGHT=1,TOP=2,BOTTOM=3; 
	public static final int DOORSIZE = 70;
	public static final int DOORIMAGESIZE2 = 95;
        public static final int DOORIMAGESIZE = 190;
        
        
        Door[] doors;
        String musicName;
        int goalDoor;
	Color color;
        Color[] colorLocks;
	boolean finished, discovered, blinkObjects;
        Image walls,floor,doorLeft,doorRight,doorTop,doorBottom,wallLeft,wallRight,wallTop,wallBottom,doorBottomClosed;
	ArrayList<GameObject> objects;
	ArrayList<AI> aiList;	
	World world;
	int x,y;
        boolean lockOnEnter;
        long objectBlinkTimer;
        int onEnterDialogue;        
	
        
	public Room(World world, int x, int y)
	{
            doors = new Door[]{ new Door(LEFT), new Door(RIGHT),new Door(TOP),new Door(BOTTOM)};
            objects = new ArrayList<>();
            aiList = new ArrayList<>();
            colorLocks = new Color[]{Color.BLACK,Color.BLACK,Color.BLACK,Color.BLACK};
            finished = false;
            discovered = false;
            blinkObjects = false;
            this.world = world;
            this.x = x;
            this.y = y;
            this.color = Color.BLACK;
            this.goalDoor = -1;
            this.lockOnEnter = false;
            this.objectBlinkTimer = 0;
            onEnterDialogue = -1;
            musicName = "";
            walls = ResourceManager.getImage("room.walls");
            floor = ResourceManager.getImage("room.floor");
            doorLeft =  ResourceManager.getImage("room.doorLeft");
            doorRight =  ResourceManager.getImage("room.doorRight");
            doorTop =  ResourceManager.getImage("room.doorTop");
            doorBottom = ResourceManager.getImage("room.doorBottom");
            doorBottomClosed = ResourceManager.getImage("room.doorBottomClosed");
            wallLeft =  ResourceManager.getImage("room.wallLeft");
            wallRight =  ResourceManager.getImage("room.wallRight");
            wallTop =  ResourceManager.getImage("room.wallTop");
            wallBottom =  ResourceManager.getImage("room.wallBottom");
	}

        public static int invertDoor(int door)
        {
            if(door == LEFT || door == TOP)
                return door+1;
            else
                return door-1;
        }  
        
	public World getWorld(){ return world; }
	public Color getColor(){ return color; }
        public int getGoalDoor(){ return goalDoor; }
        public ArrayList<GameObject> getObjects(){ return objects; }
        public int getX(){ return x; }
	public int getY(){ return y; }        
        public int getEnterDialogue(){return onEnterDialogue;}
        public String getMusic(){ return musicName; }
        
        public boolean isFinished(){ return finished; }
        public boolean isDiscovered(){ return discovered; }
        public boolean hasOnEnterDialogue(){ return onEnterDialogue >= 0; }
        
        public void setMusic(String musicName){ this.musicName = musicName;}
        public void setDiscovered(boolean value){ discovered = value; }	       
        public void setOnEnterDialogue(int dialogueID){ onEnterDialogue = dialogueID;}
        public void setColor(Color color){ this.color = color; }
        public void setGoalDoors(byte goalDoors){ this.goalDoor = goalDoors; }
	public void setFinished(boolean value){ finished = value; }
        public void setLockOnEnter(boolean value){lockOnEnter = value;}
             
        public Room getRoomFromDoor(int door)
        {
            if(door == LEFT)
                return world.getRoom(getX()-1,getY());
            else if(door == RIGHT)
                return world.getRoom(getX()+1,getY());
            else if(door == TOP)
                return world.getRoom(getX(),getY()-1);
            else if(door == BOTTOM)
                return world.getRoom(getX(),getY()+1);
            else
                return null;
        }
	
	public ArrayList<GameObject> getObjectsAt(float x, float y, GameObject ignore)
	{
		ArrayList<GameObject> ArrayList = new ArrayList<>();
		for(int i = 0;i<objects.size();i++)
                {
			if(CollisionMath.Inside(objects.get(i), x, y) && objects.get(i) != ignore)
				ArrayList.add(objects.get(i));
                        }
		return ArrayList;
	}	
        
	public ArrayList<GameObject> getObjectsInRect(float x, float y, float width, float height)
	{
		ArrayList<GameObject> ArrayList = new ArrayList<>();
		Rectangle rect = new Rectangle((int)x,(int)y,(int)width,(int)height);
		for(int i = 0;i<objects.size();i++)
                 {
			if(CollisionMath.Intersects(objects.get(i), rect))
				ArrayList.add(objects.get(i));
                }
		return ArrayList;
	}	
		
	public void resetRoom()
	{
            aiList.clear();
            objects.clear();
            GUI.getMapLoader().createObjectsForRoom(getX(),getY());    

            //IF any keycards was acquired from this room, remove them
            for(int i = 0;i<objects.size();i++)
                if(objects.get(i) instanceof KeyCard)                        
                    if(world.getPlayer().hasKeyCard(objects.get(i).getColor()))
                        world.getPlayer().removeKeyCard(objects.get(i).getColor());

            if(world.getCurrentRoom() == this)
            {
                int dxRoom = world.getLastRoom().getX()-getX(); 
                int dyRoom = world.getLastRoom().getY()-getY();

                float playerposX = (world.getWidth()-world.getPlayer().getWidth()-5)*(1+dxRoom)*0.5f;
                float playerposY = (world.getHeight()-world.getPlayer().getHeight()-5)*(1+dyRoom)*0.5f;
                world.getPlayer().setToTranslation(playerposX, playerposY);
            }
	}
	
	public void addAI(AI ai)
	{
		aiList.add(ai);
		addGameObject(ai.getGameObject(), -1);
	}
	
	public void addGameObject(GameObject object, int index)
	{
		if(index==-1)
			objects.add(object);
		else
			objects.add(index,object);
	}
	public void removeGameObject(GameObject object)
	{
		objects.remove(object);
	}
        
        
        public boolean isDoorOpened(int door)
        {
            if( doors[door].isOpened() )
                return true;
            else if(doors[door].getColorLock().equals(Color.BLACK))
                return false;
            else if(world.getPlayer().hasKeyCard(doors[door].getColorLock()))
            {
                doors[door].open();
                getRoomFromDoor(door).doors[invertDoor(door)].setOpened(true);
                return true;
            }
            else
                return false;//This will never happen
        }
	
        public void enterDoor(int door)
        {
            Room room = getRoomFromDoor(door);
            world.changeRoom( room );
            room.setDiscovered(true);            
            if(!room.finished && room.lockOnEnter)
            {
                room.closeDoor(LEFT);
                room.closeDoor(RIGHT);
                room.closeDoor(TOP);
                room.closeDoor(BOTTOM);
            }
            if(door == goalDoor || goalDoor == -1)
               finished = true;

        }
        
        public void enableDoor(int door, boolean opened)
        {
            doors[door].setEnabled(true);
            doors[door].setOpened(opened);
            getRoomFromDoor(door).doors[invertDoor(door)].setEnabled(true);
            getRoomFromDoor(door).doors[invertDoor(door)].setOpened(opened);
        }
        
        public boolean isDoorEnabled(int door)
        {
            return doors[door].isEnabled();
        }
        
        public void closeDoor(int door)
        {
            if(isDoorEnabled(door))
            {
                doors[door].close();
                getRoomFromDoor(door).doors[invertDoor(door)].setOpened(false);
            }
        }
        
        public void openDoor(int door)
        {
            if(isDoorEnabled(door))
            {
                doors[door].open();
                getRoomFromDoor(door).doors[invertDoor(door)].setOpened(true);
            }
        }	
	
        public void setColorLock(int door, Color colorLock)
        {
            doors[door].setColorLock(colorLock);
            getRoomFromDoor(door).doors[invertDoor(door)].setColorLock(colorLock);
        }
        
	public void update(long time)
	{
            objectBlinkTimer += time;
            if(objectBlinkTimer >= BLINK_INTERVAL)
            {
                objectBlinkTimer = 0;
                blinkObjects = !blinkObjects;
            }
            for(int i = 0; i < 4; i++)
                doors[i].update(time);
            for(int i = 0;i<aiList.size();i++)
            {
                if(aiList.get(i).getGameObject().isMarkedForRemoval())
                    aiList.remove(i);
                else if(aiList.get(i).getGameObject().isEnabled())
                    aiList.get(i).update(time);
            }
            for(int i = 0;i<objects.size();i++)
            {
                if(objects.get(i).isMarkedForRemoval())
                    objects.remove(i);
                else           
                    objects.get(i).update(time);
            }            
	}	        
        
        public void drawOverlay(int x, int y, Graphics2D g)
        {
            for(int i = 0; i < 4; i++)
                doors[i].draw(x,y,g);
        }
        
	public void draw(int x, int y, Graphics2D g)
	{
                g.drawImage(floor,x-GUI.getOffset(),y-GUI.getOffset(),null);
                g.drawImage(walls,x-GUI.getOffset(),y-GUI.getOffset(),null);
		//Drawgrid
		for(int i = 0;i<world.getNumCellsX();i++)
		{
                    for(int j = 0;j<world.getNumCellsY();j++)
                    {
                        float[] comp = getColor().getColorComponents(null);
                        g.setPaint( (i+j)%2==0? new Color(comp[0]*0.6f,comp[1]*0.6f,comp[2]*0.6f,0.5f) : new Color(comp[0],comp[1],comp[2],0.4f) );
                        g.fillRect(i*world.getCellWidth()+x,j*world.getCellWidth()+y, world.getCellWidth(),world.getCellHeight());
                    }
		}
                
		
		
		//Draw objects
		g.setPaint( new Color(1.0f,1.0f,1.0f,1.0f) );
		for(int i = 0;i<objects.size();i++)
		{
                    if(!objects.get(i).isBlinking() || blinkObjects)
			objects.get(i).draw(x,y,g);
		}
                if(world.getPlayer().isBlinking())
                    world.getPlayer().setVisible(blinkObjects);
	}
        
        
        private class Door
        {            
            boolean opened, enabled;
            AnimationSet animationSet;
            int ID;
            Color colorLock,colorLockTransparent;
            Point position;
            int width,height;
            
            public Door(int ID)
            {
                this.ID = ID;
                animationSet = new AnimationSet();
                colorLock = Color.BLACK;
                switch(ID)
                {
                    case Room.LEFT:
                        position = new Point(-GUI.getOffset(),(int)(GUI.getHeight()*0.5f)-Room.DOORIMAGESIZE2-GUI.getOffset());                        
                        animationSet.addAnimation( ResourceManager.getAnimation("doorLeft.disabled") );
                        animationSet.addAnimation( ResourceManager.getAnimation("doorLeft.idle.closed") );
                        animationSet.addAnimation( ResourceManager.getAnimation("doorLeft.idle.opened") );
                        animationSet.addAnimation( ResourceManager.getAnimation("doorLeft.open") );
                        animationSet.addAnimation( ResourceManager.getAnimation("doorLeft.close") );
                        width = 70;height = 190;
                        break;               
                    case Room.RIGHT:
                        position = new Point(GUI.getWidth()-GUI.getOffset()*2,(int)(GUI.getHeight()*0.5f)-Room.DOORIMAGESIZE2-GUI.getOffset());
                        animationSet.addAnimation( ResourceManager.getAnimation("doorRight.disabled") );
                        animationSet.addAnimation( ResourceManager.getAnimation("doorRight.idle.closed") );
                        animationSet.addAnimation( ResourceManager.getAnimation("doorRight.idle.opened") );
                        animationSet.addAnimation( ResourceManager.getAnimation("doorRight.open") );
                        animationSet.addAnimation( ResourceManager.getAnimation("doorRight.close") );
                        width = 70;height = 190;
                        break;
                    case Room.TOP:
                        position = new Point((int)(GUI.getWidth()*0.5f)-Room.DOORIMAGESIZE2-GUI.getOffset(),-GUI.getOffset());  
                        animationSet.addAnimation( ResourceManager.getAnimation("doorTop.disabled") );
                        animationSet.addAnimation( ResourceManager.getAnimation("doorTop.idle.closed") );
                        animationSet.addAnimation( ResourceManager.getAnimation("doorTop.idle.opened") );
                        animationSet.addAnimation( ResourceManager.getAnimation("doorTop.open") );
                        animationSet.addAnimation( ResourceManager.getAnimation("doorTop.close") );
                        width = 190;height = 70;
                        break;
                    case Room.BOTTOM:
                        position = new Point((int)(GUI.getWidth()*0.5f)-Room.DOORIMAGESIZE2-GUI.getOffset(), GUI.getHeight()-GUI.getOffset()*2);  
                        animationSet.addAnimation( ResourceManager.getAnimation("doorBottom.disabled") );
                        animationSet.addAnimation( ResourceManager.getAnimation("doorBottom.idle.closed") );
                        animationSet.addAnimation( ResourceManager.getAnimation("doorBottom.idle.opened") );
                        animationSet.addAnimation( ResourceManager.getAnimation("doorBottom.open") );
                        animationSet.addAnimation( ResourceManager.getAnimation("doorBottom.close") );
                        width = 190;height = 70;
                        break;
                }
                setEnabled(false);
            }            
            
            public void setColorLock(Color colorLock)
            { 
                this.colorLock = colorLock; 
                this.colorLockTransparent = new Color(colorLock.getRed(),colorLock.getGreen(),colorLock.getBlue(),100);
            }
            public Color getColorLock(){ return colorLock; }
            public boolean isEnabled(){ return enabled; }
            public boolean isOpened(){ return opened; }
            
            public void setEnabled(boolean value)
            {
                enabled = value;
                if(enabled)
                    animationSet.setAnimation("idle.closed");
                else
                    animationSet.setAnimation("disabled");
            }
            

            public void setOpened(boolean value)
            {
                opened = value;
                if(value)
                    animationSet.setAnimation("idle.opened");
                else
                    animationSet.setAnimation("idle.closed");
            }
            
            public void close()
            {
                animationSet.setAnimation("close");
                opened = false;
            }
            
            public void open()
            {
                animationSet.setAnimation("open");
                opened = true;
            }
            
            public void update(long time)
            {
                animationSet.update(time);
            }
            
            public void draw(int x, int y, Graphics2D g)
            {
                  g.drawImage( animationSet.getImage(), x+position.x, y+position.y, null);
                  if(!colorLock.equals(Color.BLACK) && !opened && enabled)
                  {
                    g.setColor(colorLockTransparent);
                    g.fillRect(x+position.x+30,y+position.y+30,5,130);
                    g.fillRect(x+position.x+35,y+position.y+35,5,120);
                    g.fillRect(x+position.x+40,y+position.y+40,5,110);
                  }
            }
        }
}