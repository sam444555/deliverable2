import java.util.*;

public class CoffeeMakerQuestImpl implements CoffeeMakerQuest {

	
	/*ArrayList of the rooms within the house.  
	0 --> denotes first room 
	1 --> denotes second room 
	2 --> denotes third room
	....
	5 --> denotes sixth room 
	*/
	ArrayList<Room> rooms =new ArrayList<Room>(6);
	//player
	Player p; 
	//current room 
	Room curr; 
	//did the player drink coffee
	private boolean drank; 


	//constructor 
	CoffeeMakerQuestImpl() 
	{
		//init current room to null 
		curr = null;
		//init player p to null 
		p = null; 
		//init drank to false 
		drank = false;
	}

	/**
	 * Whether the game is over. The game ends when the player drinks the coffee.
	 * 
	 * @return true if successful, false otherwise
	 */
	public boolean isGameOver() 
	{
		return get_drank();
	}

	//sets drank to true 
	private void drank_coffee()
	{
		drank = true;
	}
	//gets drank status
	private boolean get_drank()
	{
		return drank;
	}


	/**
	 * Set the player to p.
	 * 
	 * @param p the player
	 */
	public void setPlayer(Player p) 
	{
		this.p=p;
	}
	
	
	
	/**
	 * Add the first room in the game. If room is null or if this not the first room
	 * (there are pre-exiting rooms), the room is not added and false is returned.
	 * 
	 * @param room the room to add
	 * @return true if successful, false otherwise
	 */
	public boolean addFirstRoom(Room room) 
	{
		/*room parameter is null || there already exists
		rooms*/  
		if(room == null || rooms.size()>0)
			return false;
		/*add initial room to rooms arraylist & return true*/ 
		rooms.add(room);
		return true;
	}

	/**
	 * Attach room to the northern-most room. If either room, northDoor, or
	 * southDoor are null, the room is not added. If there are no pre-exiting rooms,
	 * the room is not added. If room is not a unique room (a pre-exiting room has
	 * the same adjective or furnishing), the room is not added. If all these tests
	 * pass, the room is added. Also, the north door of the northern-most room is
	 * labeled northDoor and the south door of the added room is labeled southDoor.
	 * Of course, the north door of the new room is still null because there is
	 * no room to the north of the new room.
	 * 
	 * @param room      the room to add
	 * @param northDoor string to label the north door of the current northern-most room
	 * @param southDoor string to label the south door of the newly added room
	 * @return true if successful, false otherwise
	 */
	public boolean addRoomAtNorth(Room room, String northDoor, String southDoor) 
	{

		//check if parameters == null 
		if( room == null || northDoor == null || southDoor == null)
		{
			return false;
		}
		//check if there are no preexisting rooms
		if ( rooms.size() == 0)
		{
			return false;
		}
		//check if the adjective/furnishing is unique 
		Room curr; //curr room
		//iterate through the list of rooms 
		for(int i = 0; i < rooms.size(); i++)
		{
			//get curr rom
			curr = rooms.get(i);
			//check if furnishing || adj is the same return false 
			if( curr.getAdjective() == room.getAdjective()
					|| curr.getFurnishing() == room.getFurnishing())
			{
				return false; 
			}
		}
		
		//add doors and room 
		rooms.get(rooms.size()-1).setNorthDoor(northDoor);
		room.setSouthDoor(southDoor);
		rooms.add(room);
		//return success 
		return true;
	}

	/**
	 * Returns the room the player is currently in. If location of player has not
	 * yet been initialized with setCurrentRoom, returns null.
	 * 
	 * @return room player is in, or null if not yet initialized
	 */ 
	public Room getCurrentRoom() 
	{
		return curr;
	}
	
	/**
	 * Set the current location of the player. If room does not exist in the game,
	 * then the location of the player does not change and false is returned.
	 * 
	 * @param room the room to set as the player location
	 * @return true if successful, false otherwise
	 */
	public boolean setCurrentRoom(Room room) 
	{
		

		/*find room in the list and set it if it exists.  Otherwise, return false*/
		for(int i = 0; i< rooms.size(); i++)
		{
			//check if curr room = input room 
			if(rooms.get(i) == room)
			{
				//set current room to input room 
				curr = room; 
				//return success 
				return true; 
			}
		}
		//room DNE -- return failure 
		return false;
	}
	
	/**
	 * Get the instructions string command prompt. It returns the following prompt:
	 * " INSTRUCTIONS (N,S,L,I,D,H) > ".
	 * 
	 * @return comamnd prompt string
	 */
	public String getInstructionsString() 
	{
		return " INSTRUCTIONS (N,S,L,I,D,H) > ";
	}
	
