package rgbvsu.gui;

import java.util.ArrayList;

/**
 *
 * @author Dauid
 */
public class MenuManager {
    ArrayList<Menu> menus;
    Menu currentMenu;
    
    public MenuManager()
    {
        menus = new ArrayList<Menu>();
    }
    
    public void addMenu(Menu menu)
    {
        menus.add(menu); 
        if(menus.size() == 1)
            currentMenu = menu;
    }
    
    
    
    public void setMenu(Menu menu)
    {
        currentMenu = menus.get(menus.indexOf(menu));
    }
    
    public void setMenu(String menuName)
    {
        for(int i = 0;i<menus.size();i++)
            if(menus.get(i).getName() == menuName)
            {
                currentMenu = menus.get(i);
                menus.get(i).show();
                return;
            }
    }    
    
    public Menu getCurrentMenu(){ return currentMenu; }
}
