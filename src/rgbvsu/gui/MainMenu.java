package rgbvsu.gui;

import java.awt.*;
import java.util.ArrayList;

public class MainMenu implements Menu
{
	//final int NEW_GAME=0,SETTINGS=1,EXIT=2,SAVE=3,LOAD=4;
	GUI gui;
	int index;
	Font font;
	int menuX,menuY,itemOffset;
        ArrayList<MenuItem> items;
        boolean changed=false;
	public MainMenu(GUI gui)
	{
		this.gui = gui;
		index = 0;
		menuX=50;menuY=300;itemOffset=50;
		font = new Font("Courier New", Font.BOLD, 50);
                items = new ArrayList<MenuItem>();
                
                items.add( new MenuItem(this, "New Game")
                {
                    public void action()
                    {
                        GUI gui = ((MainMenu)getParent()).gui;
                        gui.setScreen(gui.GAME_SCREEN);
                        gui.getWorld().newGame();
                    }                
                });
                
                items.add( new MenuItem(this, "Save")
                {
                    public void action()
                    {
                        GUI gui = ((MainMenu)getParent()).gui;   
                        if(gui.getWorld().isGameStarted())
                        {
                            String filename = javax.swing.JOptionPane.showInputDialog("Filename");   
                            if(filename!=null)
                                gui.getSaveLoadSystem().save(gui.getWorld(), filename);
                        }
                    }
                });
                
                items.add( new MenuItem(this, "Load")
                {
                    public void action()
                    {
                        GUI gui = ((MainMenu)getParent()).gui;
                        gui.getMenuManager().setMenu("Load Menu");
                        ((LoadMenu)gui.getMenuManager().getCurrentMenu()).fillSavegames();
                    }
                });
                
                items.add(new MenuItem(this, "Exit")
                {
                    public void action()
                    {
                        GUI gui = ((MainMenu)getParent()).gui;
                        gui.exit();
                    }                
                });
                
	}
        
        public void changeToResume()
        { 
            if(!changed)
            {
                items.remove(0);
                items.add(0, new MenuItem(this, "Resume Game")
                {
                    public void action()
                    {
                        GUI gui = ((MainMenu)getParent()).gui;
                        gui.setScreen(gui.GAME_SCREEN);
                    }
                });
                changed = true;
            }
        }
        @Override public void show()
        {
            //GUI.getSoundEngine().playMusic("m_tekno-labs");
        }        
        @Override public void up(){   index--;if(index<0)index=items.size()-1; }
        @Override public void down(){ index++;if(index>=items.size())index=0;}
        @Override public void left(){}
        @Override public void right(){}
        @Override public String getName(){ return "Main Menu"; }
        @Override public void enter()
	{
            items.get(index).action();
	}
	
        @Override public void render(Graphics2D g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(0,0,GUI.getWidth(), GUI.getScreenHeight());
                
                g.setColor(Color.GRAY);
                g.fillRect(menuX-30,menuY+itemOffset*index+10,gui.getWidth(),-itemOffset);
                
		g.setColor(Color.WHITE);	
		g.setFont(font);                
                for(int i = 0;i<items.size();i++)
                    g.drawString(items.get(i).getName(),menuX,menuY+i*itemOffset);                
		
	}
}
