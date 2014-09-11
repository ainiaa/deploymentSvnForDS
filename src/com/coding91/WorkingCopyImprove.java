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
package com.coding91;

import org.tmatesoft.svn.examples.wc.*;
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

public class WorkingCopyImprove {

    private static SVNClientManager ourClientManager;
    private static ISVNEventHandler myCommitEventHandler;
    private static ISVNEventHandler myUpdateEventHandler;
    private static ISVNEventHandler myWCEventHandler;
    private static DefaultSVNOptions options;
    private static int errCount = 0;
    private static Map<String, SVNClientManager> SVNClientManagerMap;
    private SVNClientManager clientManager;

    public SVNClientManager getCurrentClientManager(String env) {
        if (SVNClientManagerMap == null || SVNClientManagerMap.isEmpty() || !SVNClientManagerMap.containsKey(env)) {
            return initSVNClientManager(env);
        } else {
            return SVNClientManagerMap.get(env);
        }
    }

    public SVNClientManager initSVNClientManager(String env) {
        if (SVNClientManagerMap == null) {
            SVNClientManagerMap = new HashMap();
        }
        if (SVNClientManagerMap.isEmpty() || SVNClientManagerMap.containsKey(env)) {
            SVNURL repositoryURL = null;
            try {
                repositoryURL = SVNURL.parseURIEncoded("svn://svndev.shinezone.com/dev/Dessert_Shop/facebook/branches/");
            } catch (SVNException e) {
                //
            }
            String name = "liuwenyuan";
            String password = "RD56FGvderqpH";

            if (myCommitEventHandler == null) {
                myCommitEventHandler = new CommitEventHandler();
            }
            if (myUpdateEventHandler == null) {
                myUpdateEventHandler = new UpdateEventHandler();
            }

            if (myWCEventHandler == null) {
                myWCEventHandler = new WCEventHandler();
            }

            if (options == null) {
                options = (DefaultSVNOptions) SVNWCUtil.createDefaultOptions(true);
            }

            SVNClientManager currentClientManager = SVNClientManager.newInstance(options, name, password);
            currentClientManager.getCommitClient().setEventHandler(myCommitEventHandler);
            currentClientManager.getUpdateClient().setEventHandler(myUpdateEventHandler);
            currentClientManager.getWCClient().setEventHandler(myWCEventHandler);
            SVNClientManagerMap.put(env, currentClientManager);
            return currentClientManager;
        } else {
            return SVNClientManagerMap.get(env);
        }

    }

