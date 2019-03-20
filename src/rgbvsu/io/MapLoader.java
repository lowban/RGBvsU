package rgbvsu.io;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import rgbvsu.engine.Room;
import rgbvsu.engine.World;
import rgbvsu.engine.ai.*;
import rgbvsu.engine.objects.*;

public class MapLoader
{
	ArrayList<MapObject> mapObjectDatabase = new ArrayList<>();
	char[] mapData = null;
	Room[][] rooms = null;
	World world;
	
	public MapLoader(World world)
	{
            this.world = world;
	}
	
	private class MapObject
	{
            public char charIndex;
            public char objectCode;
            public char[] params;
            public int roomX,roomY;

            public MapObject(int roomX, int roomY, char charIndex, char objectCode, char[] params)
            { 
                this.roomX = roomX;
                this.roomY = roomY;
                this.charIndex = charIndex;this.objectCode = objectCode; this.params = params; 
            }
	}
	
	MapObject getMapObject(int roomX, int roomY, char charIndex) throws Exception
	{		
            for(int i = 0; i< mapObjectDatabase.size();i++)
            {
                MapObject mapObject = mapObjectDatabase.get(i);
                if( mapObject.roomX == roomX && mapObject.roomY == roomY && mapObject.charIndex == charIndex)
                        return mapObjectDatabase.get(i);
            }
            throw new Exception("Object not found with index: "+charIndex +" in room: "+roomX+" "+roomY);
	}
	
	char getCharIndexAt(char[] data, int roomWidth, int roomHeight, int roomX, int roomY, int numRoomsX, int x, int y)
	{
            return data[ roomX*(roomWidth+1)+x+((roomWidth+1)*numRoomsX+2)*(roomY*(roomHeight+1)+y) ];
	}
	
	void createObject(char charIndex, int x, int y, Room whichRoom)
	{
            try{

                int width, height, destX, destY,R,G,B,door,type;
                MapObject mapObject = getMapObject(whichRoom.getX(), whichRoom.getY(), charIndex);
                switch(mapObject.objectCode)
                {
                    case 'P':			
                        world.createPlayer(whichRoom.getX(), whichRoom.getY(), x,y);
                    break;
                    case 'B':
                        boolean movable = (int)mapObject.params[3] == 1;
                        boolean breakable = (int)mapObject.params[3] == 2;
                        Block block = new Block( world, mapObject.params == null ? null : new Color( (int)mapObject.params[0], (int)mapObject.params[1],(int)mapObject.params[2]), movable, breakable );
                        block.translate(world.getCellWidth()*x,world.getCellHeight()*y);
                        whichRoom.addGameObject( block,-1);
                    break;
                    case 'K':
                        R = (int)mapObject.params[0];
                        G = (int)mapObject.params[1];
                        B = (int)mapObject.params[2];
                        door = (int)mapObject.params[3];
                        DoorPlatform doorPlatform = new DoorPlatform( world, world.getCellWidth(),world.getCellHeight(), new Color(R,G,B), door);
                        doorPlatform.translate(world.getCellWidth()*x,world.getCellHeight()*y);
                        whichRoom.addGameObject( doorPlatform, 0 ); 
                        break;
                    case 'c':
                        Color color = new Color((int)mapObject.params[0], (int)mapObject.params[1],(int)mapObject.params[2]);
                        KeyCard card = new KeyCard(world,color);
                        card.translate(world.getCellWidth()*x,world.getCellHeight()*y);
                        whichRoom.addGameObject(card, 0);
                        break;
                    case 'T':
                        Turret turret = new Turret(world);
                        turret.translate(world.getCellWidth()*x, world.getCellHeight()*y);
                        TurretAI turretai = new TurretAI(turret);
                        whichRoom.addAI(turretai);
                        break;
                    case 'E':
                        ElectrifiedFloor electrifiedFloor = new ElectrifiedFloor(world, 1, 1);
                        electrifiedFloor.translate(world.getCellWidth()*x,world.getCellHeight()*y);
                        whichRoom.addGameObject(electrifiedFloor, 0);
                        break;
                    case 't':
                        destX = mapObject.params[0]*world.getCellWidth();
                        destY = mapObject.params[1]*world.getCellHeight();
                        TeleportPlatform teleportPlatform = new TeleportPlatform(world, 1, 1, destX, destY);
                        teleportPlatform.translate(world.getCellWidth()*x,world.getCellHeight()*y);
                        whichRoom.addGameObject(teleportPlatform, 0);
                        break;
                    case 'H':
                        type = (int)mapObject.params[0];
                        RobotFormHolder holder = new RobotFormHolder(world, type);
                        holder.translate(world.getCellWidth()*x,world.getCellHeight()*y);
                        whichRoom.addGameObject(holder, 0);
                        break;
                    case 's':
                        StupidDroid stupidDroid = new StupidDroid(world, world.getCellWidth(),world.getCellHeight());
                        stupidDroid.translate(world.getCellWidth()*x,world.getCellHeight()*y);
                        StupidDroidAI stupidDroidAI = new StupidDroidAI(stupidDroid);                        
                          whichRoom.addAI(stupidDroidAI);
                    case 'p':
                        PatrolDroid patrolDroid = new PatrolDroid(world);
                        patrolDroid.translate(world.getCellWidth()*x,world.getCellHeight()*y);
                        PatrolDroidAI patrolDroidAI = new PatrolDroidAI(patrolDroid);
                        whichRoom.addAI(patrolDroidAI);
                        break;
                    case 'A':
                        int facingX = (int)mapObject.params[0]-1;
                        int facingY = (int)mapObject.params[1]-1;
                        BigTurret bigTurret = new BigTurret(world);
                        bigTurret.translate(world.getCellWidth()*x,world.getCellHeight()*y);
                        BigTurretAI bigTurretAI = new BigTurretAI(bigTurret,GameObject.getFacing(facingX,facingY));
                        whichRoom.addAI(bigTurretAI);
                        break;
                    case 'F':
                        int spawnInterval = (int)mapObject.params[0];
                        DroidFactory droidFactory = new DroidFactory(world, spawnInterval);
                        droidFactory.translate(world.getCellWidth()*x,world.getCellHeight()*y);
                        whichRoom.addGameObject(droidFactory, -1);
                        break;
                    case 'X':
                        BlueBoss blueBoss = new BlueBoss(world);
                        blueBoss.translate(world.getCellWidth()*x,world.getCellHeight()*y);
                        whichRoom.addGameObject(blueBoss,-1);
                        break;
                }
            }
            catch(Exception e){ e.printStackTrace(); }
	}	
	
