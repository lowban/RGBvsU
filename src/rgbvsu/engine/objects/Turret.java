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
 * @author Dauid
 */
public class Turret extends Enemy{

    Sprite sprite;
    
    public Turret(World world)
    {
        super(world,50,50);
        AnimationSet set = new AnimationSet();
        set.addAnimation(ResourceManager.getAnimation("turret.idle"));
        set.addAnimation(ResourceManager.getAnimation("turret.fire"));
        set.addAnimation("death", ResourceManager.getAnimation("misc.explosion"));
        sprite = new Sprite(set, 50,50);
        setDeathTime(440);
    }
    
    public void shoot(int originx, int originy, int shootx, int shooty, float speed){
        ProjectilePhotonBolt bolt = new ProjectilePhotonBolt(getWorld(), (float) originx, (float) originy, shootx, shooty, speed,(long) 1000);
        getWorld().addProjectile(bolt);
        GUI.getSoundEngine().playSound("turretFire");
        getSprite().setAnimation("fire");
    } 
    
    @Override
    public Sprite getSprite()
    {
        return sprite;
    }
    
    @Override
    public void update(long time) {
        super.update(time);
        sprite.update(time);        
    }

    @Override
    public void draw(int x, int y, Graphics2D g) {
        sprite.draw(g, x+(int)getX(),y+(int)getY(), getRotation());
    }

    @Override
    public void checkCollision(GameObject otherobj) {
        if(!onDestination() || !otherobj.alive() || !alive())
                return;
        if(otherobj instanceof PushProjectile)
        {            
            PushProjectile p = (PushProjectile)otherobj;
            p.kill();
            if( CollisionMath.Intersects(this, otherobj))				
            {
                float checkX = (getX()+getWidth()*(0.5f+p.getPushX()));
                float checkY = (getY()+getHeight()*(0.5f+p.getPushY()));

                if(getWorld().isSpaceAvailable(checkX, checkY))
                {
                    float destinationX = (int)(getX()+getWidth()*p.getPushX());
                    float destinationY = (int)(getY()+getHeight()*p.getPushY());
                    push(destinationX,destinationY);
                }
            }
        }
        else if(otherobj instanceof CrushProjectile)
        {
            if(!sprite.getCurrentAnimation().getName().equals("death"))
            {
                sprite.setAnimation("death");
                GUI.getSoundEngine().playSound("explosion2");
                kill();
                otherobj.kill();
            }
        }
    }
    
}
