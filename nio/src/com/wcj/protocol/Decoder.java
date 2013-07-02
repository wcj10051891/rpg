package com.wcj.protocol;

import com.wcj.channel.ChannelContext;


public abstract class Decoder {
	public abstract Object decode(ChannelContext session, byte[] message);
}
