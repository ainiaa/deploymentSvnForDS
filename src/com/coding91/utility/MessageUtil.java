/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coding91.utility;

import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class MessageUtil {
    public static void showMessageDialogMessage(Exception ex) {
        String exMsg = ex.toString();
        JOptionPane.showMessageDialog(null, exMsg + new Throwable().getStackTrace()[1].toString(), "错误信息提示", JOptionPane.ERROR_MESSAGE);
    }

    public static void showMessageDialogMessage(String msg, String title) {
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
    }
    
    public static void showMessageDialogMessage(String msg, String title, int messageType) {
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void showMessageDialogMessage(String msg) {
        JOptionPane.showMessageDialog(null, msg, "错误信息提示", JOptionPane.ERROR_MESSAGE);
    }
}
