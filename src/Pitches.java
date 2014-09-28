public class Pitches {
	private static String[] fingers = new String[8];
	
	public void setPitch(int finger, String pitch)
	{
		fingers[finger - 1] = pitch;
	}
	
	public String getPitch(int finger)
	{
		return fingers[finger - 1];
	}
}
