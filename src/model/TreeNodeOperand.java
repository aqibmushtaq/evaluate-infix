/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

/**
 *
 * @author Aqib
 */
public class TreeNodeOperand extends TreeNode {
    
    private int number;
    
    public TreeNodeOperand(int n, TreeNode parentNode) {
        super(parentNode);
        number = n;
    }
    
    public int getNumber() {
        return number;
    }
    
}
