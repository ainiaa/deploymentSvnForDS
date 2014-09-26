/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coding91.ui;

import com.coding91.utility.ControllerJFrame;
import com.coding91.utility.MessageUtil;
import com.coding91.utility.WorkingCopyImprove;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.wc.SVNRevision;
import sync.Sync;

/**
 *
 * @author Administrator
 */
public class DeploymentSvnForDS extends javax.swing.JFrame {

    /**
     * Creates new form deploymentSvnForDS
     */
    public DeploymentSvnForDS() {
        
        try {
            javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DeploymentSvnForDS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(
                getClass().getClassLoader().getResource("resources/images/sync.png")));//这个不能以 '/'开头
        //下面的方式可以设置成功
//        ImageIcon iconImage = SwingResourceManager.getIcon(DeploymentSvnForDS.class, "/resources/images/sync.png");
//        this.setIconImage(iconImage.getImage());
        
        initComponents();

        try {
            printer();
        } catch (IOException ex) {
            Logger.getLogger(DeploymentSvnForDS.class.getName()).log(Level.SEVERE, null, ex);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                String special = "online";
                String content = "php";
                String env = "en_us";
                WorkingCopyImprove wc = new WorkingCopyImprove(env, special, content);
                List<String> branchList = getBranchOrTagListByEnvConf(wc, env, content, true);
                List<String> aBranchList = new ArrayList<>();
                List<String> bBranchList = new ArrayList<>();
                for (String branch : branchList) {
                    int dotLatestIndex = branch.lastIndexOf(".");
                    if (dotLatestIndex > 0) {
                        if (branch.substring(dotLatestIndex + 1).startsWith("1")) {//a版本
                            aBranchList.add(branch);
                        } else if (branch.substring(dotLatestIndex + 1).startsWith("2")) {//b版本
                            bBranchList.add(branch);
                        }
                    }
                }
                DefaultComboBoxModel<String> defaultComboBoxModel;
                if (aBranchList.size() > 0) {
                    String[] items = aBranchList.toArray(new String[]{});
                    defaultComboBoxModel = new DefaultComboBoxModel<>(items);
                    aOnlineTAGjComboBox.setModel(defaultComboBoxModel);
                } else {
                    String[] items = branchList.toArray(new String[]{});
                    defaultComboBoxModel = new DefaultComboBoxModel(items);
                    aOnlineTAGjComboBox.setModel(defaultComboBoxModel);
                }
                if (bBranchList.size() > 0) {
                    String[] items = bBranchList.toArray(new String[]{});
                    bOnlineTAGjComboBox.setModel(new DefaultComboBoxModel(items));
                } else {
                    String[] items = branchList.toArray(new String[]{});
                    bOnlineTAGjComboBox.setModel(new DefaultComboBoxModel(items));
                }

                newAVersionTagjTextField.setText(aOnlineTAGjComboBox.getSelectedItem().toString());
                newBVersionTagjTextField.setText(bOnlineTAGjComboBox.getSelectedItem().toString());

                bBranchList = null;
                aBranchList = null;
                branchList = null;

                //#########
                content = "flash";
                wc = new WorkingCopyImprove(env, special, content);
                List<String> flashList = getBranchOrTagListByEnvConf(wc, env, content, false);
                List<String> finalFlashList = new ArrayList<>();
                for (String branch : flashList) {
                    if (branch.startsWith("v")) {
                        finalFlashList.add(branch);
                    }
                }
                if (flashList.size() > 0) {
                    String[] items = finalFlashList.toArray(new String[]{});
                    flashVersionjComboBox.setModel(new DefaultComboBoxModel(items));
                }
                flashList = null;

            }
        }).start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        syncjScrollPane = new javax.swing.JScrollPane();
        jToolBar1 = new javax.swing.JToolBar();
        dsjProgressBar = new javax.swing.JProgressBar();
        contentjPanel = new javax.swing.JPanel();
        syncResourcesjButton = new javax.swing.JButton();
        syncFlashjButton = new javax.swing.JButton();
        commitResourcesjButton = new javax.swing.JButton();
        commitFlashjButton = new javax.swing.JButton();
        flashVersionjLabel = new javax.swing.JLabel();
        flashVersionjComboBox = new javax.swing.JComboBox();
        environmentjPanel = new javax.swing.JPanel();
        envENjCheckBox = new javax.swing.JCheckBox();
        envFRjCheckBox = new javax.swing.JCheckBox();
        envDEjCheckBox = new javax.swing.JCheckBox();
        envSPjCheckBox = new javax.swing.JCheckBox();
        envPTjCheckBox = new javax.swing.JCheckBox();
        phpTagjPanel = new javax.swing.JPanel();
        aVersionTagjLabel = new javax.swing.JLabel();
        newAVersionTagjTextField = new javax.swing.JTextField();
        bVersionTagjLabel = new javax.swing.JLabel();
        newBVersionTagjTextField = new javax.swing.JTextField();
        onlineTagjLabel = new javax.swing.JLabel();
        onlineTagjLabel1 = new javax.swing.JLabel();
        creatPHPTagjButton = new javax.swing.JButton();
        aOnlineTAGjComboBox = new javax.swing.JComboBox();
        bOnlineTAGjComboBox = new javax.swing.JComboBox();
        phpjPanel = new javax.swing.JPanel();
        originPHPTagjLabel = new javax.swing.JLabel();
        originPHPTagjTextField = new javax.swing.JTextField();
        dstPHPTagjLabel1 = new javax.swing.JLabel();
        dstPHPTagjTextField = new javax.swing.JTextField();
        syncPHPjButton = new javax.swing.JButton();
        jMenuBar = new javax.swing.JMenuBar();
        filejMenu = new javax.swing.JMenu();
        syncResourcesjMenuItem = new javax.swing.JMenuItem();
        editjMenu = new javax.swing.JMenu();
        editConfjMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        syncjScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "同步结果", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Monospaced", 1, 14), new java.awt.Color(255, 0, 255))); // NOI18N
        syncjScrollPane.setAutoscrolls(true);

        jToolBar1.setRollover(true);
        jToolBar1.add(dsjProgressBar);

        contentjPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "同步内容", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Monospaced", 1, 14), new java.awt.Color(153, 51, 255))); // NOI18N

        syncResourcesjButton.setText("开始同步 resources");
        syncResourcesjButton.setEnabled(false);
        syncResourcesjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                syncResourcesjButtonActionPerformed(evt);
            }
        });

        syncFlashjButton.setText("开始同步 flash");
        syncFlashjButton.setEnabled(false);
        syncFlashjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                syncFlashjButtonActionPerformed(evt);
            }
        });

        commitResourcesjButton.setText("提交resource到svn");
        commitResourcesjButton.setEnabled(false);
        commitResourcesjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commitResourcesjButtonActionPerformed(evt);
            }
        });

        commitFlashjButton.setText("提交flash到svn");
        commitFlashjButton.setEnabled(false);
        commitFlashjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commitFlashjButtonActionPerformed(evt);
            }
        });

        flashVersionjLabel.setText("flash版本:");

        flashVersionjComboBox.setEditable(true);

        javax.swing.GroupLayout contentjPanelLayout = new javax.swing.GroupLayout(contentjPanel);
        contentjPanel.setLayout(contentjPanelLayout);
        contentjPanelLayout.setHorizontalGroup(
            contentjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentjPanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(syncResourcesjButton)
                .addGap(18, 18, 18)
                .addComponent(commitResourcesjButton)
                .addGap(33, 33, 33)
                .addComponent(flashVersionjLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(flashVersionjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(syncFlashjButton, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(commitFlashjButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        contentjPanelLayout.setVerticalGroup(
            contentjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentjPanelLayout.createSequentialGroup()
                .addGroup(contentjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(syncResourcesjButton)
                    .addComponent(syncFlashjButton)
                    .addComponent(commitResourcesjButton)
                    .addComponent(commitFlashjButton)
                    .addComponent(flashVersionjLabel)
                    .addComponent(flashVersionjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        environmentjPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "同步环境", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Monospaced", 1, 14), new java.awt.Color(51, 51, 255))); // NOI18N

        envENjCheckBox.setText("英语");
        envENjCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                envENjCheckBoxActionPerformed(evt);
            }
        });

        envFRjCheckBox.setText("法语");
        envFRjCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                envFRjCheckBoxActionPerformed(evt);
            }
        });

        envDEjCheckBox.setText("德语");
        envDEjCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                envDEjCheckBoxActionPerformed(evt);
            }
        });

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

        aVersionTagjLabel.setText("新建A版本 TAG：");

        bVersionTagjLabel.setText("新建B版本 TAG：");

        onlineTagjLabel.setText("线上A版本 TAG：");

        onlineTagjLabel1.setText("线上B版本 TAG：");

        creatPHPTagjButton.setText("开始创建 PHP TAG");
        creatPHPTagjButton.setEnabled(false);
        creatPHPTagjButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                creatPHPTagjButtonActionPerformed(evt);
            }
        });

        aOnlineTAGjComboBox.setEditable(true);
        aOnlineTAGjComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aOnlineTAGjComboBoxActionPerformed(evt);
            }
        });

        bOnlineTAGjComboBox.setEditable(true);
        bOnlineTAGjComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bOnlineTAGjComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout phpTagjPanelLayout = new javax.swing.GroupLayout(phpTagjPanel);
        phpTagjPanel.setLayout(phpTagjPanelLayout);
        phpTagjPanelLayout.setHorizontalGroup(
            phpTagjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(phpTagjPanelLayout.createSequentialGroup()
                .addGroup(phpTagjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(phpTagjPanelLayout.createSequentialGroup()
                        .addGap(94, 94, 94)
                        .addGroup(phpTagjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(phpTagjPanelLayout.createSequentialGroup()
                                .addComponent(onlineTagjLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bOnlineTAGjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(phpTagjPanelLayout.createSequentialGroup()
                                .addComponent(onlineTagjLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(aOnlineTAGjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(phpTagjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(phpTagjPanelLayout.createSequentialGroup()
                                .addComponent(bVersionTagjLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(newBVersionTagjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(phpTagjPanelLayout.createSequentialGroup()
                                .addComponent(aVersionTagjLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(newAVersionTagjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(phpTagjPanelLayout.createSequentialGroup()
                        .addGap(235, 235, 235)
                        .addComponent(creatPHPTagjButton)))
                .addContainerGap(325, Short.MAX_VALUE))
        );
        phpTagjPanelLayout.setVerticalGroup(
            phpTagjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(phpTagjPanelLayout.createSequentialGroup()
                .addGroup(phpTagjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(aVersionTagjLabel)
                    .addComponent(newAVersionTagjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(onlineTagjLabel)
                    .addComponent(aOnlineTAGjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(phpTagjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bVersionTagjLabel)
                    .addComponent(newBVersionTagjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(onlineTagjLabel1)
                    .addComponent(bOnlineTAGjComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(creatPHPTagjButton)
                .addContainerGap())
        );

        phpjPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "同步PHP", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Monospaced", 1, 14), new java.awt.Color(0, 204, 204))); // NOI18N

        originPHPTagjLabel.setText("源PHPtag：");

        originPHPTagjTextField.setEnabled(false);
        originPHPTagjTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                originPHPTagjTextFieldFocusGained(evt);
            }
        });

        dstPHPTagjLabel1.setText("目的PHPtag：");

        dstPHPTagjTextField.setEnabled(false);

        syncPHPjButton.setText("开始同步 PHP");
        syncPHPjButton.setEnabled(false);

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
                .addGap(37, 37, 37)
                .addComponent(syncPHPjButton)
                .addContainerGap(320, Short.MAX_VALUE))
        );
        phpjPanelLayout.setVerticalGroup(
            phpjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(phpjPanelLayout.createSequentialGroup()
                .addGroup(phpjPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(originPHPTagjLabel)
                    .addComponent(originPHPTagjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dstPHPTagjLabel1)
                    .addComponent(dstPHPTagjTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(syncPHPjButton))
                .addGap(0, 8, Short.MAX_VALUE))
        );

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
            .addComponent(syncjScrollPane)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(environmentjPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(contentjPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(phpTagjPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(phpjPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(environmentjPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contentjPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(phpTagjPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(phpjPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(syncjScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        syncjScrollPane.getAccessibleContext().setAccessibleName("sync result");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public List<String> getEnv() {
        List<String> envList = new ArrayList<>();
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
     * 获得 PHP tag号
     *
     * @return
     */
    public Map<String, String> getPHPSync() {
        Map<String, String> phpSyncMap = new HashMap<>();
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

    private String getLocalPathKey(int version, boolean isOnline) {

        if (isOnline) {
            if (version == 0) {//a版本
                return "php.online.sysfile.path";
            } else if (version == 1) {//b版本
                return "php.online.sysfile.path";
            }
        } else {
            if (version == 0) {//a版本
                return "php.local.sysfile.path";
            } else if (version == 1) {//b版本
                return "php.local.sysfile.path";
            }
        }
        return "";
    }

    private Map<String, String> buildFileSyncArgs(String source, String target) {
        Map<String, String> args = new HashMap<>();
        args.put("source", source);
        args.put("target", target);
//        args.put("force", "y");//不能删除 
        args.put("rename", "n");
        args.put("synctime", "n");
        args.put("overwrite", "y");
        args.put("customExclude", "{.git,.idea,.svn,.settings,.project,.buildpath}");//忽略.git,.idea,.svn,.settings,.project,.buildpath文件      {.git,.idea,*.php} 忽略 .git  .idea 和 .php文件
        args.put("path", "1");
        return args;
    }

    /**
     * 是否需要创建 A 版本分支
     *
     * @param env
     * @param wc
     * @return
     */
    public boolean needCreateAVersionOfPHPBranch(String env, WorkingCopyImprove wc) {
        return wc.getConfByEnv(env).get("withABVersion").equals("1");
    }

    /**
     * 创建PHP分支
     *
     * @param wc
     * @param originTag
     * @param dstTag
     * @param env
     * @param version
     */
    public static void createPHPBranchByTag(WorkingCopyImprove wc, String originTag, String dstTag, String env, int version) {
        try {
            String commitMessage = String.format("remotely create PHP Branch/Tag '%s' to '%s' ", originTag, dstTag);
            wc.createBranchOrTagByEnvConf(env, originTag, dstTag, commitMessage, true, version);
        } catch (SVNException svne) {
            System.err.printf("remotely create PHP Branch/Tag '%s' to '%s'  error: %s\r\n", originTag, dstTag, svne.getErrorMessage());
            System.exit(-1);
        }
    }
    
    public static List<String> getBranchOrTagListByEnvConf(WorkingCopyImprove wc, String env, String content, boolean isOnline) {
        try {
            List<String> branchList = new ArrayList<>();
            Map<String, Date> branchMap = wc.getBranchOrTagListByEnvConf(env, content, isOnline);
            for (Map.Entry<String, Date> entry : branchMap.entrySet()) {
                branchList.add(entry.getKey());
            }
            return branchList;
        } catch (SVNException svne) {
            System.err.println("getBranchOrTagListByEnvConf error: ----------- " + svne.getErrorMessage());
            System.exit(-1);
        }

        return null;
    }

    public static List<String> getBranchOrTagListByEnvConf(WorkingCopyImprove wc, String env, boolean isOnline) {
        try {
            List<String> branchList = new ArrayList<>();
            Map<String, Date> branchMap = wc.getBranchOrTagListByEnvConf(env, isOnline);
            for (Map.Entry<String, Date> entry : branchMap.entrySet()) {
                branchList.add(entry.getKey());
            }
            return branchList;
        } catch (SVNException svne) {
            System.err.println("getBranchOrTagListByEnvConf error: ----------- " + svne.getErrorMessage());
            System.exit(-1);
        }

        return null;
    }

    public static String getLatestBranchOrTagListByEnvConf(WorkingCopyImprove wc, String env, boolean isOnline) {
        try {
            Map<String, Date> branchMap = wc.getBranchOrTagListByEnvConf(env, isOnline);
            for (Map.Entry<String, Date> entry : branchMap.entrySet()) {
                return entry.getKey();
            }
        } catch (SVNException svne) {
            System.err.println("getLatestBranchOrTagListByEnvConf error: ----------- " + svne.getErrorMessage());
        }

        return null;
    }

    /**
     * 将php分支更新到本地
     *
     * @param wc
     * @param env
     * @param localPathKey
     */
    public void updatePHPBranchToLocal(WorkingCopyImprove wc, String env, String localPathKey) {
        try {
            wc.updateToLocalByEnvConf(localPathKey, env, SVNRevision.HEAD, SVNDepth.INFINITY, false, false);
        } catch (SVNException ex) {
            System.err.println("updatePHPBranchToLocal '" + env + "': '" + localPathKey + "'  error: -----------" + ex.getErrorMessage());
            System.exit(-1);
        }
    }

    /**
     * 将php分支内容提交到服务器
     *
     * @param wc
     * @param wcDir
     * @param commitMessage
     * @param needAdd
     */
    public void commitPHPBranch(WorkingCopyImprove wc, String wcDir, String commitMessage, boolean needAdd) {
        try {
            wc.commit(new File(wcDir), false, commitMessage, true);
        } catch (SVNException ex) {
            Logger.getLogger(DeploymentSvnForDS.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("commitPHPBranch '" + "  wcDir: " + wcDir + "'  error: -----------" + ex.getErrorMessage());
            System.out.println("commitPHPBranch '" + "  wcDir: " + wcDir + "'  error: -----------" + ex.getErrorMessage());
//            System.exit(-1);
        }
    }

    /**
     * 将php分支内容提交到服务器
     *
     * @param wc
     * @param wcDir
     * @param commitMessage
     */
    public void commitPHPBranch(WorkingCopyImprove wc, String wcDir, String commitMessage) {
        try {
            wc.commit(new File(wcDir), false, commitMessage, true);
        } catch (SVNException ex) {
            Logger.getLogger(DeploymentSvnForDS.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("commitPHPBranch '" + "  wcDir: " + wcDir + "'  error: -----------" + ex.getErrorMessage());
            System.exit(-1);
        }
    }

    /**
     * 同步文件
     *
     * @param originDir
     * @param dstDir
     */
    public void syncFile(String originDir, String dstDir) {
        Map<String, String> args = buildFileSyncArgs(originDir, dstDir);
        Sync.syncMain(args);
    }

    public void syncFile(String originDir, String dstDir, boolean simulateOnly) {
        Map<String, String> args = buildFileSyncArgs(originDir, dstDir);

        if (simulateOnly) {
            args.put("simulate", "1");
            args.remove("force");
        }
        Sync.syncMain(args);
    }


    private void originPHPTagjTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_originPHPTagjTextFieldFocusGained
        if (originPHPTagjTextField.getText().isEmpty() && !newBVersionTagjTextField.getText().isEmpty()) {
            originPHPTagjTextField.setText(newBVersionTagjTextField.getText());
        }
    }//GEN-LAST:event_originPHPTagjTextFieldFocusGained

    /**
     *
     * @param envList
     * @param contentList
     * @param versionTagMap
     * @param phpSyncMap
     * @return
     */
    private boolean isSyncValid(List<String> envList, List<String> contentList, Map<String, String> versionTagMap, Map<String, String> phpSyncMap) {
        if (0 == envList.size()) {
            MessageUtil.showMessageDialogMessage("请选择需要同步的环境");
            return false;
        }

//        todo
//        if (phpSyncMap.get("origin").trim().isEmpty()) {
//            MessageUtil.showMessageDialogMessage("请输入 '源PHPtag'");
//            return;
//        }
//        if (phpSyncMap.get("dest").trim().isEmpty()) {
//            MessageUtil.showMessageDialogMessage("请输入 '目的PHPtag'");
//            return;
//        }
        return true;
    }

    private void syncResourcesjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_syncResourcesjButtonActionPerformed
        syncTemplate("resources", "syncResourcesjButton");
    }//GEN-LAST:event_syncResourcesjButtonActionPerformed

    private void syncTemplate(final String content, final String buttonName) {
        disableAllButton();
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> envList = getEnv();
                int envCount = envList.size();
                String env;
                if (0 == envCount) {
                    MessageUtil.showMessageDialogMessage("请选择需要同步的环境");
                    return;
                }

                for (int envNo = 0; envNo < envCount; envNo++) {
                    env = envList.get(envNo);
                    WorkingCopyImprove wc;//new WorkingCopyImprove(env);

                    String special = "local";
                    String localContentPathKey = String.format("%s.%s.sysfile.path", content, special);
                    wc = new WorkingCopyImprove(env, special, content);
                    String localContentPath = wc.getConfByEnv(env).get(localContentPathKey);
                    updatePHPBranchToLocal(wc, env, localContentPathKey);//更新 content  local 

                    special = "online";
                    String onlineContentPathKey = String.format("%s.%s.sysfile.path", content, special);
                    wc = new WorkingCopyImprove(env, special, content);
                    String onlineContentPath = wc.getConfByEnv(env).get(onlineContentPathKey);
                    updatePHPBranchToLocal(wc, env, onlineContentPathKey);//更新 content  online 

                    //将 content local 的内容同步到 content online 
                    Map<String, String> args;
                    if ("flash".equals(content)) {
                        String flash = flashVersionjComboBox.getSelectedItem().toString();
                        args = buildFileSyncArgs(localContentPath + flash, onlineContentPath);
                    } else {
                        args = buildFileSyncArgs(localContentPath, onlineContentPath);
                    }

                    Sync.syncMain(args);

                    //更新content online svn
//                    commitPHPBranch(wc, onlineContentPath, "commit by DeploymentSvnForDS");
//            commitPHPBranch(env, onlineContentPath, "commit by DeploymentSvnForDS");
                    changeButtonEnableExcept(buttonName);
                }
            }
        }).start();
    }

    private int selectedCheckboxCount = 0;

    private void syncFlashjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_syncFlashjButtonActionPerformed
        if (flashVersionjComboBox.getSelectedItem() == null || flashVersionjComboBox.getSelectedItem().toString().isEmpty()) {
            MessageUtil.showMessageDialogMessage("请选择需要同步的flash");
            return;
        }
        syncTemplate("flash", "syncFlashjButton");
    }//GEN-LAST:event_syncFlashjButtonActionPerformed

    private boolean isEnvSelected() {
        return (envENjCheckBox.isSelected() || envFRjCheckBox.isSelected() || envDEjCheckBox.isSelected());
    }

    private int calcCheckBoxCount() {
        selectedCheckboxCount = 0;
        if (envENjCheckBox.isSelected()) {
            selectedCheckboxCount += 1;
        }
        if (envFRjCheckBox.isSelected()) {
            selectedCheckboxCount += 1;
        }
        if (envDEjCheckBox.isSelected()) {
            selectedCheckboxCount += 1;
        }
        return selectedCheckboxCount;
    }

    private void envENjCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_envENjCheckBoxActionPerformed
        int calc = calcCheckBoxCount();
        if (isEnvSelected()) {
            boolean exceptCommitButton = (calc > 1);
            enableAllButton(exceptCommitButton);
        } else {
            disableAllButton();
        }
    }//GEN-LAST:event_envENjCheckBoxActionPerformed

    private void envFRjCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_envFRjCheckBoxActionPerformed
        int calc = calcCheckBoxCount();
        if (isEnvSelected()) {
            boolean exceptCommitButton = (calc > 1);
            enableAllButton(exceptCommitButton);
        } else {
            disableAllButton();
        }
    }//GEN-LAST:event_envFRjCheckBoxActionPerformed

    private void envDEjCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_envDEjCheckBoxActionPerformed
        int calc = calcCheckBoxCount();
        if (isEnvSelected()) {
            boolean exceptCommitButton = (calc > 1);
            enableAllButton(exceptCommitButton);
        } else {
            disableAllButton();
        }
    }//GEN-LAST:event_envDEjCheckBoxActionPerformed

    private void aOnlineTAGjComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aOnlineTAGjComboBoxActionPerformed
        newAVersionTagjTextField.setText(aOnlineTAGjComboBox.getSelectedItem().toString());
    }//GEN-LAST:event_aOnlineTAGjComboBoxActionPerformed

    private void creatPHPTagjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_creatPHPTagjButtonActionPerformed
        disableAllButton();
        final List<String> envList = getEnv();
        if (0 == envList.size()) {
            MessageUtil.showMessageDialogMessage("请选择需要同步的环境");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                int envCount = envList.size();
                String env;
                for (int envNo = 0; envNo < envCount; envNo++) {
                    env = envList.get(envNo);
                    String bOriginTag = bOnlineTAGjComboBox.getSelectedItem().toString().trim();
                    String bDstTag = newBVersionTagjTextField.getText().trim();
                    if (bOriginTag.isEmpty()) {
                        MessageUtil.showMessageDialogMessage("请正确填写 ‘线上B版本 TAG’ 内容");
                        return;
                    }
                    if (bDstTag.isEmpty()) {
                        MessageUtil.showMessageDialogMessage("请正确填写 ‘新建B版本 TAG：’ 内容");
                        return;
                    }
                    String special = "online";
                    String content = "php";
                    WorkingCopyImprove wc = new WorkingCopyImprove(env, special, content);
                    //b版本
                    createPHPBranchByTag(wc, bOriginTag, bDstTag, env, 1);//创建 tag  b版本

                    if (needCreateAVersionOfPHPBranch(env, wc)) {//需要创建 tag  a版本
                        String aOriginTag = aOnlineTAGjComboBox.getSelectedItem().toString().trim();
                        String aDstTag = newAVersionTagjTextField.getText().trim();
                        if (aOriginTag.isEmpty()) {
                            MessageUtil.showMessageDialogMessage("请正确填写 ‘线上A版本 TAG’ 内容");
                            return;
                        }
                        if (aDstTag.isEmpty()) {
                            MessageUtil.showMessageDialogMessage("请正确填写 ‘新建A版本 TAG：’ 内容");
                            return;
                        }
                        createPHPBranchByTag(wc, aOriginTag, aDstTag, env, 0);//创建 tag
                    }

                    updatePHPBranchToLocal(wc, env, "php.online.sysfile.path");//将线上代码update到本地

                    special = "local";
                    content = "php";
                    wc = new WorkingCopyImprove(env, special, content);
                    updatePHPBranchToLocal(wc, env, "php.local.sysfile.path");//将develop代码update到本地
                }
            }
        }).start();

        changeButtonEnableExcept("creatPHPTagjButton");
    }//GEN-LAST:event_creatPHPTagjButtonActionPerformed

    private void bOnlineTAGjComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bOnlineTAGjComboBoxActionPerformed
        newBVersionTagjTextField.setText(bOnlineTAGjComboBox.getSelectedItem().toString());
    }//GEN-LAST:event_bOnlineTAGjComboBoxActionPerformed


    private void commitResourcesjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commitResourcesjButtonActionPerformed
        disableAllButton();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String special = "online";
                String content = "resources";
                List<String> envList = getEnv();
                String env = envList.get(0);
                String onlineContentPathKey = String.format("%s.%s.sysfile.path", content, special);
                WorkingCopyImprove wc;
                wc = new WorkingCopyImprove(env, special, content);
                String onlineContentPath = wc.getConfByEnv(env).get(onlineContentPathKey);
                try {
                    Process process = Runtime.getRuntime().exec("TortoiseProc.exe /command:commit /path:" + onlineContentPath + " /logmsg:\"commit resource to online env\" /closeonend:2");
                    int result = process.waitFor();
                    changeButtonEnableExcept("commitFlashjButton");
                    if (result == -1) {
                        System.out.println("commit canceled");
                    } else if (result == 0) {
                        System.out.println("commit done!");
                    }
                } catch (IOException | InterruptedException ex) {
                    Logger.getLogger(DeploymentSvnForDS.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }//GEN-LAST:event_commitResourcesjButtonActionPerformed

    private void commitFlashjButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commitFlashjButtonActionPerformed
        disableAllButton();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String special = "online";
                String content = "flash";
                List<String> envList = getEnv();
                String env = envList.get(0);
                String onlineContentPathKey = String.format("%s.%s.sysfile.path", content, special);
                WorkingCopyImprove wc;
                wc = new WorkingCopyImprove(env, special, content);
                String onlineContentPath = wc.getConfByEnv(env).get(onlineContentPathKey);
                try {
                    Process process = Runtime.getRuntime().exec("TortoiseProc.exe /command:commit /path:" + onlineContentPath + " /logmsg:\"commit flash to online env\" /closeonend:2");
                    int result = process.waitFor();
                    changeButtonEnableExcept("commitResourcesjButton");
                    if (result == -1) {
                        System.out.println("commit canceled");
                    } else if (result == 0) {
                        System.out.println("commit done!");
                    }
                } catch (IOException | InterruptedException ex) {
                    Logger.getLogger(DeploymentSvnForDS.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }//GEN-LAST:event_commitFlashjButtonActionPerformed

    private void editConfjMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editConfjMenuItemActionPerformed
        ControllerJFrame.showEditPropConfJFrame();
    }//GEN-LAST:event_editConfjMenuItemActionPerformed

    private void syncResourcesjMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_syncResourcesjMenuItemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_syncResourcesjMenuItemActionPerformed

    private void testCommit() {
        WorkingCopyImprove wc;//new WorkingCopyImprove(env);

        String special = "local";
        String content = "php";
        String env = "en_us";
        String localContentPathKey = String.format("%s.%s.sysfile.path", content, special);
        wc = new WorkingCopyImprove(env, special, content);
        String localContentPath = wc.getConfByEnv(env).get(localContentPathKey);
        commitPHPBranch(wc, localContentPath, "commit by deploymentSvnForDS local ");
    }

    public void changeButtonEnableExcept(String buttonName) {
        switch (buttonName) {
            case "syncFlashjButton":
                syncResourcesjButton.setEnabled(true);
                creatPHPTagjButton.setEnabled(true);
                commitResourcesjButton.setEnabled(true);
                commitFlashjButton.setEnabled(true);
                break;
            case "syncResourcesjButton":
                syncFlashjButton.setEnabled(true);
                creatPHPTagjButton.setEnabled(true);
                commitResourcesjButton.setEnabled(true);
                commitFlashjButton.setEnabled(true);
                break;
            case "creatPHPTagjButton":
                syncFlashjButton.setEnabled(true);
                syncResourcesjButton.setEnabled(true);
                commitResourcesjButton.setEnabled(true);
                commitFlashjButton.setEnabled(true);
                break;
            case "commitResourcesjButton":
                syncResourcesjButton.setEnabled(true);
                creatPHPTagjButton.setEnabled(true);
                syncFlashjButton.setEnabled(true);
                commitFlashjButton.setEnabled(true);
                break;
            case "commitFlashjButton":
                syncResourcesjButton.setEnabled(true);
                creatPHPTagjButton.setEnabled(true);
                syncFlashjButton.setEnabled(true);
                commitResourcesjButton.setEnabled(true);
                break;
        }
    }

    private void enableAllButton(boolean exceptCommitButton) {
        syncFlashjButton.setEnabled(true);
        syncResourcesjButton.setEnabled(true);
        creatPHPTagjButton.setEnabled(true);
        if (!exceptCommitButton) {
            commitResourcesjButton.setEnabled(true);
            commitFlashjButton.setEnabled(true);
        } else {
            commitResourcesjButton.setEnabled(false);
            commitFlashjButton.setEnabled(false);
        }

    }

    private void disableAllButton() {
        commitResourcesjButton.setEnabled(false);
        commitFlashjButton.setEnabled(false);
        syncFlashjButton.setEnabled(false);
        syncResourcesjButton.setEnabled(false);
        creatPHPTagjButton.setEnabled(false);
    }

    private void printer() throws IOException {
        ConsoleTextArea consoleTextArea;

        try {
            consoleTextArea = new ConsoleTextArea();
            consoleTextArea.setFont(java.awt.Font.decode("monospaced"));
            consoleTextArea.setColumns(20);
            consoleTextArea.setRows(5);
            syncjScrollPane.setViewportView(consoleTextArea);
        } catch (IOException e) {
            System.err.println("cannot create LoopedStreams" + e);
            System.exit(1);
        }

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
            java.util.logging.Logger.getLogger(DeploymentSvnForDS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DeploymentSvnForDS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DeploymentSvnForDS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DeploymentSvnForDS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DeploymentSvnForDS ds = new DeploymentSvnForDS();
                ds.setLocationRelativeTo(null);
                ds.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox aOnlineTAGjComboBox;
    private javax.swing.JLabel aVersionTagjLabel;
    private javax.swing.JComboBox bOnlineTAGjComboBox;
    private javax.swing.JLabel bVersionTagjLabel;
    private javax.swing.JButton commitFlashjButton;
    private javax.swing.JButton commitResourcesjButton;
    private javax.swing.JPanel contentjPanel;
    private javax.swing.JButton creatPHPTagjButton;
    private javax.swing.JProgressBar dsjProgressBar;
    private javax.swing.JLabel dstPHPTagjLabel1;
    private javax.swing.JTextField dstPHPTagjTextField;
    private javax.swing.JMenuItem editConfjMenuItem;
    private javax.swing.JMenu editjMenu;
    private javax.swing.JCheckBox envDEjCheckBox;
    private javax.swing.JCheckBox envENjCheckBox;
    private javax.swing.JCheckBox envFRjCheckBox;
    private javax.swing.JCheckBox envPTjCheckBox;
    private javax.swing.JCheckBox envSPjCheckBox;
    private javax.swing.JPanel environmentjPanel;
    private javax.swing.JMenu filejMenu;
    private javax.swing.JComboBox flashVersionjComboBox;
    private javax.swing.JLabel flashVersionjLabel;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField newAVersionTagjTextField;
    private javax.swing.JTextField newBVersionTagjTextField;
    private javax.swing.JLabel onlineTagjLabel;
    private javax.swing.JLabel onlineTagjLabel1;
    private javax.swing.JLabel originPHPTagjLabel;
    private javax.swing.JTextField originPHPTagjTextField;
    private javax.swing.JPanel phpTagjPanel;
    private javax.swing.JPanel phpjPanel;
    private javax.swing.JButton syncFlashjButton;
    private javax.swing.JButton syncPHPjButton;
    private javax.swing.JButton syncResourcesjButton;
    private javax.swing.JMenuItem syncResourcesjMenuItem;
    private javax.swing.JScrollPane syncjScrollPane;
    // End of variables declaration//GEN-END:variables
}
