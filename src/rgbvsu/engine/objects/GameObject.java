package rgbvsu.engine.objects;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import rgbvsu.engine.World;
import rgbvsu.engine.util.Sprite;

public abstract class GameObject
{
	public enum Facing{ LEFT,RIGHT,UP,DOWN }

	float x,y,width,height,destinationX,destinationY,pushSpeed;
	Color color;
	World world;
	Facing facing;
	boolean alive, visible, enabled, pushable, markedForRemoval, blink, passable;
        final double rotateUP=0,rotateLEFT = Math.PI*3/2,rotateRIGHT=Math.PI/2,rotateDOWN=Math.PI;
	long visibleTimer, disabledTimer, deathTimer, blinkTimer;
        
	public GameObject(World world, float width, float height)
	{
		x=y=0;
		this.width = width;this.height = height;
		this.world = world;
		alive = visible = enabled = true;
                pushable = true;
                pushSpeed = 5;
                markedForRemoval = false;
                passable = false;
                blink = false;
                deathTimer = 0;
                facing = Facing.UP;
                color = Color.BLACK;
	}	
	
	//Getters
	public Color getColor(){ return color; }
	public World getWorld(){ return world; }	
        public int getDrawX(){ return (int)x-(int)x%5;}
        public int getDrawY(){ return (int)y-(int)y%5;}
	public float getCenterX(){ return x+width/2; }
	public float getCenterY(){ return y+height/2; }
	public float getX(){ return x; }
	public float getY(){ return y; }
        public float getWidth(){ return width; }
	public float getHeight(){ return height; }
        public Facing getFacing(){ return facing; } 
        public static Facing getFacing(int dx, int dy)
        {
            if(dx==0)
                if(dy==1)
                    return Facing.DOWN;
                else
                    return Facing.UP;
            else{ 
                if(dx==1)
                    return Facing.RIGHT;
                else{
                    return Facing.LEFT;      
                }
            }
        }
        public Point getFacingOffset()
        {
            return getFacingOffset(facing);
        }
        
        public static Point getFacingOffset(Facing facing)
        {
            int dx = 0,dy = 0;
            if(facing == Facing.RIGHT)
                dx = 1;
            else if(facing == Facing.LEFT)
                dx = -1;
            else if(facing == Facing.UP)
                dy = -1;
            else if(facing == Facing.DOWN)
                dy = 1;
            return new Point(dx,dy);
        }
        
        public double getRotation()
        {
            switch(facing)
            {
                case UP:
                    return rotateUP;
                case DOWN:
                    return rotateDOWN;
                case LEFT:
                    return rotateLEFT;
                case RIGHT:
                    return rotateRIGHT;
                default:
                    return 0;
            }
        }
        
        //Flags
        public boolean isEnabled(){ return enabled; }
        public boolean isVisible(){ return visible; }
        public boolean isPushable(){ return pushable; }
        public boolean isBlinking(){ return blink; }
        public boolean onDestination(){return getX() == destinationX && getY() == destinationY;}
        public boolean isMarkedForRemoval(){ return markedForRemoval; }
        public boolean isPassable(){ return passable; }
        
        //Setters
        public void translate(float dx, float dy){ x+=dx;y+=dy;destinationX=x;destinationY=y;}
        public void setColor(Color color){ this.color = color; }
        public void setPushable(boolean value){pushable = value;}
        public void setEnabled(boolean value){ enabled = value;}
        public void setVisible(boolean value){ visible = value;}        
	public void setX(float x){ this.x = x;destinationX = x; }
	public void setY(float y){ this.y = y;destinationY = y; }	
        public void setToTranslation(float x, float y){setX(x);setY(y);}
        public void setDeathTime(long deathTime){deathTimer=deathTime; }
        public void setFacing(Facing facing){ this.facing = facing; }
        public void setPassable(boolean value){passable = value;}
        public void setPushSpeed(float speed){pushSpeed = speed;}
        public void setFacing(int dx, int dy)
        {              
            facing = getFacing(dx,dy);
        }
        
        
	
