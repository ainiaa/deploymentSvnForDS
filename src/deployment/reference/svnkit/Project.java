/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deployment.reference.svnkit;

/**
 *
 * @author Administrator
 */
public class Project {

    private String svnUrl;
    private String name;

    public String getSvnUrl() {
        return svnUrl;
    }

    public void setSvnUrl(String svnUrl) {
        this.svnUrl = svnUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Project() {

    }

    public Project(String name, String svnUrl) {
        this.name = name;
        this.svnUrl = svnUrl;
    }

}
