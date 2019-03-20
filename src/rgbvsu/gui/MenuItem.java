package rgbvsu.gui;

/**
 *
 * @author Dauid
 */
public abstract class MenuItem {
    String name;
    Menu parent;
    public MenuItem(Menu parent, String name){ this.parent = parent; this.name = name;}
    public String getName(){ return name; }
    public Menu getParent(){ return parent; }
    public void setName(String name){ this.name = name; }
    public abstract void action();
}
