/*
 * $RCSfile: XmlSwiftService.java,v $
 * $Revision: 1.2 $
 * $Date: 2009/02/19 16:16:24 $
 * $Author: lchan $
 */
package com.adaptris.core.transform.swift;

import com.adaptris.core.AdaptrisMessage;
import com.adaptris.core.CoreException;
import com.adaptris.core.ServiceException;
import com.adaptris.core.licensing.License;
import com.adaptris.core.licensing.License.LicenseType;
import com.adaptris.core.licensing.LicenseChecker;
import com.adaptris.core.licensing.LicensedService;
import com.prowidesoftware.swift.io.ConversionService;
import com.prowidesoftware.swift.io.IConversionService;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Parse an incoming XML message and create an SWIFT representation of it.
 * 
 * @config xml-swift-service
 * @license STANDARD
 * 
 * @author ledwards
 * 
 */
@XStreamAlias("xml-swift-service")
public class XmlSwiftService extends LicensedService {

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

  @Override
  protected void prepareService() throws CoreException {
    LicenseChecker.newChecker().checkLicense(this);
  }

  @Override
  public boolean isEnabled(License license) {
    return license.isEnabled(LicenseType.Standard);
  }

  @Override
  protected void closeService() {
  }

  @Override
  protected void initService() throws CoreException {
  }
}
