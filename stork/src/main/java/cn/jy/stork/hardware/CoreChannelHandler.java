package cn.jy.stork.hardware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import cn.jy.stork.hardware.packet.up.IUpPacket;
import cn.jy.stork.hardware.packet.up.UpPacketWrapper;
import cn.jy.stork.hardware.util.BytesUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 上下交互的ChannleHandler，是上下位机的交互中枢组件（↑处理下位机上送报文、预解析报文、转发处理；↓监听业务组件下发指令的事件，执行报文下发的操作）
 * 
 * @author jsh
 *
 */
@Component
@Configuration
public class CoreChannelHandler extends ChannelInitializer<SocketChannel> {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	DevicePool pool;

	@Override
	protected void initChannel(SocketChannel channel) throws Exception {
		ChannelPipeline p = channel.pipeline();

		// 截取帧，解决拆包粘包
		p.addLast("frameDecoder",
				new LengthFieldBasedFrameDecoder(IUpPacket.MAX_FRAME_LENGTH, IUpPacket.LENGTH_FIELD_OFFSET,
						IUpPacket.LENGTH_FIELD_LENGTH, IUpPacket.LENGTH_ADJUSTMENT, IUpPacket.INITIAL_BYTES_TO_STRIP));

		// 业务数据初步解析（只初步解析，进一步解析由其他组件各自负责）
		p.addLast("modbusDecoder", new ChannelInboundHandlerAdapter() {

			@Override
			public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
				// 从通道中读出报文
				final ByteBuf buf = (ByteBuf) msg;
				final byte[] bytes = new byte[buf.readableBytes()];
				buf.readBytes(bytes);
				// 分支处理报文
				new UpPacketWrapper() {
					@Override
					protected void heartbeat(ChannelHandlerContext ctx, IUpPacket.Heartbeat packet) {
						pool.heartbeat(ctx, packet);
					}

					@Override
					protected void streamspeed(ChannelHandlerContext ctx, IUpPacket.SampleData packet) {
						pool.streamspeed(packet);
					}

					@Override
					protected void general(ChannelHandlerContext ctx, IUpPacket.SampleData packet) {
					}

					@Override
					protected void phosphorus(ChannelHandlerContext ctx, IUpPacket.SampleData packet) {
					}

					@Override
					protected void illegalPacket(byte[] bytes) {
						logger.debug("非法报文，丢弃:: {}", BytesUtil.barr2Hex(bytes));
					}

				}.exec(ctx, bytes);
//				dispatcher.publishEvent(new UpPacketEvent(this, address, bytes));
			}

			@Override
			public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
				cause.printStackTrace();
				logger.debug("异常 @ {}，内容：{}", ctx.channel().remoteAddress(), cause.getClass() + cause.getMessage());
			}
		});

	}// initChannel(SocketChannel channel)

}