	String getArgument(String line, int argumentIndex, char separator)
	{
		String temp = line;
		for(int i = 0; i < argumentIndex; i++)
		{
			temp = temp.replaceFirst( temp.substring(0, temp.indexOf(separator)+1), "");
		}
		return temp.substring(0, temp.indexOf(separator));
	}
	
	public void createObjectsForRoom(int roomX, int roomY)
	{                 
            for(int y = 0; y < world.getNumCellsY(); y++)
                for(int x = 0; x < world.getNumCellsX(); x++)
                {
                    char charIndex = getCharIndexAt( mapData,  world.getNumCellsX(), world.getNumCellsY(),roomX, roomY,world.getNumRoomsX(), x,y);

                    if(charIndex != '.' && charIndex != ' ' && (int)charIndex != 10 && (int)charIndex!=13)
                        createObject( charIndex, x,y,world.getRoom(roomX, roomY) );
                }
	}
	
	public void loadMapData(String filename)
	{
		int numRoomsX=0,numRoomsY=0 ,lineNumber=0;
		Room tempRoom = null;
			
		char separator = ';';
			
		try
		{		
                    //Load mapinfo
                    BufferedReader reader = new BufferedReader( new FileReader(filename) );			
                    String line = reader.readLine();

                    int R,G,B;

                    while(line != null)
                    {	
                            String command = "";

                            //Get first argument
                            command = getArgument(line, 0, separator);

                            switch(command)
                            {
                                //Set the worlds' size
                                case "worldSize":
                                        numRoomsX = Integer.parseInt( getArgument(line, 1, separator) );
                                        numRoomsY = Integer.parseInt( getArgument(line, 2, separator) );
                                        rooms = new Room[numRoomsX][numRoomsY];
                                        for(int y = 0;y<numRoomsY;y++)
                                                for(int x=0;x<numRoomsX;x++)
                                                        rooms[x][y]  = new Room(world, x, y);
                                        world.setMap(rooms);
                                        break;
                                //Set which room to manipulate
                                case "room":
                                        int roomX = Integer.parseInt( getArgument(line, 1, separator) );
                                        int roomY = Integer.parseInt( getArgument(line, 2, separator) );
                                        tempRoom = world.getRoom(roomX,roomY);
                                        break;
                                //Set the rooms color
                                case "color":
                                        R = Integer.parseInt( getArgument(line, 1, separator) );
                                        G = Integer.parseInt( getArgument(line, 2, separator) );
                                        B = Integer.parseInt( getArgument(line, 3, separator) );
                                        tempRoom.setColor( new Color(R,G,B) );
                                        break;
                                //Set which door is the exit
                                case "goalDoor":
                                        tempRoom.setGoalDoors(Byte.parseByte(getArgument(line,1,separator)));
                                        break;
                                //Lock a door with a certain color
                                case "colorLock":
                                        int door = Integer.parseInt(getArgument(line,1,separator));
                                        R = Integer.parseInt( getArgument(line, 2, separator) );
                                        G = Integer.parseInt( getArgument(line, 3, separator) );
                                        B = Integer.parseInt( getArgument(line, 4, separator) );
                                        tempRoom.setColorLock(door, new Color(R,G,B));
                                        break;
                                case "lockOnEnter":
                                        tempRoom.setLockOnEnter(true);
                                        break;
                                case "onEnterDialogue":
                                        int dialogueID = Integer.parseInt( getArgument(line, 1, separator) );
                                        tempRoom.setOnEnterDialogue(dialogueID);
                                        break;
                                case "music":
                                        String musicName = getArgument(line, 1, separator);
                                        tempRoom.setMusic(musicName);
                                        break;
                                //Junk lines
                                case "map":
                                case "\n":
                                case "//":
                                        break;
                                //As default, connect the objectCode to the room and charIndex and save the paramaters
                                //for usage in createObjects-method
                                default:
                                        int numArguments = line.length() - line.replace(""+separator, "").length() - 2;
                                        char[] params = new char[numArguments];
                                        char charIndex = getArgument(line, 0, separator).charAt(0);
                                        char objectCode = getArgument(line,1,separator).charAt(0);
                                        for(int i = 0; i < numArguments; i++)
                                        {
                                                params[i] = (char)Integer.parseInt( getArgument(line,i+2,separator) );
                                        }
                                        mapObjectDatabase.add( new MapObject( tempRoom.getX(), tempRoom.getY(), charIndex, objectCode, params) );
                                        break;
                            }

                            //The end of mapData-definitions, process loading the mapstructure and objects
                            if(command.equals("map"))
                            {				
                                int c = reader.read();
                                String temp = ""+(char)c;
                                while(c != -1)
                                {	
                                    c = reader.read();
                                    temp += (char)c;
                                }
                                mapData = temp.toCharArray();                                
                                break;
                            }

                            line = reader.readLine();lineNumber++;

                            while(line.length() == 0)
                            {
                                line = reader.readLine();lineNumber++;  
                            }

                            while(line.substring(0,2).equals( "//" ))
                            {
                                line = reader.readLine();lineNumber++;                              
                            }
                    }					
		}
		catch(Exception e){ e.printStackTrace(); System.err.println("Error @ "+lineNumber); }
	}
	
