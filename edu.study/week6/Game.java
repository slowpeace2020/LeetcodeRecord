package week6;

import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean wantPlay = true;
        while (wantPlay){
            play();
            System.out.println("Do you want to play again? Please enter Yes or No ");
            String answer = sc.nextLine();
            if(answer.equals("Yes")){
                wantPlay = true;
            }else {
                wantPlay = false;
            }

        }

    }

    public static void play() {
        char[][] grid = new char[3][3];
        while (true){
            display(grid);
            Scanner sc = new Scanner(System.in);
            System.out.println("Play 1 please tell me which row would like to enter: ");
            int x1 = sc.nextInt();
            System.out.println("Play 1 please tell me which column would like to enter: ");
            int y1 = sc.nextInt();

            grid[x1][y1] = 'X';
            if(isWin(grid,'X')){
                System.out.println("play1 win the game");
                return;
            }
            display(grid);
            System.out.println("Play 2 please tell me which row would like to enter: ");
            int x2 = sc.nextInt();
            System.out.println("Play 2 please tell me which column would like to enter: ");
            int y2 = sc.nextInt();
            grid[x2][y2] = 'O';
            if(isWin(grid,'O')){
                System.out.println("play2 win the game");
                return;
            }
        }

    }

    public static void display(char[][] grid) {
        System.out.println("display the game:");
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static boolean isWin(char[][] grid, char sign) {
        int m = grid.length;
        int n = grid[0].length;
        for (int i = 0; i < m; i++) {
            boolean win = true;
            for (int j = 0; j < n; j++) {
                if (grid[i][j] != sign) {
                    win = false;
                    break;
                }
            }
            if (win) {
                return true;
            }
        }

        for (int i = 0; i < n; i++) {
            boolean win = true;
            for (int j = 0; j < m; j++) {
                if (grid[j][i] != sign) {
                    win = false;
                    break;
                }
            }

            if (win) {
                return true;
            }
        }

        return false;
    }


}
