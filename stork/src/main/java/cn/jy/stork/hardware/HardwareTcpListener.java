package cn.jy.stork.hardware;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * <h1>整个应用主要的硬件接入监听器</h1>
 * <p>
 * 该监听器不会自动启动，应用层面应该在合适的切面启动本监听器。（注意不要在本组件中自己启动自己，因为启动方法是@Asyn异步的，自己启动自己会造成阻塞。目前在本应用的启动类中启动）
 * </p>
 * <p>
 * 该监听器会预处理收到的数据报文，初步解析（主要是解析出功能码和设备号）后派发不同类型的事件，交由不同业务组件去进一步解析。
 * </p>
 * 
 * @author jsh
 *
 */
@Component
public class HardwareTcpListener {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${hardware.master-port}")
	private Integer port;

	private EventLoopGroup parentGroup;
	private EventLoopGroup childGroup;

	@Autowired
	CoreChannelHandler handler;

	@Async
	public void start() {

		parentGroup = new NioEventLoopGroup();//
		childGroup = new NioEventLoopGroup();//
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.channel(NioServerSocketChannel.class);
			b.group(parentGroup, childGroup);

			b.childHandler(handler);// 注册自定义的channle handler

			ChannelFuture bindFuture = b.bind(port != null ? port : 8888).sync();
			bindFuture.addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					logger.info("---------------------------------------------------------");
					logger.info("  Modbus服务端启动，监听端口 {}，等待连接", port);
					logger.info("---------------------------------------------------------");
				}
			});
			bindFuture.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO 错误恢复
		} finally {
		}
	}

	@PreDestroy
	void destroy() {
		childGroup.shutdownGracefully();
		parentGroup.shutdownGracefully();
	}

}
