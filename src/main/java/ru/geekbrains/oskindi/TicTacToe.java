package ru.geekbrains.oskindi;

import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
    static char[][] map;
    static int SIZE;
    static int DOTS_TO_WIN;
    static final char DOT_PLAYER = 'X';
    static final char DOT_AI = '0';
    static final char DOT_EMPTY = '*';
    static Scanner sc = new Scanner(System.in);
    static Random random = new Random();

    public static void main(String[] args) {
        gameLevel();
        prepareMap();
        printMap();
        while (true) {
            playerTurn();
            printMap();
            if (isMapFull()) {
                System.out.println("Ничья");
                break;
            }
            if (checkWin(DOT_PLAYER)) {
                System.out.println("Победил игрок!!");
                break;
            }
            aiTurn();
            printMap();
            if (isMapFull()) {
                System.out.println("Ничья");
                break;
            }
            if (checkWin(DOT_AI)) {
                System.out.println("Победил ИИ!!");
                break;
            }
        }
        System.out.println("Игра завершена");

    }

    public static void prepareMap() {
        map = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[j][i] = DOT_EMPTY;
            }
        }
    }

    public static void printMap() {
        System.out.print("  ");
        for (int i = 1; i <= SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(map[j][i] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void playerTurn() {
        int x, y;
        do {
            System.out.println("Укажите координаты хода в формате 'X Y'");
            x = sc.nextInt() - 1;
            y = sc.nextInt() - 1;
        } while (!isCellEmpty(x, y));
        map[x][y] = DOT_PLAYER;
    }

    public static void aiTurn() {
        int x, y;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (isCellEmpty(i,j)) {
                    map[i][j] = DOT_AI;
                    if (checkWin(DOT_AI)) {
                        System.out.printf("Ход ИИ: [%d, %d]\n", j + 1, i + 1);
                        return;
                    } else {
                        map[i][j] = DOT_EMPTY;
                    }
                }
            }
        }

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (isCellEmpty(i,j)) {
                    map[i][j] = DOT_PLAYER;
                    if (checkWin(DOT_PLAYER)) {
                        System.out.printf("Ход ИИ: [%d, %d]\n", j + 1, i + 1);
                        map[i][j] = DOT_AI;
                        return;
                    } else {
                        map[i][j] = DOT_EMPTY;
                    }
                }
            }
        }

        do {
            x = random.nextInt(SIZE);
            y = random.nextInt(SIZE);
        } while (!isCellEmpty(x, y));
        map[x][y] = DOT_AI;
        System.out.printf("Ход ИИ: [%d, %d]\n", x + 1, y + 1);
    }



    public static boolean isCellEmpty(int x, int y) {
        if (x < 0 || y < 0 || x >= SIZE || y >= SIZE) {
            return false;
        }
        if (map[x][y] == DOT_EMPTY) {
            return true;
        }
        return false;
    }

    public static boolean isMapFull() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean checkWin(char dot) {
        int vertCount;
        int horCount;
        int mainDiagCount;
        int sideDiagCount;
        for (int entryVert = 0; entryVert <= SIZE - DOTS_TO_WIN; entryVert++) {
            for (int entryHor = 0; entryHor <= SIZE - DOTS_TO_WIN; entryHor++) {
                mainDiagCount = 0;
                sideDiagCount = 0;
                for (int i = 0 + entryVert; i < DOTS_TO_WIN + entryVert; i++) {
                    vertCount = 0;
                    horCount = 0;
                    for (int j = 0 + entryHor; j < DOTS_TO_WIN + entryHor; j++) {
                        if (map[i][j] == dot) {
                            vertCount++;
                        }
                        if (map[j][i] == dot) {
                            horCount++;
                        }
                        if ((map[i][j] == dot) && (i - entryVert == j - entryHor)) {
                            mainDiagCount++;
                        }
                        if ((map[i][j] == dot) && (i - entryVert == DOTS_TO_WIN + entryHor - 1 - j)) {
                            sideDiagCount++;
                        }
                    }
                    if (vertCount == DOTS_TO_WIN || horCount == DOTS_TO_WIN || mainDiagCount == DOTS_TO_WIN || sideDiagCount == DOTS_TO_WIN) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void gameLevel() {
        System.out.println("Добро пожаловать в игру 'Крестики-Нолики'!!!");
        System.out.println("Введите размеры поля для игры");
        SIZE = sc.nextInt();
        System.out.println("Введите количество фишек для победы");
        DOTS_TO_WIN = sc.nextInt();
        System.out.printf("Вы выбрали размер поля %d/%d и количество фишек для победы %d", SIZE, SIZE, DOTS_TO_WIN);
        System.out.println();
    }
}
