
package rgbvsu.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author Dauid
 */
public class LoadMenu implements Menu {
    ArrayList<MenuItem> items;    
    GUI gui;
    int index;
    Font itemFont, mainFont;
    int menuX,menuY,itemOffset;
    
    public LoadMenu(GUI gui)
    {
        this.gui = gui;
        menuX=50;menuY=200;itemOffset=20;
	itemFont = new Font("Courier New", Font.BOLD, 30);
        mainFont = new Font("Courier New", Font.BOLD, 50);
        items = new ArrayList<MenuItem>();
        items.add( new MenuItem(this, "Back"){
           public void action()
           {
               GUI gui = ((LoadMenu)getParent()).gui;
               gui.getMenuManager().setMenu("Main Menu");
           }
        });
    }
    
    
    public void fillSavegames()
    {   
        while(items.size() > 1)
            items.remove(0);
        String[] savegames = gui.getSaveLoadSystem().getSaveGames();
        for(int i = 0;i<savegames.length;i++)
        {
            items.add(0, new MenuItem(this, savegames[i]){
                  public void action()
                  {
                    GUI gui = ((LoadMenu)getParent()).gui;
                    gui.getSaveLoadSystem().load(gui.getWorld(), this.getName());
                    gui.setScreen(gui.GAME_SCREEN);
                  }
               });
        }
    }
    @Override public void show(){ }
    @Override public void up(){   index--;if(index<0)index=items.size()-1; }
    @Override public void down(){ index++;if(index>=items.size())index=0;}
    @Override public void left(){}
    @Override public void right(){}
    @Override public String getName(){ return "Load Menu"; }
    @Override public void enter()
    {
        items.get(index).action();
    }
	
    @Override public void render(Graphics2D g)
    {
	g.setColor(Color.BLACK);
	g.fillRect(0,0,GUI.getWidth(), GUI.getScreenHeight());
        
        g.setColor(Color.GRAY);
        g.fillRect(menuX-30,menuY+itemOffset*index+1,gui.getWidth(),-itemOffset);
        
	g.setColor(Color.WHITE);	
	g.setFont(itemFont);               
        for(int i = 0;i<items.size();i++)
           g.drawString(items.get(i).getName(),menuX,menuY+(i)*itemOffset);                
    }
}
