package cn.jy.stork.hardware.event;

import org.springframework.context.ApplicationEvent;

public class DoSampleEvent extends ApplicationEvent {

	private static final long serialVersionUID = 4181198688933063675L;

	public DoSampleEvent(Object source) {
		super(source);
	}

}
