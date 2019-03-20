package rgbvsu.engine.util;


import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;
/**
 *
 * @author Dauid
 */
public class Sprite extends AnimationSet{
    AffineTransform transform;
    int width,height,widthD2,heighD2;
    long forcedAnimationTimer;
    
    public Sprite(AnimationSet animations, int width, int height)
    {
        super(animations);
        transform = new AffineTransform();
        transform.setToIdentity();
        forcedAnimationTimer = 0;
        this.width = width;
        this.height = height;
        this.widthD2 = width/2;
        this.heighD2 = height/2;
    }
    
    public Sprite(int width, int height)
    {
        this(new AnimationSet(),width,height);
    }
    
    public void setAnimation(String name, long time)
    {
        if(forcedAnimationTimer <= 0)
        {       
            super.setAnimation(name);
            forcedAnimationTimer = time;
        }
    }
    
    public void update(long time)
    {
        super.update(time);
        if(forcedAnimationTimer>0)
            forcedAnimationTimer -= time;
    }
    
    public void draw(Graphics2D g, int x, int y, double rotation)
    {
        transform.setToTranslation(x,y);                        
        transform.rotate(rotation,widthD2,heighD2);
        g.drawImage(getImage(),transform,null);
    }
    
}
