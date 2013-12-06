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
public class TreeNodeOperator extends TreeNode {
    
    private Operator operator;
    
    public TreeNodeOperator(Operator op, TreeNode parentNode) {
        super(parentNode);
        operator = op;
    }
    
    public Operator getOperator() {
        return operator;
    }
    
}
