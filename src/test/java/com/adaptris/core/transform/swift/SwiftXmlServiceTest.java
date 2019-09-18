/*
 * $RCSfile: SwiftXmlServiceTest.java,v $
 * $Revision: 1.5 $
 * $Date: 2009/02/19 16:19:06 $
 * $Author: lchan $
 */
package com.adaptris.core.transform.swift;

import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import com.adaptris.core.AdaptrisMessage;
import com.adaptris.core.AdaptrisMessageFactory;
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
  public SwiftXmlServiceTest(String testName) {
    super(testName);
  }

  /**
   * Test method for
   * {@link com.adaptris.core.transform.SwiftXmlService#doService(com.adaptris.core.AdaptrisMessage)}
   * .
   *
   * @throws ServiceException
   * @throws XPathExpressionException
   * @throws ParserConfigurationException
   * @throws IOException
   * @throws SAXException
   */
  public void testDoService() throws ServiceException,
      XPathExpressionException, ParserConfigurationException, SAXException,
      IOException {
    SwiftXmlService service = new SwiftXmlService();
    AdaptrisMessage msg = AdaptrisMessageFactory.getDefaultInstance()
        .newMessage(SWIFT);
    service.doService(msg);
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder db = dbf.newDocumentBuilder();
    Document doc = db.parse(new InputSource(new StringReader(msg
        .getContent())));

    XPathFactory xpf = XPathFactory.newInstance();
    XPath xp = xpf.newXPath();
    String item = xp.evaluate(
        "count(/message/*[starts-with(name(.), 'block')])", doc);
    System.out.println(msg.getContent());
    System.out.println(item);

    assertEquals("Should be 4 blocks returned", 4, Integer.parseInt(item));
  }

  /**
   * @see com.adaptris.core.ExampleConfigCase#retrieveObjectForSampleConfig()
   */
  @Override
  protected Object retrieveObjectForSampleConfig() {
    return new SwiftXmlService();
  }

}
