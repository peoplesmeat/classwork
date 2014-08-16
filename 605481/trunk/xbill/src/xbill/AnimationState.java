package xbill;

import java.util.ArrayList;

/**
 * This class maintains information about animation, where
 * an animation consists of a group of frames in an indexed 
 * array.  
 * @author bdavis
 *
 */
class AnimationState {
	enum AnimationType {
		Loop, Bounce
	}

	AnimationType animationType = AnimationType.Bounce;
	int currFrame;
	int counter;
	int offset;
	int length;
	int fps = 1;
	int ticks = 0;
	int frameIncrement = 1;

	public AnimationState(int offset, int length, int counter) {
		this.currFrame = 0;
		this.offset = offset;
		this.length = length;
		this.counter = counter;
	}

	public void setAnimationType(AnimationType type) {
		animationType = type;
	}

	public int getFPS() {
		return fps;
	}

	public AnimationState(int offset, int length, int counter, int fps) {
		this.currFrame = 0;
		this.offset = offset;
		this.length = length;
		this.fps = fps;
		this.counter = counter;
	}

	public int tick() {
		ticks += 1;
		if (counter == 0) {
			return offset + currFrame;
		}
		if (ticks % fps == 0) {
			currFrame += frameIncrement;
			if (currFrame == length - 1
					&& animationType == AnimationType.Bounce)
				frameIncrement = -1;
			else if (currFrame == length - 1)
				currFrame = 0;
			if (currFrame == 0) {
				frameIncrement = 1;
				counter -= 1;

			}
			if (counter == 0) {
				for (AnimationActionListener l : listeners)
					l.animationEnded();
			}

		}
		return offset + currFrame;
	}

	public int getFrame() {
		return offset + currFrame;
	}

	public void reset() {
		currFrame = 0;
	}

	private ArrayList<AnimationActionListener> listeners = new ArrayList<AnimationActionListener>();

	public void addAnimationCompleteListener(AnimationActionListener l) {
		listeners.add(l);
	}

	public void removeAnimationCompleteListener(AnimationActionListener l) {
		listeners.remove(l);
	}

}