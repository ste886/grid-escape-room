package it.davidem;

public class Player extends GridElement {

  public Player(int x, int y) {
    super(x, y);
  }

  @Override
  public String toString() {
    return "P";
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

}
