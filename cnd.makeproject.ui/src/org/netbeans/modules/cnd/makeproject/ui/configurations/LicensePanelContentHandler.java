/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2016 Oracle and/or its affiliates. All rights reserved.
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
 *
 * Contributor(s):
 *
 * Portions Copyrighted 2016 Sun Microsystems, Inc.
 */
package org.netbeans.modules.cnd.makeproject.ui.configurations;

import org.netbeans.api.annotations.common.NonNull;
import org.netbeans.api.annotations.common.NullAllowed;
import org.openide.filesystems.FileObject;

/**
 * handle data inputs and outputs in the license headers customizer panel.
 * Copied from org.netbeans.spi.project.support.ant.ui.CustomizerUtilities
 * See bug #257920
 */
public interface LicensePanelContentHandler {

    /**
     * raw, unevaluated value points to the file in project space containing the header,
     * if present takes precedence over <code>getGlobalLicenseName()</code>
     *
     * @return value or null if the value is unknown/doesn't exist
     */
    String getProjectLicenseLocation();

    /**
     * value of pointing to the global license header in the IDE.
     * @return
     */
    String getGlobalLicenseName();

    /**
     * take the value from <code>getProjectLicenseLocation</code> and evaluate it to FileObject
     *
     * @param path
     * @return FileObject instance if found, otherwise null
     */
    FileObject resolveProjectLocation(@NonNull String path);

    /**
     * new value for project location, null value allowed and is meant to remove the value (to effectively use the global license)
     * @param newLocation
     */
    void setProjectLicenseLocation(@NullAllowed String newLocation);

    /**
     * set new value of global license template
     * @param newName
     */
    void setGlobalLicenseName(@NullAllowed String newName);

    /**
     * if no <code>getProjectLicenseLocation</code> is returned, this method will return the default project location.
     * @return
     */
    String getDefaultProjectLicenseLocation();

    /**
     * set the user edited content of the license header file in project space.
     * @param text
     */
    void setProjectLicenseContent(@NullAllowed String text);
    
}