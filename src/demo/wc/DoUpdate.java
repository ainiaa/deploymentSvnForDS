/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo.wc;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.wc.ISVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNStatusClient;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

/*此类用来把版本库中文件的某个版本更新到工作副本中*/
public class DoUpdate {
//声明SVN客户端管理类 

    private static SVNClientManager ourClientManager;

    public static void main(String[] args) throws Exception {
        doUpdate();
    }

    public static void doUpdate() {
        //初始化支持svn://协议的库。 必须先执行此操作。 
        SVNRepositoryFactoryImpl.setup();
        //相关变量赋值 
        SVNURL repositoryURL = null;
        try {
            repositoryURL = SVNURL.parseURIEncoded("svn://svndev.shinezone.com/dev/Dessert_Shop/facebook/branches/dev_greenhouse");
        } catch (SVNException e) {
        }
        String name = "liuwenyuan";
        String password = "RD56FGvderqpH";

        ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
        //实例化客户端管理类 
        ourClientManager = SVNClientManager.newInstance(
                (DefaultSVNOptions) options, name, password);
        //要更新的文件 
        File updateFile = new File("D:\\www\\dessert\\code\\branches\\dev_greenhouse\\j7\\app\\ACTION\\GreenHouse\\");
        //获得updateClient的实例 
        SVNUpdateClient updateClient = ourClientManager.getUpdateClient();
        updateClient.setIgnoreExternals(false);
        //执行更新操作 
        long versionNum;
        try {
            versionNum = updateClient.doUpdate(updateFile, SVNRevision.HEAD, SVNDepth.INFINITY, false, false);
            System.out.println("工作副本更新后的版本：" + versionNum);
        } catch (SVNException ex) {
            Logger.getLogger(DoUpdate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
