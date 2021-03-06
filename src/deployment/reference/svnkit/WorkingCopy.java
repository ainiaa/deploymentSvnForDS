/*
 * ====================================================================
 * Copyright (c) 2004-2008 TMate Software Ltd.  All rights reserved.
 *
 * This software is licensed as described in the file COPYING, which
 * you should have received as part of this distribution.  The terms
 * are also available at http://svnkit.com/license.html
 * If newer versions of this license are posted there, you may use a
 * newer version instead, at your option.
 * ====================================================================
 */
package deployment.reference.svnkit;

import deployment.reference.tmatesoftexampleswc.WCEventHandler;
import deployment.reference.tmatesoftexampleswc.StatusHandler;
import deployment.reference.tmatesoftexampleswc.InfoHandler;
import deployment.reference.tmatesoftexampleswc.CommitEventHandler;
import deployment.reference.tmatesoftexampleswc.UpdateEventHandler;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.util.SVNPathUtil;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.wc.ISVNEventHandler;
import org.tmatesoft.svn.core.wc.ISVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNCopyClient;
import org.tmatesoft.svn.core.wc.SVNCopySource;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

public class WorkingCopy {

    private static SVNClientManager ourClientManager;
    private static ISVNEventHandler myCommitEventHandler;
    private static ISVNEventHandler myUpdateEventHandler;
    private static ISVNEventHandler myWCEventHandler;
    private static Map<String, String> logMap;
    private static int errCount = 0;

