package rgbvsu.engine.ai;

import rgbvsu.engine.World;
import rgbvsu.engine.objects.GameObject;
import rgbvsu.engine.ai.AI;

public abstract class Enemy extends GameObject
{
	AI ai;
	public Enemy(World world, float w, float h)
	{
		super(world,w,h);
	}
	
	public AI getAI(){ return ai; }
	public void setAI(AI ai){this.ai = ai;}
}