import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.*;
import org.mockito.*;
import static org.mockito.Mockito.*;

public class CoffeeMakerQuestTest {

	CoffeeMakerQuest cmq;
	Player player;
	Room room1;	// Small room
	Room room2;	// Funny room
	Room room3;	// Refinanced room
	Room room4;	// Dumb room
	Room room5;	// Bloodthirsty room
	Room room6;	// Rough room

	@Before
	public void setup() {
		// 0. Turn on bug injection for Player and Room.
		Config.setBuggyPlayer(true);
		Config.setBuggyRoom(true);
		
		//1. Create the Coffee Maker Quest object and assign to cmq.
		cmq = CoffeeMakerQuest.createInstance();

		//2. Create a mock Player and assign to player and call cmq.setPlayer(player). 
		player = Mockito.mock(Player.class);
		cmq.setPlayer(player);
		// Player should not have any items (no coffee, no cream, no sugar)
		when(player.getInventoryString()).thenReturn("YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n");



		//3. Create mock Rooms and assign to room1, room2, ..., room6.
		// Mimic the furnishings / adjectives / items of the rooms in the original Coffee Maker Quest.
		room1 = Mockito.mock(Room.class);
			when(room1.getFurnishing()).thenReturn("Quaint sofa");
			when(room1.getAdjective()).thenReturn("Small");
			when(room1.getItem()).thenReturn(Item.CREAM);
		room2 = Mockito.mock(Room.class);
			when(room2.getFurnishing()).thenReturn("Sad record player");
			when(room2.getAdjective()).thenReturn("Funny");
			when(room2.getItem()).thenReturn(Item.NONE);
		room3 = Mockito.mock(Room.class);
			when(room3.getFurnishing()).thenReturn("Tight pizza");
			when(room3.getAdjective()).thenReturn("Refinanced");
			when(room3.getItem()).thenReturn(Item.COFFEE);
		room4 = Mockito.mock(Room.class);
			when(room4.getFurnishing()).thenReturn("Flat energy drink");
			when(room4.getAdjective()).thenReturn("Dumb");
			when(room4.getItem()).thenReturn(Item.NONE);
		room5 = Mockito.mock(Room.class);
			when(room5.getFurnishing()).thenReturn("Beautiful bag of money");
			when(room5.getAdjective()).thenReturn("Bloodthirsty");
			when(room5.getItem()).thenReturn(Item.NONE);
		room6 = Mockito.mock(Room.class);
			when(room6.getFurnishing()).thenReturn("Perfect air hockey table");
			when(room6.getAdjective()).thenReturn("Rough");
			when(room6.getItem()).thenReturn(Item.SUGAR);
		
	// TODO: 4. Add the rooms created above to mimic the layout of the original Coffee Maker Quest.
		cmq.addFirstRoom(room1);
		cmq.addRoomAtNorth(room2, "Magenta", "Massive");
		cmq.addRoomAtNorth(room3, "Beige", "Smart");
		cmq.addRoomAtNorth(room4, "Dead", "Slim");
		cmq.addRoomAtNorth(room5, "Vivacious", "Sandy");
		cmq.addRoomAtNorth(room6, "Purple", "Minimalist");
	
		
	}

	@After
	public void tearDown() 
	{
	
	}
	
	/**
	 * Test case for String getInstructionsString().
	 * Preconditions: None.
	 * Execution steps: Call cmq.getInstructionsString().
	 * Postconditions: Return value is " INSTRUCTIONS (N,S,L,I,D,H) > ".
	 */
	@Test
	public void testGetInstructionsString() 
	{
		assertEquals(cmq.getInstructionsString(), " INSTRUCTIONS (N,S,L,I,D,H) > ");
	}
	