    public static void main(String[] args) throws SVNException {
        /*
         * Initializes the library (it must be done before ever using the library 
         * itself)
         */
        setupLibrary();
        logMap = new HashMap<>();

        SVNURL repositoryURL = null;
        try {
            repositoryURL = SVNURL.parseURIEncoded("svn://svndev.shinezone.com/dev/Dessert_Shop/facebook/branches/");
        } catch (SVNException e) {
            //
        }
        String name = "liuwenyuan";
        String password = "RD56FGvderqpH";
        String myWorkingCopyPath = "/MyWorkingCopy";

        String importDir = "/svnkitImportDir";
        String importFile = importDir + "/importFile.txt";
        String importFileText = "This unversioned file is imported into a repository";

        String newDir = "/svnkitNewDir";
        String newFile = newDir + "/newFile.txt";
        String fileText = "This is a new file added to the working copy";

        if (args != null) {
            try {
                repositoryURL = (args.length >= 1) ? SVNURL.parseURIEncoded(args[0]) : repositoryURL;
            } catch (SVNException e) {
                System.err.println("'" + args[0] + "' is not a valid URL");
                WorkingCopy.errCount++;
                System.exit(1);//
            }
            /*
             * Obtains a path to be a working copy root directory
             */
            myWorkingCopyPath = (args.length >= 2) ? args[1] : myWorkingCopyPath;
            /*
             * Obtains an account name 
             */
            name = (args.length >= 3) ? args[2] : name;
            /*
             * Obtains a password
             */
            password = (args.length >= 4) ? args[3] : password;
        }

        /*
         * That's where a new directory will be created
         */
        SVNURL url = repositoryURL.appendPath("MyRepos", false);
        /*
         * That's where '/MyRepos' will be copied to (branched)
         */
        SVNURL copyURL = repositoryURL.appendPath("MyReposCopy", false);
        /*
         * That's where a local directory will be imported into.
         * Note that it's not necessary that the '/importDir' directory must already
         * exist - the SVN repository server will take care of creating it. 
         */
        SVNURL importToURL = url.appendPath(importDir, false);

        myCommitEventHandler = new CommitEventHandler();

        myUpdateEventHandler = new UpdateEventHandler();

        myWCEventHandler = new WCEventHandler();

        ISVNOptions options = SVNWCUtil.createDefaultOptions(true);

        ourClientManager = SVNClientManager.newInstance((DefaultSVNOptions) options, name, password);

        ourClientManager.getCommitClient().setEventHandler(myCommitEventHandler);

        ourClientManager.getUpdateClient().setEventHandler(myUpdateEventHandler);

        ourClientManager.getWCClient().setEventHandler(myWCEventHandler);

        long committedRevision = -1;
        System.out.println("Making a new directory at '" + url + "'...");
        String commitMessage;
        try {
            /*
             * creates a new version comtrolled directory in a repository and 
             * displays what revision the repository was committed to
             */
            commitMessage = "making a new directory at '" + url + "'";
            committedRevision = makeDirectory(url, commitMessage).getNewRevision();
        } catch (SVNException svne) {
            error("error while making a new directory at '" + url + "'", svne);
        }
        System.out.println("Committed to revision " + committedRevision);
        System.out.println();

        File anImportDir = new File(importDir);
        File anImportFile = new File(anImportDir, SVNPathUtil.tail(importFile));
        /*
         * creates a new local directory - './importDir' and a new file - 
         * './importDir/importFile.txt' that will be imported into the repository
         * into the '/MyRepos/importDir' directory 
         */
        createLocalDir(anImportDir, new File[]{anImportFile}, new String[]{importFileText});

        System.out.println("Importing a new directory into '" + importToURL + "'...");
        try {
            /*
             * recursively imports an unversioned directory into a repository 
             * and displays what revision the repository was committed to
             */
//            committedRevision = importDirectory(anImportDir, importToURL, "importing a new directory '" + anImportDir.getAbsolutePath() + "'", isRecursive).getNewRevision();
            commitMessage = "importing a new directory '" + anImportDir.getAbsolutePath() + "'";
            committedRevision = importDirectory(anImportDir, importToURL, commitMessage).getNewRevision();
        } catch (SVNException svne) {
            error("error while importing a new directory '" + anImportDir.getAbsolutePath() + "' into '" + importToURL + "'", svne);
        }
        System.out.println("Committed to revision " + committedRevision);
        System.out.println();

        /*
         * creates a local directory where the working copy will be checked out into
         */
        File wcDir = new File(myWorkingCopyPath);
        if (wcDir.exists()) {
            error("the destination directory '"
                    + wcDir.getAbsolutePath() + "' already exists!", null);
        }
        wcDir.mkdirs();

        System.out.println("Checking out a working copy from '" + url + "'...");
        try {
            /*
             * recursively checks out a working copy from url into wcDir.
             * SVNRevision.HEAD means the latest revision to be checked out. 
             */
//            checkout(url, SVNRevision.HEAD, wcDir, true);
            checkout(url, SVNRevision.HEAD, wcDir);
        } catch (SVNException svne) {
            error("error while checking out a working copy for the location '"
                    + url + "'", svne);
        }
        System.out.println();

        /*
         * recursively displays info for wcDir at the current working revision 
         * in the manner of 'svn info -R' command
         */
        try {
            showInfo(wcDir, SVNRevision.WORKING, true);
        } catch (SVNException svne) {
            error("error while recursively getting info for the working copy at'"
                    + wcDir.getAbsolutePath() + "'", svne);
        }
        System.out.println();

        File aNewDir = new File(wcDir, newDir);
        File aNewFile = new File(aNewDir, SVNPathUtil.tail(newFile));
        /*
         * creates a new local directory - 'wcDir/newDir' and a new file - 
         * '/MyWorkspace/newDir/newFile.txt' 
         */
        createLocalDir(aNewDir, new File[]{aNewFile}, new String[]{fileText});

        System.out.println("Recursively scheduling a new directory '" + aNewDir.getAbsolutePath() + "' for addition...");
        try {
            /*
             * recursively schedules aNewDir for addition
             */
//            addEntry(aNewDir);
            addEntry(aNewDir, false);
        } catch (SVNException svne) {
            error("error while recursively adding the directory '"
                    + aNewDir.getAbsolutePath() + "'", svne);
        }
        System.out.println();

        boolean isRecursive = true;
        boolean isRemote = true;
        boolean isReportAll = false;
        boolean isIncludeIgnored = true;
        boolean isCollectParentExternals = false;
        System.out.println("Status for '" + wcDir.getAbsolutePath() + "':");
        try {
            /*
             * gets and shows status information for the WC directory.
             * status will be recursive on wcDir, will also cover the repository, 
             * won't cover unmodified entries, will disregard 'svn:ignore' property 
             * ignores (if any), will ignore externals definitions.
             */
            showStatus(wcDir, isRecursive, isRemote, isReportAll,
                    isIncludeIgnored, isCollectParentExternals);
        } catch (SVNException svne) {
            error("error while recursively performing status for '"
                    + wcDir.getAbsolutePath() + "'", svne);
        }
        System.out.println();

        System.out.println("Updating '" + wcDir.getAbsolutePath() + "'...");
        try {
            /*
             * recursively updates wcDir to the latest revision (SVNRevision.HEAD)
             */
//            update(wcDir, SVNRevision.HEAD, true);
            update(wcDir, SVNRevision.HEAD, SVNDepth.INFINITY, false, false);
        } catch (SVNException svne) {
            error("error while recursively updating the working copy at '"
                    + wcDir.getAbsolutePath() + "'", svne);
        }
        System.out.println("");

        System.out.println("Committing changes for '" + wcDir.getAbsolutePath() + "'...");
        try {
            /*
             * commits changes in wcDir to the repository with not leaving items 
             * locked (if any) after the commit succeeds; this will add aNewDir & 
             * aNewFile to the repository. 
             */
            commitMessage = "'/newDir' with '/newDir/newFile.txt' were added";
            committedRevision = commit(wcDir, false, commitMessage, true).getNewRevision();
        } catch (SVNException svne) {
            error("error while committing changes to the working copy at '"
                    + wcDir.getAbsolutePath()
                    + "'", svne);
        }
        System.out.println("Committed to revision " + committedRevision);
        System.out.println();

        System.out
                .println("Locking (with stealing if the entry is already locked) '"
                        + aNewFile.getAbsolutePath() + "'.");
//        //不再lock该文件  已测试
//        try {
//            /*
//             * locks aNewFile with stealing (if it has been already locked by someone
//             * else), providing a lock comment
//             */
//            lock(aNewFile, true, "locking '/newDir/newFile.txt'");
//
//        } catch (SVNException svne) {
//            error("error while locking the working copy file '"
//                    + aNewFile.getAbsolutePath() + "'", svne);
//        }
//        System.out.println();

        System.out.println("Status for '" + wcDir.getAbsolutePath() + "':");
        try {
            /*
             * displays status once again to see the file is really locked
             */
            showStatus(wcDir, isRecursive, isRemote, isReportAll,
                    isIncludeIgnored, isCollectParentExternals);
        } catch (SVNException svne) {
            error("error while recursively performing status for '"
                    + wcDir.getAbsolutePath() + "'", svne);
        }
        System.out.println();

        System.out.println("Copying '" + url + "' to '" + copyURL + "'...");
        try {
            /*
             * makes a branch of url at copyURL - that is URL->URL copying
             * with history
             */
            commitMessage = "remotely copying '" + url + "' to '" + copyURL + "'";
            committedRevision = copy(url, copyURL, false, commitMessage).getNewRevision();
        } catch (SVNException svne) {
            error("error while copying '" + url + "' to '"
                    + copyURL + "'", svne);
        }
        /*
         * displays what revision the repository was committed to
         */
        System.out.println("Committed to revision " + committedRevision);
        System.out.println();
      
        System.out.println("Switching '" + wcDir.getAbsolutePath() + "' to '"
                + copyURL + "'...");
        try {
            /*
             * recursively switches wcDir to copyURL in the latest revision 
             * (SVNRevision.HEAD)
             */
            switchToURL(wcDir, copyURL, SVNRevision.HEAD, true);
        } catch (SVNException svne) {
            error("error while switching '"
                    + wcDir.getAbsolutePath() + "' to '" + copyURL + "'", svne);
        }
        System.out.println();

        /*
         * recursively displays info for the working copy once again to see
         * it was really switched to a new URL
         */
        try {
            showInfo(wcDir, SVNRevision.WORKING, true);
        } catch (SVNException svne) {
            error("error while recursively getting info for the working copy at'"
                    + wcDir.getAbsolutePath() + "'", svne);
        }
        System.out.println();

        System.out.println("Scheduling '" + aNewDir.getAbsolutePath() + "' for deletion ...");
        try {
            /*
             * schedules aNewDir for deletion (with forcing)
             */
            delete(aNewDir, true);
        } catch (SVNException svne) {
            error("error while schediling '"
                    + wcDir.getAbsolutePath() + "' for deletion", svne);
        }
        System.out.println();

        System.out.println("Status for '" + wcDir.getAbsolutePath() + "':");
        try {
            /*
             * recursively displays status once more to see whether aNewDir
             * was really scheduled for deletion  
             */
            showStatus(wcDir, isRecursive, isRemote, isReportAll,
                    isIncludeIgnored, isCollectParentExternals);
        } catch (SVNException svne) {
            error("error while recursively performing status for '"
                    + wcDir.getAbsolutePath() + "'", svne);
        }
        System.out.println();

        System.out.println("Committing changes for '" + wcDir.getAbsolutePath() + "'...");
        try {
            /*
             * lastly commits changes in wcDir to the repository; all items that
             * were locked by the user (if any) will be unlocked after the commit 
             * succeeds; this commit will remove aNewDir from the repository. 
             */
            committedRevision = commit(
                    wcDir,
                    false,
                    "deleting '" + aNewDir.getAbsolutePath()
                    + "' from the filesystem as well as from the repository", true).getNewRevision();
        } catch (SVNException svne) {
            error("error while committing changes to the working copy '"
                    + wcDir.getAbsolutePath()
                    + "'", svne);
        }
        System.out.println("Committed to revision " + committedRevision);
        System.exit(0);
    }

