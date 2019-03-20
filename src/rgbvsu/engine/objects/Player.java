package rgbvsu.engine.objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import rgbvsu.engine.World;
import rgbvsu.engine.ai.Enemy;
import rgbvsu.engine.util.CollisionMath;
import rgbvsu.engine.util.Sprite;
import rgbvsu.gui.GUI;
import rgbvsu.input.InputManager;

public class Player extends GameObject
{
	float speed = 0.2f, pushX,pushY;
	ArrayList<RobotForm> forms;
        ArrayList<KeyCard> keycards;
	RobotForm currentForm;
	int hp,maxhp, currentFormIndex;
        boolean invulnarble, controlsEnabled, pushed, disableBlink;
        long pushTimer, pushTimerLimit;
        long disableBlinkTimer;
        
	public Player(World world)
	{
            super(world,50,50);
            forms = new ArrayList<>();
            keycards = new ArrayList<>();
            forms.add( new RobotFormBasic(this));
            currentFormIndex = 0;
            facing = Facing.UP;
            invulnarble = false;
            pushed = false;
            hp = maxhp = 15;
            disabledTimer = visibleTimer = 0;
            changeForm(0);
            color = new Color(0.4f,0.4f,0.4f);
	}	                
        
	public int getHP(){ return hp; }
        public int getMaxHP(){return maxhp;}
	public float getHPPercent(){ return (float)hp/maxhp; }
        public ArrayList<RobotForm> getForms(){ return forms;}
        public int currentFormIndex(){return currentFormIndex;}
        public void setHP(int hp){this.hp=hp;if(hp>0)alive=true;}
        
        public void damage(int damage, float pushX, float pushY, long pushTimer)
        {
            hp -= damage;
            if(pushTimer > 0)
            {
                this.pushTimer = 0;
                pushTimerLimit = pushTimer;
                setDisabledTimer(pushTimer);
                setBlinkTimer(pushTimer);
            }
            if(pushX != 0 || pushY != 0)
            {
                pushed = true;
                this.pushX = getX()+pushX;
                this.pushY = getY()+pushY;
            }                
        }
        
        public void addKeyCard(KeyCard card){ keycards.add(card); }
        public ArrayList<KeyCard> getKeyCards(){ return keycards; }
        
        public void removeKeyCard(Color color)
        {
            for(int i=0;i<keycards.size();i++)
                if(keycards.get(i).getColor().equals(color))
                    keycards.remove(i);
        }
        
        public boolean hasKeyCard(Color color)
        {
            for(int i=0;i<keycards.size();i++)
                if(keycards.get(i).getColor().equals(color))
                    return true;
            return false;
        }  
        
        
	public void changeForm(int index)
	{
            if(index < 0 || index >= forms.size())
                return;
            else
                currentForm = forms.get(index);
            currentFormIndex = index;
	}
	
	public void addForm(RobotForm form)
	{
            if(forms.get(0) instanceof RobotFormBasic)
            {
                forms.remove(0);
                forms.add(form);
                currentForm = form;
            }
            if(!forms.contains(form))
                forms.add(form);
	}
	
        @Override
	public void checkCollision(GameObject otherobj)
	{
            if(!otherobj.isPassable())
            {
                CollisionMath.MinimumTranslation(this, otherobj);
                pushed = false;
            }
            if(isEnabled())
            {		
		if(otherobj instanceof PressurePlatform)
                    otherobj.checkCollision(this);
		else if(otherobj instanceof Enemy)
                    otherobj.checkCollision(this);
                else if(otherobj instanceof KeyCard)
                    otherobj.checkCollision(this);
                else if(otherobj instanceof RobotFormHolder)
                    otherobj.checkCollision(this);
                else if(otherobj instanceof Projectile)
                    otherobj.checkCollision(this);                
            }
	}
	
        public void doActions()
	{
            if( InputManager.keyDownOnce( KeyEvent.VK_SPACE ) )
            {
		currentForm.action();
            }
	}

        @Override
        public Sprite getSprite()
        {
            return currentForm.getSprite();
        }
        
        @Override
	public void update(long time)
	{            
            if(hp <= 0 && alive())
            {
                kill();
                world.gameOver();
                currentForm.getSprite().setAnimation("death");
                GUI.getSoundEngine().playSound("explosion2");
                pushed = false;
            }
            else if(alive())
            {
                super.update(time);
                
                //Update the form
                currentForm.update(time);

                //Save old positioncalues (For animation)
                float oldX = getX(), oldY = getY();

                if(pushTimer < pushTimerLimit ) pushTimer += time;
                else pushed = false;                 

                //Push player
                if(pushed)
                {
                    float dx = (pushX-getX())*((float)pushTimer/pushTimerLimit);
                    float dy = (pushY-getY())*((float)pushTimer/pushTimerLimit);
                    translate(dx,dy);
                }


                //Check input
                if(isEnabled())
                {
                    if( InputManager.keyDown( KeyEvent.VK_LEFT ) )
                    {
                        translate(-speed*time,0);facing = Facing.LEFT;
                    }
                    if( InputManager.keyDown( KeyEvent.VK_RIGHT ) )
                    {
                        translate(speed*time,0);facing = Facing.RIGHT;
                    }
                    if( InputManager.keyDown( KeyEvent.VK_DOWN ) )
                    {
                        translate(0,speed*time);facing = Facing.DOWN;
                    }
                    if( InputManager.keyDown( KeyEvent.VK_UP ) )
                    {
                        translate(0,-speed*time);facing = Facing.UP;
                    }
                    if( InputManager.keyDownOnce( KeyEvent.VK_W) )
                    {
                        //world.getCurrentRoom().enableDoor(rgbvsu.engine.Room.BOTTOM, false);
                        GUI.fadeOutAndIn(2500);
                    }
                }

                if(InputManager.keyDownOnce( KeyEvent.VK_Q) )
                    changeForm( currentFormIndex > 0 ? --currentFormIndex : forms.size()-1);
                else if(InputManager.keyDownOnce( KeyEvent.VK_E) )
                    changeForm( currentFormIndex < forms.size()-1 ? ++currentFormIndex : 0);

                if( InputManager.keyDownOnce( KeyEvent.VK_1) )
                        changeForm( 0 );
                if( InputManager.keyDownOnce( KeyEvent.VK_2) )
                        changeForm( 1 );
                if( InputManager.keyDownOnce( KeyEvent.VK_3) )
                        changeForm( 2 );
                if( InputManager.keyDownOnce( KeyEvent.VK_4) )
                        changeForm( 3 );

                //Set animation
                if(oldX == getX() && oldY == getY())
                    currentForm.getSprite().setAnimation("idle");
                else if(currentForm.getSprite().getCurrentAnimation().getName().equals("idle"))
                    currentForm.getSprite().setAnimation("move");
            }
	}		
	
        @Override
	public void draw(int x, int y,Graphics2D g)
	{
            if(isVisible())
                currentForm.draw(x,y,g);           
	}
}