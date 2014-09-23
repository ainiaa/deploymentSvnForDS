/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deployment.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class Test {

    public static void main(String[] args) throws IOException {
        File f = new File("D:\\www\\framework");
        File[] ff = f.listFiles();
        for (File cf : ff) {
            System.out.println(cf.getAbsoluteFile() + " " + cf.getCanonicalFile() + " " + cf.getName());
        }

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
    }
}
