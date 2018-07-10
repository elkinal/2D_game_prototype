package Main;

/**
 * Created by alxye on 30-Jun-18.
 */
public class Level {
    String name;
    double demonSpeed;
    int coinAmount;
    int[][] content;
    int id;
    int demonX;
    int demonY;
    /*public static double demonX = tileSize * 3 + XOffset;
    public static double demonY = tileSize * 3 + YOffset + demonYMod;*/
    public Level(String name, int demonX, int demonY, double demonSpeed, int coinAmount, int id, int[][] content) {
        this.name = name;
        this.content = content;
        this.demonSpeed = demonSpeed;
        this.coinAmount = coinAmount;
        this.id = id;
        this.demonX = demonX;
        this.demonY = demonY;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String n) {
        this.name = n;
    }
    public double getDemonSpeed() {
        return this.demonSpeed;
    }
    public void setDemonSpeed(double s) {
        this.demonSpeed = s;
    }
    public int[][] getContent() {
        return this.content;
    }
    public void setContent(int[][] n) {
        this.content = n;
    }
    public int getCoinAmount() {
        return this.coinAmount;
    }
    public void setCoinAmount(int c) {
        this.coinAmount = c;
    }
    public int getId() {
        return this.id;
    }
    public int getDemonX() {
        return demonX;
    }
    public int getDemonY() {
        return demonY;
    }
}