	/**
	 * Processes the user command given in String cmd and returns the response
	 * string. For the list of commands, please see the Coffee Maker Quest
	 * requirements documentation (note that commands can be both upper-case and
	 * lower-case). For the response strings, observe the response strings printed
	 * by coffeemaker.jar. The "N" and "S" commands potentially change the location
	 * of the player. The "L" command potentially adds an item to the player
	 * inventory. The "D" command drinks the coffee and ends the game. Make
     * sure you use Player.getInventoryString() whenever you need to display
     * the inventory.
	 * 
	 * @param cmd the user command
	 * @return response string for the command
	 */
	public String processCommand(String cmd) 
	{
		//make all input lowercase 
		cmd = cmd.toLowerCase();
		//index of current room [avoid redundancy]
		int loc = rooms.indexOf(curr);
		switch(cmd)
		{

			//go north
			case "n" :
			{
				//edge case -- in last room & trying to go further
				if(loc == rooms.size()-1)
				{
					return "A door in that direction does not exist.\n";
				}
				//go to northern room 
				if(setCurrentRoom(rooms.get(loc+1)))
				{
					return "";
				}
			}
			//go south 
			case "s" :
			{
				//edge case -- in initial room & trying to go south
				if( loc == 0)
					return "A door in that direction does not exist.\n";
				//go to southern room 
				if(setCurrentRoom(rooms.get(rooms.indexOf(curr)-1)))
				{
					return "";
				}
			}
			//check contents of room
			case "l" :
			{
				/*Get item from room [if any]*/ 
				Item i = curr.getItem();
				//no items in room 
				if(i == Item.NONE)
					return "You don't see anything out of the ordinary.\n";
				/*add the item*/
				p.addItem(i);
				//cream in room 
				if(i == Item.CREAM)
				{
					return "There might be something here...\nYou found some creamy cream!\n";
				}
				//coffee in room 
				if(i == Item.COFFEE)
				{
					return "There might be something here...\nYou found some caffeinated coffee!\n";
				}
				//sugar in room 
				if(i == Item.SUGAR)
				{
					return "There might be something here...\nYou found some sweet sugar!\n";
				}
			}
			//check inventory 
			case "i" :
			{
				return p.getInventoryString();
			}
			//get help 
			case "h" :
			{
				return 
				"N - Go north\nS - Go south\nL - Look and collect any items in the room\n"+
				"I - Show inventory of items collected\nD - Drink coffee made from items in inventory\n";
			}

			//drink
			case "d" :
			{	//return string 
				String ret = p.getInventoryString()+'\n';
				//player drinks coffee 
				drank_coffee();
				//only sugar 
				if(!p.checkCream() && !p.checkCoffee() && p.checkSugar())
				{
					return ret+"You eat the sugar, but without caffeine, you cannot study.\nYou lose!\n";
				}
				//only sugar and cream 
				if(p.checkCream() && p.checkSugar() && !p.checkCoffee())
				{
					return ret+"You drink the sweetened cream, but without caffeine you cannot study.\nYou lose!\n";
				}
				//only sugar and coffee 
				if(!p.checkCream() && p.checkSugar() && p.checkCoffee() )
				{
					return ret+"Without cream, you get an ulcer and cannot study.\nYou lose!\n";
				}
				//only coffee 
				if(!p.checkCream() && !p.checkSugar() && p.checkCoffee())
				{
					return ret+"Without cream, you get an ulcer and cannot study.\nYou lose!\n";
				}
				//only coffee and cream 
				if(p.checkCream() && !p.checkSugar() && p.checkCoffee())
				{
					return ret+"Without sugar, the coffee is too bitter. You cannot study.\nYou lose!\n";
				}
				//only cream 
				if(p.checkCream() && !p.checkSugar() && !p.checkCoffee())
				{
					return ret+"You drink the cream, but without caffeine, you cannot study.\nYou lose!\n";
				}
				//nothing
				if(!p.checkCream() && !p.checkSugar() && !p.checkCoffee())
				{
					return ret+"You drink the air, as you have no coffee, sugar, or cream.\n"+
					"The air is invigorating, but not invigorating enough. You cannot study.\nYou lose!\n";
				}
				//success -- have everything 
				return ret+"You drink the beverage and are ready to study!\nYou win!\n";
			}
			//invalid input 
			default:
			{
				return "What?";
			}
		}
	}
	
}
