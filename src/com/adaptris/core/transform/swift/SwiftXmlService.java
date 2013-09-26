/*
 * $RCSfile: SwiftXmlService.java,v $
 * $Revision: 1.2 $
 * $Date: 2009/02/19 16:16:24 $
 * $Author: lchan $
 */
package com.adaptris.core.transform.swift;

import com.prowidesoftware.swift.io.ConversionService;
import com.prowidesoftware.swift.io.IConversionService;
import com.prowidesoftware.swift.model.SwiftMessage;

import com.adaptris.core.AdaptrisMessage;
import com.adaptris.core.CoreException;
import com.adaptris.core.ServiceException;
import com.adaptris.core.ServiceImp;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Parse an incoming SWIFT message and create an XML representation of it.
 ** <p>
 * In the adapter configuration file this class is aliased as <b>swift-xml-service</b> which is the preferred alternative to the
 * fully qualified classname when building your configuration.
 * </p>

 * @author stuellidge
 *
 */
@XStreamAlias("swift-xml-service")
public class SwiftXmlService extends ServiceImp {

  /**
   * Service method to parse an incoming SWIFT message and create an XML
   * representation of it. Limited support for character encoding - UTF-8 only.
   *
   * @see com.adaptris.core.Service#doService(com.adaptris.core.AdaptrisMessage)
   */
  public void doService(AdaptrisMessage msg) throws ServiceException {
    try {
      IConversionService service = new ConversionService();
      SwiftMessage swift = service.getMessageFromFIN(msg.getStringPayload());
      msg.setStringPayload(service.getXml(swift), msg.getCharEncoding());
    }
    catch (Throwable t) {
      // log.error("Failed to process incoming payload as a SWIFT FIN message",
      // t);
      throw new ServiceException(
          "Failed to process incoming payload as a SWIFT FIN message", t);
    }
  }

  /**
   * Empty resource disposal method
   *
   * @see com.adaptris.core.AdaptrisComponent#close()
   */
  public void close() {

  }

  /**
   * Empty resource initialiser
   *
   * @see com.adaptris.core.AdaptrisComponent#init()
   */
  public void init() throws CoreException {

  }

}