    /*
     * Initializes the library to work with a repository via 
     * different protocols.
     */
    private static void setupLibrary() {
        /*
         * For using over http:// and https://
         */
        DAVRepositoryFactory.setup();
        /*
         * For using over svn:// and svn+xxx://
         */
        SVNRepositoryFactoryImpl.setup();

        /*
         * For using over file:///
         */
        FSRepositoryFactory.setup();
    }

    /*
     * Creates a new version controlled directory (doesn't create any intermediate
     * directories) right in a repository. Like 'svn mkdir URL -m "some comment"' 
     * command. It's done by invoking 
     * 
     * SVNCommitClient.doMkDir(SVNURL[] urls, String commitMessage) 
     * 
     * which takes the following parameters:
     * 
     * urls - an array of URLs that are to be created;
     * 
     * commitMessage - a commit log message since a URL-based directory creation is 
     * immediately committed to a repository.
     */
    private static SVNCommitInfo makeDirectory(SVNURL url, String commitMessage) throws SVNException {
        /*
         * Returns SVNCommitInfo containing information on the new revision committed 
         * (revision number, etc.) 
         */
        return ourClientManager.getCommitClient().doMkDir(new SVNURL[]{url}, commitMessage);
    }

    /*
     * Imports an unversioned directory into a repository location denoted by a
     * destination URL (all necessary parent non-existent paths will be created 
     * automatically). This operation commits the repository to a new revision. 
     * Like 'svn import PATH URL (-N) -m "some comment"' command. It's done by 
     * invoking 
     * 
     * SVNCommitClient.doImport(File path, SVNURL dstURL, String commitMessage, boolean recursive) 
     * 
     * which takes the following parameters:
     * 
     * path - a local unversioned directory or singal file that will be imported into a 
     * repository;
     * 
     * dstURL - a repository location where the local unversioned directory/file will be 
     * imported into; this URL path may contain non-existent parent paths that will be 
     * created by the repository server;
     * 
     * commitMessage - a commit log message since the new directory/file are immediately
     * created in the repository;
     * 
     * recursive - if true and path parameter corresponds to a directory then the directory
     * will be added with all its child subdirictories, otherwise the operation will cover
     * only the directory itself (only those files which are located in the directory).  
     */
    private static SVNCommitInfo importDirectory(File localPath, SVNURL dstURL, String commitMessage, boolean isRecursive) throws SVNException {
        /*
         * Returns SVNCommitInfo containing information on the new revision committed 
         * (revision number, etc.) 
         */
        return ourClientManager.getCommitClient().doImport(localPath, dstURL, commitMessage, isRecursive);
//java.io.File path,
//                              SVNURL dstURL,
//                              java.lang.String commitMessage,
//                              SVNProperties revisionProperties,
//                              boolean useGlobalIgnores,
//                              boolean ignoreUnknownNodeTypes,
//                              SVNDepth depth
    }

