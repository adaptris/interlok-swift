/*
 * $RCSfile: XmlSwiftService.java,v $
 * $Revision: 1.2 $
 * $Date: 2009/02/19 16:16:24 $
 * $Author: lchan $
 */
package com.adaptris.core.transform.swift;

import net.sourceforge.wife.services.ConversionService;
import net.sourceforge.wife.services.IConversionService;
import net.sourceforge.wife.swift.model.SwiftMessage;

import com.adaptris.core.AdaptrisMessage;
import com.adaptris.core.CoreException;
import com.adaptris.core.ServiceException;
import com.adaptris.core.ServiceImp;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Parse an incoming XML message and create an SWIFT representation of it.
 ** <p>
 * In the adapter configuration file this class is aliased as <b>xml-swift-service</b> which is the preferred alternative to the
 * fully qualified classname when building your configuration.
 * </p>

 * @author ledwards
 *
 */
@XStreamAlias("xml-swift-service")
public class XmlSwiftService extends ServiceImp {

  /**
   * Service method to parse an incoming XML message and create an SWIFT
   * representation of it. Limited support for character encoding - UTF-8 only.
   *
   * @see com.adaptris.core.Service#doService(com.adaptris.core.AdaptrisMessage)
   */
  public void doService(AdaptrisMessage msg) throws ServiceException {
    try {
      IConversionService service = new ConversionService();
      SwiftMessage swift = service.getMessageFromXML(msg.getStringPayload());
      String fin = service.getFIN(swift);
      msg.setStringPayload(fin, msg.getCharEncoding());
    }
    catch (Throwable t) {
      // log.error("Unable to create SWIFT FIN message from XML", t);
      throw new ServiceException("Unable to create SWIFT FIN message from XML",
          t);
    }
  }

  /**
   * @see com.adaptris.core.AdaptrisComponent#close()
   */
  public void close() {
  }

  /**
   * @see com.adaptris.core.AdaptrisComponent#init()
   */
  public void init() throws CoreException {
  }

}
