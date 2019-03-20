package rgbvsu.io;

import java.util.ArrayList;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import rgbvsu.gui.GUI;
import rgbvsu.engine.World;

/**
 *
 * @author Dauid
 */
public class SaveLoadSystem {
                
    javax.swing.JFrame parent;
    
    public SaveLoadSystem(javax.swing.JFrame parent ){ this.parent = parent; }
    
    public void save(World world, String filename)
    {
        File file = new File("./savegames/"+filename);
        file.getParentFile().mkdir();
        if(file.exists())
            if(javax.swing.JOptionPane.showConfirmDialog(parent, "File exists, overwrite?") != JOptionPane.YES_OPTION)
                return;

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("savegames/"+filename+".sav"));
            
            int roomX = world.getCurrentRoom().getX();
            int roomY = world.getCurrentRoom().getY();
            int dxRoom = world.getLastRoom().getX()-world.getCurrentRoom().getX()+1;
            int dyRoom = world.getLastRoom().getY()-world.getCurrentRoom().getY()+1;
            
            writer.write(roomX);
            writer.write(roomY);
            writer.write(dxRoom);
            writer.write(dyRoom);            
            
            for(int y = 0; y < world.getNumRoomsY();y++)
                for(int x = 0; x < world.getNumRoomsX();x++)
                {
                    writer.write(world.getRoom(x,y).isFinished() ? 1 : 0);
                    writer.write(world.getRoom(x,y).isDiscovered() ? 1 : 0);
                }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void load(World world, String path)
    {
        GUI.getMapLoader().loadMap("World.map");
                
        
        try {
            BufferedReader reader = new BufferedReader( new FileReader("savegames/"+path) );
            
            int roomX = reader.read();
            int roomY = reader.read();
            int dxRoom = reader.read()-1; 
            int dyRoom = reader.read()-1;
            
            float playerposX = (world.getWidth()-world.getPlayer().getWidth()-5)*(1+dxRoom)*0.5f;
            float playerposY = (world.getHeight()-world.getPlayer().getHeight()-5)*(1+dyRoom)*0.5f;
            
            world.setRoom(roomX,roomY);
            world.getPlayer().setToTranslation(playerposX,playerposY);
            world.getPlayer().setFacing(dxRoom*-1,dyRoom*-1);
            
            for(int y = 0; y < world.getNumRoomsY();y++)
                for(int x = 0; x < world.getNumRoomsX();x++)
                {
                    if(reader.read()==1)
                        world.getRoom(x,y).setFinished(true);
                    if(reader.read()==1)
                        world.getRoom(x,y).setDiscovered(true);
                }
        } 
        catch( IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public String[] getSaveGames()
    {
        File file = new File("savegames");
        return file.list( new SaveFilter() ) ;
    }
    
    private class SaveFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return name.endsWith("sav");
        }
    }
}
