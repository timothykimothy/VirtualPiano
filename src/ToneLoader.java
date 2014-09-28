import java.util.concurrent.PriorityBlockingQueue;

public class ToneLoader {

	private PriorityBlockingQueue<String> toneQueue;
	private Play play = new Play();

	public ToneLoader(PriorityBlockingQueue<String> toneQueue) {
		this.toneQueue = toneQueue;
		System.out.println("Creat queue:" + toneQueue);
	}

	public void run() {

//		while (true) {
		

			while (toneQueue.peek() != null) {
				final String current = (String) toneQueue.poll();
				Thread playerThread = new Thread(new Runnable() {

					public void run() {
						play.play(current);
					}
				});
				playerThread.start();
//				try {
//					playerThread.join();
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}
//		}
	}

	// private String parseNotes (String tone){
	//
	// HashMap<Integer, Character> pitchMap = new HashMap<Integer, Character>();
	// pitchMap.put(1, 'C');
	// pitchMap.put(2, 'D');
	// pitchMap.put(3, 'E');
	// pitchMap.put(4, 'F');
	// pitchMap.put(5, 'G');
	// pitchMap.put(6, 'A');
	// pitchMap.put(7, 'B');
	// pitchMap.put(8, 'C');
	//
	// int divide = tone.indexOf('_');
	// String pattern = "";
	//
	// //concat first half
	// String levelFirst = tone.substring(0,1);
	// String first = tone.substring(1, divide);
	// for (int i = 0; i < first.length(); i ++){
	// pattern.concat(pitchMap.get(first.charAt(i))+levelFirst+"+");
	// }
	//
	// //if there is a second half, concat the second half
	// if(divide == tone.length()-1){
	//
	// String levelSecond = tone.substring(divide, divide+1);
	// String second = tone.substring(divide+2);
	//
	// for (int i = 0; i < second.length(); i ++){
	// pattern.concat(pitchMap.get(second.charAt(i))+levelSecond+"+");
	// }
	//
	// }
	//
	// //return the pattern without ending + sign
	// return pattern.substring(0,pattern.length()-1);
	// }
}
