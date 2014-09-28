/******************************************************************************\
 * Copyright (C) 2012-2013 Leap Motion, Inc. All rights reserved.               *
 * Leap Motion proprietary and confidential. Not for distribution.              *
 * Use subject to the terms of the Leap Motion SDK Agreement available at       *
 * https://developer.leapmotion.com/sdk_agreement, or another agreement         *
 * between Leap Motion and you, your company or other organization.             *
\******************************************************************************/

import java.io.IOException;

import com.leapmotion.leap.Arm;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Finger.Type;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Listener;

class SampleListener extends Listener {
	private float wrist = 0;
	private static Play play = new Play();
	private int delay = 15;
	private static musicThread[] threads = new musicThread[8];

	static {
		for (int i = 0; i < 8; i++) {
			threads[i] = new musicThread(play, i + 1);
		}

		for (musicThread m : threads) {
			System.out.println(m.getId());
			if (m != null) {
				m.start();
				try {
					m.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
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
		System.out.println("In Frame");
		// Get the most recent frame and report some basic information
		Frame frame = controller.frame();
		// Get hands
		for (Hand hand : frame.hands()) {
			System.out.println("Before if");
			if (hand.isLeft()) {
				System.out.println("In Left");
				// Get arm height
				Arm arm = hand.arm();
				wrist = arm.wristPosition().getY();
				int pitchL = (int) (wrist / 101);
				for (Finger finger : hand.fingers()) {
					if (finger.type() == Type.TYPE_RING
							&& frame.id() % delay == 0)
						if (finger.tipVelocity().getY() < -200)
							threads[0].setPitch(pitchL + "1");
					if (finger.type() == Type.TYPE_MIDDLE
							&& frame.id() % delay == 0)
						if (finger.tipVelocity().getY() < -200)
							threads[1].setPitch(pitchL + "2");
					if (finger.type() == Type.TYPE_INDEX
							&& frame.id() % delay == 0)
						if (finger.tipVelocity().getY() < -200)
							threads[2].setPitch(pitchL + "3");
					if (finger.type() == Type.TYPE_THUMB
							&& frame.id() % delay == 0)
						if (finger.tipVelocity().getY() < -200)
							threads[3].setPitch(pitchL + "4");
				}
			}
			if (hand.isRight()) {
				System.out.println("In Right");
				// Get arm height
				Arm arm = hand.arm();
				wrist = arm.wristPosition().getY();
				int pitchR = (int) (wrist / 101);
				for (Finger finger : hand.fingers()) {
					if (finger.type() == Type.TYPE_RING
							&& frame.id() % delay == 0)
						if (finger.tipVelocity().getY() < -200)
							threads[7].setPitch(pitchR + "8");
					if (finger.type() == Type.TYPE_MIDDLE
							&& frame.id() % delay == 0)
						if (finger.tipVelocity().getY() < -200)
							threads[6].setPitch(pitchR + "7");
					if (finger.type() == Type.TYPE_INDEX
							&& frame.id() % delay == 0)
						if (finger.tipVelocity().getY() < -200)
							threads[5].setPitch(pitchR + "6");
					if (finger.type() == Type.TYPE_THUMB
							&& frame.id() % delay == 0)
						if (finger.tipVelocity().getY() < -200)
							threads[4].setPitch(pitchR + "5");
				}
			}
		}
	}
}

class HelloWorld {
	public static void main(String[] args) {
		// Create a sample listener and controller
		SampleListener listener = new SampleListener();
		Controller controller = new Controller();

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