	/**
	 * Test case for boolean addFirstRoom(Room room).
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                Create a mock room and assign to myRoom.
	 * Execution steps: Call cmq.addFirstRoom(myRoom).
	 * Postconditions: Return value is false.
	 */
	@Test
	public void testAddFirstRoom() 
	{
		//mock room myRoom 
		Room myRoom = Mockito.mock(Room.class);
		assertEquals(cmq.addFirstRoom(myRoom),false);
	}

	
	/**
	 * Test case for boolean addRoomAtNorth(Room room, String northDoor, String southDoor).
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                Create a mock "Fake" room with "Fake bed" furnishing with no item, and assign to myRoom.
	 * Execution steps: Call cmq.addRoomAtNorth(myRoom, "North", "South").
	 * Postconditions: Return value is true.
	 *                 room6.setNorthDoor("North") is called.
	 *                 myRoom.setSouthDoor("South") is called.
	 */
	@Test
	public void testAddRoomAtNorthUnique() 
	{
		/*PRECOND*/
		//create fake room 
		Room myRoom = Mockito.mock(Room.class);
		//fake bed furnishing 
		when(myRoom.getFurnishing()).thenReturn("Fake bed");
		//with no item 
		when(myRoom.getItem()).thenReturn(Item.NONE);
		/*EXE*/
		assertEquals(cmq.addRoomAtNorth(myRoom, "North", "South"),true);
		/*POST*/
		Mockito.verify(room6, times(1)).setNorthDoor("North");
		Mockito.verify(myRoom, times(1)).setSouthDoor("South");


	}
	
	/**
	 * Test case for boolean addRoomAtNorth(Room room, String northDoor, String southDoor).
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                Create a mock "Fake" room with "Flat energy drink" furnishing with no item, and assign to myRoom.
	 * Execution steps: Call cmq.addRoomAtNorth(myRoom, "North", "South").
	 * Postconditions: Return value is false.
	 *                 room6.setNorthDoor("North") is not called.
	 *                 myRoom.setSouthDoor("South") is not called.
	 */
	@Test
	public void testAddRoomAtNorthDuplicate() 
	{
		/*PRECOND*/
		//create fake room 
		Room myRoom = Mockito.mock(Room.class);
		//fake bed furnishing 
		when(myRoom.getFurnishing()).thenReturn("Flat energy drink");
		//with no item 
		when(myRoom.getItem()).thenReturn(Item.NONE);
		/*EXE*/
		assertFalse(cmq.addRoomAtNorth(myRoom, "North", "South"));
		/*POST*/
		Mockito.verify(room6, times(0)).setNorthDoor("North");
		Mockito.verify(myRoom, times(0)).setSouthDoor("South");
	}
	
	/**
	 * Test case for Room getCurrentRoom().
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(Room) has not yet been called.
	 * Execution steps: Call cmq.getCurrentRoom().
	 * Postconditions: Return value is null.
	 */
	@Test
	public void testGetCurrentRoom() 
	{
		assertNull(cmq.getCurrentRoom());
	}
	
	/**
	 * Test case for void setCurrentRoom(Room room) and Room getCurrentRoom().
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(Room room) has not yet been called.
	 * Execution steps: Call cmq.setCurrentRoom(room3).
	 *                  Call cmq.getCurrentRoom().
	 * Postconditions: Return value of cmq.setCurrentRoom(room3) is true. 
	 *                 Return value of cmq.getCurrentRoom() is room3.
	 */
	@Test
	public void testSetCurrentRoom() 
	{
		assertTrue(cmq.setCurrentRoom(room3));
		assertEquals(cmq.getCurrentRoom(),room3);
	}
	
	/**
	 * Test case for String processCommand("I").
	 * Preconditions: Player does not have any items.
	 * Execution steps: Call cmq.processCommand("I").
	 * Postconditions: Return value is "YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n".
	 */
	@Test
	public void testProcessCommandI() 
	{
		assertEquals(cmq.processCommand("I"),"YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n");
	}
	
	/**
	 * Test case for String processCommand("l").
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(room1) has been called.
	 * Execution steps: Call cmq.processCommand("l").
	 * Postconditions: Return value is "There might be something here...\nYou found some creamy cream!\n".
	 *                 player.addItem(Item.CREAM) is called.
	 */
	@Test
	public void testProcessCommandLCream() 
	{
		/*PRE*/
		cmq.setCurrentRoom(room1);
		/*EXE && POST*/
		assertEquals(cmq.processCommand("l"),"There might be something here...\nYou found some creamy cream!\n");
		Mockito.verify(player,times(1)).addItem(Item.CREAM);
}
	