	public void initRoomDoors(int roomX, int roomY)
	{            
            char doorHorizontal = getCharIndexAt( mapData, world.getNumCellsX(), world.getNumCellsY(),roomX, roomY,world.getNumRoomsX(), 11, 5);
            if( doorHorizontal == 'O' )
                rooms[roomX][roomY].enableDoor( Room.RIGHT, true );
            else if( doorHorizontal == 'X' )
                rooms[roomX][roomY].enableDoor( Room.RIGHT, false );

            char doorVertical = getCharIndexAt( mapData, world.getNumCellsX(), world.getNumCellsY(),roomX, roomY,world.getNumRoomsX(), 5, 11);
            if( doorVertical == 'O' )
                rooms[roomX][roomY].enableDoor( Room.BOTTOM, true );
            else if( doorVertical == 'X' )
                rooms[roomX][roomY].enableDoor( Room.BOTTOM, false );
	}
	
	public void loadMap(String filename)
	{	
            //Load map with mapdata
            loadMapData(filename);
            
            //Create content
            for(int roomY = 0; roomY < world.getNumRoomsY(); roomY++)
                for(int roomX = 0; roomX < world.getNumRoomsX(); roomX++)
                {									
                        initRoomDoors(roomX,roomY);				
                        createObjectsForRoom(roomX,roomY);
                }
	}
}