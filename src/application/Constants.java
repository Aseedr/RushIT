package application;

public interface Constants {
  final int SCREEN_WIDTH = 1280;
  final int SCREEN_HEIGHT = 720;
  final int HERO_SIZE = 32;
  final int HERO_CENTER = 16;

  final int count = 3;
  final int columns = 3;
  final int offsetX = 0;
  final int offsetY = 0;
  final int width = 32;
  final int height = 32;

  enum Maps { DESERT_MAP, FIELD_MAP, SANDY_ROAD_MAP }
  enum Direction { DIRECTION_DOWN, DIRECTION_LEFT, DIRECTION_UP,
      DIRECTION_RIGHT }

  enum Levels {
    STRONG_LEVEL(1), HARD_LEVEL(2), NORMAL_LEVEL(3), EASY_LEVEL(4);
    public int value;
    private Levels(int value) {
      this.value = value;
    }
  }

  enum Sides {
    DOWN(0), LEFT(1), RIGHT(2), UP(3);
    public int value;
    private Sides(int value) {
      this.value = value;
    }
  }
}
