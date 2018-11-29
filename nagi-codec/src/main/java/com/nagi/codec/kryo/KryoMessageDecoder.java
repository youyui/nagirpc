package com.nagi.codec.kryo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class KryoMessageDecoder extends ByteToMessageDecoder {
    protected void decode(ChannelHandlerContext context, ByteBuf byteBuf, List<Object> list) throws Exception {

    }
}
