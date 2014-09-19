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

import com.coding91.utility.MapUtil;
import deployment.reference.tmatesoftexampleswc.CommitEventHandler;
import deployment.reference.tmatesoftexampleswc.InfoHandler;
import deployment.reference.tmatesoftexampleswc.StatusHandler;
import deployment.reference.tmatesoftexampleswc.UpdateEventHandler;
import deployment.reference.tmatesoftexampleswc.WCEventHandler;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.tmatesoft.svn.core.ISVNDirEntryHandler;
import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.wc.ISVNEventHandler;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNCopyClient;
import org.tmatesoft.svn.core.wc.SVNCopySource;
import org.tmatesoft.svn.core.wc.SVNLogClient;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;
import sync.Sync;

public final class WorkingCopyImprove {

    private static ISVNEventHandler myCommitEventHandler;
    private static ISVNEventHandler myUpdateEventHandler;
    private static ISVNEventHandler myWCEventHandler;
    private static DefaultSVNOptions options;
    private static Map<String, SVNClientManager> SVNClientManagerMap;
    private final SVNClientManager clientManager;

    public SVNClientManager getCurrentClientManager(String env) {
        if (SVNClientManagerMap == null || SVNClientManagerMap.isEmpty() || !SVNClientManagerMap.containsKey(env)) {
            return initSVNClientManager(env);
        } else {
            return SVNClientManagerMap.get(env);
        }
    }

    public Map<String, Map<String, String>> conf;

    public Map<String, String> getConfByEnv(String env) {
        if (env.isEmpty()) {
            env = "en_us";
        }
        if (conf == null || !conf.containsKey(env)) {
            buildConf(env);
        }
        return conf.get(env);
    }

