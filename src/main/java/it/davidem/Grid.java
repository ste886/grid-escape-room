package it.davidem;

import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

public class Grid implements GridOperation {

  private final int gridSize;
  private final Set<GridElement> elements;
  private final Player player;

  public Grid(int gridSize) {
    this.gridSize = gridSize;
    this.elements = new HashSet<>();
    this.player = generatePlayer();
  }

  @Override
  public String generateGrid() {
    StringBuilder sb = new StringBuilder();
    for (int x = gridSize; x > 0; x--) {
      for (int y = 1; y <= gridSize; y++) {
        maybeElement(x, y).ifPresentOrElse(
            (GridElement ge) -> sb.append(String.format("[%s] ", ge)),
            () -> sb.append("[ ] ")
        );
//        sb.append(String.format("[%s::%s] ", x, y));
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  private Optional<GridElement> maybeElement(int x, int y) {
    return elements.stream()
        .filter(p -> (p.getX() == x) && (p.getY() == y))
        .findFirst();
  }

  @Override
  public void generateBlocks() {
    Random random = new Random();
    int numberOfBlock = gridSize - 1;
    for (; numberOfBlock > 0; numberOfBlock--) {
      int x;
      int y;
      do {
        x = random.nextInt(gridSize) + 1;
        y = random.nextInt(gridSize) + 1;
      } while (x == 1 || maybeElement(x, y).isPresent());
      elements.add(new Block(x, y));
    }
  }

  private Player generatePlayer() {
    Random random = new Random();
    int x = 1;
    int y = random.nextInt(gridSize) + 1;
    Player player = new Player(x, y);
    elements.add(player);
    return player;
  }

  public boolean isMoving() {
    Random random = new Random();
    int direction = random.nextInt(4);
    int newPosition;
    switch (direction) {
      case 0:
        newPosition = player.getX() + 1;
        if (isPositionAllowed(newPosition) && !isCollision(newPosition, player.getY())) {
          player.setX(newPosition);
          return amIWinning();
        }
        return false;
      case 1:
        newPosition = player.getY() - 1;
        if (isPositionAllowed(newPosition) && !isCollision(player.getX(), newPosition)) {
          player.setY(newPosition);
        }
        return false;
      case 2:
        newPosition = player.getX() - 1;
        if (isPositionAllowed(newPosition) && !isCollision(newPosition, player.getY())) {
          player.setX(newPosition);
        }
        return false;
      case 3:
        newPosition = player.getY() + 1;
        if (isPositionAllowed(newPosition) && !isCollision(player.getX(), newPosition)) {
          player.setY(newPosition);
        }
        return false;
      default:
        return false;
    }
  }

  private boolean amIWinning() {
    if (player.getX() == gridSize) {
      System.out.println(generateGrid());
      return true;
    } else {
      return false;
    }
  }

  public void startGame() throws InterruptedException {
    while (!isMoving()) {
      System.out.println(generateGrid());
      Thread.sleep(1000);
    }
  }

  public boolean isPositionAllowed(int x) {
    return (x <= gridSize && x > 0);
  }

  public boolean isCollision(int x, int y) {
    return maybeElement(x, y).isPresent();
  }

}
