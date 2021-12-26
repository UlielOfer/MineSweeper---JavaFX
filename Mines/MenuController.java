package Mines;

public class MenuController {

    private Mines mines;

    public MenuController(int w, int h, int m) {
        this.mines = new Mines(w, h, m);
    }

    public String click(int i, int j) {
        mines.open(i, j);
        return mines.get(i, j);
    }

    public String get(int i, int j) {
        return mines.get(i, j);
    }

    public String[][] getMinesStrings() {
        String[][] strings = new String[mines.getWidth()][mines.getHeight()];
        for (int i = 0; i < mines.getWidth(); i++) {
            for (int j = 0; j < mines.getHeight(); j++) {
                strings[i][j] = mines.get(i, j);
            }
        }
        return strings;
    }

    public boolean getHasMine(int x, int y) {
        return mines.getHasMine(x, y);
    }

    public void toggleFlag(int x, int y) {
        mines.toggleFlag(x, y);
    }

    public boolean checkIfWon() {
        return mines.isDone();
    }

}
