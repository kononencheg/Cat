package com.github.kononencheg.cat.stat;

public interface Arrow<O> {
  O dom();
  O cod();

  default boolean id() {
    return dom() == cod();
  }
}
