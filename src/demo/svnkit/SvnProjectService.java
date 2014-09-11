/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo.svnkit;

import java.io.File;
import java.util.ResourceBundle;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNWCUtil;
import org.tmatesoft.svn.examples.wc.CommitEventHandler;
import org.tmatesoft.svn.examples.wc.UpdateEventHandler;

public class SvnProjectService {

    private final Logger logger = Logger.getLogger(SvnProjectService.class);

    // 项目的存放位置
    private String workspace = null;
    private String templete = null;

    private final ResourceBundle rb = ResourceBundle.getBundle("application");

    private String username = null;
    private String password = null;

    private void init() {
        String webapp = SvnProjectService.class.getClassLoader().getResource("").toString().substring(6);
        webapp = webapp.substring(0, webapp.indexOf(rb.getString("project.webapp")) + 10);
        username = rb.getString("svn.username");
        password = rb.getString("svn.password");
        workspace = rb.getString("project.svn.path");
        templete = webapp + File.separator + rb.getString("project.template");
    }

    public SvnProjectService() {
        super();
        init();
    }

    /**
     * 从SVN更新项目到work copy
     *
     * @param project Project
     * @return
     */
    public boolean updateProjectFromSvn(Project project) {
        if (null == project || null == project.getSvnUrl()) {
            return false;
        }

        SVNClientManager clientManager = SVNUtil.authSvn(project.getSvnUrl(), username, password);
        if (null == clientManager) {
//            logger.error("SVN login error! >>> url:" + project.getSvnUrl()
//                    + " username:" + username + " password:" + password);
            System.out.println("SVN login error! >>> url:" + project.getSvnUrl()
                    + " username:" + username + " password:" + password);
            return false;
        }

        // 注册一个更新事件处理器
        clientManager.getCommitClient().setEventHandler(new UpdateEventHandler());

        SVNURL repositoryURL = null;
        try {
            repositoryURL = SVNURL.parseURIEncoded(project.getSvnUrl());//SVNURL.parseURIEncoded(project.getSvnUrl()).appendPath("trunk/" + project.getName(), false);
        } catch (SVNException ex) {
            java.util.logging.Logger.getLogger(SvnProjectService.class.getName()).log(Level.SEVERE, null, ex);
        }

        File ws = new File(new File(workspace), project.getName());
        if (!SVNWCUtil.isVersionedDirectory(ws)) {
            long l = SVNUtil.checkout(clientManager, repositoryURL, SVNRevision.HEAD, new File(workspace), SVNDepth.INFINITY);
            System.out.println("SVNUtil.checkout :" + l);
        } else {
            long l = SVNUtil.update(clientManager, ws, SVNRevision.HEAD, SVNDepth.INFINITY);
            System.out.println("SVNUtil.update :" + l);
        }
        return true;
    }

    public String buildSvnUrl() {
        //SVNURL.parseURIEncoded(project.getSvnUrl()).appendPath("trunk/" + project.getName(), false);
        return "";
    }

    /**
     * 提交项目到SVN
     *
     * @param project Project
     * @return
     */
    public boolean commitProjectToSvn(Project project) {
        if (null == project || null == project.getSvnUrl()) {
            return false;
        }

        SVNClientManager clientManager = SVNUtil.authSvn(project.getSvnUrl(), username, password);
        if (null == clientManager) {
//            logger.error("SVN login error! >>> url:" + project.getSvnUrl()
//                    + " username:" + username + " password:" + password);
            System.out.println("SVN login error! >>> url:" + project.getSvnUrl()
                    + " username:" + username + " password:" + password);
            return false;
        }

        // 注册一个提交事件处理器
        clientManager.getCommitClient().setEventHandler(new CommitEventHandler());

        SVNURL repositoryURL = null;
        try {
            repositoryURL = SVNURL.parseURIEncoded(project.getSvnUrl());//SVNURL.parseURIEncoded(project.getSvnUrl()).appendPath("trunk", false);
        } catch (SVNException ex) {
            java.util.logging.Logger.getLogger(SvnProjectService.class.getName()).log(Level.SEVERE, null, ex);
        }

        String wsDir = workspace + File.separator + project.getName();
        File ws = new File(wsDir);

        if (!SVNUtil.isWorkingCopy(ws)) {
            SVNUtil.checkout(clientManager, repositoryURL, SVNRevision.HEAD, new File(workspace), SVNDepth.EMPTY);
        }
        if (!SVNWCUtil.isVersionedDirectory(ws)) {
            SVNUtil.addEntry(clientManager, ws);
        }
        String commitMessage = "Commit '" + wsDir + "' to '" + repositoryURL.getPath() + "'";

        SVNCommitInfo SVNCommitInfoObj = SVNUtil.commit(clientManager, ws, false, commitMessage);
//        System.out.println("SVNCommitInfoObj:" + SVNCommitInfoObj.toString());
        return true;
    }

    public static void main(String[] args) {
        SvnProjectService svnService = new SvnProjectService();
        Project project = new Project("dev_greenhouse", "svn://svndev.shinezone.com/dev/Dessert_Shop/facebook/branches/dev_greenhouse/");
        svnService.updateProjectFromSvn(project);//update 
        
        
        
//        svnService.commitProjectToSvn(project);
    }

}
