/******************************************************************************\
 * Copyright (C) 2012-2013 Leap Motion, Inc. All rights reserved.               *
 * Leap Motion proprietary and confidential. Not for distribution.              *
 * Use subject to the terms of the Leap Motion SDK Agreement available at       *
 * https://developer.leapmotion.com/sdk_agreement, or another agreement         *
 * between Leap Motion and you, your company or other organization.             *
\******************************************************************************/

import java.io.IOException;
import java.util.concurrent.PriorityBlockingQueue;

import com.leapmotion.leap.Arm;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Finger.Type;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Listener;

class LeapListener extends Listener {
	private float wrist = 0;
	private int delay = 10;
	private int yVelocity = -50;
	private int zVelocity = 52;
	private final PriorityBlockingQueue<String> q;
	private final ToneLoader loader;
	
	public LeapListener(PriorityBlockingQueue<String> q, ToneLoader loader){
		this.q = q;
		this.loader = loader;
	}

	public void onInit(Controller controller) {
		System.out.println("Initialized");
	}

	public void onConnect(Controller controller) {
		System.out.println("Connected");
		controller.enableGesture(Gesture.Type.TYPE_SWIPE);
		controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
		controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
		controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
	}

	public void onDisconnect(Controller controller) {
		// Note: not dispatched when running in a debugger.
		System.out.println("Disconnected");
	}

	public void onExit(Controller controller) {
		System.out.println("Exited");
	}

	public void onFrame(Controller controller) {
		// Get the most recent frame and report some basic information
		Frame frame = controller.frame();
		Frame pframe = controller.frame(20);
//		Hand handy2 = frame.hands().frontmost();
//		for (Finger fingery2 : handy2.fingers()) {
//			if(fingery2.type() == Type.TYPE_RING)
//				System.out.println("current: " + fingery2.tipPosition().getZ() + "  previous: " + fingery2.tipPosition().getZ());
//		}
		
		
		StringBuilder pitches = new StringBuilder();
		// Get hands
		for (Hand hand : frame.hands()) {
			if (hand.isLeft()) {
				// Get arm height
				Arm arm = hand.arm();
				wrist = arm.wristPosition().getY();
//				String pitchL = "";
				int pitchL = (int) (wrist / 101) + 3;
				for (Finger finger : hand.fingers()) {
					if (finger.type() == Type.TYPE_RING
							&& frame.id() % delay == 0)
						if (finger.tipVelocity().getY() < yVelocity-15 && finger.tipVelocity().getZ() > zVelocity)
							pitches.append("C" + pitchL + "+");
					if (finger.type() == Type.TYPE_MIDDLE
							&& frame.id() % delay == 0)
						if (finger.tipVelocity().getY() < yVelocity && finger.tipVelocity().getZ() > zVelocity)
							pitches.append("D" + pitchL + "+");
					if (finger.type() == Type.TYPE_INDEX
							&& frame.id() % delay == 0)
						if (finger.tipVelocity().getY() < yVelocity && finger.tipVelocity().getZ() > zVelocity)
							pitches.append("E" + pitchL + "+");
					if (finger.type() == Type.TYPE_THUMB
							&& frame.id() % delay == 0)
						if (finger.tipVelocity().getY() < yVelocity-15)
							pitches.append("F" + pitchL + "+");
				}
//				if (pitchL.length() > 1)
//					pitches += pitchL;
			}
			if (hand.isRight()) {
				// Get arm height
				Arm arm = hand.arm();
				wrist = arm.wristPosition().getY();
//				String pitchR = "";
				int pitchR = (int) (wrist / 101) + 3;
				for (Finger finger : hand.fingers()) {
					if (finger.type() == Type.TYPE_RING
							&& frame.id() % delay == 0)
						if (finger.tipVelocity().getY() < yVelocity-15 && finger.tipVelocity().getZ() > zVelocity)
							pitches.append("C" + (pitchR+1) + "+");
					if (finger.type() == Type.TYPE_MIDDLE
							&& frame.id() % delay == 0)
						if (finger.tipVelocity().getY() < yVelocity && finger.tipVelocity().getZ() > zVelocity)
							pitches.append("B" + pitchR + "+");
					if (finger.type() == Type.TYPE_INDEX
							&& frame.id() % delay == 0)
						if (finger.tipVelocity().getY() < yVelocity && finger.tipVelocity().getZ() > zVelocity)
							pitches.append("A" + pitchR + "+");
					if (finger.type() == Type.TYPE_THUMB
							&& frame.id() % delay == 0)
						if (finger.tipVelocity().getY() < yVelocity-15)
							pitches.append("G" + pitchR + "+");
				}
//				if (pitches.length() > 1  && pitchR.length() > 1)
//					pitches += "_" + pitchR;
//				else if (pitchR.length() > 1)
//					pitches += pitchR;
			}
		}
		
		if (pitches.length() > 1)
		{
			String test = pitches.substring(0, pitches.length()-1);
			System.out.println(test);
			q.add(test);
		}
		
		loader.run();
	}
}

class VirtualPiano {
	public static void main(String[] args) {
		PriorityBlockingQueue<String> q = new PriorityBlockingQueue<>();
		ToneLoader loader = new ToneLoader(q);
		
		// Create a sample listener and controller
		LeapListener listener = new LeapListener(q, loader);
		Controller controller = new Controller();
		
//		loader.start();

		// Have the sample listener receive events from the controller
		controller.addListener(listener);

		// Keep this process running until Enter is pressed
		System.out.println("Press Enter to quit...");
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Remove the sample listener when done
		controller.removeListener(listener);
	}
}