    public WorkingCopyImprove(String env) {
        /*
         * Initializes the library (it must be done before ever using the library 
         * itself)
         */
        setupLibrary();
        clientManager = initSVNClientManager(env);
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
    private SVNCommitInfo makeDirectory(SVNURL url, String commitMessage) throws SVNException {
        /*
         * Returns SVNCommitInfo containing information on the new revision committed 
         * (revision number, etc.) 
         */
        return clientManager.getCommitClient().doMkDir(new SVNURL[]{url}, commitMessage);
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
    private SVNCommitInfo importDirectory(File localPath, SVNURL dstURL, String commitMessage, boolean isRecursive) throws SVNException {
        /*
         * Returns SVNCommitInfo containing information on the new revision committed 
         * (revision number, etc.) 
         */
        return clientManager.getCommitClient().doImport(localPath, dstURL, commitMessage, isRecursive);
//java.io.File path,
//                              SVNURL dstURL,
//                              java.lang.String commitMessage,
//                              SVNProperties revisionProperties,
//                              boolean useGlobalIgnores,
//                              boolean ignoreUnknownNodeTypes,
//                              SVNDepth depth
    }

    private SVNCommitInfo importDirectory(File localPath, SVNURL dstURL, String commitMessage, SVNProperties revisionProperties, boolean useGlobalIgnores, boolean ignoreUnknownNodeTypes, SVNDepth depth) throws SVNException {
        /*
         * Returns SVNCommitInfo containing information on the new revision committed 
         * (revision number, etc.) 
         */
        return clientManager.getCommitClient().doImport(localPath, dstURL, commitMessage, revisionProperties, useGlobalIgnores, ignoreUnknownNodeTypes, depth);
    }

    private SVNCommitInfo importDirectory(File localPath, SVNURL dstURL, String commitMessage) throws SVNException {
        /*
         * Returns SVNCommitInfo containing information on the new revision committed 
         * (revision number, etc.) 
         */
        return clientManager.getCommitClient().doImport(localPath, dstURL, commitMessage, new SVNProperties(), false, false, SVNDepth.INFINITY);
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

    private SVNCommitInfo commit(File wcPath, boolean keepLocks, String commitMessage)
            throws SVNException {
        /*
         * Returns SVNCommitInfo containing information on the new revision committed 
         * (revision number, etc.) 
         */
        return clientManager.getCommitClient().doCommit(new File[]{wcPath}, keepLocks,
                commitMessage, false, true);
    }

    private SVNCommitInfo commit(File wcPath, boolean keepLocks, String commitMessage, boolean recurse)
            throws SVNException {
        /*
         * Returns SVNCommitInfo containing information on the new revision committed 
         * (revision number, etc.) 
         */
        //doCommit(java.io.File[] paths, boolean keepLocks, java.lang.String commitMessage, SVNProperties revisionProperties, java.lang.String[] changelists, boolean keepChangelist, boolean force, SVNDepth depth)
        //doCommit(SVNCommitPacket[] commitPackets, boolean keepLocks, java.lang.String commitMessage)
        return clientManager.getCommitClient().doCommit(
                new File[]{wcPath}, keepLocks, commitMessage, null,
                null, false, false, SVNDepth.fromRecurse(true));
//        return clientManager.getCommitClient().doCommit(new File[]{wcPath}, keepLocks,
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
    private long checkout(SVNURL url,
            SVNRevision revision, File dstPath, boolean isRecursive)
            throws SVNException {

        SVNUpdateClient updateClient = clientManager.getUpdateClient();
        /*
         * sets externals not to be ignored during the checkout
         */
        updateClient.setIgnoreExternals(false);
        /*
         * returns the number of the revision at which the working copy is 
         */
        return updateClient.doCheckout(url, dstPath, revision, revision, isRecursive);
    }

    private long checkout(SVNURL url, SVNRevision pegRevision,
            SVNRevision revision, SVNDepth depth, File dstPath, boolean allowUnversionedObstructions)
            throws SVNException {

        SVNUpdateClient updateClient = clientManager.getUpdateClient();
        /*
         * sets externals not to be ignored during the checkout
         */
        updateClient.setIgnoreExternals(false);
        /*
         * returns the number of the revision at which the working copy is 
         */
        return updateClient.doCheckout(url, dstPath, pegRevision, revision, depth, allowUnversionedObstructions);
    }

    private long checkout(SVNURL url, SVNRevision revision, File dstPath)
            throws SVNException {

        SVNUpdateClient updateClient = clientManager.getUpdateClient();
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
    private long update(File wcPath,
            SVNRevision updateToRevision, boolean isRecursive)
            throws SVNException {

        SVNUpdateClient updateClient = clientManager.getUpdateClient();
        /*
         * sets externals not to be ignored during the update
         */
        updateClient.setIgnoreExternals(false);
        /*
         * returns the number of the revision wcPath was updated to
         */

        return updateClient.doUpdate(wcPath, updateToRevision, isRecursive);
    }

    private long update(File wcPath,
            SVNRevision updateToRevision, SVNDepth depth)
            throws SVNException {

        SVNUpdateClient updateClient = clientManager.getUpdateClient();
        /*
         * sets externals not to be ignored during the update
         */
        updateClient.setIgnoreExternals(false);
        /*
         * returns the number of the revision wcPath was updated to
         */
        return updateClient.doUpdate(wcPath, updateToRevision, depth, false, false);
    }

    public long update(File wcPath,
            SVNRevision updateToRevision, SVNDepth depth, boolean allowUnversionedObstructions, boolean depthIsSticky)
            throws SVNException {

        SVNUpdateClient updateClient = clientManager.getUpdateClient();
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
    private long switchToURL(File wcPath,
            SVNURL url, SVNRevision updateToRevision, boolean isRecursive)
            throws SVNException {
        SVNUpdateClient updateClient = clientManager.getUpdateClient();
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
    private void showStatus(File wcPath, boolean isRecursive, boolean isRemote, boolean isReportAll,
            boolean isIncludeIgnored, boolean isCollectParentExternals)
            throws SVNException {
        /*
         * StatusHandler displays status information for each entry in the console (in the 
         * manner of the native Subversion command line client)
         */
        clientManager.getStatusClient().doStatus(wcPath, isRecursive, isRemote, isReportAll,
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
    private void showInfo(File wcPath, SVNRevision revision, boolean isRecursive) throws SVNException {
        /*
         * InfoHandler displays information for each entry in the console (in the manner of
         * the native Subversion command line client)
         */
        clientManager.getWCClient().doInfo(wcPath, revision, isRecursive, new InfoHandler());
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
    private void addEntry(File wcPath) throws SVNException {
        clientManager.getWCClient().doAdd(wcPath, false, false, false, true);
    }

    private void addEntry(java.io.File wcPath, boolean force, boolean mkdir, boolean climbUnversionedParents, SVNDepth depth, boolean includeIgnored, boolean makeParents) throws SVNException {
        clientManager.getWCClient().doAdd(wcPath, force, mkdir, climbUnversionedParents, depth, includeIgnored, makeParents);
    }

    private void addEntry(java.io.File wcPath, boolean force, boolean mkdir) throws SVNException {
        clientManager.getWCClient().doAdd(wcPath, force, mkdir, true, SVNDepth.INFINITY, false, true);
    }

    private void addEntry(java.io.File wcPath, boolean force) throws SVNException {
        clientManager.getWCClient().doAdd(wcPath, force, true, true, SVNDepth.INFINITY, false, true);
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
    private void lock(File wcPath, boolean isStealLock, String lockComment) throws SVNException {
        clientManager.getWCClient().doLock(new File[]{wcPath}, isStealLock, lockComment);
    }

    private void unlock(File wcPath, boolean isStealLock) throws SVNException {
        clientManager.getWCClient().doUnlock(new File[]{wcPath}, isStealLock);
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
    private void delete(File wcPath, boolean force) throws SVNException {
        clientManager.getWCClient().doDelete(wcPath, force, false);
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
    public SVNCommitInfo copy(SVNURL srcURL, SVNURL dstURL,
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
//        return clientManager.getCopyClient().doCopy(svncse, dstURL, isMove, true, false, commitMessage, fileProperties);

//        origin code of svnkit example 
//        return clientManager.getCopyClient().doCopy(srcURL,  SVNRevision.HEAD,
//                dstURL, isMove, commitMessage);
        SVNCopyClient copyClient = clientManager.getCopyClient();
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
