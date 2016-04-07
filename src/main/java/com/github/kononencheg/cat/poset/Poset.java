package com.github.kononencheg.cat.poset;


public interface Poset<P, I extends Interval<P, I>> {

  static <P, I extends Interval<P, I>> Category<P, I> create(Poset<P, I> core) {
    return new Category<>(core);
  }

  boolean commute(P dom, P cod);

  I arrow(P dom, P cod);
}
