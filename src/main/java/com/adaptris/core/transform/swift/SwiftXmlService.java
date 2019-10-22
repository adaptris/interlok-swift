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

import com.adaptris.annotation.AdapterComponent;
import com.adaptris.annotation.ComponentProfile;
import com.adaptris.core.AdaptrisMessage;
import com.adaptris.core.CoreException;
import com.adaptris.core.ServiceException;
import com.adaptris.core.ServiceImp;
import com.adaptris.core.util.ExceptionHelper;
import com.prowidesoftware.swift.io.ConversionService;
import com.prowidesoftware.swift.model.SwiftMessage;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Parse an incoming SWIFT message and create an XML representation of it.
 * 
 * @config swift-xml-service
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
    catch (Exception e) {
      throw ExceptionHelper.wrapServiceException("Failed to process incoming payload as a SWIFT FIN message", e);
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