        public void setDisabledTimer(long time){disabledTimer = time;setEnabled(false);}
        public void setVisibleTimer(long time){visibleTimer = time;setVisible(false);}
        public void setBlinkTimer(long time){blinkTimer = time;blink = true;}
        
        public void push(float destinationX, float destinationY)
        {
            this.destinationX = destinationX;
            this.destinationY = destinationY;
        }
    
	public boolean inAxisView(GameObject otherObj)
	{
		float x=0, y=0,width=0, height=0;
		if( otherObj.getX() < getX()+getWidth() && otherObj.getX() + otherObj.getWidth() > getX() )
		{
			x = getCenterX();// < otherObj.getCenterX() ? getCenterX() : otherObj.getCenterX();
			y = getCenterY() < otherObj.getCenterY() ? getCenterY() : otherObj.getCenterY();
			width = 1;
			height = Math.abs(getCenterY()-otherObj.getCenterY());
		}
		else if( otherObj.getY() < getY()+getHeight() && otherObj.getY() + otherObj.getHeight() > getY() )
		{
			x = getCenterX() < otherObj.getCenterX() ? getCenterX() : otherObj.getCenterX();
			y = getCenterY();// < otherObj.getCenterY() ? getCenterY() : otherObj.getCenterY();
			width = Math.abs(getCenterX()-otherObj.getCenterX());;
			height = 1;
		}
		else
		{
			return false;
		}
		ArrayList<GameObject> list = world.getCurrentRoom().getObjectsInRect(x,y,width,height);
		return list.size() == 1;
	}
        
        public boolean inFacingView(GameObject otherObj)
        {
            Point facingOffset = getFacingOffset();
            float x = getCenterX();
            float y = getCenterY();
            float temp;
            float width = facingOffset.x*(Math.abs(getX()-otherObj.getX()));
            float height = facingOffset.y*(Math.abs(getY()-otherObj.getY()));
            
            if( width < 0 )
            {
                width = Math.abs(width);
                x -= width;             
            }
            if( height < 0)
            {
                height = Math.abs(height);
                y -= height;             
            }
            
            width = width==0?++width:width;
            height = height==0?++height:height;
            
            if( facingOffset.x == 0)
                if( !(otherObj.getX() < getX()+getWidth() && otherObj.getX() + otherObj.getWidth() > getX()) ||
                        (otherObj.getY() < getY()*facingOffset.y))
                    return false;
                    
            if( facingOffset.y == 0)
                if(!(otherObj.getY() < getY()+getHeight() && otherObj.getY() + otherObj.getHeight() > getY()) ||
                        (otherObj.getX() < getX()*facingOffset.x))
                    return false;
            
            
            ArrayList<GameObject> list = world.getCurrentRoom().getObjectsInRect(x,y,width,height);
            return list.size() == 1;
        }
        
	public void kill(){ alive = false; }
        public void stun(long duration)
        {
            setDisabledTimer(duration);
            setBlinkTimer(duration);
        }
        
        
	public boolean alive(){ return alive; }
	public long getDebug(){return deathTimer;}
        
	public void update(long time)
        {
            //Update timers
            if(disabledTimer > 0) disabledTimer -= time; 
            else setEnabled(true);

            if(visibleTimer > 0) visibleTimer -= time;
            else setVisible(true);
            
            if(!alive && deathTimer > 0)
                deathTimer -= time;
            else if(!alive && deathTimer <= 0)
                markedForRemoval = true;
            if(blinkTimer > 0) blinkTimer -= time;
            else blink = false;
            
            
            //For pushing
            if(destinationX != getX())
                x += pushSpeed*Math.signum(destinationX-getX());
            if(destinationY != getY())
                y += pushSpeed*Math.signum(destinationY-getY());
        }
        public abstract Sprite getSprite();
	public abstract void draw(int x, int y, java.awt.Graphics2D g);
	public abstract void checkCollision(GameObject otherobj);
}