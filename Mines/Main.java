package Mines;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Random rand = new Random();
        Scanner in = new Scanner(System.in);
        Mines mine = new Mines(4, 5, 1);
        mine.printBoard();
        mine.open(1,1);
        System.out.println();
        mine.printBoard();
//            System.out.println("t");
//            while (true) {
//                System.out.println("enter open:");
//                mine.open(in.nextInt(), in.nextInt());
//            }
//            mine.showCells(1, 1);
    }

}
