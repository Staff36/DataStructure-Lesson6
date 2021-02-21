package com.Lesson6;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        List<TreeImpl<Integer>> myList= new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            myList.add(getTree());
        }
        displayBalancingOfTree(myList);

    }
    public static TreeImpl<Integer> getTree(){
        TreeImpl<Integer> myTree= new TreeImpl<>(4);
        Random random= new Random();
        int countOfDigits=(int)Math.pow(2,myTree.getMAX_DEEP())-1;
        for (int i = 0; i < countOfDigits; i++) {
            myTree.add(random.nextInt(50)-25);
        }
        return myTree;
    }
    public static void displayBalancingOfTree(List<TreeImpl<Integer>> list){
        int countInbalancedTree=0;
        for (TreeImpl tree :list) {
            countInbalancedTree=tree.checkTree()?countInbalancedTree:countInbalancedTree+1;
        }
        float percent=100*countInbalancedTree/list.size();
        System.out.printf("Total trees: %d\nTotal inbalanced trees: %d\nPercent inbalanced trees: %s%%%n", list.size(), countInbalancedTree, percent);
    }

}

