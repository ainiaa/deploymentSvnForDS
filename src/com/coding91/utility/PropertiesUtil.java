/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coding91.utility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

/**
 *
 * @author Administrator
 */
public class PropertiesUtil {

    //写入properties信息
    public static void writeProperties(String filePath, String parameterName, String parameterValue) {
        Properties prop = new Properties();
        try {
            InputStream fis = new FileInputStream(filePath);
            prop.load(fis);
            OutputStream fos = new FileOutputStream(filePath);
            prop.setProperty(parameterName, parameterValue);
            prop.store(fos, "Update '" + parameterName + "' value");
        } catch (IOException e) {
            System.err.println("Visit " + filePath + " for updating " + parameterName + " value error");
        }
    }

    //写入properties信息
    public static void writeProperties(String filePath, Map<String, String> properties) {
        Properties prop = new Properties();
        try {
            InputStream fis = new FileInputStream(filePath);
            prop.load(fis);
            OutputStream fos = new FileOutputStream(filePath);
            String parameterName, parameterValue;
            for (Entry<String, String> entry : properties.entrySet()) {
                parameterName = entry.getKey();
                parameterValue = entry.getValue();
                prop.setProperty(parameterName, parameterValue);
            }

            prop.store(fos, "Update value");
        } catch (IOException e) {
            System.err.println("Visit " + filePath + " for updating value error");
        }
    }
}
