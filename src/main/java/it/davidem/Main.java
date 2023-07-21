package it.davidem;

public class Main {

  public static void main(String[] args) {
    Grid grid = new Grid(5);
    System.out.println(grid.generateGrid());
    grid.startGame();
//    grid.generateBlocks();
//    System.out.println(grid.generateGrid());
  }
}