    private static SVNCommitInfo importDirectory(File localPath, SVNURL dstURL, String commitMessage, SVNProperties revisionProperties, boolean useGlobalIgnores, boolean ignoreUnknownNodeTypes, SVNDepth depth) throws SVNException {
        /*
         * Returns SVNCommitInfo containing information on the new revision committed 
         * (revision number, etc.) 
         */
        return ourClientManager.getCommitClient().doImport(localPath, dstURL, commitMessage, revisionProperties, useGlobalIgnores, ignoreUnknownNodeTypes, depth);
    }

    private static SVNCommitInfo importDirectory(File localPath, SVNURL dstURL, String commitMessage) throws SVNException {
        /*
         * Returns SVNCommitInfo containing information on the new revision committed 
         * (revision number, etc.) 
         */
        return ourClientManager.getCommitClient().doImport(localPath, dstURL, commitMessage, new SVNProperties(), false, false, SVNDepth.INFINITY);
    }
    /*
     * Committs changes in a working copy to a repository. Like 
     * 'svn commit PATH -m "some comment"' command. It's done by invoking 
     * 
     * SVNCommitClient.doCommit(File[] paths, boolean keepLocks, String commitMessage, 
     * boolean force, boolean recursive) 
     * 
     * which takes the following parameters:
     * 
     * paths - working copy paths which changes are to be committed;
     * 
     * keepLocks - if true then doCommit(..) won't unlock locked paths; otherwise they will
     * be unlocked after a successful commit; 
     * 
     * commitMessage - a commit log message;
     * 
     * force - if true then a non-recursive commit will be forced anyway;  
     * 
     * recursive - if true and a path corresponds to a directory then doCommit(..) recursively 
     * commits changes for the entire directory, otherwise - only for child entries of the 
     * directory;
     */

