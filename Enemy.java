abstract class Enemy extends Creature {
    private double targetDestinationX = 0;
    private double targetDestinationY = 0;
    private int detectRange = 0;

    Enemy(int x, int y, int length, int width, String picName, int rows, int columns) {
        super(x, y, length, width, picName, rows, columns);
        super.setType("Enemy");
    }

    // abstract void attack();

    // abstract void idleMovement();

    abstract void search();

    // Setters
    public void setDetectRange(int range) {
        this.detectRange = range;
    }

    public void setDestinationX(double x) {
        this.targetDestinationX = x;
    }

    public void setDestinationY(double y) {
        this.targetDestinationY = y;
    }

    // Getters
    public double getDestinationX() {
        return this.targetDestinationX;
    }

    public double getDestinationY() {
        return this.targetDestinationY;
    }

}
