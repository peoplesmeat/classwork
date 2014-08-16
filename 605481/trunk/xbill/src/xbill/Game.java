package xbill;

import java.util.*; 
/**
 * 
 * @author bdavis
 *
 * Represents in game elements. Including current score and 
 * all alive characters. 
 */
public class Game {
	private List<Character> characters;
	private ArrayList<Character> backingArray = new ArrayList<Character>(); 
	protected int score = 0; 
	
	public void initialize()
	{
		characters = Collections.synchronizedList(backingArray); 
	}
	public void addCharacter(Character c) 
	{
		c.addAliveEventListener(new AliveEventListener() { 
			public void characterAliveStateChanged(Character c, Character.AliveState oldState, 
					Character.AliveState newState)
			{
				if (newState == Character.AliveState.Dead)
				{
					System.out.println("Character Died");
					int points = (1000 - c.getAge()*8) * c.getMultiplier();
					if (points < 100)
						points = 100;
					score += points; 
						
				}
				
			}
		}); 
		characters.add(c);
		c.start(); 
	}
	
	public int getScore() 
	{
		return score; 
	}
	
	public Iterator<Character> characterIterator() 
	{
		return characters.iterator();
	}
	
	public void clearDead() 
	{
		//Clear Dead Characters
		HashSet<Character> h = new HashSet<Character>(); 
		for (Character c : characters) 
		{
			if (c.getAliveState() == Character.AliveState.Dead)
				h.add(c); 
			
		} 
		
		for (Character c : h)
			characters.remove(c); 
		
	}
	
	public void addMoreCharacters() 
	{
		if (characters.size() < 15) 
		{
			if (Math.random() < 0.1) 
			{
				if (Math.random() < 0.2) 
					addCharacter(new CharacterTwo(
							(int)Math.random()*3+1,
							(int)Math.random()*3+1)); 
				else
					addCharacter(new CharacterOne(1,2)); 
			}
		}
		
	}
	
	public void removeCharacter(Character c) 
	{
		characters.remove(c); 
	}
	public void hitTest(int x, int y) 
	{
		synchronized(characters) 
		{
			for (Character c : characters) 
			{
				if (c.hitTest(x, y))
				{
					c.hit(); 				  
				}
			}			
		}
	}
	
	
	
}
