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

import com.adaptris.core.AdaptrisMessage;
import com.adaptris.core.AdaptrisMessageFactory;
import com.adaptris.core.ServiceException;
import com.adaptris.core.transform.TransformServiceExample;

public class XmlSwiftServiceTest extends TransformServiceExample {

  private static String SWIFT_XML = "<message>\r\n" + "<block1>\r\n"
      + "	<applicationId>F</applicationId>\r\n"
      + "	<serviceId>01</serviceId>\r\n"
      + "	<logicalTerminal>BANKDEFMAXXX</logicalTerminal>\r\n"
      + "	<sessionNumber>2039</sessionNumber>\r\n"
      + "	<sequenceNumber>063581</sequenceNumber>\r\n" + "</block1>\r\n"
      + "<block2 type=\"output\">\r\n" + "	<messageType>103</messageType>\r\n"
      + "	<senderInputTime>1609</senderInputTime>\r\n"
      + "	<MIRDate>050901</MIRDate>\r\n"
      + "	<MIRLogicalTerminal>BANKDEFXAXXX</MIRLogicalTerminal>\r\n"
      + "	<MIRSessionNumber>8954</MIRSessionNumber>\r\n"
      + "	<MIRSequenceNumber>982945</MIRSequenceNumber>\r\n"
      + "	<receiverOutputDate>894981</receiverOutputDate>\r\n"
      + "	<receiverOutputTime>1609</receiverOutputTime>\r\n"
      + "	<messagePriority>N</messagePriority>\r\n" + "</block2>\r\n"
      + "<block3>\r\n" + "	<tag>\r\n" + "		<name>108</name>\r\n"
      + "		<value>00750532785315</value>\r\n" + "	</tag>\r\n" + "</block3>\r\n"
      + "<block4>\r\n" + "	<tag>\r\n" + "		<name>20</name>\r\n"
      + "		<value>007505327853</value>\r\n" + "	</tag>\r\n" + "	<tag>\r\n"
      + "		<name>23B</name>\r\n" + "		<value>CRED</value>\r\n" + "	</tag>\r\n"
      + "	<tag>\r\n" + "		<name>32A</name>\r\n"
      + "		<value>050902JPY3520000,</value>\r\n" + "	</tag>\r\n" + "	<tag>\r\n"
      + "		<name>33B</name>\r\n" + "		<value>JPY3520000,</value>\r\n"
      + "	</tag>\r\n" + "	<tag>\r\n" + "		<name>50K</name>\r\n"
      + "		<value>EUROXXXEI</value>\r\n" + "	</tag>\r\n" + "	<tag>\r\n"
      + "		<name>52A</name>\r\n" + "		<value>FEBXXXM1</value>\r\n"
      + "	</tag>\r\n" + "	<tag>\r\n" + "		<name>53A</name>\r\n"
      + "		<value>MHCXXXJT</value>\r\n" + "	</tag>\r\n" + "	<tag>\r\n"
      + "		<name>54A</name>\r\n" + "		<value>FOOBICXX</value>\r\n"
      + "	</tag>\r\n" + "	<tag>\r\n" + "		<name>59</name>\r\n"
      + "		<value>/13212312 RECEIVER NAME S.A</value>\r\n" + "	</tag>\r\n"
      + "	<tag>\r\n" + "		<name>70</name>\r\n" + "		<value>FUTURES</value>\r\n"
      + "	</tag>\r\n" + "	<tag>\r\n" + "		<name>71A</name>\r\n"
      + "		<value>SHA</value>\r\n" + "	</tag>\r\n" + "	<tag>\r\n"
      + "		<name>71F</name>\r\n" + "		<value>EUR12,00</value>\r\n"
      + "	</tag>\r\n" + "	<tag>\r\n" + "		<name>71F</name>\r\n"
      + "		<value>EUR2,34</value>\r\n" + "	</tag>\r\n" + "</block4>\r\n"
      + "</message>\r\n";

  /**
   *
   */
  public XmlSwiftServiceTest(String testName) {
    super(testName);
  }

  // @Test
  public void testDoService() throws ServiceException {
    XmlSwiftService service = new XmlSwiftService();
    AdaptrisMessage msg = AdaptrisMessageFactory.getDefaultInstance()
        .newMessage(SWIFT_XML);
    service.doService(msg);
    System.out.println(msg.getContent());
    assertTrue("Must contain block1", msg.getContent().contains("{1:"));
    assertTrue("Must contain block2", msg.getContent().contains("{2:"));
    assertTrue("Must contain block3", msg.getContent().contains("{3:"));
    assertTrue("Must contain block4", msg.getContent().contains("{4:"));
  }

  /**
   * @see com.adaptris.core.ExampleConfigCase#retrieveObjectForSampleConfig()
   */
  @Override
  protected Object retrieveObjectForSampleConfig() {
    return new XmlSwiftService();
  }

}
