package rgbvsu.engine.ai;

import java.awt.Point;
import java.util.ArrayList;
import rgbvsu.engine.objects.GameObject;
import rgbvsu.engine.objects.GameObject.Facing;
import rgbvsu.engine.objects.PatrolDroid;

public class PatrolDroidAI extends AI {

    private final int SEARCH=0,CHASE=1;
    private int state;
    private float destinationX;
    private float destinationY;
    private float searchSpeed,chaseSpeed,currentSpeed;

    public PatrolDroidAI(PatrolDroid droid) {
        super(droid);
        droid.setAI(this);
        searchSpeed = 1.0f;
        chaseSpeed = 2.0f;
        destinationX = getGameObject().getX();
        destinationY = getGameObject().getY();
        state = SEARCH;
    }

    @Override
    public void collisionResponse(GameObject otherobj) 
    {
        
    }

    @Override
    public void update(long time) {
        
        GameObject owner = getGameObject();
           
        if(owner.getX() == destinationX && owner.getY() == destinationY)
        {
            AITick();
        }

        Point facingOffset = owner.getFacingOffset();        
        owner.translate(facingOffset.x*currentSpeed,facingOffset.y*currentSpeed);        
    }

    private void AITick() {
        GameObject owner = getGameObject();
        GameObject player = owner.getWorld().getPlayer();

        //Evaluate state
        if (owner.inAxisView(player))
            state = CHASE;
        else         
            state = SEARCH;
        
        switch(state)
        {
            case SEARCH:
        
                ArrayList<Facing> directions = checkFreeDirections();

                int randomIndex = (int)(Math.random()*directions.size());
                Point facingOffset = GameObject.getFacingOffset(directions.get(randomIndex));
                owner.setFacing(directions.get(randomIndex));
                destinationX = owner.getX()+facingOffset.x*owner.getWidth();
                destinationY = owner.getY()+facingOffset.y*owner.getHeight(); 
                currentSpeed = searchSpeed;
                break;
            case CHASE:
                float maxX = Math.abs(owner.getX()-player.getX());
                float maxY = Math.abs(owner.getY()-player.getY());
                int dx=0,dy=0;
                if(maxX > maxY)
                {
                    dx = (int)Math.signum(player.getX()-owner.getX());
                }
                else
                {
                    dy = (int)Math.signum(player.getY()-owner.getY());
                }
                destinationX = owner.getX()+dx*owner.getWidth();
                destinationY = owner.getY()+dy*owner.getHeight();
                owner.setFacing(dx,dy);
                currentSpeed = chaseSpeed;
                break;
        }
    }

    
    private ArrayList<Facing> checkFreeDirections(){
        GameObject owner = getGameObject();        
        ArrayList<Facing> freedirs = new ArrayList<>();
        if(owner.getWorld().isSpaceAvailable(owner.getCenterX()-owner.getWidth(),owner.getCenterY()))
            freedirs.add(Facing.LEFT);
        if(owner.getWorld().isSpaceAvailable(owner.getCenterX()+owner.getWidth(),owner.getCenterY()))
            freedirs.add(Facing.RIGHT);
        if(owner.getWorld().isSpaceAvailable(owner.getCenterX(),owner.getCenterY()-owner.getHeight()))
            freedirs.add(Facing.UP);
        if(owner.getWorld().isSpaceAvailable(owner.getCenterX(),owner.getCenterY()+owner.getHeight()))
            freedirs.add(Facing.DOWN);
        return freedirs;
    }
}