	/**
	 * Test case for String processCommand("n").
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(room4) has been called.
	 * Execution steps: Call cmq.processCommand("n").
	 *                  Call cmq.getCurrentRoom().
	 * Postconditions: Return value of cmq.processCommand("n") is "".
	 *                 Return value of cmq.getCurrentRoom() is room5.
	 */
	@Test
	public void testProcessCommandN() 
	{
		/*PRE*/
		cmq.setCurrentRoom(room4);
		/*EXE && POST*/
		assertEquals(cmq.processCommand("n"),"");
		assertEquals(cmq.getCurrentRoom(),room5);
	}
	
	/**
	 * Test case for String processCommand("s").
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(room1) has been called.
	 * Execution steps: Call cmq.processCommand("s").
	 *                  Call cmq.getCurrentRoom().
	 * Postconditions: Return value of cmq.processCommand("s") is "A door in that direction does not exist.\n".
	 *                 Return value of cmq.getCurrentRoom() is room1.
	 */
	@Test
	public void testProcessCommandS() 
	{
		/*PRE*/
		cmq.setCurrentRoom(room1);
		/*EXE && POST*/
		assertEquals(cmq.processCommand("s"),"A door in that direction does not exist.\n");
		assertEquals(cmq.getCurrentRoom(),room1);
	}
	
	/**
	 * Test case for String processCommand("D").
	 * Preconditions: Player has no items.
	 * Execution steps: Call cmq.processCommand("D").
	 *                  Call cmq.isGameOver().
	 * Postconditions: Return value of cmq.processCommand("D") is "YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n\nYou drink the air, as you have no coffee, sugar, or cream.\nThe air is invigorating, but not invigorating enough. You cannot study.\nYou lose!\n".
	 *                 Return value of cmq.isGameOver() is true.
	 */
	@Test
	public void testProcessCommandDLose() 
	{
		/*EXE && POST*/
		assertEquals(cmq.processCommand("D"),"YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n\nYou drink the air, as you have no coffee, sugar, or cream.\nThe air is invigorating, but not invigorating enough. You cannot study.\nYou lose!\n");
		assertTrue(cmq.isGameOver());
	}
	
	/**
	 * Test case for String processCommand("D").
	 * Preconditions: Player has all 3 items (coffee, cream, sugar).
	 * Execution steps: Call cmq.processCommand("D").
	 *                  Call cmq.isGameOver().
	 * Postconditions: Return value of cmq.processCommand("D") is "You have a cup of delicious coffee.\nYou have some fresh cream.\nYou have some tasty sugar.\n\nYou drink the beverage and are ready to study!\nYou win!\n".
	 *                 Return value of cmq.isGameOver() is true.
	 */
	@Test
	public void testProcessCommandDWin() 
	{
		/*PRE:: give player all items*/ 
		when(player.checkSugar()).thenReturn(true);
		when(player.checkCoffee()).thenReturn(true);
		when(player.checkCream()).thenReturn(true);
		when(player.getInventoryString()).thenReturn("You have a cup of delicious coffee.\nYou have some fresh cream.\nYou have some tasty sugar.\n");
		/*EXE && POST*/ 
		assertEquals(cmq.processCommand("D"),"You have a cup of delicious coffee.\nYou have some fresh cream.\nYou have some tasty sugar.\n\nYou drink the beverage and are ready to study!\nYou win!\n");
		assertTrue(cmq.isGameOver());
	}

