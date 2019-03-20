package rgbvsu.engine.objects;

import java.awt.*;
import java.util.ArrayList;
import rgbvsu.ResourceManager;
import rgbvsu.engine.World;
import rgbvsu.engine.ai.Enemy;
import rgbvsu.engine.util.AnimationSet;
import rgbvsu.engine.util.CollisionMath;
import rgbvsu.engine.util.Sprite;

public class BlueBoss extends Enemy
{
	ArrayList<WeakSpot> weakSpots;
        ArrayList<WeakSpot> aliveWeakSpots;
        ArrayList<Sprite> explosions;
        ArrayList<Point> explosionPositions;
	long activateWeakspotInterval, timer;
	boolean weakSpotActivated;
        Image image;
	
	public BlueBoss(World world)
	{
            super(world, 250,150);
            weakSpots = new ArrayList<>();
            aliveWeakSpots = new ArrayList<>();
            explosions = new ArrayList<>();
            explosionPositions = new ArrayList<>();
            weakSpots.add(new WeakSpot(this,0,50, false, -1,0));
            weakSpots.add(new WeakSpot(this,100,100, true, 0,1));
            weakSpots.add(new WeakSpot(this,200,50, false, 1,0)); 
            aliveWeakSpots.addAll(weakSpots);          
            weakSpotActivated = false;
            image = ResourceManager.getImage("objects.bosses.blue.blueBoss");
            setDeathTime(3500);
            resetTimer();            
	}
	
        public void resetTimer()
        {
            activateWeakspotInterval = (int)(Math.random()*2500+2500);
            timer = 0;
        }
        
        
        
        public void activateWeakSpot()
        {
            weakSpotActivated = true;     
            int random =  (int)(Math.random()*aliveWeakSpots.size());
            WeakSpot spot = aliveWeakSpots.get( random );
            spot.Active = true;
            
            int dx=0,dy=0;
            if(aliveWeakSpots.get(random).Horizontal)
            {
                dx = 1;
                dy = 0;
            }
            else
            {
                dx = 0;
                dy = 1;
            }
            
            for(int i = -1;i<2;i++)
            {
                int x = (int)getX()+spot.Rect.x+dx*i*spot.Rect.width+spot.Rect.width*spot.DX;
                int y = (int)getY()+spot.Rect.y+dy*i*spot.Rect.height+spot.Rect.height*spot.DY;
                
                if(getWorld().getCurrentRoom().getObjectsAt(x+25, y+25, null).isEmpty())
                {
                    Block block = new Block(getWorld(), Color.BLUE, false, true);
                    block.translate(x,y);                        
                    if(CollisionMath.Intersects(getWorld().getPlayer(), block))
                    {
                        getWorld().getPlayer().damage(1,spot.DX*50,spot.DY*50,500);
                    }
                    getWorld().getCurrentRoom().addGameObject(block, 0);
                }
            }
        }
        
        public void resetWeakSpots()
        {
            weakSpotActivated = false;
            for(int i = 0;i<weakSpots.size();i++)
                weakSpots.get(i).Active = false;
        }
        
        @Override
        public Sprite getSprite(){return null;}
        
	@Override
	public void checkCollision(GameObject otherObj)
	{
            if( otherObj instanceof Player)
            {
                CollisionMath.MinimumTranslation(otherObj, this);
            }
            else if(otherObj instanceof Projectile)
            {
                for(int i = 0; i <aliveWeakSpots.size();i++)
                {
                    if(aliveWeakSpots.get(i).Active)
                    {
                        Rectangle rect = new Rectangle( aliveWeakSpots.get(i).Rect.x+(int)getX(),
                                                        aliveWeakSpots.get(i).Rect.y+(int)getY(),
                                                        aliveWeakSpots.get(i).Rect.width,
                                                        aliveWeakSpots.get(i).Rect.height);
                        if( CollisionMath.Inside(rect, otherObj.getCenterX(),otherObj.getCenterY()))
                        {
                            aliveWeakSpots.get(i).Alive = false;
                            aliveWeakSpots.remove(i);
                            resetWeakSpots();
                            resetTimer();
                            if(aliveWeakSpots.isEmpty())
                            {
                                kill();
                                timer = 0;
                                KeyCard keyCard = new KeyCard(getWorld(), Color.BLUE);
                                keyCard.translate(getX(),getY());
                                getWorld().getCurrentRoom().addGameObject(keyCard, 0);
                            }
                        }
                    }
                }
            }
	}
	
	@Override
	public void update(long time)
	{
            super.update(time);
            if(alive())
            {
                timer += time;
                if( timer >= activateWeakspotInterval)
                {
                    resetTimer();
                    if(weakSpotActivated)
                    {
                        resetWeakSpots();
                    }
                    else
                    {
                        activateWeakSpot();                    
                    }
                }
            }
            else
            {
                timer += time;
                if(timer >= 50)
                {
                    Sprite sprite = new Sprite(50,50);
                    sprite.addAnimation(ResourceManager.getAnimation("misc.explosion"));
                    explosions.add(sprite);
                    explosionPositions.add( new Point((int)(getX()+Math.random()*(getWidth()-50)),(int)(getY()+Math.random()*(getHeight()-50))) );
                    timer = 0;
                }
                for(int i = 0;i<explosions.size();i++)
                {
                    explosions.get(i).update(time);
                    if(explosions.get(i).getCurrentAnimation().done())
                    {
                        explosions.remove(i);
                        explosionPositions.remove(i);
                    }
                }
            }
	}
	
	@Override
	public void draw(int x, int y, Graphics2D g)
	{
            g.drawImage(image, x+getDrawX(),y+getDrawY(),null);
            for(int i = 0;i<weakSpots.size();i++)
                weakSpots.get(i).draw(x,y,g);
            for(int i = 0;i<explosions.size();i++)
                explosions.get(i).draw(g,x+explosionPositions.get(i).x,y+explosionPositions.get(i).y,0);
	}
	
	
	private class WeakSpot
	{
            public boolean Active, Alive;
            public Rectangle Rect;
            GameObject obj;
            public boolean Horizontal;
            public int DX,DY;

            public WeakSpot(GameObject obj, int x, int y, boolean horizontal, int dx,int dy)
            {
                this.obj = obj;
                Alive = true; Active = false;
                Rect = new Rectangle(x,y,50,50);                
                this.Horizontal = horizontal;
                this.DX = dx;
                this.DY = dy;
            }

            public void draw(int x, int y, Graphics2D g)
            {
                if(Alive)
                {
                    if(Active)
                        g.setColor(Color.GREEN);
                    else
                        g.setColor(Color.RED);                                                                        

                }
                else
                {
                    g.setColor(Color.GRAY);
                }
                g.fillRect(x+obj.getDrawX()+Rect.x,y+obj.getDrawY()+Rect.y,Rect.width,Rect.height);
            }
	}
}