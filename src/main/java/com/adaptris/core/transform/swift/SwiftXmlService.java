/*
 * $RCSfile: SwiftXmlService.java,v $
 * $Revision: 1.2 $
 * $Date: 2009/02/19 16:16:24 $
 * $Author: lchan $
 */
package com.adaptris.core.transform.swift;

import com.adaptris.annotation.AdapterComponent;
import com.adaptris.annotation.ComponentProfile;
import com.adaptris.core.AdaptrisMessage;
import com.adaptris.core.CoreException;
import com.adaptris.core.ServiceException;
import com.adaptris.core.ServiceImp;
import com.prowidesoftware.swift.io.ConversionService;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Parse an incoming SWIFT message and create an XML representation of it.
 * 
 * @config swift-xml-service
 * @license STANDARD
 * 
 * @author stuellidge
 * 
 */
@XStreamAlias("swift-xml-service")
@AdapterComponent
@ComponentProfile(summary = "Transform a SWIFT message to XML", tag = "service,transform,xml,swift")
public class SwiftXmlService extends ServiceImp {

  /**
   * Service method to parse an incoming SWIFT message and create an XML
   * representation of it. Limited support for character encoding - UTF-8 only.
   *
   * @see com.adaptris.core.Service#doService(com.adaptris.core.AdaptrisMessage)
   */
  @Override
  public void doService(AdaptrisMessage msg) throws ServiceException {
    try {
      ConversionService service = new ConversionService();
      SwiftMessage swift = service.getMessageFromFIN(msg.getContent());
      msg.setContent(service.getXml(swift), msg.getContentEncoding());
    }
    catch (Throwable t) {
      // log.error("Failed to process incoming payload as a SWIFT FIN message",
      // t);
      throw new ServiceException(
          "Failed to process incoming payload as a SWIFT FIN message", t);
    }
  }

  @Override
  protected void closeService() {
  }

  @Override
  protected void initService() throws CoreException {
  }

  @Override
  public void prepare() throws CoreException {
  }
}
