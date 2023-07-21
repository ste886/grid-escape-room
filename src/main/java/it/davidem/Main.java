package it.davidem;

public class Main {

  public static void main(String[] args) {
    Grid grid = new Grid(3);
    grid.generateBlocks();
    System.out.println(grid.generateGrid());
    try {
      grid.startGame();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}