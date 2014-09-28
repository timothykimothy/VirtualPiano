import java.util.HashMap;
import org.jfugue.Player;

public class Play {
	private static HashMap<Integer, Character> pitch = new HashMap<Integer, Character>();

	public Play() {
		pitch.put(1, 'C');
		pitch.put(2, 'D');
		pitch.put(3, 'E');
		pitch.put(4, 'F');
		pitch.put(5, 'G');
		pitch.put(6, 'A');
		pitch.put(7, 'B');
		pitch.put(8, 'C');
	}

	public static void play(String note) {
		Player player = new Player();
		int h = Character.getNumericValue(note.charAt(0)) + 3;
		char v = pitch.get(Character.getNumericValue(note.charAt(1)));
		System.out.println("Note :" + v + h);
		player.play(v + "" + h);
	}
}