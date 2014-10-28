/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coding91.utility;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class TortoiseProcUtil {

    /**
     * 使用tortoise 提交
     *
     * @param onlineContentPath
     * @param commitLog
     * @return
     */
    public static int commit(String onlineContentPath, String commitLog) {
        try {
            Process process = Runtime.getRuntime().exec("TortoiseProc.exe /command:commit /path:" + onlineContentPath + " /logmsg:" + commitLog + " /closeonend:2");
            return process.waitFor();
        } catch (InterruptedException | IOException ex) {
            Logger.getLogger(TortoiseProcUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * 使用tortoise 对比
     *
     * @param originContentPath
     * @param destContentPath
     * @return
     */
    public static int compare(String originContentPath, String destContentPath) {
        try {
            Process process = Runtime.getRuntime().exec("TortoiseProc.exe /command:diff /path:" + originContentPath + " /path2:" + destContentPath + " /closeonend:2");
            return process.waitFor();
        } catch (InterruptedException | IOException ex) {
            Logger.getLogger(TortoiseProcUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

}
