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

class LeapListener extends Listener {
	private float wrist = 0;
	private int delay = 15;

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
		StringBuilder pitchL = new StringBuilder();
		StringBuilder pitchR = new StringBuilder();
		// Get hands
		for (Hand hand : frame.hands()) {
			if (hand.isLeft()) {
				// Get arm height
				Arm arm = hand.arm();
				wrist = arm.wristPosition().getY();
				pitchL.append((int) (wrist / 101));
				for (Finger finger : hand.fingers()) {
					if (finger.type() == Type.TYPE_RING
							&& frame.id() % delay == 0)
						if (finger.tipVelocity().getY() < -100)
							pitchL.append(1);
					if (finger.type() == Type.TYPE_MIDDLE
							&& frame.id() % delay == 0)
						if (finger.tipVelocity().getY() < -100)
							pitchL.append(2);
					if (finger.type() == Type.TYPE_INDEX
							&& frame.id() % delay == 0)
						if (finger.tipVelocity().getY() < -100)
							pitchL.append(3);
					if (finger.type() == Type.TYPE_THUMB
							&& frame.id() % delay == 0)
						if (finger.tipVelocity().getY() < -100)
							pitchL.append(4);
				}
			}
			if (hand.isRight()) {
				// Get arm height
				Arm arm = hand.arm();
				wrist = arm.wristPosition().getY();
				pitchR.append((int) (wrist / 101));
				for (Finger finger : hand.fingers()) {
					if (finger.type() == Type.TYPE_RING
							&& frame.id() % delay == 0)
						if (finger.tipVelocity().getY() < -100)
							pitchR.append(8);
					if (finger.type() == Type.TYPE_MIDDLE
							&& frame.id() % delay == 0)
						if (finger.tipVelocity().getY() < -100)
							pitchR.append(7);
					if (finger.type() == Type.TYPE_INDEX
							&& frame.id() % delay == 0)
						if (finger.tipVelocity().getY() < -100)
							pitchR.append(6);
					if (finger.type() == Type.TYPE_THUMB
							&& frame.id() % delay == 0)
						if (finger.tipVelocity().getY() < -100)
							pitchR.append(5);
				}
			}
		}
		if (pitchL.length() > 1)
		System.out.println(pitchL);
		if (pitchR.length() > 1)
		System.out.println(pitchR);
	}
}

class VirtualPiano {
	public static void main(String[] args) {
		// Create a sample listener and controller
		LeapListener listener = new LeapListener();
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
