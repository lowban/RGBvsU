package rgbvsu.engine.objects;

import java.awt.Graphics2D;
import rgbvsu.ResourceManager;
import rgbvsu.engine.World;
import rgbvsu.engine.ai.Enemy;
import rgbvsu.engine.util.AnimationSet;
import rgbvsu.engine.util.Sprite;

/**
 *
 * @author Robin Horneman
 */
public class BigTurret extends Enemy {
    
    private Sprite sprite;
    
    public BigTurret(World world){
        super(world,50,50);
        AnimationSet set = new AnimationSet();
        set.addAnimation(ResourceManager.getAnimation("bigturret.idle"));
        set.addAnimation(ResourceManager.getAnimation("bigturret.fire"));        
        sprite = new Sprite(set, 50,50);
    }
    
    @Override
    public void update(long time) {
        sprite.update(time);
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void shoot(int originx, int originy, int shootx, int shooty){
        ProjectileDeathBolt bolt = new ProjectileDeathBolt(getWorld(), (float) originx, (float) originy, (int) shootx, (int) shooty,1.0f, (long) 10000);
        getWorld().addProjectile(bolt);
        //GUI.getSoundEngine().playSound("turretFire");
        getSprite().setAnimation("fire");
    } 
    
    @Override
    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public void draw(int x, int y, Graphics2D g) {
        sprite.draw(g, x+(int)getX(),y+(int)getY(), getRotation());
    }

    @Override
    public void checkCollision(GameObject otherobj) {
        if(otherobj instanceof CrushProjectile)
        {
            setDisabledTimer(2000);
            setBlinkTimer(2000);
            otherobj.kill();
        }
    }
    
}
