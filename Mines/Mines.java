package Mines;

import java.util.Random;


public class Mines {

    // Instance Variables - Do Not Modified
    private int height;
    private int width;
    private int numMines;
    private boolean showAll;
    private Cell minesArray[][];
    private Random random;
    // End Of Variables Declaration

    // Constructor
    public Mines(int width, int height, int numMines) {
        if (numMines > width * height) {
            System.err.println("cannot init mines board");
        }
        this.width = width;
        this.height = height;
        showAll = false;
        random = new Random();
        setNumMines(numMines);
    }

    // Getters && Setters
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getNumMines() {
        return numMines;
    }

    public void setFlagOn(int i, int j) {
        if (i >= 0 && i < width && j >= 0 && j < height) {
            minesArray[i][j].toggleFlag(true);
        }
    }

    public void setFlagOff(int i, int j) {
        if (i >= 0 && i < width && j >= 0 && j < height) {
            minesArray[i][j].toggleFlag(false);
        }
    }

    public boolean isDone() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (minesArray[i][j].getHasMine() && !minesArray[i][j].getFlag())
                    return false;
            }
        }
        return true;
    }

    public String get(int i, int j) {
        Cell c;
        String res = "";

        if (i >= 0 && i < width && j >= 0 && j < height) {
            c = minesArray[i][j];
            if (c.getIsOpen() || showAll) {
                if (c.getHasMine())
                    return "X";
                else {
                    int counter = countNeighbourMines(i, j);
                    if (counter == 0) return " ";
                    return String.valueOf(counter);
                }
            } else {
                if (c.getFlag())
                    return "F";
                else
                    return ".";
            }
        }
        return null;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                result.append(get(i, j) + " ");
            }
            result.append("\n");
        }
        return result.toString();
    }

    public void setShowAll(boolean showAll) {
        this.showAll = showAll;
    }

    public void toggleFlag(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && x < height)
            if (minesArray[x][y].getFlag())
                minesArray[x][y].toggleFlag(false);
            else
                minesArray[x][y].toggleFlag(true);
    }

    public void setNumMines(int numMines) {
        this.numMines = numMines;
        this.minesArray = new Cell[this.width][this.height];
        int k = 0;

        for (int i = 0; i < minesArray.length; i++) {
            for (int j = 0; j < minesArray[0].length; j++) {
                minesArray[i][j] = new Cell();
            }
        }
        while (k < this.numMines) {
            addRandMine();
            k++;
        }
    }

    public boolean addMine(int widthSell, int heightSell) {
        if (!this.minesArray[widthSell][heightSell].getHasMine()) {
            this.minesArray[widthSell][heightSell].setHasMine(true);
            return true;
        } else {
            return false;
        }
    }

    public boolean open(int i, int j) {
        boolean hasMine = minesArray[i][j].getHasMine();
        if (hasMine) {
            minesArray[i][j].setIsOpen(true);
        } else {
            showCells(i, j);
        }
        return minesArray[i][j].getHasMine();
    }

    public boolean hasNeighboorNotMine(int i, int j) {
        if (checkNighbour(i - 1, j - 1) && checkNighbour(i - 1, j) &&
                checkNighbour(i - 1, j + 1) && checkNighbour(i, j - 1) &&
                checkNighbour(i, j + 1) && checkNighbour(i + 1, j + 1) &&
                checkNighbour(i - 1, j - 1) && checkNighbour(i + 1, j))
            return true;
        return false;
    }

    public int countNeighbourMines(int x, int y) {
        int counter = 0;
        if (x - 1 < width && x - 1 >= 0 && y - 1 < height && y - 1 >= 0 && minesArray[x - 1][y - 1].getHasMine())
            counter++;
        if (x - 1 < width && x - 1 >= 0 && y + 1 < height && y + 1 >= 0 && minesArray[x - 1][y + 1].getHasMine())
            counter++;
        if (x + 1 < width && x + 1 >= 0 && minesArray[x + 1][y].getHasMine()) counter++;
        if (x - 1 < width && x - 1 >= 0 && minesArray[x - 1][y].getHasMine()) counter++;
        if (x + 1 < width && x + 1 >= 0 && y + 1 < height && y + 1 >= 0 && minesArray[x + 1][y + 1].getHasMine())
            counter++;
        if (x < width && x >= 0 && y + 1 < height && y + 1 >= 0 && minesArray[x][y + 1].getHasMine()) counter++;
        if (x < width && x >= 0 && y - 1 < height && y - 1 >= 0 && minesArray[x][y - 1].getHasMine()) counter++;
        return counter;
    }

    public boolean checkNighbour(int i, int j) {
        if (i < 0 || j < 0 || i >= width || j >= height)
            return true;
        else if (minesArray[i][j].getHasMine())
            return false;
        return true;
    }

    public void showCells(int x, int y) { // 2, 1
        // Check the bounds
        if (x >= this.width || y >= this.height || x < 0 || y < 0) {
            return;
        }
        if (minesArray[x][y].getHasMine()) {
            return;
        }
        if (minesArray[x][y].getIsOpen()) {
            return;
        } else {
            minesArray[x][y].setIsOpen(true);
        }
        boolean isMineFound = minesArray[x][y].getHasMine();

        if (!isMineFound && hasNeighboorNotMine(x, y)) {
            showCells(x + 1, y);
            showCells(x - 1, y);
            showCells(x + 1, y + 1);
            showCells(x + 1, y - 1);
            showCells(x, y + 1);
            showCells(x, y - 1);
            showCells(x - 1, y + 1);
            showCells(x - 1, y - 1);
        } else {
            return;
        }
    }

    private void addRandMine() {
        boolean result = addMine(random.nextInt(width), random.nextInt(height));
        while (result == false) {
            result = addMine(random.nextInt(width), random.nextInt(height));
        }
    }

    public void printBoard() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                System.out.print(" [mine:" + minesArray[i][j].getHasMine() +
                        ", open:" + minesArray[i][j].getIsOpen() + "]");
            }
            System.out.println();
        }
    }

    public boolean getHasMine(int x, int y) {
        return minesArray[x][y].getHasMine();
    }

}
