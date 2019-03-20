package rgbvsu.engine.objects;

import java.awt.*;
import rgbvsu.ResourceManager;
import rgbvsu.gui.GUI;
import rgbvsu.engine.World;
import rgbvsu.engine.ai.Enemy;
import rgbvsu.engine.objects.GameObject;
import rgbvsu.engine.objects.Projectile;
import rgbvsu.engine.util.AnimationSet;
import rgbvsu.engine.util.Sprite;

public class PushProjectile extends Projectile
{
    Sprite sprite;
    int pushx,pushy;
    public PushProjectile(World world, int pushx, int pushy)
    {
        super(world,30,30,250);
        this.pushy = pushy; this.pushx = pushx;
        sprite = new Sprite(new AnimationSet(), (int)getWidth(),(int)getHeight());
        sprite.addAnimation(ResourceManager.getAnimation("projectiles.push"));
        setFacing(pushx,pushy);        
    }

    public int getPushX(){ return pushx; }
    public int getPushY(){ return pushy; }

    @Override
    public Sprite getSprite()
    {
        return sprite;
    }

    @Override
    public void update(long time)
    {
        super.update(time);
        sprite.update(time);
    }
    
    @Override
    public void checkCollision(GameObject otherObj)
    {
        if(otherObj instanceof Block)
            otherObj.checkCollision(otherObj);
        else if(otherObj instanceof Enemy)
            otherObj.checkCollision(otherObj);
    }

    @Override
    public void draw(int x, int y,Graphics2D g)
    {
        sprite.draw(g,x+getDrawX(),y+getDrawY(),getRotation());
    }
}