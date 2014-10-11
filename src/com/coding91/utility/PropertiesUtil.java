/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coding91.utility;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/**
 *
 * @author Administrator
 */
public class PropertiesUtil {

    public static Map<String, Map<String, String>> conf;
    
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

    // 复制文件
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null) {
                inBuff.close();
            }
            if (outBuff != null) {
                outBuff.close();
            }
        }
    }

    //写入properties信息
    public static boolean writeProperties(String filePath, Map<String, String> properties, String jarFilePath, String newJarFilePath) throws URISyntaxException {
        Properties prop = new Properties();
        try {
//            InputStream fis = new FileInputStream(filePath);
            InputStream fis = PropertiesUtil.class.getClassLoader().getResourceAsStream(filePath);
            System.out.println("filePath:" + filePath);

            prop.load(fis);
            fis.close();

            URL url = PropertiesUtil.class.getClassLoader().getResource(filePath);
            int beginIndex = url.getPath().lastIndexOf("/") + 1;
            String fileName = url.getPath().substring(beginIndex);
            System.out.println("url.getPath():" + url.getPath());
            System.out.println("PropertiesUtil.class.getClassLoader():");
            System.out.println("fileName:" + fileName);
            String dstPath = System.getProperty("java.io.tmpdir");
            System.out.println("tmpdir:" + dstPath);
            dstPath = dstPath + fileName;
//            OutputStream fos = new FileOutputStream(new File(url.toURI()));
            File dstFile = new File(dstPath);
            OutputStream fos = new FileOutputStream(dstFile);
            String parameterName, parameterValue;
            for (Entry<String, String> entry : properties.entrySet()) {
                parameterName = entry.getKey();
                parameterValue = entry.getValue();
                prop.setProperty(parameterName, parameterValue);
            }
            System.out.println(filePath);
            prop.store(fos, "Update value");
            fos.close();

            //copy 一个新的jar文件
            File jarFilePathFile = new File(jarFilePath);
            File newJarFilePathFile = new File(newJarFilePath);
            if (!newJarFilePathFile.exists()) {
                copyFile(jarFilePathFile, newJarFilePathFile);
            }

            //添加到jar里面
            try {
                //"D:\\www\\GitHub\\deploymentSvnForDS\\dist\\DeploymentSvnForDS.jar"
                ZipFile zipFile = new ZipFile(newJarFilePath);

                // Build the list of files to be added in the array list
                ArrayList<File> filesToAdd = new ArrayList<>();
                filesToAdd.add(dstFile);

                // Initiate Zip Parameters 
                ZipParameters parameters = new ZipParameters();
                parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); // set compression method to deflate compression

                // Set the compression level.
                parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

                // Sets the folder in the zip file to which these new files will be added.
                // In this example, test2 is the folder to which these files will be added.
                // Another example: if files were to be added to a directory test2/test3, then
                // below statement should be parameters.setRootFolderInZip("test2/test3/");
                parameters.setRootFolderInZip("resources\\properties\\");

                // Now add files to the zip file
                zipFile.addFiles(filesToAdd, parameters);
            } catch (ZipException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            System.err.println("Visit " + filePath + " for updating value error:" + e.getMessage());
            MessageUtil.showMessageDialogMessage("Visit " + filePath + " for updating value error:" + e.getMessage());
        }
        
        return true;
    }

    public static String getPropertyFilePath(String env) {
        return "resources/properties/" + env + ".conf.properties";
    }

    public static Properties getProperties(String env) {
        String propFile = PropertiesUtil.getPropertyFilePath(env);
        InputStream fis = PropertiesUtil.class.getClassLoader().getResourceAsStream(propFile);
        Properties prop = new Properties();
        try {
            prop.load(fis);
        } catch (Exception ex) {
            MessageUtil.showMessageDialogMessage("load " + propFile + " error:" + ex.getMessage());
        }
        return prop;
    }
    
    public static Properties getProperties() {
        InputStream fis = PropertiesUtil.class.getClassLoader().getResourceAsStream("resources/properties/conf.properties");
        Properties prop = new Properties();
        try {
            prop.load(fis);
        } catch (IOException ex) {
            System.exit(-1);
        }
        return prop;
    }
    
    public static void buildConf(String env) {
        if (conf == null || !conf.containsKey(env)) {
            Map<String, String> currentConf = new HashMap<>();
            Properties prop = getProperties(env);
            if (conf == null) {
                conf = new HashMap<>();
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
    
    public static Map<String, String> getConfByEnv(String env) {
        if (env.isEmpty()) {
            env = "en_us";
        }
        if (conf == null || !conf.containsKey(env)) {
            buildConf(env);
        }
        return conf.get(env);
    }

}