    private static SVNCommitInfo commit(File wcPath, boolean keepLocks, String commitMessage)
            throws SVNException {
        /*
         * Returns SVNCommitInfo containing information on the new revision committed 
         * (revision number, etc.) 
         */
        return ourClientManager.getCommitClient().doCommit(new File[]{wcPath}, keepLocks,
                commitMessage, false, true);
    }

    private static SVNCommitInfo commit(File wcPath, boolean keepLocks, String commitMessage, boolean recurse)
            throws SVNException {
        /*
         * Returns SVNCommitInfo containing information on the new revision committed 
         * (revision number, etc.) 
         */
        //doCommit(java.io.File[] paths, boolean keepLocks, java.lang.String commitMessage, SVNProperties revisionProperties, java.lang.String[] changelists, boolean keepChangelist, boolean force, SVNDepth depth)
        //doCommit(SVNCommitPacket[] commitPackets, boolean keepLocks, java.lang.String commitMessage)
        return ourClientManager.getCommitClient().doCommit(
                new File[]{wcPath}, keepLocks, commitMessage, null,
                null, false, false, SVNDepth.fromRecurse(true));
//        return ourClientManager.getCommitClient().doCommit(new File[]{wcPath}, keepLocks,
//                commitMessage, false, true);
    }

    /*
     * Checks out a working copy from a repository. Like 'svn checkout URL[@REV] PATH (-r..)'
     * command; It's done by invoking 
     * 
     * SVNUpdateClient.doCheckout(SVNURL url, File dstPath, SVNRevision pegRevision, 
     * SVNRevision revision, boolean recursive)
     * 
     * which takes the following parameters:
     * 
     * url - a repository location from where a working copy is to be checked out;
     * 
     * dstPath - a local path where the working copy will be fetched into;
     * 
     * pegRevision - an SVNRevision representing a revision to concretize
     * url (what exactly URL a user means and is sure of being the URL he needs); in other
     * words that is the revision in which the URL is first looked up;
     * 
     * revision - a revision at which a working copy being checked out is to be; 
     * 
     * recursive - if true and url corresponds to a directory then doCheckout(..) recursively 
     * fetches out the entire directory, otherwise - only child entries of the directory;   
     */
    private static long checkout(SVNURL url,
            SVNRevision revision, File dstPath, boolean isRecursive)
            throws SVNException {

        SVNUpdateClient updateClient = ourClientManager.getUpdateClient();
        /*
         * sets externals not to be ignored during the checkout
         */
        updateClient.setIgnoreExternals(false);
        /*
         * returns the number of the revision at which the working copy is 
         */
        return updateClient.doCheckout(url, dstPath, revision, revision, isRecursive);
    }

    private static long checkout(SVNURL url, SVNRevision pegRevision,
            SVNRevision revision, SVNDepth depth, File dstPath, boolean allowUnversionedObstructions)
            throws SVNException {

        SVNUpdateClient updateClient = ourClientManager.getUpdateClient();
        /*
         * sets externals not to be ignored during the checkout
         */
        updateClient.setIgnoreExternals(false);
        /*
         * returns the number of the revision at which the working copy is 
         */
        return updateClient.doCheckout(url, dstPath, pegRevision, revision, depth, allowUnversionedObstructions);
    }

    private static long checkout(SVNURL url, SVNRevision revision, File dstPath)
            throws SVNException {

        SVNUpdateClient updateClient = ourClientManager.getUpdateClient();
        /*
         * sets externals not to be ignored during the checkout
         */
        updateClient.setIgnoreExternals(false);
        /*
         * returns the number of the revision at which the working copy is 
         */
        return updateClient.doCheckout(url, dstPath, SVNRevision.UNDEFINED, revision, SVNDepth.INFINITY, false);
    }

//

    /*
     * Updates a working copy (brings changes from the repository into the working copy). 
     * Like 'svn update PATH' command; It's done by invoking 
     * 
     * SVNUpdateClient.doUpdate(File file, SVNRevision revision, boolean recursive) 
     * 
     * which takes the following parameters:
     * 
     * file - a working copy entry that is to be updated;
     * 
     * revision - a revision to which a working copy is to be updated;
     * 
     * recursive - if true and an entry is a directory then doUpdate(..) recursively 
     * updates the entire directory, otherwise - only child entries of the directory;   
     */
    private static long update(File wcPath,
            SVNRevision updateToRevision, boolean isRecursive)
            throws SVNException {

        SVNUpdateClient updateClient = ourClientManager.getUpdateClient();
        /*
         * sets externals not to be ignored during the update
         */
        updateClient.setIgnoreExternals(false);
        /*
         * returns the number of the revision wcPath was updated to
         */

        return updateClient.doUpdate(wcPath, updateToRevision, isRecursive);
    }

