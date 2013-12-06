/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.


(33+2)*(4-2)/(8+7)
 */
package controller;

import java.util.Scanner;
import java.util.Stack;
import model.Operator;
import model.TreeNode;
import model.TreeNodeOperand;
import model.TreeNodeOperator;

/**
 *
 * @author Aqib
 */
public class Main {

    private static int solveOperation (int n1, int n2, Operator op) {
        if (op == Operator.ADDITION)
            return n1 + n2;
        else if (op == Operator.SUBTRACTION)
            return n1 - n2;
        else if (op == Operator.MULTIPLICATION)
            return n1 * n2;
        else if (op == Operator.DIVISION)
            return n1 / n2;
        
        throw new RuntimeException("Operation not suppored: " + op);
    }
    
    private static Operator getOperator (char c) {
        if (c == '+')
            return Operator.ADDITION;
        else if (c == '-')
            return Operator.SUBTRACTION;
        else if (c == '*')
            return Operator.MULTIPLICATION;
        else if (c == '/')
            return Operator.DIVISION;
        
        throw new RuntimeException();
    }
    
    private static String infixToPostfix(String infix) {
        String postfix = " "; //space is a hack to solve first loop problem
        Stack<Character> stack = new Stack<Character>();
        for (int i = 0; i < infix.length(); i++) {
            char c = infix.charAt(i);
            if(Character.isDigit(c)) {  
                //find all the numbers adjacent to this one
                int j = 0;
                while ((i+j < infix.length()) && Character.isDigit(infix.charAt(i + j))) {
                    postfix += Character.toString(infix.charAt(i + j));
                    j++;
                }
                i += j - 1; // -1 because the FOR-LOOP will also increment i
                postfix += " ";
                
            } else {
                char last = ' ';
                if (stack.size() != 0)
                    last = stack.lastElement();
                if (c == '+') {
                    if (last == '-')
                        postfix += Character.toString(stack.pop());
                    stack.push(c);
                } else if (c == '-') {
                    if (last == '+')
                        postfix += Character.toString(stack.pop());
                    stack.push(c);
                } else if (c == '/') {
                    if (last == '*')
                        postfix += Character.toString(stack.pop());
                    stack.push(c);
                } else if (c == '*') {
                    if (last == '/')
                        postfix += Character.toString(stack.pop());
                    stack.push(c);
                } else if (c == '(') {
                    stack.push(c);
                } else if (c == ')') {
                    while(stack.lastElement() != '(')
                        postfix += Character.toString(stack.pop());
                    stack.pop();    //lose the opening bracket '('
                }
            }
        }
        while (stack.size() != 0)
            postfix += Character.toString(stack.pop());
        return postfix.trim();
    }
    
    private static TreeNode buildTreeNode (String value, TreeNode parent) {
        try {
            return new TreeNodeOperand(Integer.parseInt(value), parent);
        } catch (NumberFormatException ex) {
            //not a number, carry on to check if it is an operator....
        }
        
        if (value.equals("+"))
            return new TreeNodeOperator(Operator.ADDITION, parent);
        else if (value.equals("-"))
            return new TreeNodeOperator(Operator.SUBTRACTION, parent);
        else if (value.equals("*"))
            return new TreeNodeOperator(Operator.MULTIPLICATION, parent);
        else if (value.equals("/"))
            return new TreeNodeOperator(Operator.DIVISION, parent);
        
        throw new RuntimeException("Character not recognised: " + value);
    }
    
    private static void buildTree(TreeNode tree, String postfix) {
        System.out.println("post: " + postfix);
        TreeNode current = tree;    //current position in tree
        //parse the postfix from right to left
        for(int i = postfix.length() - 1; i >= 0; i--) {
            char c = postfix.charAt(i);
            if (c != ' ') { //ignore if space
                String value = "";
                //if a digit is found then find all the other digits to it's left
                if (Character.isDigit(c)) {
                    int j = 0;
                    while ((i - j >= 0) && Character.isDigit(postfix.charAt(i - j))) {
                        value += Character.toString(postfix.charAt(i - j));
                        j++;
                    }
                    //if more than 1 digit is found then reverse it because they are in reverse order
                    if (value.length() > 1) {
                        value = new StringBuilder(value).reverse().toString();
                        //update the index as we have parsed more than 1 character
                        i -= j - 1; //minus one because the first FOR-LOOP will iterative to the next char
                    }
                } else {
                    value = Character.toString(c);
                }
                
                //if the current node is an operand then find the next operator which has a free node
                if (current instanceof TreeNodeOperand) {
                    while (current instanceof TreeNodeOperand || (current.hasRight() && current.hasLeft())) {
                        current = current.getParent();
                    }
                }
                
                if (current instanceof TreeNodeOperator && !current.hasRight()) {
                    System.out.printf("Creating node for '%s' on the right\n", value);
                    current.setRight(buildTreeNode(value, current));
                    current = current.getRight();
                } else if (current instanceof TreeNodeOperator && !current.hasLeft()) {
                    System.out.printf("Creating node for '%s' on the left\n", value);
                    current.setLeft(buildTreeNode(value, current));
                    current = current.getLeft();
                }
            }
        }
        
    }
    
    private static int solveTree (TreeNode tree) {
        if (tree instanceof TreeNodeOperand) {
            return ((TreeNodeOperand)tree).getNumber();
        } else if (tree instanceof TreeNodeOperator) {
            int leftN = 0;
            int rightN = 0;
            if (tree.hasLeft()) {
                 leftN = solveTree (tree.getLeft());
            }
            if (tree.hasRight()) {
                rightN = solveTree (tree.getRight());
            }
            return solveOperation(leftN, rightN, ((TreeNodeOperator)tree).getOperator());
        }
        return 0;
    }
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter infix: ");
        String infix = in.nextLine();

        String postfix = infixToPostfix(infix);
        System.out.printf("Postfix: %s\n", postfix);
        
        TreeNode tree = new TreeNodeOperator(getOperator(postfix.charAt(postfix.length() - 1)), null);
        postfix = postfix.substring(0, postfix.length() - 1);
        
        buildTree(tree, postfix);
        
        int result = solveTree(tree);
        System.out.printf("Solution: %s\n", result);
        
        System.out.println("Press enter to quit...");
        in.nextLine();
    }

}
