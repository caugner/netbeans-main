/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2012 Oracle and/or its affiliates. All rights reserved.
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
 * Portions Copyrighted 2012 Sun Microsystems, Inc.
 */
package org.netbeans.modules.web.livehtml.ui;

import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

/**
 * Top component which displays something.
 */
//@ConvertAsProperties(
//    dtd = "-//org.netbeans.modules.web.livehtml.ui//LiveHTML//EN",
//autostore = false)
@TopComponent.Description(
    preferredID = LiveHTMLTopComponent.PREFERRED_ID,
//iconBase="SET/PATH/TO/ICON/HERE", 
persistenceType = TopComponent.PERSISTENCE_NEVER)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
//@ActionID(category = "Window", id = "org.netbeans.modules.web.livehtml.ui.LiveHTMLTopComponent")
//@ActionReference(path = "Menu/Window" /*, position = 333 */)
//@TopComponent.OpenActionRegistration(
//    displayName = "#CTL_LiveHTMLAction",
//preferredID = LiveHTMLTopComponent.PREFERRED_ID)
@Messages({
    "CTL_LiveHTMLAction=LiveHTML",
    "CTL_LiveHTMLTopComponent=LiveHTML Window",
    "HINT_LiveHTMLTopComponent=This is a LiveHTML window"
})
public final class LiveHTMLTopComponent extends TopComponent {
    
    public static final String PREFERRED_ID = "LiveHTMLTopComponent";
    
    private static LiveHTMLTopComponent instance;

    private LiveHTMLTopComponent() {
        initComponents();
        setName(Bundle.CTL_LiveHTMLTopComponent());
        setToolTipText(Bundle.HINT_LiveHTMLTopComponent());

    }

    public synchronized static LiveHTMLTopComponent getInstance() {
        if (instance == null) {
            instance = new LiveHTMLTopComponent();
        }
        return instance;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        analysisPanel1 = new org.netbeans.modules.web.livehtml.ui.AnalysisPanel();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(analysisPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(analysisPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.netbeans.modules.web.livehtml.ui.AnalysisPanel analysisPanel1;
    // End of variables declaration//GEN-END:variables

//    void writeProperties(java.util.Properties p) {
//        // better to version settings since initial version as advocated at
//        // http://wiki.apidesign.org/wiki/PropertyFiles
//        p.setProperty("version", "1.0");
//        // TODO store your settings
//    }

//    void readProperties(java.util.Properties p) {
//        String version = p.getProperty("version");
//        // TODO read your settings according to their version
//    }
}