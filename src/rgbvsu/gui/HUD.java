package rgbvsu.gui;

import java.awt.*;
import java.util.ArrayList;
import rgbvsu.ResourceManager;
import rgbvsu.engine.World;
import rgbvsu.engine.objects.KeyCard;
import rgbvsu.engine.objects.RobotForm;

public class HUD
{
	GUI gui;
	Font font;
	int width,height;
        
        int hpBarX,hpBarY;
        int hpBarWidth,hpBarHeight;
        
        int keyCardsX,keyCardsY;
        int keyCardWidth, keyCardHeight;
        
        int formsX,formsY,formsWidth,formsHeight,formsOffset;
        
	int mapX, mapY,mapWidth,mapHeight, fullMapWidth,fullMapHeight;
	int mapRoomWidth, mapRoomHeight;
	int numRoomsX,numRoomsY;        
	Color backgroundColor;
        Image background;
	
	public HUD(GUI gui, int width, int height)
	{
		this.gui = gui;		
		this.width = width;
		this.height = height;		
	
	}
	
	public void init()
	{
		font = new Font("Terminal", Font.BOLD, 15);		
		backgroundColor = new Color(0.05f,0.05f,0.05f);
                
                hpBarX = 40;
                hpBarY = 40;
                hpBarWidth = 100;
                hpBarHeight = 20;
                
                keyCardsX = 160;
                keyCardsY = 40;
                keyCardWidth = 25;
                keyCardHeight = 25;
                
                formsX = 200;
                formsY = 20;
                formsWidth=50;
                formsHeight=50;
                formsOffset = 5;
                
		mapX = GUI.getWidth()-70;
		mapY = 25;
		mapWidth = mapHeight = 45;
		numRoomsX = gui.getWorld().getNumRoomsX();
		numRoomsY = gui.getWorld().getNumRoomsY();
                mapRoomWidth = 5;
                mapRoomHeight = 5;
                
                
                background = ResourceManager.getImage("gui.hud");
	}
	
	public int getHeight(){ return height; }
	
        public void drawMap(int x, int y, Graphics2D g)
        {
            World world = gui.getWorld();
            g.setColor(Color.GRAY.darker().darker().darker());
            g.fillRect(mapX-5,mapY-5,mapWidth+10,mapHeight+10);
		
            
            for(int mapy = 0; mapy < numRoomsY; mapy++)
		for(int mapx = 0; mapx < numRoomsX; mapx++)
		{
                    if(world.getRoom(mapx,mapy).isDiscovered())
                    {
                        g.setColor(mapx==world.getCurrentRoom().getX() && mapy==world.getCurrentRoom().getY() ? Color.WHITE :  world.getRoom(mapx,mapy).getColor() );				
                        g.fillRect( mapX+mapx*mapRoomWidth, mapY+mapy*mapRoomHeight, mapRoomWidth, mapRoomHeight);
                                    //g.setColor(mapx==world.getCurrentRoom().getX() && mapy==world.getCurrentRoom().getY() ? Color.WHITE : Color.BLACK);
				//g.drawRect( mapX+mapx*mapRoomWidth, mapY+mapy*mapRoomHeight, mapRoomWidth-1, mapRoomHeight-1);
                    }
                }
        }
        
	public void draw(int x, int y, Graphics2D g)
	{
		World world = gui.getWorld();
		
                //Background
                g.drawImage(background,x,y,null);
		/*g.setPaint(backgroundColor.darker());
		g.fillRect(x,y,width,height);
		g.setPaint(backgroundColor);
		g.fillRect(x+15,y+15,width-30,height-30);
		g.setPaint(Color.GRAY);
		Color roomColor = world.getCurrentRoom().getColor();
		Color playerColor = world.getPlayer().getColor();*/
                
                //HP-Bar
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString("Health:",hpBarX,hpBarY-10);
                g.setColor(Color.GRAY.darker().darker().darker());
                g.fillRect(x+hpBarX-5,y+hpBarY-5,hpBarWidth+10,hpBarHeight+10);
		g.setColor(Color.RED);
		g.fillRect( x+hpBarX,y+hpBarY, hpBarWidth, hpBarHeight);
		g.setColor(Color.GREEN);
		g.fillRect( x+hpBarX,y+hpBarY, (int)(hpBarWidth*world.getPlayer().getHPPercent()), hpBarHeight);
		
                //Keycards
                ArrayList<KeyCard> keycards = world.getPlayer().getKeyCards();
                for(int i = 0;i<keycards.size();i++)
                {
                    g.setColor(keycards.get(i).getColor());
                    g.fillRect(x+keyCardsX,y+keyCardsY+i*keyCardHeight,keyCardWidth,keyCardHeight);
                }
                
                //Forms
                ArrayList<RobotForm> forms = world.getPlayer().getForms();
                for(int i = 0;i<forms.size();i++)
                {
                    g.setColor(i == world.getPlayer().currentFormIndex() ? Color.WHITE : Color.BLACK);
                    g.fillRect( x+formsX+i*(formsWidth+formsOffset),y+formsY,formsWidth,formsHeight);
                    g.drawImage(forms.get(i).getIcon(), x+formsX+i*(formsWidth+formsOffset),y+formsY,null);
                    
                }
                //Minimap
		g.setColor(Color.GRAY.darker().darker().darker());
                g.fillRect(mapX-5,mapY-5,mapWidth+10,mapHeight+10);
		
		for(int mapy = 0; mapy < numRoomsY; mapy++)
			for(int mapx = 0; mapx < numRoomsX; mapx++)
			{
                            if(world.getRoom(mapx,mapy).isDiscovered())
                            {
                            	g.setColor(mapx==world.getCurrentRoom().getX() && mapy==world.getCurrentRoom().getY() ? Color.WHITE :  world.getRoom(mapx,mapy).getColor() );				
				g.fillRect( mapX+mapx*mapRoomWidth, mapY+mapy*mapRoomHeight, mapRoomWidth, mapRoomHeight);
                            }
                                //g.setColor(mapx==world.getCurrentRoom().getX() && mapy==world.getCurrentRoom().getY() ? Color.WHITE : Color.BLACK);
				//g.drawRect( mapX+mapx*mapRoomWidth, mapY+mapy*mapRoomHeight, mapRoomWidth-1, mapRoomHeight-1);			
			}
	}
}