	/**
	 * Test case for being in northmost room and attempting to go north 
	 * Preconditions: call cmq.setCurrentRoom(room6)
	 * Execution steps: Call cmq.processCommand("n")
	 * Postconditions: Return value of cmq.processCommand("n") is "A door in that direction doesn't exist.\n"
	 */
	@Test 
	public void testNorthMostBoundary()
	{
		cmq.setCurrentRoom(room6);
		assertEquals(cmq.processCommand("n"),"A door in that direction does not exist.\n");
	}
	/**
	 * Test case for being in room 2 and going south to room 1 
	 * Preconditions: call cmq.setCurrentRoom(room2)
	 * Execution steps: Call cmq.processCommand("s")
	 * 					Call cmq.getCurrentRoom()
	 * Postconditions: Return value of cmq.getcurrentRoom() is room1
	 */
	@Test 
	public void testGoSouthRoom2()
	{
		cmq.setCurrentRoom(room2);
		cmq.processCommand("s");
		assertEquals(cmq.getCurrentRoom(),room1);

	}
	/**
	 * Test case for attempting to add a room north with all null values 
	 * Preconditions: rooms 1-6 have been added 
	 * Execution steps: Call cmq.addroomAtNorth(null,null,null)

	 * Postconditions: Return value of cmq.addroomAtNorth(null,null,null) is false
	 */
	@Test 
	public void testaddRoomAtNorthInvalidInput()
	{
		assertFalse(cmq.addRoomAtNorth(null,null,null));
	}
	/**
	 * Test case for attempting to set current room to a room that DNE 
	 * Preconditions: rooms 1-6 have been added, create mock room and assign to myRoom 
	 * Execution steps: Call cmq.setCurrentRoom(myRoom)
	 * Postconditions: Return value of cmq.setCurrent(myRoom) is false
	 */
	@Test 
	public void testSetCurrDNE()
	{
		//mock room 
		Room myRoom = Mockito.mock(Room.class);
		assertFalse(cmq.setCurrentRoom(myRoom));
	}
	/**
	 * Test case for searching room 2 for an item and there being nothing 
	 * Preconditions: rooms 1-6 have been added, room2 has no items, call cmq.setCurrentRoom(room2)
	 * Execution steps: Call cmq.processCommand("l")
	 * Postconditions: Return value of cmq.processCommand("l") is "You don't see anything out of the ordinary.\n"
	 */
	@Test 
	public void testSearchRoom2Empty()
	{
		cmq.setCurrentRoom(room2);
		assertEquals(cmq.processCommand("l"),"You don't see anything out of the ordinary.\n");
	}
	/**
	 * Test case for invalid input in room1
	 * Preconditions: room1 ~ room6 have been added to cmq.
	 *                cmq.setCurrentRoom(room1) has been called.
	 * Execution steps: Call cmq.processCommand("ASDF")
	 * Postconditions: Return value of cmq.processCommand("l") is "What?"
	 */
	@Test 
	public void testInvalidInputRoom1()
	{
		cmq.setCurrentRoom(room1);
		assertEquals(cmq.processCommand("ASDF"),"What?");
	}
	/**
	 * Test case drink only sugar 
	 * Preconditions: player has sugar 
	 * Execution steps: Call cmq.processCommand("d")
	 * Postconditions: Return value of cmq.processCommand("d") is "YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYou have some tasty sugar.\n\nYou eat the sugar, but without caffeine, you cannot study.\nYou lose!\n"
	 */
	@Test 
	public void testDrinkSugar()
	{
		//give player sugar 
		when(player.checkSugar()).thenReturn(true);
		when(player.getInventoryString()).thenReturn("YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYou have some tasty sugar.\n");
		 assertEquals(cmq.processCommand("d"),"YOU HAVE NO COFFEE!\nYOU HAVE NO CREAM!\nYou have some tasty sugar.\n\nYou eat the sugar, but without caffeine, you cannot study.\nYou lose!\n");
	}
	/**
	 * Test case drink cream and sugar 
	 * Preconditions: player has sugar and cream 
	 * Execution steps: Call cmq.processCommand("d")
	 * Postconditions: Return value of cmq.processCommand("d") is "YOU HAVE NO COFFEE!\nYou have some fresh cream.\nYou have some tasty sugar.\n\nYou drink the sweetened cream, but without caffeine you cannot study.\nYou lose!\n"
	 */
	@Test 
	public void testDrinkSugarAndCream()
	{
		//give player sugar 
		when(player.checkSugar()).thenReturn(true);
		//give player cream
		when(player.checkCream()).thenReturn(true);
		when(player.getInventoryString()).thenReturn("YOU HAVE NO COFFEE!\nYou have some fresh cream.\nYou have some tasty sugar.\n");
		 assertEquals(cmq.processCommand("d"),"YOU HAVE NO COFFEE!\nYou have some fresh cream.\nYou have some tasty sugar.\n\nYou drink the sweetened cream, but without caffeine you cannot study.\nYou lose!\n");
	}
	/**
	 * Test case drink coffee and sugar 
	 * Preconditions: player has sugar and coffee 
	 * Execution steps: Call cmq.processCommand("d")
	 * Postconditions: Return value of cmq.processCommand("d") is "You have a cup of delicious coffee.\nYOU HAVE NO CREAM!\nYou have some tasty sugar.\n\nWithout cream, you get an ulcer and cannot study.\nYou lose!\n"
	 */
	@Test 
	public void testDrinkSugarAndCoffee()
	{
		//give player sugar 
		when(player.checkSugar()).thenReturn(true);
		//give player coffee
		when(player.checkCoffee()).thenReturn(true);
		when(player.getInventoryString()).thenReturn("You have a cup of delicious coffee.\nYOU HAVE NO CREAM!\nYou have some tasty sugar.\n");
		 assertEquals(cmq.processCommand("d"),"You have a cup of delicious coffee.\nYOU HAVE NO CREAM!\nYou have some tasty sugar.\n\nWithout cream, you get an ulcer and cannot study.\nYou lose!\n");
	}
	/**
	 * Test case drink only coffee
	 * Preconditions: player has coffee 
	 * Execution steps: Call cmq.processCommand("d")
	 * Postconditions: Return value of cmq.processCommand("d") is "You have a cup of delicious coffee.\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n\nWithout cream, you get an ulcer and cannot study.\nYou lose!\n"
	 */
	@Test 
	public void testDrinkCoffee()
	{
		
		//give player coffee
		when(player.checkCoffee()).thenReturn(true);
		when(player.getInventoryString()).thenReturn("You have a cup of delicious coffee.\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n");
		 assertEquals(cmq.processCommand("d"),"You have a cup of delicious coffee.\nYOU HAVE NO CREAM!\nYOU HAVE NO SUGAR!\n\nWithout cream, you get an ulcer and cannot study.\nYou lose!\n");
	}
	/**
	 * Test case drink  coffee and cream 
	 * Preconditions: player has coffee 
	 * Execution steps: Call cmq.processCommand("d")
	 * Postconditions: Return value of cmq.processCommand("d") is "You have a cup of delicious coffee.\nYou have some fresh cream.\nYOU HAVE NO SUGAR!\n\nWithout sugar, the coffee is too bitter. You cannot study.\nYou lose!\n"
	 */
	@Test 
	public void testDrinkCoffeeAndCream()
	{
		
		//give player coffee
		when(player.checkCoffee()).thenReturn(true);
		//give player cream
		when(player.checkCream()).thenReturn(true);
		when(player.getInventoryString()).thenReturn("You have a cup of delicious coffee.\nYou have some fresh cream.\nYOU HAVE NO SUGAR!\n");
		assertEquals(cmq.processCommand("d"),"You have a cup of delicious coffee.\nYou have some fresh cream.\nYOU HAVE NO SUGAR!\n\nWithout sugar, the coffee is too bitter. You cannot study.\nYou lose!\n");
	}
		/**
	 * Test case drink  only cream 
	 * Preconditions: player has cream 
	 * Execution steps: Call cmq.processCommand("d")
	 * Postconditions: Return value of cmq.processCommand("d") is "YOU HAVE NO COFFEE!\nYou have some fresh cream.\nYOU HAVE NO SUGAR!\n\nYou drink the cream, but without caffeine, you cannot study.\nYou lose!\n"
	 */
	@Test 
	public void testDrinkCream()
	{
		//give player cream
		when(player.checkCream()).thenReturn(true);
		when(player.getInventoryString()).thenReturn("YOU HAVE NO COFFEE!\nYou have some fresh cream.\nYOU HAVE NO SUGAR!\n");
		assertEquals(cmq.processCommand("d"),"YOU HAVE NO COFFEE!\nYou have some fresh cream.\nYOU HAVE NO SUGAR!\n\nYou drink the cream, but without caffeine, you cannot study.\nYou lose!\n");
	}



}
