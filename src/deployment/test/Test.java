/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deployment.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class Test {

    public static void main(String[] args) throws IOException {
//        File f = new File("D:\\www\\framework");
//        File[] ff = f.listFiles();
//        for (File cf : ff) {
//            System.out.println(cf.getAbsoluteFile() + " " + cf.getCanonicalFile() + " " + cf.getName());
//        }

//        String ls_1;
//        Process process = Runtime.getRuntime().exec("TortoiseProc.exe /command:commit /path:\"D:\\www\\dessert\\code\\branches\\dev_testsync\\\" /logmsg:\"test log message\" /closeonend:0");
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//        while ((ls_1 = bufferedReader.readLine()) != null) {
//            System.out.println(ls_1);
//        }
//        try {
//            process.waitFor();
//        } catch (InterruptedException ex) {
//            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
//        System.out.println(System.getProperty("java.class.path"));//
//        System.out.println(Test.class.getProtectionDomain().getCodeSource().getLocation().getPath());
//        String path = System.getProperty("user.dir");
//        System.out.println(path);//
        
        
        
        //        List<String> test = new ArrayList<>();
//        test.add("V0.8.4.2000");
//        test.add("V0.8.4.2001");
//        test.add("V0.8.4.1000");
//        test.add("V0.8.4.1002");
//
//        test.add("V0.8.3.2000");
//        test.add("V0.8.3.2001");
//        test.add("V0.8.3.1000");
//        test.add("V0.8.3.1002");
//
//        for (String branch : test) {
//            int dotLatestIndex = branch.lastIndexOf(".");
//            if (dotLatestIndex > 0) {
//                String s = branch.substring(dotLatestIndex + 1);
//                System.out.print(s + " :" + dotLatestIndex);
//                if (s.startsWith("1")) {
//                    System.out.println(" belongs a");
//                } else if (s.startsWith("2")) {
//                    System.out.println(" belongs b");
//                }
//            }
//        }
//        copyStreamToFile();
        copyFile(new File("d:/j建筑升级newly.xls"), new File("d:/j建筑升级newlylyly.xls"));
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
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }
    
    public static void copyStreamToFile() throws IOException
    {
        FileOutputStream foutOutput = null;
        String oldDir =  "d:/j建筑升级.xls";
        System.out.println(oldDir);
        String newDir = "d:/j建筑升级newly.xls";  // name as the destination file name to be done
        File f = new File(oldDir);
        f.renameTo(new File(newDir));
    }

}
