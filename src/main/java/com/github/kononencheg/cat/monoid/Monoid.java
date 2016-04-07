package com.github.kononencheg.cat.monoid;

public interface Monoid<M extends Monoid> {
  M unit();
  M add(M monoid);
}