    public void buildConf(String env) {
        if (conf == null || !conf.containsKey(env)) {
            Map<String, String> currentConf = new HashMap<>();
            Properties prop = getProperties(env);
            if (conf == null) {
                conf = new HashMap();
            }
            Set<String> names = prop.stringPropertyNames();
            Iterator<String> it = names.iterator();
            while (it.hasNext()) {
                try {
                    String key = it.next();
                    String value = new String(prop.getProperty(key).getBytes("ISO-8859-1"), "UTF-8");
                    currentConf.put(key, value);
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(WorkingCopyImprove.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            conf.put(env, currentConf);
        }
    }

    public Properties getProperties(String env) {
        String propFile = "resources/properties/" + env + ".conf.properties";
        InputStream fis = getClass().getClassLoader().getResourceAsStream(propFile);
        Properties prop = new Properties();
        try {
            prop.load(fis);
        } catch (IOException ex) {
            Sync.printlnFlush(ex.getMessage());
            System.exit(-1);
        }

        return prop;
    }

    public Properties getProperties() {
        InputStream fis = getClass().getClassLoader().getResourceAsStream("resources/properties/conf.properties");
        Properties prop = new Properties();
        try {
            prop.load(fis);
        } catch (IOException ex) {
            Sync.printlnFlush(ex.getMessage());
            System.exit(-1);
        }

        return prop;
    }

    public String buildPropKey(String env, String key) {
        return env + "." + key;
    }

    public SVNClientManager initSVNClientManager(String env, String special, String content) {

        if (SVNClientManagerMap == null) {
            SVNClientManagerMap = new HashMap();
        }
        String key = env + "." + special + "." + content;
        if (SVNClientManagerMap.isEmpty() || !SVNClientManagerMap.containsKey(env)) {
            Map<String, String> currentConf = getConfByEnv(env);
            String name, password;
            name = currentConf.get(content + "." + special + ".svn.username");
            password = currentConf.get(content + "." + special + ".svn.password");

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
            SVNClientManagerMap.put(key, currentClientManager);
            return currentClientManager;
        } else {
            return SVNClientManagerMap.get(key);
        }

    }

    public SVNClientManager initSVNClientManager(String env) {
        if (SVNClientManagerMap == null) {
            SVNClientManagerMap = new HashMap();
        }
        if (SVNClientManagerMap.isEmpty() || SVNClientManagerMap.containsKey(env)) {
            Map<String, String> currentConf = getConfByEnv(env);

            String name = currentConf.get("svn.username");
            String password = currentConf.get("svn.password");

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

    public WorkingCopyImprove(String env, String special, String content) {
        setupLibrary();
        buildConf(env);
        clientManager = initSVNClientManager(env, special, content);
    }

    public WorkingCopyImprove initWorkingCopyImprove(String env, String special, String content) {
        return new WorkingCopyImprove(env, special, content);
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

    public SVNCommitInfo commit(File wcPath, boolean keepLocks, String commitMessage, boolean recurse)
            throws SVNException {
        /*
         * Returns SVNCommitInfo containing information on the new revision committed 
         * (revision number, etc.) 
         */
        return clientManager.getCommitClient().doCommit(
                new File[]{wcPath}, keepLocks, commitMessage, null,
                null, false, false, SVNDepth.fromRecurse(recurse));
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

    public Map<Date, String> getBranchOrTagListByEnvConf(String env, boolean isOnline) throws SVNException {
        String rootUrl;
        if (isOnline) {
            rootUrl = getConfByEnv(env).get("php.online.svn.url");
        } else {
            rootUrl = getConfByEnv(env).get("php.local.svn.url");
        }
        SVNURL svnUrl = SVNURL.parseURIEncoded(rootUrl);
        return list(svnUrl, SVNRevision.HEAD, SVNRevision.HEAD, false, false);
    }

    public Map<Date, String> list(SVNURL url,
            SVNRevision pegRevision,
            SVNRevision revision,
            boolean fetchLocks,
            boolean recursive) {
        return list(url, SVNRevision.HEAD, SVNRevision.HEAD, false, false, false);
    }

    public Map<Date, String> list(SVNURL url,
            SVNRevision pegRevision,
            SVNRevision revision,
            boolean fetchLocks,
            boolean recursive,
            final boolean isGetLatest) {
        final Map<Date, String> branchListMap = new HashMap<>();

        list(url, pegRevision, revision, fetchLocks, recursive,
                new ISVNDirEntryHandler() {
                    @Override
                    public void handleDirEntry(SVNDirEntry dirEntry) throws SVNException {
                        if (!dirEntry.getRelativePath().isEmpty()) {
                            branchListMap.put(dirEntry.getDate(), dirEntry.getName());
                        }
                    }
                });

        Map<Date, String> needSortedMap = new HashMap<>(branchListMap);
        needSortedMap = MapUtil.sortByKey(needSortedMap, MapUtil.SortingOrder.DESCENDING);
        
        if (isGetLatest) {
            for (Entry<Date, String> entry : needSortedMap.entrySet()) {
                Map<Date, String> tmpMap = new HashMap<>();
                tmpMap.put(entry.getKey(), entry.getValue());
                return tmpMap;
            }
        }
        return needSortedMap;
    }

    public Map<Date, String> getLatestBranchOrTagByEnvConf(String env, boolean isOnline) throws SVNException {
        String rootUrl;
        if (isOnline) {
            rootUrl = getConfByEnv(env).get("php.online.svn.url");
        } else {
            rootUrl = getConfByEnv(env).get("php.local.svn.url");
        }
        SVNURL svnUrl = SVNURL.parseURIEncoded(rootUrl);
        return list(svnUrl, SVNRevision.HEAD, SVNRevision.HEAD, false, false, true);
    }

    private void list(SVNURL url,
            SVNRevision pegRevision,
            SVNRevision revision,
            boolean fetchLocks,
            boolean recursive,
            ISVNDirEntryHandler handler) {
        SVNLogClient logClient = clientManager.getLogClient();
        /*
         * sets externals not to be ignored during the update
         */
        logClient.setIgnoreExternals(false);

        try {
            logClient.doList(url, pegRevision, revision, fetchLocks, recursive, handler);
        } catch (SVNException ex) {
            Logger.getLogger(WorkingCopyImprove.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void list(java.io.File path,
            SVNRevision pegRevision,
            SVNRevision revision,
            boolean fetchLocks,
            SVNDepth depth,
            int entryFields) {
        list(path, pegRevision, revision, fetchLocks, depth, entryFields,
                new ISVNDirEntryHandler() {
                    @Override
                    public void handleDirEntry(SVNDirEntry dirEntry) throws SVNException {
                        System.out.println("tag: "
                                + dirEntry.getRelativePath());
                    }
                });
    }

    private void list(java.io.File path,
            SVNRevision pegRevision,
            SVNRevision revision,
            boolean fetchLocks,
            SVNDepth depth,
            int entryFields,
            ISVNDirEntryHandler handler) {

        SVNLogClient logClient = clientManager.getLogClient();
        /*
         * sets externals not to be ignored during the update
         */
        logClient.setIgnoreExternals(false);

        try {
            logClient.doList(path, pegRevision, revision, fetchLocks, depth, entryFields, handler);
        } catch (SVNException ex) {
            Logger.getLogger(WorkingCopyImprove.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    /**
     *
     * @param localPathKey
     * @param env
     * @param updateToRevision
     * @param depth
     * @param allowUnversionedObstructions
     * @param depthIsSticky
     * @return
     * @throws SVNException
     */
    public long updateToLocalByEnvConf(String localPathKey, String env,
            SVNRevision updateToRevision, SVNDepth depth, boolean allowUnversionedObstructions, boolean depthIsSticky)
            throws SVNException {

        String localPath = getConfByEnv(env).get(localPathKey);
        Sync.printlnFlush("env:" + env + " localPathKey:" + localPathKey + "  localPath:" + localPath + "\r\n");
        File wcPath = new File(localPath);
        SVNUpdateClient updateClient = clientManager.getUpdateClient();
        /*
         * sets externals not to be ignored during the update
         */
        updateClient.setIgnoreExternals(false);
        /*
         * returns the number of the revision wcPath was updated to
         */
        Sync.printlnFlush("updateToLocalByEnvConf  env:'" + env + "' localPath '" + localPath + "'  start=================\r\n");
        long version = updateClient.doUpdate(wcPath, updateToRevision, depth, allowUnversionedObstructions, depthIsSticky);
        Sync.printlnFlush("updateToLocalByEnvConf  env:'" + env + "' localPath '" + localPath + "'  end=================\r\n");
        return version;
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
            boolean isMove, String commitMessage) {
        /*
         * SVNRevision.HEAD means the latest revision.
         * Returns SVNCommitInfo containing information on the new revision committed 
         * (revision number, etc.) 
         */
        SVNCopyClient copyClient = clientManager.getCopyClient();
        SVNCopySource[] sources = new SVNCopySource[]{new SVNCopySource(SVNRevision.HEAD, SVNRevision.HEAD, srcURL)};
        Sync.printlnFlush("remotely create PHP Branch/Tag  '" + srcURL + "' to '" + dstURL + "'  start=================\r\n");
        SVNCommitInfo commitInfo;
        try {
            commitInfo = copyClient.doCopy(sources, dstURL, isMove, isMove, isMove, commitMessage, null);
            return commitInfo;
        } catch (SVNException ex) {
            Sync.printlnFlush("remotely create PHP Branch/Tag '" + srcURL + "' to '" + dstURL + "'  error :" + ex.getErrorMessage());
        }
        Sync.printlnFlush("remotely create PHP Branch/Tag  '" + srcURL + "' to '" + dstURL + "'  end=================\r\n");
        return null;
    }

    /**
     * 根据env 创建 branch/tag
     *
     * @param env
     * @param originTag
     * @param dstTag
     * @param commitMessage
     * @param isOnline
     * @param version
     * @return
     * @throws SVNException
     */
    public SVNCommitInfo createBranchOrTagByEnvConf(String env, String originTag, String dstTag, String commitMessage, boolean isOnline, int version) throws SVNException {
        String rootUrl;

        String svnKey;

        if (isOnline) {
            if (version == 0) {//a版本
                svnKey = "php.online.svn.url";
            } else if (version == 1) {//b版本
                svnKey = "php.online.svn.url";
            } else {//其他
                svnKey = "php.online.svn.url";
            }
            rootUrl = getConfByEnv(env).get(svnKey);

        } else {
            if (version == 0) {//a版本
                svnKey = "php.local.svn.url";
            } else if (version == 1) {//b版本
                svnKey = "php.local.svn.url";
            } else {//其他
                svnKey = "php.local.svn.url";
            }
            rootUrl = getConfByEnv(env).get(svnKey);
        }
        SVNURL srcURL = SVNURL.parseURIEncoded(rootUrl + originTag);
        SVNURL dstURL = SVNURL.parseURIEncoded(rootUrl + dstTag);
        return copy(srcURL, dstURL, false, commitMessage);
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
