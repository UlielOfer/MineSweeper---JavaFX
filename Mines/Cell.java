package Mines;

public class Cell {
    private boolean isOpen;
    private boolean hasMine;
    private boolean flag;

    public Cell() {
        isOpen = false;
        hasMine = false;
        flag = false;
    }

    public void toggleFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean getFlag() { return this.flag; }

    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public boolean getIsOpen() {
        return this.isOpen;
    }

    public void setHasMine(boolean hasMine) {
        this.hasMine = hasMine;
    }

    public boolean getHasMine() {
        return this.hasMine;
    }

}
