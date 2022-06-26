package com.company;


public class Main {

    //public int spaceCount = 0;

    public static void createTree(int minValue, int maxValue, int maxHeight) throws Exception {

        SimpleBinaryTree tree = new SimpleBinaryTree();
        tree.fromArrayNotation(maxValue, minValue, maxHeight);
        System.out.println(tree.toBracketStr());

    }

    public static void main(String[] args) throws Exception {
        createTree(10,20, 4);
    }
}