    private static long update(File wcPath,
            SVNRevision updateToRevision, SVNDepth depth)
            throws SVNException {

        SVNUpdateClient updateClient = ourClientManager.getUpdateClient();
        /*
         * sets externals not to be ignored during the update
         */
        updateClient.setIgnoreExternals(false);
        /*
         * returns the number of the revision wcPath was updated to
         */
        return updateClient.doUpdate(wcPath, updateToRevision, depth, false, false);
    }

    private static long update(File wcPath,
            SVNRevision updateToRevision, SVNDepth depth, boolean allowUnversionedObstructions, boolean depthIsSticky)
            throws SVNException {

        SVNUpdateClient updateClient = ourClientManager.getUpdateClient();
        /*
         * sets externals not to be ignored during the update
         */
        updateClient.setIgnoreExternals(false);
        /*
         * returns the number of the revision wcPath was updated to
         */
        return updateClient.doUpdate(wcPath, updateToRevision, depth, allowUnversionedObstructions, depthIsSticky);
    }

    /*
     * Updates a working copy to a different URL. Like 'svn switch URL' command.
     * It's done by invoking 
     * 
     * SVNUpdateClient.doSwitch(File file, SVNURL url, SVNRevision revision, boolean recursive) 
     * 
     * which takes the following parameters:
     * 
     * file - a working copy entry that is to be switched to a new url;
     * 
     * url - a target URL a working copy is to be updated against;
     * 
     * revision - a revision to which a working copy is to be updated;
     * 
     * recursive - if true and an entry (file) is a directory then doSwitch(..) recursively 
     * switches the entire directory, otherwise - only child entries of the directory;   
     */
    private static long switchToURL(File wcPath,
            SVNURL url, SVNRevision updateToRevision, boolean isRecursive)
            throws SVNException {
        SVNUpdateClient updateClient = ourClientManager.getUpdateClient();
        /*
         * sets externals not to be ignored during the switch
         */
        updateClient.setIgnoreExternals(false);
        /*
         * returns the number of the revision wcPath was updated to
         */
        return updateClient.doSwitch(wcPath, url, updateToRevision,
                isRecursive);
    }

    /*
     * Collects status information on local path(s). Like 'svn status (-u) (-N)' 
     * command. It's done by invoking 
     * 
     * SVNStatusClient.doStatus(File path, boolean recursive, 
     * boolean remote, boolean reportAll, boolean includeIgnored, 
     * boolean collectParentExternals, ISVNStatusHandler handler) 
     * 
     * which takes the following parameters:
     * 
     * path - an entry which status info to be gathered;
     * 
     * recursive - if true and an entry is a directory then doStatus(..) collects status 
     * info not only for that directory but for each item inside stepping down recursively;
     * 
     * remote - if true then doStatus(..) will cover the repository (not only the working copy)
     * as well to find out what entries are out of date;
     * 
     * reportAll - if true then doStatus(..) will also include unmodified entries;
     * 
     * includeIgnored - if true then doStatus(..) will also include entries being ignored; 
     * 
     * collectParentExternals - if true then externals definitions won't be ignored;
     * 
     * handler - an implementation of ISVNStatusHandler to process status info per each entry
     * doStatus(..) traverses; such info is collected in an SVNStatus object and
     * is passed to a handler's handleStatus(SVNStatus status) method where an implementor
     * decides what to do with it.  
     */
    private static void showStatus(File wcPath, boolean isRecursive, boolean isRemote, boolean isReportAll,
            boolean isIncludeIgnored, boolean isCollectParentExternals)
            throws SVNException {
        /*
         * StatusHandler displays status information for each entry in the console (in the 
         * manner of the native Subversion command line client)
         */
        ourClientManager.getStatusClient().doStatus(wcPath, isRecursive, isRemote, isReportAll,
                isIncludeIgnored, isCollectParentExternals, new StatusHandler(isRemote));
    }

