package cn.jy.stork.hardware.event;

import org.springframework.context.ApplicationEvent;

public class ManualSamplingEvent extends ApplicationEvent {
	private static final long serialVersionUID = -6988563591871602663L;

	public ManualSamplingEvent(Object source, boolean pause) {
		super(source);
		this.pause = pause;
	}

	private boolean pause;

	public boolean is() {
		return pause;
	}

}
