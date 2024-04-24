/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.finalforestgameproject;

/**
 *
 * @author LENOVO
 */
import java.util.Random;
import java.util.Scanner;

/**
 * A simple text-based adventure game where the player explores a virtual forest.
 */
public class ForestGame {
    // Constants for forest symbols
    private static final char TREE = 'T';
    private static final char OPEN_SPACE = '.';
    private static final char PLAYER = 'P';

    public static void main(String[] args) {
        // Create a Scanner object to take input from the user
        Scanner scanner = new Scanner(System.in);

        // Generate the initial forest
        char[][] forest = generateForest(8, 8);

        // Display the initial forest
        displayForest(forest);

        // Player movement loop
        while (true) {
            System.out.print("Enter direction (W/A/S/D to move, Q to quit): ");
            char direction = scanner.next().toUpperCase().charAt(0);
            if (direction == 'Q') {
                break; // Exit the loop if the user enters 'Q'
            }
            movePlayer(forest, direction); // Move the player according to the input direction
            displayForest(forest); // Display the updated forest
        }

        // Close the scanner to release resources
        scanner.close();
    }

    /**
     * Generates a 2D character array representing the forest.
     * 
     * @param rows The number of rows in the forest.
     * @param cols The number of columns in the forest.
     * @return The generated forest.
     */
    public static char[][] generateForest(int rows, int cols) {
        char[][] forest = new char[rows][cols];
        Random rand = new Random();

        // Populate forest with open space and trees
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (rand.nextDouble() < 0.3) {
                    forest[i][j] = TREE; // Tree
                } else {
                    forest[i][j] = OPEN_SPACE; // Open space
                }
            }
        }

        // Place player at a random empty location
        int playerRow, playerCol;
        do {
            playerRow = rand.nextInt(rows);
            playerCol = rand.nextInt(cols);
        } while (forest[playerRow][playerCol] == TREE); // Ensure not on a tree
        forest[playerRow][playerCol] = PLAYER; // Player

        return forest;
    }

    /**
     * Displays the forest to the console.
     * 
     * @param forest The forest to display.
     */
    public static void displayForest(char[][] forest) {
        System.out.println("Current Forest:");
        for (char[] row : forest) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Moves the player within the forest based on the given direction.
     * 
     * @param forest    The forest in which the player is moving.
     * @param direction The direction in which the player is moving ('W' for Up, 'A' for Left, 'S' for Down, 'D' for Right).
     */
    public static void movePlayer(char[][] forest, char direction) {
        // Find the player's current position
        int[] playerPos = findPlayer(forest);
        int row = playerPos[0];
        int col = playerPos[1];

        // Calculate the new position based on the direction
        switch (direction) {
            case 'W': // Up
                row--;
                break;
            case 'A': // Left
                col--;
                break;
            case 'S': // Down
                row++;
                break;
            case 'D': // Right
                col++;
                break;
            default:
                System.out.println("Invalid direction! Please enter W, A, S, or D.");
                return; // Exit the method if direction is invalid
        }

        // Check if the new position is within the forest boundaries and not a tree
        if (isValidMove(forest, row, col)) {
            // Move the player to the new position
            forest[playerPos[0]][playerPos[1]] = OPEN_SPACE; // Clear the previous player position
            forest[row][col] = PLAYER; // Update the player's new position
        } else {
            System.out.println("Invalid move! You cannot move there.");
        }
    }

    /**
     * Finds the player's position within the forest.
     * 
     * @param forest The forest to search for the player.
     * @return An array containing the row and column indices of the player's position.
     */
    public static int[] findPlayer(char[][] forest) {
        int[] playerPos = new int[2];
        for (int i = 0; i < forest.length; i++) {
            for (int j = 0; j < forest[i].length; j++) {
                if (forest[i][j] == PLAYER) {
                    playerPos[0] = i; // Row index
                    playerPos[1] = j; // Column index
                    return playerPos;
                }
            }
        }
        return playerPos;
    }

    /**
     * Checks if the given position is a valid move within the forest.
     * 
     * @param forest The forest in which the move is being checked.
     * @param row    The row index of the move.
     * @param col    The column index of the move.
     * @return True if the move is valid, false otherwise.
     */
    public static boolean isValidMove(char[][] forest, int row, int col) {
        return row >= 0 && row < forest.length && col >= 0 && col < forest[0].length && forest[row][col] != TREE;
    }
}