    /*
     * Collects information on local path(s). Like 'svn info (-R)' command.
     * It's done by invoking 
     * 
     * SVNWCClient.doInfo(File path, SVNRevision revision,
     * boolean recursive, ISVNInfoHandler handler) 
     * 
     * which takes the following parameters:
     * 
     * path - a local entry for which info will be collected;
     * 
     * revision - a revision of an entry which info is interested in; if it's not
     * WORKING then info is got from a repository;
     * 
     * recursive - if true and an entry is a directory then doInfo(..) collects info 
     * not only for that directory but for each item inside stepping down recursively;
     * 
     * handler - an implementation of ISVNInfoHandler to process info per each entry
     * doInfo(..) traverses; such info is collected in an SVNInfo object and
     * is passed to a handler's handleInfo(SVNInfo info) method where an implementor
     * decides what to do with it.     
     */
    private static void showInfo(File wcPath, SVNRevision revision, boolean isRecursive) throws SVNException {
        /*
         * InfoHandler displays information for each entry in the console (in the manner of
         * the native Subversion command line client)
         */
        ourClientManager.getWCClient().doInfo(wcPath, revision, isRecursive, new InfoHandler());
    }

    /*
     * Puts directories and files under version control scheduling them for addition
     * to a repository. They will be added in a next commit. Like 'svn add PATH' 
     * command. It's done by invoking 
     * 
     * SVNWCClient.doAdd(File path, boolean force, 
     * boolean mkdir, boolean climbUnversionedParents, boolean recursive) 
     * 
     * which takes the following parameters:
     * 
     * path - an entry to be scheduled for addition;
     * 
     * force - set to true to force an addition of an entry anyway;
     * 
     * mkdir - if true doAdd(..) creates an empty directory at path and schedules
     * it for addition, like 'svn mkdir PATH' command;
     * 
     * climbUnversionedParents - if true and the parent of the entry to be scheduled
     * for addition is not under version control, then doAdd(..) automatically schedules
     * the parent for addition, too;
     * 
     * recursive - if true and an entry is a directory then doAdd(..) recursively 
     * schedules all its inner dir entries for addition as well. 
     */
    private static void addEntry(File wcPath) throws SVNException {
        ourClientManager.getWCClient().doAdd(wcPath, false, false, false, true);
    }

    private static void addEntry(java.io.File wcPath, boolean force, boolean mkdir, boolean climbUnversionedParents, SVNDepth depth, boolean includeIgnored, boolean makeParents) throws SVNException {
        ourClientManager.getWCClient().doAdd(wcPath, force, mkdir, climbUnversionedParents, depth, includeIgnored, makeParents);
    }

    private static void addEntry(java.io.File wcPath, boolean force, boolean mkdir) throws SVNException {
        ourClientManager.getWCClient().doAdd(wcPath, force, mkdir, true, SVNDepth.INFINITY, false, true);
    }

    private static void addEntry(java.io.File wcPath, boolean force) throws SVNException {
        ourClientManager.getWCClient().doAdd(wcPath, force, true, true, SVNDepth.INFINITY, false, true);
    }

    /*
     * Locks working copy paths, so that no other user can commit changes to them.
     * Like 'svn lock PATH' command. It's done by invoking 
     * 
     * SVNWCClient.doLock(File[] paths, boolean stealLock, String lockMessage) 
     * 
     * which takes the following parameters:
     * 
     * paths - an array of local entries to be locked;
     * 
     * stealLock - set to true to steal the lock from another user or working copy;
     * 
     * lockMessage - an optional lock comment string.
     */
    private static void lock(File wcPath, boolean isStealLock, String lockComment) throws SVNException {
        ourClientManager.getWCClient().doLock(new File[]{wcPath}, isStealLock, lockComment);
    }

    private static void unlock(File wcPath, boolean isStealLock) throws SVNException {
        ourClientManager.getWCClient().doUnlock(new File[]{wcPath}, isStealLock);
    }

    /*
     * Schedules directories and files for deletion from version control upon the next
     * commit (locally). Like 'svn delete PATH' command. It's done by invoking 
     * 
     * SVNWCClient.doDelete(File path, boolean force, boolean dryRun) 
     * 
     * which takes the following parameters:
     * 
     * path - an entry to be scheduled for deletion;
     * 
     * force - a boolean flag which is set to true to force a deletion even if an entry
     * has local modifications;
     * 
     * dryRun - set to true not to delete an entry but to check if it can be deleted;
     * if false - then it's a deletion itself.  
     */
    private static void delete(File wcPath, boolean force) throws SVNException {
        ourClientManager.getWCClient().doDelete(wcPath, force, false);
    }

