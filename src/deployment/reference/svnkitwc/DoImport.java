/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deployment.reference.svnkitwc;

import java.io.File;
import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.wc.ISVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNWCUtil;
/*此类执行的操作是把本地目录下的内容上传到版本库中。*/

public class DoImport {
//声明SVN客户端管理类 

    private static SVNClientManager ourClientManager;

    public static void main(String[] args) throws Exception {
//初始化支持svn://协议的库。 必须先执行此操作。 
        SVNRepositoryFactoryImpl.setup();
//相关变量赋值 
        SVNURL repositoryURL = null;
        try {
            repositoryURL = SVNURL.parseURIEncoded("svn://localhost/");
        } catch (SVNException e) {
// 
        }
        String name = "test";
        String password = "test";
        ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
//实例化客户端管理类 
        ourClientManager = SVNClientManager.newInstance(
                (DefaultSVNOptions) options, name, password);
//要把此目录中的内容导入到版本库 
        File impDir = new File("e:/svntest/impDir");
//执行导入操作 
        SVNCommitInfo commitInfo = ourClientManager.getCommitClient().doImport(impDir, repositoryURL,
                "import operation!", null, false, false, SVNDepth.INFINITY);
        System.out.println(commitInfo.toString());

    }

}
