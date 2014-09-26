/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coding91.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author Administrator
 */
public class ZipUtil {

    public void addFilesToZip(File source, File[] files, String path) {
        try {
            File tmpZip = File.createTempFile(source.getName(), null);
            tmpZip.delete();
            if (!source.renameTo(tmpZip)) {
                throw new Exception("Could not make temp file (" + source.getName() + ")");
            }
            byte[] buffer = new byte[4096];
            ZipInputStream zin = new ZipInputStream(new FileInputStream(tmpZip));
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(source));
            for (int i = 0; i < files.length; i++) {
                InputStream in = new FileInputStream(files[i]);
                out.putNextEntry(new ZipEntry(path + files[i].getName()));
                for (int read = in.read(buffer); read > -1; read = in.read(buffer)) {
                    out.write(buffer, 0, read);
                }
                out.closeEntry();
                in.close();
            }
            for (ZipEntry ze = zin.getNextEntry(); ze != null; ze = zin.getNextEntry()) {
                if (!zipEntryMatch(ze.getName(), files, path)) {
                    out.putNextEntry(ze);
                    for (int read = zin.read(buffer); read > -1; read = zin.read(buffer)) {
                        out.write(buffer, 0, read);
                    }
                    out.closeEntry();
                }
            }
            out.close();
            tmpZip.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean zipEntryMatch(String zeName, File[] files, String path) {
        for (int i = 0; i < files.length; i++) {
            if ((path + files[i].getName()).equals(zeName)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        File[] files = {new File("D:\\www\\GitHub\\deploymentSvnForDS\\dist\\README.TXT")};
        ZipUtil zipUtil = new ZipUtil();
        zipUtil.addFilesToZip(new File("D:\\www\\GitHub\\deploymentSvnForDS\\dist\\DeploymentSvnForDS.jar"), files, "resources\\properties\\");
    }
}
