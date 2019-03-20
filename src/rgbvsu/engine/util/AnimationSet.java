package rgbvsu.engine.util;


import java.util.HashMap;
import java.awt.Image;
/**
 *
 * @author Dauid
 */
public class AnimationSet {
    HashMap<String,Animation> animations;
    Animation currentAnimation;
    
    public AnimationSet()
    {
        animations = new HashMap<>();
        currentAnimation = null;
    }
    
    public AnimationSet(AnimationSet animationSet)
    {
        this.animations = animationSet.animations;
        currentAnimation = animationSet.getCurrentAnimation();
    }
    
    public void addAnimation(Animation animation)
    {
        addAnimation(animation.getName(), animation);
    }
    
    public void addAnimation(String name, Animation animation)
    {
        if(!animation.getName().equals(name))
            animation.setName(name);
        if(currentAnimation == null)
            currentAnimation = animation;
        animations.put(animation.getName(), animation);
    }
    
    public Image getImage()
    {
        return getCurrentAnimation().getImage();
    }
    
    public Animation getCurrentAnimation()
    {
        return currentAnimation;
    }
    
    public void setAnimation(String name)
    {
        if(getCurrentAnimation() == null)
            currentAnimation = animations.get(name);
        else
        {            
            currentAnimation = animations.get(name);          
            currentAnimation.reset();
        }
    }
    
    public void update(long time)
    {
        getCurrentAnimation().update(time);
    }
}
