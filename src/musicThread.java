public class musicThread extends Thread {
	//	private static Pitches p = new Pitches();
	private int finger = 0;
	private volatile String pitch = null;
	private Play play = null;
	private boolean wake = false;

	musicThread(Play play, int finger) {
		this.finger = finger;
		this.play = play;
	}

	//	public void setPitch(int finger, String pitch){
	//		p.setPitch(finger, pitch);
	//	}

	public void setPitch(String pitch)
	{
		this.pitch = pitch;
		this.wake = true;
	}

	public void run() {
		//TODO
		while(true){
			if (wake == true) 
				play.play(pitch);
		}
	}
}
