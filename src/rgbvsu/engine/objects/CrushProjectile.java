/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rgbvsu.engine.objects;

import java.awt.Graphics2D;
import rgbvsu.ResourceManager;
import rgbvsu.engine.World;
import rgbvsu.engine.ai.Enemy;
import rgbvsu.engine.util.AnimationSet;
import rgbvsu.engine.util.Sprite;

/**
 *
 * @author Dauid
 */
public class CrushProjectile extends Projectile{

    Sprite sprite;

    public CrushProjectile(World world, Facing facing)
    {
        super(world,30,30,250);
        sprite = new Sprite(new AnimationSet(), (int)getWidth(),(int)getHeight());
        sprite.addAnimation(ResourceManager.getAnimation("projectiles.crush"));
        setFacing(facing);        
    }
    @Override
    public Sprite getSprite() {
        return sprite;
    }
    @Override
    public void update(long time)
    {
        super.update(time);
        sprite.update(time);
    }
    @Override
    public void checkCollision(GameObject otherObject) {
        if(otherObject instanceof Enemy)
            otherObject.checkCollision(this);
    }

    @Override
    public void draw(int x, int y, Graphics2D g) {
        sprite.draw(g,x+getDrawX(),y+getDrawY(),getRotation());
    }   
    
}
