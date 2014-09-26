/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coding91.ui;

import com.coding91.utility.ControllerJFrame;
import com.coding91.utility.MessageUtil;
import com.coding91.utility.PropertiesUtil;
import java.awt.Toolkit;
import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Administrator
 */
public class EditPropConfJFrame extends javax.swing.JFrame {

    /**
     * Creates new form EditPropConfJFrame
     */
    public EditPropConfJFrame() {

        try {
            javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(EditPropConfJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(
                getClass().getClassLoader().getResource("resources/images/sync.png")));//这个不能以 '/'开头

        setLocationRelativeTo(null);// *** this will center your app ***

        initComponents();

        new Thread(new Runnable() {
            @Override
            public void run() {
                loadPropAndShow("en_us");
            }
        }).start();
    }

    private void loadPropAndShow(String env) {
        Properties prop = PropertiesUtil.getProperties(env);
        String langStr = (String) prop.getProperty("langList");
        if (!langStr.isEmpty() && currentLangjComboBox.getSelectedIndex() == -1) {
            System.out.println("currentLangjComboBox.getSelectedIndex():xxx" + currentLangjComboBox.getSelectedIndex());
            String[] langArray = langStr.split(",");
            if (langArray.length > 0) {
                currentLangjComboBox.setModel(new DefaultComboBoxModel(langArray));
            } else {
                langArray = new String[]{"en_us", "fr_fr", "de_de"};
                currentLangjComboBox.setModel(new DefaultComboBoxModel(langArray));
            }
        }
        currentLangjComboBox.setSelectedItem(env);

        langListjTextField.setText(prop.getProperty("langList", ""));
        withABVersionjTextField.setText(prop.getProperty("withABVersion", ""));
        defaultVersionjTextField.setText(prop.getProperty("defaultVersion", ""));
        //#### online  resources
        onlineResourceSvnUrljTextField.setText(prop.getProperty("resources.online.svn.url", ""));
        onlineResourceSvnNamejTextField.setText(prop.getProperty("resources.online.svn.username", ""));
        onlineResourceSvnPasswordjTextField.setText(prop.getProperty("resources.online.svn.password", ""));
        onlineResourceSvnPathjTextField.setText(prop.getProperty("resources.online.sysfile.path", ""));

        //#### develop  resources
        developResourceSvnUrljTextField.setText(prop.getProperty("resources.local.svn.url", ""));
        developResourceSvnNamejTextField.setText(prop.getProperty("resources.local.svn.username", ""));
        developResourceSvnPasswordjTextField.setText(prop.getProperty("resources.local.svn.password", ""));
        developResourceSvnPathjTextField.setText(prop.getProperty("resources.local.sysfile.path", ""));

        //#### online  flash
        onlineFlashSvnUrljTextField.setText(prop.getProperty("flash.online.svn.url", ""));
        onlineFlashSvnNamejTextField.setText(prop.getProperty("flash.online.svn.username", ""));
        onlineFlashSvnPasswordjTextField.setText(prop.getProperty("flash.online.svn.password", ""));
        onlineFlashSvnPathjTextField.setText(prop.getProperty("flash.online.sysfile.path", ""));

        //#### develop  flash
        developFlashSvnUrljTextField.setText(prop.getProperty("flash.local.svn.url", ""));
        developFlashSvnNamejTextField.setText(prop.getProperty("flash.local.svn.username", ""));
        developFlashSvnPasswordjTextField.setText(prop.getProperty("flash.local.svn.password", ""));
        developFlashSvnPathjTextField.setText(prop.getProperty("flash.local.sysfile.path", ""));

        //#### online  php
        onlinePHPSvnUrljTextField.setText(prop.getProperty("php.online.svn.url", ""));
        onlinePHPSvnNamejTextField.setText(prop.getProperty("php.online.svn.username", ""));
        onlinePHPSvnPasswordjTextField.setText(prop.getProperty("php.online.svn.password", ""));
        onlinePHPSvnPathjTextField.setText(prop.getProperty("php.online.sysfile.path", ""));

        //#### develop  php
        developPHPSvnUrljTextField.setText(prop.getProperty("php.local.svn.url", ""));
        developPHPSvnNamejTextField.setText(prop.getProperty("php.local.svn.username", ""));
        developPHPSvnPasswordjTextField.setText(prop.getProperty("php.local.svn.password", ""));
        developPHPSvnPathjTextField.setText(prop.getProperty("php.local.sysfile.path", ""));
    }

    /**
     * Creates new form EditPropConfJFrame
     */
    public EditPropConfJFrame(boolean visible) {
        initComponents();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Properties prop = PropertiesUtil.getProperties("en_us");
                String langStr = (String) prop.getProperty("langList");
                if (!langStr.isEmpty()) {
                    String[] langArray = langStr.split(",");
                    if (langArray.length > 0) {
                        currentLangjComboBox.setModel(new DefaultComboBoxModel<>(langArray));
                    } else {
                        langArray = new String[]{"en_us", "fr_fr", "de_de"};
                        currentLangjComboBox.setModel(new DefaultComboBoxModel<>(langArray));
                    }
                }

                langListjTextField.setText(prop.getProperty("langList", ""));
                withABVersionjTextField.setText(prop.getProperty("withABVersion", ""));
                defaultVersionjTextField.setText(prop.getProperty("defaultVersion", ""));
                //#### online  resources
                onlineResourceSvnUrljTextField.setText(prop.getProperty("resources.online.svn.url", ""));
                onlineResourceSvnNamejTextField.setText(prop.getProperty("resources.online.svn.username", ""));
                onlineResourceSvnPasswordjTextField.setText(prop.getProperty("resources.online.svn.password", ""));
                onlineResourceSvnPathjTextField.setText(prop.getProperty("resources.online.sysfile.path", ""));

                //#### develop  resources
                developResourceSvnUrljTextField.setText(prop.getProperty("resources.local.svn.url", ""));
                developResourceSvnNamejTextField.setText(prop.getProperty("resources.local.svn.username", ""));
                developResourceSvnPasswordjTextField.setText(prop.getProperty("resources.local.svn.password", ""));
                developResourceSvnPathjTextField.setText(prop.getProperty("resources.local.sysfile.path", ""));

                //#### online  flash
                onlineFlashSvnUrljTextField.setText(prop.getProperty("flash.online.svn.url", ""));
                onlineFlashSvnNamejTextField.setText(prop.getProperty("flash.online.svn.username", ""));
                onlineFlashSvnPasswordjTextField.setText(prop.getProperty("flash.online.svn.password", ""));
                onlineFlashSvnPathjTextField.setText(prop.getProperty("flash.online.sysfile.path", ""));

                //#### develop  flash
                developFlashSvnUrljTextField.setText(prop.getProperty("flash.local.svn.url", ""));
                developFlashSvnNamejTextField.setText(prop.getProperty("flash.local.svn.username", ""));
                developFlashSvnPasswordjTextField.setText(prop.getProperty("flash.local.svn.password", ""));
                developFlashSvnPathjTextField.setText(prop.getProperty("flash.local.sysfile.path", ""));

                //#### online  php
                onlinePHPSvnUrljTextField.setText(prop.getProperty("php.online.svn.url", ""));
                onlinePHPSvnNamejTextField.setText(prop.getProperty("php.online.svn.username", ""));
                onlinePHPSvnPasswordjTextField.setText(prop.getProperty("php.online.svn.password", ""));
                onlinePHPSvnPathjTextField.setText(prop.getProperty("php.online.sysfile.path", ""));

                //#### develop  php
                developPHPSvnUrljTextField.setText(prop.getProperty("php.local.svn.url", ""));
                developPHPSvnNamejTextField.setText(prop.getProperty("php.local.svn.username", ""));
                developPHPSvnPasswordjTextField.setText(prop.getProperty("php.local.svn.password", ""));
                developPHPSvnPathjTextField.setText(prop.getProperty("php.local.sysfile.path", ""));
            }
        }).start();
        this.setVisible(visible);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        withABVersionjLabel = new javax.swing.JLabel();
        withABVersionjTextField = new javax.swing.JTextField();
        defaultVersionjLabel = new javax.swing.JLabel();
        defaultVersionjTextField = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        onlineResourceSvnUrljLabel = new javax.swing.JLabel();
        onlineResourceSvnUrljTextField = new javax.swing.JTextField();
        onlineResourceSvnNamejLabel = new javax.swing.JLabel();
        onlineResourceSvnNamejTextField = new javax.swing.JTextField();
        onlineResourceSvnPasswordjLabel = new javax.swing.JLabel();
        onlineResourceSvnPasswordjTextField = new javax.swing.JTextField();
        onlineResourceSvnPathjLabel = new javax.swing.JLabel();
        onlineResourceSvnPathjTextField = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        developResourceSvnUrljLabel = new javax.swing.JLabel();
        developResourceSvnUrljTextField = new javax.swing.JTextField();
        developResourceSvnNamejLabel = new javax.swing.JLabel();
        developResourceSvnNamejTextField = new javax.swing.JTextField();
        developResourceSvnPasswordjLabel1 = new javax.swing.JLabel();
        developResourceSvnPasswordjTextField = new javax.swing.JTextField();
        developResourceSvnPathjLabel1 = new javax.swing.JLabel();
        developResourceSvnPathjTextField = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        onlineFlashSvnUrljLabel = new javax.swing.JLabel();
        onlineFlashSvnUrljTextField = new javax.swing.JTextField();
        onlineFlashSvnNamejLabel = new javax.swing.JLabel();
        onlineFlashSvnNamejTextField = new javax.swing.JTextField();
        onlineFlashSvnPasswordjLabel = new javax.swing.JLabel();
        onlineFlashSvnPasswordjTextField = new javax.swing.JTextField();
        onlineFlashSvnPathjLabel = new javax.swing.JLabel();
        onlineFlashSvnPathjTextField = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        developFlashSvnUrljLabel = new javax.swing.JLabel();
        developFlashSvnUrljTextField = new javax.swing.JTextField();
        developFlashSvnNamejLabel = new javax.swing.JLabel();
        developFlashSvnNamejTextField = new javax.swing.JTextField();
        developFlashSvnPasswordjLabel = new javax.swing.JLabel();
        developFlashSvnPasswordjTextField = new javax.swing.JTextField();
        developFlashSvnPathjLabel = new javax.swing.JLabel();
        developFlashSvnPathjTextField = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        developPHPSvnUrljLabel = new javax.swing.JLabel();
        developPHPSvnUrljTextField = new javax.swing.JTextField();
        developPHPSvnNamejLabel = new javax.swing.JLabel();
        developPHPSvnNamejTextField = new javax.swing.JTextField();
        developPHPSvnPasswordjLabel = new javax.swing.JLabel();
        developPHPSvnPasswordjTextField = new javax.swing.JTextField();
        developPHPSvnPathjLabel = new javax.swing.JLabel();
        developPHPSvnPathjTextField = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        onlinePHPSvnUrljLabel = new javax.swing.JLabel();
        onlinePHPSvnUrljTextField = new javax.swing.JTextField();
        onlinePHPSvnNamejLabel = new javax.swing.JLabel();
        onlinePHPSvnNamejTextField = new javax.swing.JTextField();
        onlinePHPSvnPasswordjLabel = new javax.swing.JLabel();
        onlinePHPSvnPasswordjTextField = new javax.swing.JTextField();
        onlinePHPSvnPathjLabel = new javax.swing.JLabel();
        onlinePHPSvnPathjTextField = new javax.swing.JTextField();
        saveConfjButton = new javax.swing.JButton();
        canceljButton = new javax.swing.JButton();
        langListjLabel = new javax.swing.JLabel();
        langListjTextField = new javax.swing.JTextField();
        currentLangjLabel = new javax.swing.JLabel();
        currentLangjComboBox = new javax.swing.JComboBox();
        jarFilejLabel = new javax.swing.JLabel();
        jarFilejTextField = new javax.swing.JTextField();
        jarFilejButton = new javax.swing.JButton();
        jMenuBar = new javax.swing.JMenuBar();
        filejMenu = new javax.swing.JMenu();
        syncResourcesjMenuItem = new javax.swing.JMenuItem();
        editjMenu = new javax.swing.JMenu();
        editConfjMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        withABVersionjLabel.setText("区分ab版本：");

        defaultVersionjLabel.setText("默认版本：");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "线上resource", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Monospaced", 2, 12), new java.awt.Color(255, 102, 102))); // NOI18N

        onlineResourceSvnUrljLabel.setText("svn地址：");

        onlineResourceSvnNamejLabel.setText("svn用户名：");

        onlineResourceSvnPasswordjLabel.setText("svn密码：");

        onlineResourceSvnPathjLabel.setText("所在目录：");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(onlineResourceSvnPathjLabel)
                    .addComponent(onlineResourceSvnNamejLabel)
                    .addComponent(onlineResourceSvnUrljLabel)
                    .addComponent(onlineResourceSvnPasswordjLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(onlineResourceSvnNamejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(onlineResourceSvnPathjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(onlineResourceSvnUrljTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(onlineResourceSvnPasswordjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(onlineResourceSvnUrljLabel)
                    .addComponent(onlineResourceSvnUrljTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(onlineResourceSvnNamejLabel)
                    .addComponent(onlineResourceSvnNamejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(onlineResourceSvnPasswordjLabel)
                    .addComponent(onlineResourceSvnPasswordjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(onlineResourceSvnPathjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(onlineResourceSvnPathjLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "开发resource", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Monospaced", 2, 12), new java.awt.Color(255, 102, 102))); // NOI18N

        developResourceSvnUrljLabel.setText("svn地址：");

        developResourceSvnNamejLabel.setText("svn用户名：");

        developResourceSvnPasswordjLabel1.setText("svn密码：");

        developResourceSvnPathjLabel1.setText("所在目录：");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(developResourceSvnNamejLabel)
                            .addComponent(developResourceSvnUrljLabel)
                            .addComponent(developResourceSvnPasswordjLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(developResourceSvnNamejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(developResourceSvnPasswordjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(developResourceSvnUrljTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(developResourceSvnPathjLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(developResourceSvnPathjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(developResourceSvnUrljLabel)
                    .addComponent(developResourceSvnUrljTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(developResourceSvnNamejLabel)
                    .addComponent(developResourceSvnNamejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(developResourceSvnPasswordjLabel1)
                    .addComponent(developResourceSvnPasswordjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(developResourceSvnPathjLabel1)
                    .addComponent(developResourceSvnPathjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "线上flash", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Monospaced", 2, 12), new java.awt.Color(255, 102, 102))); // NOI18N

        onlineFlashSvnUrljLabel.setText("svn地址：");

        onlineFlashSvnNamejLabel.setText("svn用户名：");

        onlineFlashSvnPasswordjLabel.setText("svn密码：");

        onlineFlashSvnPathjLabel.setText("所在目录：");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(onlineFlashSvnPathjLabel)
                    .addComponent(onlineFlashSvnNamejLabel)
                    .addComponent(onlineFlashSvnUrljLabel)
                    .addComponent(onlineFlashSvnPasswordjLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(onlineFlashSvnNamejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(onlineFlashSvnPasswordjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(onlineFlashSvnPathjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(onlineFlashSvnUrljTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(onlineFlashSvnUrljLabel)
                    .addComponent(onlineFlashSvnUrljTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(onlineFlashSvnNamejLabel)
                    .addComponent(onlineFlashSvnNamejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(onlineFlashSvnPasswordjLabel)
                    .addComponent(onlineFlashSvnPasswordjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(onlineFlashSvnPathjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(onlineFlashSvnPathjLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "开发flash", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Monospaced", 2, 12), new java.awt.Color(255, 102, 102))); // NOI18N

        developFlashSvnUrljLabel.setText("svn地址：");

        developFlashSvnNamejLabel.setText("svn用户名：");

        developFlashSvnPasswordjLabel.setText("svn密码：");

        developFlashSvnPathjLabel.setText("所在目录：");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(developFlashSvnNamejLabel)
                            .addComponent(developFlashSvnUrljLabel)
                            .addComponent(developFlashSvnPasswordjLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(developFlashSvnNamejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(developFlashSvnPasswordjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(developFlashSvnUrljTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(developFlashSvnPathjLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(developFlashSvnPathjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(developFlashSvnUrljLabel)
                    .addComponent(developFlashSvnUrljTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(developFlashSvnNamejLabel)
                    .addComponent(developFlashSvnNamejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(developFlashSvnPasswordjLabel)
                    .addComponent(developFlashSvnPasswordjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(developFlashSvnPathjLabel)
                    .addComponent(developFlashSvnPathjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "开发PHP", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Monospaced", 2, 12), new java.awt.Color(255, 102, 102))); // NOI18N

        developPHPSvnUrljLabel.setText("svn地址：");

        developPHPSvnNamejLabel.setText("svn用户名：");

        developPHPSvnPasswordjLabel.setText("svn密码：");

        developPHPSvnPathjLabel.setText("所在目录：");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(developPHPSvnNamejLabel)
                            .addComponent(developPHPSvnUrljLabel)
                            .addComponent(developPHPSvnPasswordjLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(developPHPSvnNamejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(developPHPSvnPasswordjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(developPHPSvnUrljTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(developPHPSvnPathjLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(developPHPSvnPathjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(developPHPSvnUrljLabel)
                    .addComponent(developPHPSvnUrljTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(developPHPSvnNamejLabel)
                    .addComponent(developPHPSvnNamejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(developPHPSvnPasswordjLabel)
                    .addComponent(developPHPSvnPasswordjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(developPHPSvnPathjLabel)
                    .addComponent(developPHPSvnPathjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "线上PHP", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Monospaced", 2, 12), new java.awt.Color(255, 102, 102))); // NOI18N

        onlinePHPSvnUrljLabel.setText("svn地址：");

        onlinePHPSvnNamejLabel.setText("svn用户名：");

        onlinePHPSvnPasswordjLabel.setText("svn密码：");

        onlinePHPSvnPathjLabel.setText("所在目录：");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(onlinePHPSvnPathjLabel)
                    .addComponent(onlinePHPSvnNamejLabel)
                    .addComponent(onlinePHPSvnUrljLabel)
                    .addComponent(onlinePHPSvnPasswordjLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(onlinePHPSvnNamejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(onlinePHPSvnPasswordjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(onlinePHPSvnPathjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(onlinePHPSvnUrljTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(onlinePHPSvnUrljLabel)
                    .addComponent(onlinePHPSvnUrljTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(onlinePHPSvnNamejLabel)
                    .addComponent(onlinePHPSvnNamejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(onlinePHPSvnPasswordjLabel)
                    .addComponent(onlinePHPSvnPasswordjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(onlinePHPSvnPathjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(onlinePHPSvnPathjLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        saveConfjButton.setText("保存");
        saveConfjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveConfjButtonActionPerformed(evt);
            }
        });

        canceljButton.setText("取消");
        canceljButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                canceljButtonActionPerformed(evt);
            }
        });

        langListjLabel.setText("语言列表：");

        currentLangjLabel.setText("当前语言：");

        currentLangjComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                currentLangjComboBoxActionPerformed(evt);
            }
        });

        jarFilejLabel.setText("jar文件：");

        jarFilejTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jarFilejTextFieldMouseClicked(evt);
            }
        });

        jarFilejButton.setText("浏览");
        jarFilejButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jarFilejButtonActionPerformed(evt);
            }
        });

        filejMenu.setText("文件");

        syncResourcesjMenuItem.setText("同步");
        syncResourcesjMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                syncResourcesjMenuItemActionPerformed(evt);
            }
        });
        filejMenu.add(syncResourcesjMenuItem);

        jMenuBar.add(filejMenu);

        editjMenu.setText("编辑");

        editConfjMenuItem.setText("修改配置项(EN_US)");
        editConfjMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editConfjMenuItemActionPerformed(evt);
            }
        });
        editjMenu.add(editConfjMenuItem);

        jMenuBar.add(editjMenu);

        setJMenuBar(jMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(withABVersionjLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(withABVersionjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(defaultVersionjLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(defaultVersionjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(langListjLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(langListjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(currentLangjLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(currentLangjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(saveConfjButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(canceljButton))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(35, 35, 35)
                            .addComponent(jarFilejLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jarFilejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jarFilejButton))))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(withABVersionjLabel)
                    .addComponent(withABVersionjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(defaultVersionjLabel)
                    .addComponent(defaultVersionjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(langListjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(langListjLabel)
                    .addComponent(currentLangjLabel)
                    .addComponent(currentLangjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jarFilejLabel)
                    .addComponent(jarFilejTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jarFilejButton))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveConfjButton)
                    .addComponent(canceljButton))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveConfjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveConfjButtonActionPerformed

        String jarFilePath = jarFilejTextField.getText().trim();
        if (jarFilePath.isEmpty()) {
            MessageUtil.showMessageDialogMessage("请选择jar文件");
            return;
        } else if (!jarFilePath.endsWith("DeploymentSvnForDS.jar")) {
            MessageUtil.showMessageDialogMessage("请选择 DeploymentSvnForDS.jar 所在的正确目录");
            return;
        } else {
            File f = new File(jarFilePath);
            if (!f.exists()) {
                MessageUtil.showMessageDialogMessage("您请选择的‘" + jarFilePath + "’文件不存在");
                return;
            }
        }
        String newJarFilePath = jarFilePath + ".new";

        Map<String, String> propertiesMap = new HashMap<>();
        if (!langListjTextField.getText().trim().isEmpty()) {
            propertiesMap.put("langList", langListjTextField.getText().trim());
        }
        if (!withABVersionjTextField.getText().trim().isEmpty()) {
            propertiesMap.put("withABVersion", withABVersionjTextField.getText().trim());
        }
        if (!defaultVersionjTextField.getText().trim().isEmpty()) {
            propertiesMap.put("defaultVersion", defaultVersionjTextField.getText().trim());
        }
        //#### online  resources
        if (!onlineResourceSvnUrljTextField.getText().trim().isEmpty()) {
            propertiesMap.put("resources.online.svn.url", onlineResourceSvnUrljTextField.getText().trim());
        }
        if (!onlineResourceSvnNamejTextField.getText().trim().isEmpty()) {
            propertiesMap.put("resources.online.svn.username", onlineResourceSvnNamejTextField.getText().trim());
        }
        if (!onlineResourceSvnPasswordjTextField.getText().trim().isEmpty()) {
            propertiesMap.put("resources.online.svn.password", onlineResourceSvnPasswordjTextField.getText().trim());
        }
        if (!onlineResourceSvnPathjTextField.getText().trim().isEmpty()) {
            propertiesMap.put("resources.online.sysfile.path", onlineResourceSvnPathjTextField.getText().trim());
        }

        //#### develop  resources
        if (!developResourceSvnUrljTextField.getText().trim().isEmpty()) {
            propertiesMap.put("resources.local.svn.url", developResourceSvnUrljTextField.getText().trim());
        }
        if (!developResourceSvnNamejTextField.getText().trim().isEmpty()) {
            propertiesMap.put("resources.local.svn.username", developResourceSvnNamejTextField.getText().trim());
        }
        if (!developResourceSvnPasswordjTextField.getText().trim().isEmpty()) {
            propertiesMap.put("resources.local.svn.password", developResourceSvnPasswordjTextField.getText().trim());
        }
        if (!developResourceSvnPathjTextField.getText().trim().isEmpty()) {
            propertiesMap.put("resources.local.sysfile.path", developResourceSvnPathjTextField.getText().trim());
        }

        //#### online  flash
        if (!onlineFlashSvnUrljTextField.getText().trim().isEmpty()) {
            propertiesMap.put("flash.online.svn.url", onlineFlashSvnUrljTextField.getText().trim());
        }
        if (!onlineFlashSvnNamejTextField.getText().trim().isEmpty()) {
            propertiesMap.put("flash.online.svn.username", onlineFlashSvnNamejTextField.getText().trim());
        }
        if (!onlineFlashSvnPasswordjTextField.getText().trim().isEmpty()) {
            propertiesMap.put("flash.online.svn.password", onlineFlashSvnPasswordjTextField.getText().trim());
        }
        if (!onlineFlashSvnPathjTextField.getText().trim().isEmpty()) {
            propertiesMap.put("flash.online.sysfile.path", onlineFlashSvnPathjTextField.getText().trim());
        }

        //#### develop  flash
        if (!developFlashSvnUrljTextField.getText().trim().isEmpty()) {
            propertiesMap.put("flash.local.svn.url", developFlashSvnUrljTextField.getText().trim());
        }
        if (!developFlashSvnNamejTextField.getText().trim().isEmpty()) {
            propertiesMap.put("flash.local.svn.username", developFlashSvnNamejTextField.getText().trim());
        }
        if (!developFlashSvnPasswordjTextField.getText().trim().isEmpty()) {
            propertiesMap.put("flash.local.svn.password", developFlashSvnPasswordjTextField.getText().trim());
        }
        if (!developFlashSvnPathjTextField.getText().trim().isEmpty()) {
            propertiesMap.put("flash.local.sysfile.path", developFlashSvnPathjTextField.getText().trim());
        }

        //#### online  php
        if (!onlinePHPSvnUrljTextField.getText().trim().isEmpty()) {
            propertiesMap.put("php.online.svn.url", onlinePHPSvnUrljTextField.getText().trim());
        }
        if (!onlinePHPSvnNamejTextField.getText().trim().isEmpty()) {
            propertiesMap.put("php.online.svn.username", onlinePHPSvnNamejTextField.getText().trim());
        }
        if (!onlinePHPSvnPasswordjTextField.getText().trim().isEmpty()) {
            propertiesMap.put("php.online.svn.password", onlinePHPSvnPasswordjTextField.getText().trim());
        }
        if (!onlinePHPSvnPathjTextField.getText().trim().isEmpty()) {
            propertiesMap.put("php.online.sysfile.path", onlinePHPSvnPathjTextField.getText().trim());
        }

        //#### develop  php
        if (!developPHPSvnUrljTextField.getText().trim().isEmpty()) {
            propertiesMap.put("php.local.svn.url", developPHPSvnUrljTextField.getText().trim());
        }
        if (!developPHPSvnNamejTextField.getText().trim().isEmpty()) {
            propertiesMap.put("php.local.svn.username", developPHPSvnNamejTextField.getText().trim());
        }
        if (!developPHPSvnPasswordjTextField.getText().trim().isEmpty()) {
            propertiesMap.put("php.local.svn.password", developPHPSvnPasswordjTextField.getText().trim());
        }
        if (!developPHPSvnPathjTextField.getText().trim().isEmpty()) {
            propertiesMap.put("php.local.sysfile.path", developPHPSvnPathjTextField.getText().trim());
        }

        //保存
        String env = currentLangjComboBox.getSelectedItem().toString();
        String propFile = PropertiesUtil.getPropertyFilePath(env);
        try {
            PropertiesUtil.writeProperties(propFile, propertiesMap, jarFilePath, newJarFilePath);
        } catch (URISyntaxException ex) {
            Logger.getLogger(EditPropConfJFrame.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtil.showMessageDialogMessage("writeProperties:" + ex.getMessage());
        }

    }//GEN-LAST:event_saveConfjButtonActionPerformed

    public static void showMessageDialogMessage(String msg) {
        JOptionPane.showMessageDialog(null, msg, "错误信息提示", JOptionPane.ERROR_MESSAGE);
    }

    private void canceljButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_canceljButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_canceljButtonActionPerformed

    private void editConfjMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editConfjMenuItemActionPerformed
        //
    }//GEN-LAST:event_editConfjMenuItemActionPerformed

    private void syncResourcesjMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_syncResourcesjMenuItemActionPerformed
        ControllerJFrame.showDeploymentSvnForDSJFrame();
    }//GEN-LAST:event_syncResourcesjMenuItemActionPerformed

    private void currentLangjComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_currentLangjComboBoxActionPerformed
        String env = currentLangjComboBox.getSelectedItem().toString();
        loadPropAndShow(env);
    }//GEN-LAST:event_currentLangjComboBoxActionPerformed

    private void selectJarFile() {
        File f = new File(System.getProperty("user.dir"));//new File(EditPropConfJFrame.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        JFileChooser jfc = new JFileChooser(f);
        int result;
        jfc.setAcceptAllFileFilterUsed(false);//屏蔽“所有文件”
        jfc.addChoosableFileFilter(new FileNameExtensionFilter("Java Archive，Java 归档文件", "jar"));
        result = jfc.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = jfc.getSelectedFile();
            jarFilejTextField.setText(file.getPath());
        }
    }

    private void jarFilejButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jarFilejButtonActionPerformed
        selectJarFile();
    }//GEN-LAST:event_jarFilejButtonActionPerformed

    private void jarFilejTextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jarFilejTextFieldMouseClicked
        selectJarFile();
    }//GEN-LAST:event_jarFilejTextFieldMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EditPropConfJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditPropConfJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditPropConfJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditPropConfJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        try {
            javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(EditPropConfJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                EditPropConfJFrame ep = new EditPropConfJFrame();
                ep.setLocationRelativeTo(null);
                ep.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton canceljButton;
    private javax.swing.JComboBox currentLangjComboBox;
    private javax.swing.JLabel currentLangjLabel;
    private javax.swing.JLabel defaultVersionjLabel;
    private javax.swing.JTextField defaultVersionjTextField;
    private javax.swing.JLabel developFlashSvnNamejLabel;
    private javax.swing.JTextField developFlashSvnNamejTextField;
    private javax.swing.JLabel developFlashSvnPasswordjLabel;
    private javax.swing.JTextField developFlashSvnPasswordjTextField;
    private javax.swing.JLabel developFlashSvnPathjLabel;
    private javax.swing.JTextField developFlashSvnPathjTextField;
    private javax.swing.JLabel developFlashSvnUrljLabel;
    private javax.swing.JTextField developFlashSvnUrljTextField;
    private javax.swing.JLabel developPHPSvnNamejLabel;
    private javax.swing.JTextField developPHPSvnNamejTextField;
    private javax.swing.JLabel developPHPSvnPasswordjLabel;
    private javax.swing.JTextField developPHPSvnPasswordjTextField;
    private javax.swing.JLabel developPHPSvnPathjLabel;
    private javax.swing.JTextField developPHPSvnPathjTextField;
    private javax.swing.JLabel developPHPSvnUrljLabel;
    private javax.swing.JTextField developPHPSvnUrljTextField;
    private javax.swing.JLabel developResourceSvnNamejLabel;
    private javax.swing.JTextField developResourceSvnNamejTextField;
    private javax.swing.JLabel developResourceSvnPasswordjLabel1;
    private javax.swing.JTextField developResourceSvnPasswordjTextField;
    private javax.swing.JLabel developResourceSvnPathjLabel1;
    private javax.swing.JTextField developResourceSvnPathjTextField;
    private javax.swing.JLabel developResourceSvnUrljLabel;
    private javax.swing.JTextField developResourceSvnUrljTextField;
    private javax.swing.JMenuItem editConfjMenuItem;
    private javax.swing.JMenu editjMenu;
    private javax.swing.JMenu filejMenu;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JButton jarFilejButton;
    private javax.swing.JLabel jarFilejLabel;
    private javax.swing.JTextField jarFilejTextField;
    private javax.swing.JLabel langListjLabel;
    private javax.swing.JTextField langListjTextField;
    private javax.swing.JLabel onlineFlashSvnNamejLabel;
    private javax.swing.JTextField onlineFlashSvnNamejTextField;
    private javax.swing.JLabel onlineFlashSvnPasswordjLabel;
    private javax.swing.JTextField onlineFlashSvnPasswordjTextField;
    private javax.swing.JLabel onlineFlashSvnPathjLabel;
    private javax.swing.JTextField onlineFlashSvnPathjTextField;
    private javax.swing.JLabel onlineFlashSvnUrljLabel;
    private javax.swing.JTextField onlineFlashSvnUrljTextField;
    private javax.swing.JLabel onlinePHPSvnNamejLabel;
    private javax.swing.JTextField onlinePHPSvnNamejTextField;
    private javax.swing.JLabel onlinePHPSvnPasswordjLabel;
    private javax.swing.JTextField onlinePHPSvnPasswordjTextField;
    private javax.swing.JLabel onlinePHPSvnPathjLabel;
    private javax.swing.JTextField onlinePHPSvnPathjTextField;
    private javax.swing.JLabel onlinePHPSvnUrljLabel;
    private javax.swing.JTextField onlinePHPSvnUrljTextField;
    private javax.swing.JLabel onlineResourceSvnNamejLabel;
    private javax.swing.JTextField onlineResourceSvnNamejTextField;
    private javax.swing.JLabel onlineResourceSvnPasswordjLabel;
    private javax.swing.JTextField onlineResourceSvnPasswordjTextField;
    private javax.swing.JLabel onlineResourceSvnPathjLabel;
    private javax.swing.JTextField onlineResourceSvnPathjTextField;
    private javax.swing.JLabel onlineResourceSvnUrljLabel;
    private javax.swing.JTextField onlineResourceSvnUrljTextField;
    private javax.swing.JButton saveConfjButton;
    private javax.swing.JMenuItem syncResourcesjMenuItem;
    private javax.swing.JLabel withABVersionjLabel;
    private javax.swing.JTextField withABVersionjTextField;
    // End of variables declaration//GEN-END:variables
}
