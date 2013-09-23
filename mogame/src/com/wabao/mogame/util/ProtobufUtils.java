package com.wabao.mogame.util;

import com.google.protobuf.ByteString;
import com.google.protobuf.Message;
import com.google.protobuf.Message.Builder;
import com.wabao.mogame.protocol.dto.ProtoBufDtoProto.ProtoBufDto;
import com.wabao.mogame.protocol.dto.RequestDtoProto.RequestDto;
import com.wabao.mogame.protocol.dto.ResponseDtoProto.ResponseDto;

public abstract class ProtobufUtils {
	
	public static ResponseDto response(int sn, boolean isError, Object protobufObject) {
		if(!(protobufObject instanceof Message))
			throw new IllegalArgumentException("param protobuf object is not protobuf Message.");
		if(protobufObject instanceof ResponseDto)
			throw new IllegalArgumentException("param protobuf object can not nested.");
		return ResponseDto.newBuilder().setSn(sn).setIsError(isError).setResult(protobuf(protobufObject)).build();
	}
	
	public static Object getResponseResult(ResponseDto response) throws Exception {
		return decode(response.getResult());
	}
	
	public static ProtoBufDto protobuf(Object protobufObject) {
		return ProtoBufDto.newBuilder()
			.setTypeName(protobufObject.getClass().getName())
			.setData(encode(protobufObject))
			.build();
	}
	
	public static ByteString encode(Object protobufObject) {
		return ((Message)protobufObject).toByteString();
	}
	
	public static Object decode(ProtoBufDto protobufDto) throws Exception {
		return decode(protobufDto.getTypeName(), protobufDto.getData().toByteArray());
	}
	
	public static Object decode(String typeName, ByteString bytes) throws Exception {
		return getBuilder(typeName).mergeFrom(bytes).build();
	}
	
	public static Object decode(String typeName, byte[] data) throws Exception {
		return getBuilder(typeName).mergeFrom(data).build();
	}
	
	public static Message getMessage(String typeName) throws Exception {
		return (Message)Class.forName(typeName).getDeclaredMethod("getDefaultInstance").invoke(null);
	}
	
	public static Builder getBuilder(String typeName) throws Exception {
		return getMessage(typeName).newBuilderForType();
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(Class.forName(RequestDto.class.getName()));
	}
}
