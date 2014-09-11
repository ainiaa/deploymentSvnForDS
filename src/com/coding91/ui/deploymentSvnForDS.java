/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coding91.ui;

import com.coding91.WorkingCopyImprove;
import com.coding91.ui.ConsoleTextArea;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.wc.SVNRevision;
import sync.Sync;

/**
 *
 * @author Administrator
 */
public class deploymentSvnForDS extends javax.swing.JFrame {

    /**
     * Creates new form deploymentSvnForDS
     */
    public deploymentSvnForDS() {
//        ImageIcon iconImage = new ImageIcon("resources/images/sync.png");
//        this.setIconImage(iconImage.getImage());
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(
                getClass().getClassLoader().getResource("resources/images/sync.png")));//这个不能以 '/'开头
        //下面的方式可以设置成功
//        ImageIcon iconImage = SwingResourceManager.getIcon(deploymentSvnForDS.class, "/resources/images/sync.png");
//        this.setIconImage(iconImage.getImage());
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        syncjButton = new javax.swing.JButton();
        syncjScrollPane = new javax.swing.JScrollPane();
        jToolBar1 = new javax.swing.JToolBar();
        contentjPanel = new javax.swing.JPanel();
        contentResourcesjCheckBox = new javax.swing.JCheckBox();
        contentFlashjCheckBox = new javax.swing.JCheckBox();
        needSyncContentjCheckBox = new javax.swing.JCheckBox();
        environmentjPanel = new javax.swing.JPanel();
        envENjCheckBox = new javax.swing.JCheckBox();
        envFRjCheckBox = new javax.swing.JCheckBox();
        envDEjCheckBox = new javax.swing.JCheckBox();
        envSPjCheckBox = new javax.swing.JCheckBox();
        envPTjCheckBox = new javax.swing.JCheckBox();
        phpTagjPanel = new javax.swing.JPanel();
        aVersionTagjLabel = new javax.swing.JLabel();
        aVersionTagjTextField = new javax.swing.JTextField();
        bVersionTagjLabel = new javax.swing.JLabel();
        bVersionTagjTextField = new javax.swing.JTextField();
        needCreatePHPTagjCheckBox = new javax.swing.JCheckBox();
        canceljButton = new javax.swing.JButton();
        phpjPanel = new javax.swing.JPanel();
        originPHPTagjLabel = new javax.swing.JLabel();
        originPHPTagjTextField = new javax.swing.JTextField();
        dstPHPTagjLabel1 = new javax.swing.JLabel();
        dstPHPTagjTextField = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        syncjButton.setText("开始同步");
        syncjButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                syncjButtonMouseClicked(evt);
            }
        });

        syncjScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "同步结果", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Monospaced", 1, 14), new java.awt.Color(255, 0, 255))); // NOI18N
        syncjScrollPane.setAutoscrolls(true);

        jToolBar1.setRollover(true);

        contentjPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "同步内容", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Monospaced", 1, 14), new java.awt.Color(153, 51, 255))); // NOI18N

        contentResourcesjCheckBox.setText("resources");

        contentFlashjCheckBox.setText("flash");

        needSyncContentjCheckBox.setSelected(true);
        needSyncContentjCheckBox.setText(" 需要同步内容");

        javax.swing.GroupLayout contentjPanelLayout = new javax.swing.GroupLayout(contentjPanel);
        contentjPanel.setLayout(contentjPanelLayout);
        contentjPanelLayout.setHorizontalGroup(
            contentjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentjPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(contentResourcesjCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(contentFlashjCheckBox)
                .addGap(18, 18, 18)
                .addComponent(needSyncContentjCheckBox)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        contentjPanelLayout.setVerticalGroup(
            contentjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentjPanelLayout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addGroup(contentjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(contentResourcesjCheckBox)
                    .addComponent(contentFlashjCheckBox)
                    .addComponent(needSyncContentjCheckBox)))
        );

        environmentjPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "同步环境", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Monospaced", 1, 14), new java.awt.Color(51, 51, 255))); // NOI18N

        envENjCheckBox.setText("英语");

        envFRjCheckBox.setText("法语");

        envDEjCheckBox.setText("德语");

        envSPjCheckBox.setText("西班牙语");
        envSPjCheckBox.setEnabled(false);

        envPTjCheckBox.setText("葡萄牙语");
        envPTjCheckBox.setEnabled(false);

        javax.swing.GroupLayout environmentjPanelLayout = new javax.swing.GroupLayout(environmentjPanel);
        environmentjPanel.setLayout(environmentjPanelLayout);
        environmentjPanelLayout.setHorizontalGroup(
            environmentjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(environmentjPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(envENjCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(envFRjCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(envDEjCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(envSPjCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(envPTjCheckBox)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        environmentjPanelLayout.setVerticalGroup(
            environmentjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(environmentjPanelLayout.createSequentialGroup()
                .addGroup(environmentjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(envENjCheckBox)
                    .addComponent(envFRjCheckBox)
                    .addComponent(envDEjCheckBox)
                    .addComponent(envSPjCheckBox)
                    .addComponent(envPTjCheckBox))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        phpTagjPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "创建 PHP Tag", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Monospaced", 1, 14), new java.awt.Color(255, 51, 153))); // NOI18N

        aVersionTagjLabel.setText("A版本tag：");

        bVersionTagjLabel.setText("B版本tag：");

        needCreatePHPTagjCheckBox.setSelected(true);
        needCreatePHPTagjCheckBox.setText(" 需要新建 PHP tag");

        javax.swing.GroupLayout phpTagjPanelLayout = new javax.swing.GroupLayout(phpTagjPanel);
        phpTagjPanel.setLayout(phpTagjPanelLayout);
        phpTagjPanelLayout.setHorizontalGroup(
            phpTagjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(phpTagjPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(aVersionTagjLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(aVersionTagjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(bVersionTagjLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bVersionTagjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(needCreatePHPTagjCheckBox)
                .addContainerGap(206, Short.MAX_VALUE))
        );
        phpTagjPanelLayout.setVerticalGroup(
            phpTagjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(phpTagjPanelLayout.createSequentialGroup()
                .addGroup(phpTagjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(phpTagjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(bVersionTagjLabel)
                        .addComponent(bVersionTagjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(needCreatePHPTagjCheckBox))
                    .addGroup(phpTagjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(aVersionTagjLabel)
                        .addComponent(aVersionTagjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 8, Short.MAX_VALUE))
        );

        canceljButton.setText("取消同步");
        canceljButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                canceljButtonMouseClicked(evt);
            }
        });

        phpjPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "同步PHP", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Monospaced", 1, 14), new java.awt.Color(0, 204, 204))); // NOI18N

        originPHPTagjLabel.setText("源PHPtag：");

        originPHPTagjTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                originPHPTagjTextFieldFocusGained(evt);
            }
        });

        dstPHPTagjLabel1.setText("目的PHPtag：");

        javax.swing.GroupLayout phpjPanelLayout = new javax.swing.GroupLayout(phpjPanel);
        phpjPanel.setLayout(phpjPanelLayout);
        phpjPanelLayout.setHorizontalGroup(
            phpjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(phpjPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(originPHPTagjLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(originPHPTagjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(dstPHPTagjLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dstPHPTagjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        phpjPanelLayout.setVerticalGroup(
            phpjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(phpjPanelLayout.createSequentialGroup()
                .addGroup(phpjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(originPHPTagjLabel)
                    .addComponent(originPHPTagjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dstPHPTagjLabel1)
                    .addComponent(dstPHPTagjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 10, Short.MAX_VALUE))
        );

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(syncjScrollPane)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(environmentjPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(contentjPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(phpTagjPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(phpjPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(146, 146, 146)
                .addComponent(syncjButton)
                .addGap(223, 223, 223)
                .addComponent(canceljButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(environmentjPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contentjPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(phpTagjPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(phpjPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(canceljButton, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(syncjButton, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(14, 14, 14)
                .addComponent(syncjScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        syncjScrollPane.getAccessibleContext().setAccessibleName("sync result");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void canceljButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_canceljButtonMouseClicked
        int dialogResult = JOptionPane.showConfirmDialog(null, "你确定要取消同步？", "取消同步提示", JOptionPane.YES_NO_OPTION);
        if (JOptionPane.YES_OPTION == dialogResult) {
            this.dispose();
            System.exit(1);
        }
    }//GEN-LAST:event_canceljButtonMouseClicked

    public List<String> getEnv() {
        List envList = new ArrayList<>();
        if (envENjCheckBox.isSelected()) {//英语
            envList.add("en_us");
        }
        if (envFRjCheckBox.isSelected()) {//法语
            envList.add("fr_fr");
        }
        if (envDEjCheckBox.isSelected()) {//德语
            envList.add("de_de");
        }
        if (envSPjCheckBox.isSelected()) {//西班牙语
            envList.add("sp_sp");
        }
        if (envPTjCheckBox.isSelected()) {//葡萄牙语
            envList.add("pt_pt");
        }
        return envList;
    }

    /**
     * 获得同步的内容
     *
     * @return
     */
    public List<String> getContent() {
        List contentList = new ArrayList<>();
        if (contentResourcesjCheckBox.isSelected()) {//resource
            contentList.add("resource");
        }
        if (contentFlashjCheckBox.isSelected()) {//flash
            contentList.add("flash");
        }

        return contentList;
    }

    /**
     * 获得 PHP tag号
     *
     * @return
     */
    public Map<String, String> getPHPTag() {
        Map versionTagMap = new HashMap<>();
        versionTagMap.put("a", aVersionTagjTextField.getText().trim());//a版本 tag号
        versionTagMap.put("b", bVersionTagjTextField.getText().trim());//b版本 tag号
        return versionTagMap;
    }

    /**
     * 获得 PHP tag号
     *
     * @return
     */
    public Map<String, String> getPHPSync() {
        Map phpSyncMap = new HashMap<>();
        phpSyncMap.put("origin", originPHPTagjTextField.getText().trim());//源php tag号
        phpSyncMap.put("dest", dstPHPTagjTextField.getText().trim());//目标 tag号
        return phpSyncMap;
    }

    private static void showMessageDialogMessage(Exception ex) {
        String exMsg = ex.toString();
        JOptionPane.showMessageDialog(null, exMsg + new Throwable().getStackTrace()[1].toString(), "错误信息提示", JOptionPane.ERROR_MESSAGE);
    }

    private static void showMessageDialogMessage(String msg, String title) {
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
    }

    private static void showMessageDialogMessage(String msg) {
        JOptionPane.showMessageDialog(null, msg, "错误信息提示", JOptionPane.ERROR_MESSAGE);
    }

    private void syncjButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_syncjButtonMouseClicked
        List<String> envList = getEnv();
        List<String> contentList = getContent();
        Map<String, String> versionTagMap = getPHPTag();
        Map<String, String> phpSyncMap = getPHPSync();
        if (0 == envList.size()) {
            showMessageDialogMessage("请选择需要同步的环境");
            return;
        }
        if (0 == contentList.size() && needSyncContentjCheckBox.isSelected()) {
            showMessageDialogMessage("请选择需要同步的内容。如果不需要同步内容，请取消 ‘需要同步内容’ 勾选");
            return;
        }

        if (needCreatePHPTagjCheckBox.isSelected()) {//需要新建php tag 需要要输入a、b tag号
            if (versionTagMap.get("a").isEmpty()) {
                showMessageDialogMessage("请输入 A 版本tag号。如果不需要新建 php tag 请取消 ‘需要新建 PHP tag’ 勾选");
                return;
            }
            if (versionTagMap.get("b").isEmpty()) {
                showMessageDialogMessage("请输入 B 版本 tag 号。如果不需要新建 php tag 请取消 ‘ 需要新建 PHP tag’ 勾选");
                return;
            }
        }

        if (phpSyncMap.get("origin").isEmpty()) {
            showMessageDialogMessage("请输入 '源PHPtag'");
            return;
        }
        if (phpSyncMap.get("dest").isEmpty()) {
            showMessageDialogMessage("请输入 '目的PHPtag'");
            return;
        }
        syncjButton.setEnabled(false);
        try {
            printer();
        } catch (IOException ex) {
            Logger.getLogger(deploymentSvnForDS.class.getName()).log(Level.SEVERE, null, ex);
        }

//        Map<String, String> args = new HashMap<>();
//        args.put("source", "D:\\www\\framework");
//        args.put("target", "D:\\www\\frameworkbak");
//        args.put("force", "y");
//        Sync.syncMain(args);
        createPHPBranch("svn://svndev.shinezone.com/dev/Dessert_Shop/facebook/branches/dev_greenhouse/", "svn://svndev.shinezone.com/dev/Dessert_Shop/facebook/branches/dev_greenhousebak");
        updatePHPBranchToLocal();
    }//GEN-LAST:event_syncjButtonMouseClicked

    /**
     * 创建同步任务
     *
     * @return
     */
    public Map buildSyncTask() {

        return new HashMap();
    }

    /**
     * 执行同步任务
     *
     * @param taskMap
     */
    public void execSyncTask(Map taskMap) {
    }

    /**
     * 创建PHP分支
     */
    public static void createPHPBranch(String urlStr, String copyURLStr) {
        System.out.println("Copying '" + urlStr + "' to '" + copyURLStr + "'...");
        try {
            /*
             * makes a branch of url at copyURL - that is URL->URL copying
             * with history
             */
            SVNURL url = SVNURL.parseURIEncoded(urlStr);
            SVNURL copyURL = SVNURL.parseURIEncoded(copyURLStr);
            String commitMessage = "remotely copying '" + url + "' to '" + copyURL + "'";
            WorkingCopyImprove wc = new WorkingCopyImprove("en");
            long committedRevision = wc.copy(url, copyURL, false, commitMessage).getNewRevision();
        } catch (SVNException svne) {
        }
    }

    /**
     * 将php分支更新到本地
     */
    public void updatePHPBranchToLocal() {
        WorkingCopyImprove wc = new WorkingCopyImprove("en");
        try {
            wc.update(new File("D:\\www\\dessert\\code\\branches"), SVNRevision.HEAD, SVNDepth.INFINITY, false, false);
        } catch (SVNException ex) {
            Logger.getLogger(deploymentSvnForDS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 将php分支内容提交到服务器
     */
    public void commitPHPBranch() {

    }

    /**
     * 同步文件
     *
     * @param originDir
     * @param dstDir
     */
    public void syncFile(String originDir, String dstDir) {

    }


    private void originPHPTagjTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_originPHPTagjTextFieldFocusGained
        if (originPHPTagjTextField.getText().isEmpty() && !bVersionTagjTextField.getText().isEmpty()) {
            originPHPTagjTextField.setText(bVersionTagjTextField.getText());
        }
    }//GEN-LAST:event_originPHPTagjTextFieldFocusGained

    private void printer() throws IOException {
        ConsoleTextArea consoleTextArea = null;

        try {
            consoleTextArea = new ConsoleTextArea();
        } catch (IOException e) {
            System.err.println("cannot create LoopedStreams" + e);
            System.exit(1);
        }

        consoleTextArea.setFont(java.awt.Font.decode("monospaced"));
        consoleTextArea.setColumns(20);
        consoleTextArea.setRows(5);
        syncjScrollPane.setViewportView(consoleTextArea);
    }

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
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    break;
//                }
//            }
            javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(deploymentSvnForDS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(deploymentSvnForDS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(deploymentSvnForDS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(deploymentSvnForDS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new deploymentSvnForDS().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel aVersionTagjLabel;
    private javax.swing.JTextField aVersionTagjTextField;
    private javax.swing.JLabel bVersionTagjLabel;
    private javax.swing.JTextField bVersionTagjTextField;
    private javax.swing.JButton canceljButton;
    private javax.swing.JCheckBox contentFlashjCheckBox;
    private javax.swing.JCheckBox contentResourcesjCheckBox;
    private javax.swing.JPanel contentjPanel;
    private javax.swing.JLabel dstPHPTagjLabel1;
    private javax.swing.JTextField dstPHPTagjTextField;
    private javax.swing.JCheckBox envDEjCheckBox;
    private javax.swing.JCheckBox envENjCheckBox;
    private javax.swing.JCheckBox envFRjCheckBox;
    private javax.swing.JCheckBox envPTjCheckBox;
    private javax.swing.JCheckBox envSPjCheckBox;
    private javax.swing.JPanel environmentjPanel;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JCheckBox needCreatePHPTagjCheckBox;
    private javax.swing.JCheckBox needSyncContentjCheckBox;
    private javax.swing.JLabel originPHPTagjLabel;
    private javax.swing.JTextField originPHPTagjTextField;
    private javax.swing.JPanel phpTagjPanel;
    private javax.swing.JPanel phpjPanel;
    private javax.swing.JButton syncjButton;
    private javax.swing.JScrollPane syncjScrollPane;
    // End of variables declaration//GEN-END:variables
}