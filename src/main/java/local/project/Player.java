package local.project;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Scanner;

public class Player extends Area{
    @Getter
    @Setter
    private String name;


//    Logic of destroying the ship
    private ArrayList<int[]> zeroList = new ArrayList<>();
    private ArrayList<int[]> mustBeDestroyed = new ArrayList<>();
    private ArrayList<int[]> allreadyDestroyed = new ArrayList<>();

    private void around(int x, int y){
        int[] support = {y, x};
        this.allreadyDestroyed.add(support);
        ArrayList<int[]> nearCords = new ArrayList<>();

//        Manual
        int[] supOne = {y-1, x-1};
        nearCords.add(supOne);
        int[] supTwo = {y-1, x};
        nearCords.add(supTwo);
        int[] supThree = {y-1, x+1};
        nearCords.add(supThree);
        int[] supFour = {y, x-1};
        nearCords.add(supFour);
        int[] supFive = {y, x+1};
        nearCords.add(supFive);
        int[] supSix = {y+1, x-1};
        nearCords.add(supSix);
        int[] supSeven = {y+1, x};
        nearCords.add(supSeven);
        int[] supEight = {y+1, x+1};
        nearCords.add(supEight);

        for(int index = 0; index < nearCords.size(); index++) {
            if(nearCords.get(index)[0] == 1 & nearCords.get(index)[1] == 0){
                continue;
            }
            if(nearCords.get(index)[0] == 0 & nearCords.get(index)[1] == 1){
                continue;
            }
            if(nearCords.get(index)[0] == 0  & nearCords.get(index)[1] == 10){
                continue;
            }
            if(nearCords.get(index)[0] == 1 & nearCords.get(index)[1] == 11){
                continue;
            }
            if(nearCords.get(index)[0] == 10 & nearCords.get(index)[1] == 11){
                continue;
            }
            if(nearCords.get(index)[0] == 11 & nearCords.get(index)[1] == 10){
                continue;
            }
            if(nearCords.get(index)[0] == 10 & nearCords.get(index)[1] == 0){
                continue;
            }
            if(nearCords.get(index)[0] == 11 & nearCords.get(index)[1] == 1){
                continue;
            }
            int intSup = this.playerArea[nearCords.get(index)[0]][nearCords.get(index)[1]];
            if (intSup == 0) {
                this.zeroList.add(nearCords.get(index));
            } else if (intSup == 1) {
                this.mustBeDestroyed.add(nearCords.get(index));
            }
        }

        ArrayList<int[]> mustBeDestroyedClon = (ArrayList<int[]>) this.mustBeDestroyed.clone();
        for(int index = 0; index < this.allreadyDestroyed.size(); index++){
            for(int innerIndex = 0; innerIndex < this.mustBeDestroyed.size(); innerIndex ++){
                if(this.allreadyDestroyed.get(index)[0] == this.mustBeDestroyed.get(innerIndex)[0]){
                    if(this.allreadyDestroyed.get(index)[1] == this.mustBeDestroyed.get(innerIndex)[1]){
                        mustBeDestroyedClon.remove(innerIndex);
                    }
                }
            }
        }
        this.mustBeDestroyed = (ArrayList<int[]>) mustBeDestroyedClon.clone();

        if(this.mustBeDestroyed.isEmpty()){
            for(int[] element: this.zeroList){
                this.currentPlayerArea[element[0]][element[1]] = 3;
            }
            this.zeroList.clear();
            System.out.println("Got it");
        }
    }
//    /*Logic of destroying the ship/*


    Scanner input = new Scanner(System.in);

    public void turn(String xy) throws StringIndexOutOfBoundsException{
        int x = Character.digit(xy.charAt(0), 10);
        int y = Character.digit(xy.charAt(1), 10);
        if(x == 0){
            x = 10;
        }
        if(y == 0){
            y = 10;
        }
        if(this.playerArea[y][x] == 1){
            this.currentPlayerArea[y][x] = 1;
            this.setDestroyedParts(this.getDestroyedParts() - 1);
            around(x, y);
            showPlayerArea(this.currentPlayerArea);
            System.out.println("One more shot, " + this.getName());
            turn(input.nextLine());
        }else{
            System.out.println(this.getDestroyedParts());
            this.currentPlayerArea[y][x] = 3;
            showPlayerArea(this.currentPlayerArea);
            System.out.println("Miss, another Player's turn");
        }

    }
}
