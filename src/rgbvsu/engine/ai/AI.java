package rgbvsu.engine.ai;

import rgbvsu.engine.objects.GameObject;

public abstract class AI
{	
	GameObject gameObject;
	
	public AI(GameObject gameObject)
	{
            this.gameObject = gameObject;
	}
	
	public GameObject getGameObject(){ return gameObject; }
	
	public abstract void collisionResponse(GameObject obj);
	public abstract void update(long time);
}