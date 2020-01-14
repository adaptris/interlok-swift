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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import com.adaptris.core.AdaptrisMessage;
import com.adaptris.core.AdaptrisMessageFactory;
import com.adaptris.core.ServiceCase;
import com.adaptris.core.ServiceException;
import com.adaptris.core.transform.TransformServiceExample;

/**
 * @author stuellidge
 *
 */
public class SwiftXmlServiceTest extends TransformServiceExample {

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


  /**
   *
   */
  public SwiftXmlServiceTest() {
    super();
  }

  @Test
  public void testDoService() throws Exception {
    SwiftXmlService service = new SwiftXmlService();
    AdaptrisMessage msg = AdaptrisMessageFactory.getDefaultInstance().newMessage(SWIFT);
    ServiceCase.execute(service, msg);
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder db = dbf.newDocumentBuilder();
    Document doc = db.parse(new InputSource(new StringReader(msg.getContent())));

    XPathFactory xpf = XPathFactory.newInstance();
    XPath xp = xpf.newXPath();
    String item = xp.evaluate("count(/message/*[starts-with(name(.), 'block')])", doc);
    System.out.println(msg.getContent());
    System.out.println(item);

    assertEquals("Should be 4 blocks returned", 4, Integer.parseInt(item));

  }

  @Test
  public void testDoService_Fails() throws Exception {
    SwiftXmlService service = new SwiftXmlService();
    try {
      ServiceCase.execute(service, null);
      fail();
    } catch (ServiceException expected) {

    }
  }


  /**
   * @see com.adaptris.core.ExampleConfigCase#retrieveObjectForSampleConfig()
   */
  @Override
  protected Object retrieveObjectForSampleConfig() {
    return new SwiftXmlService();
  }

  @Override
  public boolean isAnnotatedForJunit4() {
    return true;
  }

}
