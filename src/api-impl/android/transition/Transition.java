package android.transition;

import android.animation.TimeInterpolator;
import android.view.View;
import java.util.ArrayList;

public class Transition implements Cloneable {

	public interface TransitionListener {
		void onTransitionStart(Transition transition);
		void onTransitionEnd(Transition transition);
		void onTransitionCancel(Transition transition);
		void onTransitionPause(Transition transition);
		void onTransitionResume(Transition transition);
	}

	private ArrayList<TransitionListener> mListeners = null;
	private long mDuration = -1;
	private TimeInterpolator mInterpolator = null;

	@Override
	public Transition clone() {
		try {
			Transition clone = (Transition) super.clone();
			if (mListeners != null) {
				clone.mListeners = new ArrayList<>(mListeners);
			}
			return clone;
		} catch (CloneNotSupportedException e) {
			return new Transition();
		}
	}

	public Transition addListener(TransitionListener listener) {
		if (listener == null) {
			return this;
		}
		if (mListeners == null) {
			mListeners = new ArrayList<>();
		}
		if (!mListeners.contains(listener)) {
			mListeners.add(listener);
		}
		return this;
	}

	public Transition removeListener(TransitionListener listener) {
		if (mListeners != null && listener != null) {
			mListeners.remove(listener);
		}
		return this;
	}

	public Transition excludeTarget(int targetId, boolean exclude) {
		return this;
	}

	public Transition addTarget(View target) {
		return this;
	}

	public Transition addTarget(int targetId) {
		return this;
	}

	public Transition addTarget(String targetName) {
		return this;
	}

	public Transition removeTarget(View target) {
		return this;
	}

	public Transition removeTarget(int targetId) {
		return this;
	}

	public Transition setDuration(long duration) {
		mDuration = duration;
		return this;
	}

	public long getDuration() {
		return mDuration;
	}

	public Transition setInterpolator(TimeInterpolator interpolator) {
		mInterpolator = interpolator;
		return this;
	}

	public TimeInterpolator getInterpolator() {
		return mInterpolator;
	}
}
