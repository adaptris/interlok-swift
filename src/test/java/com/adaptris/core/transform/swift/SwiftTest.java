/*******************************************************************************
 * Copyright 2019 Adaptris Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.adaptris.core.transform.swift;

import com.prowidesoftware.swift.io.ConversionService;
import com.prowidesoftware.swift.io.IConversionService;
import com.prowidesoftware.swift.model.SwiftMessage;

import org.junit.Test;


public class SwiftTest {
	  String SWIFT = "{1:F01BANKDEFMAXXX2039063581}\r\n" +
	  "{2:O1031609050901BANKDEFXAXXX89549829458949811609N}\r\n" +
	  "{3:{108:00750532785315}}{4:\r\n" +
	  ":20:007505327853\r\n" +
	  ":23B:CRED\r\n" +
	  ":32A:050902JPY3520000,\r\n" +
	  ":33B:JPY3520000,\r\n" +
	  ":50K:EUROXXXEI\r\n" +
	  ":52A:FEBXXXM1\r\n" +
	  ":53A:MHCXXXJT\r\n" +
	  ":54A:FOOBICXX\r\n" +
	  ":59:/13212312\r\n" +
	  "RECEIVER NAME S.A\r\n" +
	  ":70:FUTURES\r\n" +
	  ":71A:SHA\r\n" +
	  ":71F:EUR12,00\r\n" +
	  ":71F:EUR2,34\r\n" +
	  "-} \r\n";

	  
	  @Test
	  public void testSwiftChain() {
		  IConversionService svc = new ConversionService();
		  SwiftMessage msg = svc.getMessageFromFIN(SWIFT);
		  String xml = svc.getXml(msg);
		  System.out.println(xml);
		  msg = svc.getMessageFromXML(xml);
		  String fin = svc.getFIN(msg);
		  System.out.println(fin);
	  }
}
