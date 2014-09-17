/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deployment.test;

import java.io.File;
import java.io.IOException;

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
    }
}
