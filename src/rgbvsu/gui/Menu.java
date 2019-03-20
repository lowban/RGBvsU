package rgbvsu.gui;

public interface Menu
{
        public abstract void show();
	public abstract void up();
	public abstract void down();
	public abstract void left();
	public abstract void right();
	public abstract void enter();
	public abstract String getName();
	public abstract void render(java.awt.Graphics2D g);
}