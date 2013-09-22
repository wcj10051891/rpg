package com.wabao.mogame;

import com.wabao.mogame.protocol.dto.ResponseDtoProto.ResponseDto;
import com.wabao.mogame.protocol.dto.primitive.BooleanDtoProto.BooleanDto;

public class Test {
	public static void main(String[] args) throws Exception {
		byte[] data = response();
		ResponseDto dto = ResponseDto.newBuilder().mergeFrom(data).build();
		System.out.println(ProtobufUtils.responseResult(dto));
	}
	
	public static byte[] response() {
		return ProtobufUtils.response(5, BooleanDto.newBuilder().setValue(true).build()).toByteArray();
	}
}