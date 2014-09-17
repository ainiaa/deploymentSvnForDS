/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deployment.reference.svnkitwc;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.wc.ISVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNDiffClient;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNWCUtil;
/*此类用来比较某个文件两个版本的差异*/

public class DoDiff {
//声明SVN客户端管理类 

    private static SVNClientManager ourClientManager;

    public static void main(String[] args) throws Exception {
//初始化支持svn://协议的库。 必须先执行此操作。 
        SVNRepositoryFactoryImpl.setup();
//相关变量赋值 
        SVNURL repositoryURL = null;//在此例中用不上。 
        try {
            repositoryURL = SVNURL.parseURIEncoded("svn://svndev.shinezone.com/dev/Dessert_Shop/facebook/branches/dev_greenhouse");
        } catch (SVNException e) {
// 
        }
        String name = "liuwenyuan";
        String password = "RD56FGvderqpH";
        ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
//实例化客户端管理类 
        ourClientManager = SVNClientManager.newInstance(
                (DefaultSVNOptions) options, name, password);
//要比较的文件 
        File compFile = new File("D:\\www\\dessert\\code\\branches\\dev_greenhouse\\j7\\app\\ACTION\\GreenHouse\\UsePropACTION.php");
//获得SVNDiffClient类的实例。 
        SVNDiffClient diff = ourClientManager.getDiffClient();
//保存比较结果的输出流 
        BufferedOutputStream result = new BufferedOutputStream(new FileOutputStream("E:/result.txt"));
 //比较compFile文件的SVNRevision.WORKING版本和 SVNRevision.HEAD版本的差异，结果保存在E:/result.txt文件中。
        //SVNRevision.WORKING版本指工作副本中当前内容的版本，SVNRevision.HEAD版本指的是版本库中最新的版本。 
        diff.doDiff(compFile, SVNRevision.HEAD, SVNRevision.WORKING, SVNRevision.HEAD, SVNDepth.INFINITY, true, result, null);
        result.close();
        System.out.println("比较的结果保存在E:/result.txt文件中！");

    }

}