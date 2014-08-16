package xbill;

/**
 * 
 * @author bdavis
 *
 * Listen for alive events from characters. These occur 
 * when the character changes it's alive state, for example when 
 * going from dying to dead
 */
public interface AliveEventListener {
	void characterAliveStateChanged(Character c, Character.AliveState oldState, 
			Character.AliveState newState);
}
