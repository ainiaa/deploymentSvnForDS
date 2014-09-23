/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coding91.utility;

import com.coding91.ui.DeploymentSvnForDS;
import com.coding91.ui.EditPropConfJFrame;

/**
 *
 * @author Administrator
 */
public class ControllerJFrame {

    private static DeploymentSvnForDS deploymentSvnForDS;
    private static EditPropConfJFrame editPropConfJFrame;

    public static void main(String[] args) {
        deploymentSvnForDS = new DeploymentSvnForDS();
        editPropConfJFrame = new EditPropConfJFrame();
        deploymentSvnForDS.setVisible(true);
        deploymentSvnForDS.setLocationRelativeTo(null);  
    }

    public static void showEditPropConfJFrame() {
        if (editPropConfJFrame == null) {
            editPropConfJFrame = new EditPropConfJFrame();
        }
        if (deploymentSvnForDS == null) {
            deploymentSvnForDS = new DeploymentSvnForDS();
        }
        editPropConfJFrame.setLocationRelativeTo(null);
        deploymentSvnForDS.setVisible(false);
        editPropConfJFrame.setVisible(true);
        
    }
    
    public static void showDeploymentSvnForDSJFrame() {
        if (editPropConfJFrame == null) {
            editPropConfJFrame = new EditPropConfJFrame();
        }
        if (deploymentSvnForDS == null) {
            deploymentSvnForDS = new DeploymentSvnForDS();
        }
        deploymentSvnForDS.setLocationRelativeTo(null);
        editPropConfJFrame.setVisible(false);
        deploymentSvnForDS.setVisible(true);
    }
}
