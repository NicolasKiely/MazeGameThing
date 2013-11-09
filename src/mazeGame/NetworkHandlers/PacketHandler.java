package mazeGame.NetworkHandlers;


/**
 * Represents handler for a packet
 * Catchers target a string, and if the string matches
 * a string in the header, the catcher's hook method
 * is called
 * @author Nic
 *
 */
public interface PacketHandler {
	
	/** Returns the string to match to the header for desired packets */
	public String getMatcherString();
	
	/** Calls on packet matched */
	public void onPacketMatch(NetTable packet);
}
