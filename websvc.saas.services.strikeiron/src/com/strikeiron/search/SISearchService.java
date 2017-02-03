/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * Contributor(s):
 * 
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
 * Microsystems, Inc. All Rights Reserved.
 * 
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 */

package com.strikeiron.search;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
//FIXME - Refactor
//import javax.xml.ws.Service;
//import javax.xml.ws.WebEndpoint;
//import javax.xml.ws.WebServiceClient;
//import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.2-hudson-182-RC1
 * Generated source version: 2.1
 * 
 */
//FIXME - Refactor
//@WebServiceClient(name = "SISearchService", targetNamespace = "http://www.strikeiron.com", wsdlLocation = "http://ws.strikeiron.com/Searchsunsi01.StrikeIron/MarketplaceSearch?WSDL")
public class SISearchService
//    extends Service
{

    private final static URL SISEARCHSERVICE_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(com.strikeiron.search.SISearchService.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = com.strikeiron.search.SISearchService.class.getResource(".");
            url = new URL(baseUrl, "http://ws.strikeiron.com/Searchsunsi01.StrikeIron/MarketplaceSearch?WSDL");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'http://ws.strikeiron.com/Searchsunsi01.StrikeIron/MarketplaceSearch?WSDL', retrying as a local file");
            logger.warning(e.getMessage());
        }
        SISEARCHSERVICE_WSDL_LOCATION = url;
    }

    public SISearchService(URL wsdlLocation, QName serviceName) {
//        super(wsdlLocation, serviceName);
    }

    public SISearchService() {
//        super(SISEARCHSERVICE_WSDL_LOCATION, new QName("http://www.strikeiron.com", "SISearchService"));
    }

    /**
     * 
     * @return
     *     returns SISearchServiceSoap
     */
//    @WebEndpoint(name = "SISearchServiceSoap")
    public SISearchServiceSoap getSISearchServiceSoap() {
//        return super.getPort(new QName("http://www.strikeiron.com", "SISearchServiceSoap"), SISearchServiceSoap.class);
        return null;
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SISearchServiceSoap
     */
//    @WebEndpoint(name = "SISearchServiceSoap")
//    public SISearchServiceSoap getSISearchServiceSoap(WebServiceFeature... features) {
//        return super.getPort(new QName("http://www.strikeiron.com", "SISearchServiceSoap"), SISearchServiceSoap.class, features);
//        return null;
//    }

}