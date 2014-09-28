import org.jfugue.Player;

public class Play {
	//private static HashMap<Integer, Character> pitchMap = new HashMap<Integer, Character>();
	private Player player;
	
	public Play(){
		player = new Player();
	}

	public void play(String pattern) {
		player.play(pattern);
	}
}
