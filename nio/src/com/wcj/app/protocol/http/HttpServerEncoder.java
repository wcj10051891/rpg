/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *  
 *    http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License. 
 *  
 */
package com.wcj.app.protocol.http;

import java.nio.charset.Charset;
import java.util.Map;

import com.wcj.NioException;
import com.wcj.app.protocol.http.api.HttpResponse;
import com.wcj.protocol.Encoder;

public class HttpServerEncoder extends Encoder {
	public static final HttpServerEncoder INSTANCE = new HttpServerEncoder();
	
	public byte[] encode(Object message) {
		if (!(message instanceof HttpResponse))
			throw new NioException("message must be a HttpResponse type.");
		HttpResponse msg = (HttpResponse) message;
		StringBuilder sb = new StringBuilder(msg.getStatus().line());

		for (Map.Entry<String, String> header : msg.getHeaders().entrySet()) {
			sb.append(header.getKey());
			sb.append(": ");
			sb.append(header.getValue());
			sb.append("\r\n");
		}
		sb.append("\r\n");
		return sb.toString().getBytes(Charset.forName("UTF-8"));
	}
}
