package cn.jy.stork.hardware;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import cn.jy.stork.hardware.event.ManualSamplingEvent;
import cn.jy.stork.hardware.task.RegularSamplingTask;

//@Component
//@Configuration
//@EnableAsync
public class ManualSampler implements ApplicationListener<ManualSamplingEvent> {

	@Autowired
	RegularSamplingTask task;

	@Override
	public void onApplicationEvent(ManualSamplingEvent event) {
		if (event.is()) {
			new Thread() {
				@Override
				public void run() {
					try {
						manualSampling();
					} catch (InterruptedException e) {
					}
				}
			}.start();
			System.err.println("已进入手动采样...");
		}

	}

	/**
	 * 手动采样
	 * 
	 * @throws InterruptedException
	 */
	@Async
	void manualSampling() throws InterruptedException {
//		// 多参数换水
//		task._generalFlush();
//		Thread.sleep(1000L * 60 * 3);
//		// 多参数关闭换水，上电
//		task._generalOpen();
//		Thread.sleep(1000L * 30);
//		// 多参数取数据
//		task._generalFetchData();
//		Thread.sleep(1000L * 30);
//		// 多参数下电
//		task._generalOff();
//		Thread.sleep(100);
//		// 取流速
//		task._streamspeedFetchData();
	}
}