    /*
     * Duplicates srcURL to dstURL (URL->URL)in a repository remembering history.
     * Like 'svn copy srcURL dstURL -m "some comment"' command. It's done by
     * invoking 
     * 
     * doCopy(SVNURL srcURL, SVNRevision srcRevision, SVNURL dstURL, 
     * boolean isMove, String commitMessage) 
     * 
     * which takes the following parameters:
     * 
     * srcURL - a source URL that is to be copied;
     * 
     * srcRevision - a definite revision of srcURL 
     * 
     * dstURL - a URL where srcURL will be copied; if srcURL & dstURL are both 
     * directories then there are two cases: 
     * a) dstURL already exists - then doCopy(..) will duplicate the entire source 
     * directory and put it inside dstURL (for example, 
     * consider srcURL = svn://localhost/rep/MyRepos, 
     * dstURL = svn://localhost/rep/MyReposCopy, in this case if doCopy(..) succeeds 
     * MyRepos will be in MyReposCopy - svn://localhost/rep/MyReposCopy/MyRepos); 
     * b) dstURL doesn't exist yet - then doCopy(..) will create a directory and
     * recursively copy entries from srcURL into dstURL (for example, consider the same
     * srcURL = svn://localhost/rep/MyRepos, dstURL = svn://localhost/rep/MyReposCopy, 
     * in this case if doCopy(..) succeeds MyRepos entries will be in MyReposCopy, like:
     * svn://localhost/rep/MyRepos/Dir1 -> svn://localhost/rep/MyReposCopy/Dir1...);  
     * 
     * isMove - if false then srcURL is only copied to dstURL what
     * corresponds to 'svn copy srcURL dstURL -m "some comment"'; but if it's true then
     * srcURL will be copied and deleted - 'svn move srcURL dstURL -m "some comment"'; 
     * 
     * commitMessage - a commit log message since URL->URL copying is immediately 
     * committed to a repository.
     */
    private static SVNCommitInfo copy(SVNURL srcURL, SVNURL dstURL,
            boolean isMove, String commitMessage) throws SVNException {
        /*
         * SVNRevision.HEAD means the latest revision.
         * Returns SVNCommitInfo containing information on the new revision committed 
         * (revision number, etc.) 
         */
//        SVNRevision svnr = null;
//        SVNCopySource svncs = new SVNCopySource(svnr, SVNRevision.HEAD, srcURL);
//        SVNCopySource[] svncse = new SVNCopySource[1];
//        svncse[0] = svncs;
//        dstURL.getPath();
//        //doCopy(SVNCopySource[],SVNURL, boolean isMove, boolean makeParents, boolean failWhenDstExists, String commitMessage, SVNProperties revisionProperties)
//        //此变量用来存放要查看的文件的属性名/属性值列表。 
//        SVNProperties fileProperties = new SVNProperties();
//        return ourClientManager.getCopyClient().doCopy(svncse, dstURL, isMove, true, false, commitMessage, fileProperties);

//        origin code of svnkit example 
//        return ourClientManager.getCopyClient().doCopy(srcURL,  SVNRevision.HEAD,
//                dstURL, isMove, commitMessage);
        SVNCopyClient copyClient = ourClientManager.getCopyClient();
        SVNCopySource[] sources = new SVNCopySource[]{new SVNCopySource(SVNRevision.HEAD, SVNRevision.HEAD, srcURL)};
        return copyClient.doCopy(sources, dstURL, isMove, isMove, isMove, commitMessage, null);
    }

    /*
     * Displays error information and exits. 
     */
    private static void error(String message, Exception e) {
        System.err.println(message + (e != null ? ": " + e.getMessage() : ""));
        System.exit(1);
    }

    /*
     * This method does not relate to SVNKit API. Just a method which creates
     * local directories and files :)
     */
    private static void createLocalDir(File aNewDir, File[] localFiles, String[] fileContents) {
        if (!aNewDir.mkdirs()) {
            error("failed to create a new directory '" + aNewDir.getAbsolutePath() + "'.", null);
        }
        for (int i = 0; i < localFiles.length; i++) {
            File aNewFile = localFiles[i];
            try {
                if (!aNewFile.createNewFile()) {
                    error("failed to create a new file '"
                            + aNewFile.getAbsolutePath() + "'.", null);
                }
            } catch (IOException ioe) {
                aNewFile.delete();
                error("error while creating a new file '"
                        + aNewFile.getAbsolutePath() + "'", ioe);
            }

            String contents = null;
            if (i > fileContents.length - 1) {
                continue;
            }
            contents = fileContents[i];

            /*
             * writing a text into the file
             */
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(aNewFile);
                fos.write(contents.getBytes());
            } catch (FileNotFoundException fnfe) {
                error("the file '" + aNewFile.getAbsolutePath() + "' is not found", fnfe);
            } catch (IOException ioe) {
                error("error while writing into the file '"
                        + aNewFile.getAbsolutePath() + "'", ioe);
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException ioe) {
                        //
                    }
                }
            }
        }
    }
}
