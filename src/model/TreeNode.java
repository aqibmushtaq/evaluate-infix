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
public abstract class TreeNode {
    
    private TreeNode parentNode = null;
    private TreeNode left = null;
    private TreeNode right = null;
    
    public TreeNode (TreeNode p) {
        parentNode = p;
    }
    
    public boolean hasLeft() {
        return (left != null);
    }
    
    public boolean hasRight() {
        return (right != null);
    }
    
    public TreeNode getParent () {
        return parentNode;
    }
    
    public TreeNode getLeft() {
        return left;
    }
    
    public TreeNode getRight() {
        return right;
    }
    
    public void setRight(TreeNode r) {
        right = r;
    }
    
    public void setLeft(TreeNode l) {
        left = l;
    }    
}
