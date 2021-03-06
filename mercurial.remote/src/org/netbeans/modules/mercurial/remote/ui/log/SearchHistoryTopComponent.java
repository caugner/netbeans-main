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
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2009 Sun
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
package org.netbeans.modules.mercurial.remote.ui.log;

import java.awt.event.ActionEvent;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle;
import org.openide.util.HelpCtx;

import java.util.*;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import org.netbeans.modules.mercurial.remote.HgModuleConfig;
import org.netbeans.modules.mercurial.remote.Mercurial;
import org.netbeans.modules.mercurial.remote.ui.branch.BranchSelector;
import org.netbeans.modules.mercurial.remote.ui.branch.HgBranch;
import org.netbeans.modules.mercurial.remote.ui.diff.DiffSetupSource;
import org.netbeans.modules.mercurial.remote.ui.diff.Setup;
import org.netbeans.modules.versioning.core.api.VCSFileProxy;
import org.netbeans.modules.versioning.util.Utils;

/**
 * @author Maros Sandor
 */
public class SearchHistoryTopComponent extends TopComponent implements DiffSetupSource {

    private SearchHistoryPanel shp;
    private SearchCriteriaPanel scp;

    public SearchHistoryTopComponent() {
        getAccessibleContext().setAccessibleName(NbBundle.getMessage(SearchHistoryTopComponent.class, "ACSN_SearchHistoryT_Top_Component")); // NOI18N
        getAccessibleContext().setAccessibleDescription(NbBundle.getMessage(SearchHistoryTopComponent.class, "ACSD_SearchHistoryT_Top_Component")); // NOI18N
    }
    
    public SearchHistoryTopComponent(VCSFileProxy[] files, String branchName, String revision) {
        this();
        initComponents(files, revision, revision, branchName);
    }

    /**
     * Support for openning file history with a specific DiffResultsView
     * @param file it's history shall be shown
     * @param fac factory creating a specific DiffResultsView - just override its createDiffResultsView method
     */
    SearchHistoryTopComponent(VCSFileProxy file, DiffResultsViewFactory fac) {
        this();
        initComponents(new VCSFileProxy[] {file}, null, null, ""); //NOI18N
        shp.setDiffResultsViewFactory(fac);
    }

    public void search (boolean showCriteria) {        
        shp.executeSearch();
        shp.setSearchCriteria(showCriteria);
    }
    
    public void searchOut() {  
        shp.setOutSearch();
        scp.setTo("");
        shp.setSearchCriteria(false);
        shp.executeSearch();
    }

    public void searchIncoming() {  
        shp.setIncomingSearch();
        scp.setTo("");
        shp.setSearchCriteria(false);
        shp.executeSearch();
    }

    void activateDiffView (boolean selectFirstRevision) {
        shp.activateDiffView(selectFirstRevision);
    }

    private void initComponents(final VCSFileProxy[] roots, String from, String to, String branchName) {
        setLayout(new BorderLayout());
        scp = new SearchCriteriaPanel(roots);
        if (from != null){ 
            scp.setFrom(from);
        }
        if (to != null){
            scp.setTo(to);
        }
        shp = new SearchHistoryPanel(roots, scp);
        add(shp);
        shp.setCurrentBranch(branchName);
        if (!HgBranch.DEFAULT_NAME.equals(branchName) && HgModuleConfig.getDefault(roots[0]).isSearchOnBranchEnabled(branchName)) {
            // only for branches other than default
            scp.setBranch(branchName);
        }
        if (roots.length > 0) {
            scp.btnSelectBranch.addActionListener(new BranchSelectorOpener(roots, scp));
        }
    }

    @Override
    public int getPersistenceType(){
       return TopComponent.PERSISTENCE_NEVER;
    }
    
    @Override
    protected void componentClosed() {
       shp.windowClosed();
       super.componentClosed();
    }
    
    @Override
    protected String preferredID(){
        if (shp.isIncomingSearch()) {
            return "Hg.IncomingSearchHistoryTopComponent";    // NOI18N
        } else if (shp.isOutSearch()) {
            return "Hg.OutSearchHistoryTopComponent";    // NOI18N
        }
        return "Hg.SearchHistoryTopComponent";    // NOI18N
    }

    @Override
    public HelpCtx getHelpCtx() {
        return new HelpCtx(getClass());
    }

    @Override
    public Collection<Setup> getSetups() {
        return shp.getSetups();
    }

    @Override
    public String getSetupDisplayName() {
        return getDisplayName();
    }
    
    /**
     * Provides an initial diff view. To display a specific one, override createDiffResultsView.
     */
    public static class DiffResultsViewFactory {
        DiffResultsView createDiffResultsView(SearchHistoryPanel panel, List<RepositoryRevision> results) {
            return new DiffResultsView(panel, results);
        }
    }
    
    private static class BranchSelectorOpener implements ActionListener {
        private final SearchCriteriaPanel scp;
        private final VCSFileProxy root;

        public BranchSelectorOpener (VCSFileProxy[] roots, SearchCriteriaPanel scp) {
            this.scp = scp;
            this.root = roots[0];
        }
        
        @Override
        public void actionPerformed (ActionEvent e) {
            scp.btnSelectBranch.setEnabled(false);
            Utils.postParallel(new Runnable() {
                @Override
                public void run () {
                    final String branchName;
                    VCSFileProxy repoRoot = Mercurial.getInstance().getRepositoryRoot(root);
                    if (repoRoot == null) {
                        branchName = null;
                    } else {
                        BranchSelector selector = new BranchSelector(repoRoot);
                        if (selector.showGeneralDialog()) {
                            branchName = selector.getBranchName();
                        } else {
                            branchName = null;
                        }
                    }
                    EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run () {
                            scp.btnSelectBranch.setEnabled(true);
                            if (branchName != null) {
                                scp.setBranch(branchName);
                            }
                        }
                    });
                }
            }, 0);
        }
    }
}
