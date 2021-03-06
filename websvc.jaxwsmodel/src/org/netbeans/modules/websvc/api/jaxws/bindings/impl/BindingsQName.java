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
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
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
package org.netbeans.modules.websvc.api.jaxws.bindings.impl;

import java.util.HashSet;
import java.util.Set;
import javax.xml.namespace.QName;

/**
 *
 * @author Roderico Cruz
 */
public enum BindingsQName {

    HANDLER_CHAINS(createHandlerQName("handler-chains")),
    HANDLER_CHAIN(createHandlerQName("handler-chain")),
    HANDLER(createHandlerQName("handler")),
    HANDLER_CLASS(createHandlerQName("handler-class")),
    HANDLER_NAME(createHandlerQName("handler-name")),
    BINDINGS(createBindingsQName("bindings"));
    
    public static final String JAVAEE_NS_URI = "http://java.sun.com/xml/ns/javaee";
    public static final String JAVAEE_NS_PREFIX = "jws";
    public static final String JAXWS_NS_URI = "http://java.sun.com/xml/ns/jaxws";
    public static final String JAXWS_NS_PREFIX = "jaxws";

    public static QName createHandlerQName(String localName) {
        return new QName(JAVAEE_NS_URI, localName, JAVAEE_NS_PREFIX);
    }

    public static QName createBindingsQName(String localName) {
        return new QName(JAXWS_NS_URI, localName, JAXWS_NS_PREFIX);
    }

    BindingsQName(QName name) {
        qName = name;
    }

    public QName getQName() {
        return qName;
    }
    private static Set<QName> qnames = null;

    public static Set<QName> getQNames() {
        return qnames;
    }
    
    public static void initQNames() {
        qnames = new HashSet<QName>();
        for (BindingsQName wq : values()) {
            qnames.add(wq.getQName());
        }
    }
    
    static {
        initQNames();
    }
    
    private final QName qName;
}

