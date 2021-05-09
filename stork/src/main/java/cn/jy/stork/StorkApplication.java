package cn.jy.stork;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import cn.jy.stork.hardware.HardwareTcpListener;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@EnableTransactionManagement
//@MapperScan("cn.jy.stork")
public class StorkApplication {

	@Autowired
	HardwareTcpListener modbusTcpListener;

	public static void main(String[] args) {
		SpringApplication.run(StorkApplication.class, args);
	}

	/**
	 * 整个应用启动后，启动modbus服务端，参见 {@link HardwareTcpListener}
	 * 
	 * @return
	 */
	@Bean(name = "runner-modbus")
	ApplicationRunner modbus() {
		return new ApplicationRunner() {
			@Override
			public void run(ApplicationArguments args) throws Exception {
				modbusTcpListener.start();
			}
		};
	}
}
