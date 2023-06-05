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
/*
 * $RCSfile: XmlSwiftService.java,v $
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
import com.adaptris.core.util.ExceptionHelper;
import com.prowidesoftware.swift.io.ConversionService;
import com.prowidesoftware.swift.io.IConversionService;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Parse an incoming XML message and create an SWIFT representation of it.
 *
 * @config xml-swift-service
 * @author ledwards
 *
 */
@XStreamAlias("xml-swift-service")
@AdapterComponent
@ComponentProfile(summary = "Transform a XML message to SWIFT", tag = "service,transform,xml,swift")
public class XmlSwiftService extends ServiceImp {

  /**
   * Service method to parse an incoming XML message and create an SWIFT representation of it. Limited support for character encoding -
   * UTF-8 only.
   *
   * @see com.adaptris.core.Service#doService(com.adaptris.core.AdaptrisMessage)
   */
  @Override
  public void doService(AdaptrisMessage msg) throws ServiceException {
    try {
      IConversionService service = new ConversionService();
      SwiftMessage swift = service.getMessageFromXML(msg.getContent());
      String fin = service.getFIN(swift);
      msg.setContent(fin, msg.getContentEncoding());
    } catch (Exception e) {
      throw ExceptionHelper.wrapServiceException("Unable to create SWIFT FIN message from XML", e);
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
