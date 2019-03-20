package rgbvsu.engine.util;

import java.awt.Rectangle;
import rgbvsu.engine.objects.GameObject;

public class CollisionMath
{
	public static boolean Inside(GameObject object, float x, float y)
	{
		return 	x >= object.getX() && x <= object.getX()+object.getWidth() &&
				y >= object.getY() && y <= object.getY()+object.getHeight();
	}
        public static boolean Inside(Rectangle rect, float x, float y)
        {
            	return 	x >= rect.getX() && x <= rect.getX()+rect.getWidth() &&
				y >= rect.getY() && y <= rect.getY()+rect.getHeight();
        }
	public static boolean Intersects(GameObject object1, GameObject object2)
	{	
		return object1.getX()+object1.getWidth() > object2.getX() &&
			object1.getX() < object2.getX()+object2.getWidth() &&
			object1.getY()+object1.getHeight() > object2.getY() &&
			object1.getY() < object2.getY()+object2.getHeight();
	}
        
        public static boolean Intersects(GameObject object1, Rectangle rectangle)
	{	
		return object1.getX()+object1.getWidth() > rectangle.getX() &&
			object1.getX() < rectangle.getX()+rectangle.getWidth() &&
			object1.getY()+object1.getHeight() > rectangle.getY() &&
			object1.getY() < rectangle.getY()+rectangle.getHeight();
	}
        
	public static void MinimumTranslation(GameObject object1, GameObject object2)
	{
            if( Intersects(object1, object2))
                {
                    float minX,minY;
                    float left = object1.getX()+object1.getWidth()-object2.getX();
                    float right = object1.getX()-object2.getX()-object2.getWidth();
                    float top = object1.getY()+object1.getHeight()-object2.getY();
                    float bottom = object1.getY()-object2.getY()-object2.getHeight();
                    if(Math.abs(left) > Math.abs(right))minX=right;
                    else minX=left;
                    if(Math.abs(top) > Math.abs(bottom))minY = bottom;
                    else minY = top;
                    if( Math.abs(minX) < Math.abs(minY) ) object1.setToTranslation(GenericMath.roundToDecimal(object1.getX()-minX,3),object1.getY());
                    else object1.setToTranslation(object1.getX(),GenericMath.roundToDecimal(object1.getY()-minY, 3));				
                }
	}
}