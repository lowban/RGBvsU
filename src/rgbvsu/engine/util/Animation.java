package rgbvsu.engine.util;

import java.util.ArrayList;
import java.awt.Image;

/**
 *
 * @author Dauid
 */
public class Animation {
    ArrayList<Frame> frames;
    int currentIndex;
    long time;
    boolean loop;        
    String name;
    
    
    public Animation(ArrayList<Frame> frames, String name, boolean loop)
    {
        this.frames = frames;
        currentIndex = 0;
        time = 0;
        this.loop = loop;
        this.name = name;
    }
    
    public Animation(String name, boolean loop)
    {
        this(new ArrayList<Frame>(), name, loop);
    }        
    
    public Animation(String name)
    {
        this(new ArrayList<Frame>(),name, false);
    }
    
    public Animation(Animation copy)
    {
        this(copy.frames, copy.name, copy.loop);
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public void addFrame(Image img, long duration)
    {
        if(currentIndex == -1)
            currentIndex = 0;
        frames.add( new Frame(img,duration));
    }
    
    public void reset()
    {
        currentIndex = 0;
        time = 0;
    }
    
    public boolean done(){ return !loop && frames.size() > 1 && currentIndex == frames.size()-1; }
    
    public Image getImage()
    {
        return getCurrentFrame().Image;
    }
    
    public int getCurrentIndex()
    {
        return currentIndex;
    }
    
    private Frame getCurrentFrame()
    {
        return frames.get(currentIndex);
    }
    
    public void nextFrame()
    {
        if(currentIndex < frames.size()-1)
            currentIndex++;
        else if(loop)
            currentIndex=0;
    }
    
    public void update(long time)
    {
        if(getCurrentFrame().Duration > 0)
        {
            this.time += time;
            if(this.time >= getCurrentFrame().Duration)
            {
                this.time = 0;
                nextFrame();
            }
        }
    }
    
    private class Frame
    {
        public long Duration;
        public Image Image;
        
        public Frame(Image img, long dur)
        {
            Duration = dur;
            Image = img;
        }
    }
}
