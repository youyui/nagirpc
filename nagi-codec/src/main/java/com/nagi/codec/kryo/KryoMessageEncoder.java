package com.nagi.codec.kryo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class KryoMessageEncoder extends MessageToByteEncoder<Object> {

    protected void encode(ChannelHandlerContext channelHandlerContext, Object t, ByteBuf byteBuf) throws Exception {
        
    }
}
