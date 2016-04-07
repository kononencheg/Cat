package com.github.kononencheg.cat.poset;

import com.github.kononencheg.cat.monoid.Monoid;
import com.github.kononencheg.cat.stat.Arrow;

abstract class Interval<T, I extends Interval<T, I>>
    implements Arrow<T>, Monoid<I> {

  private final T _cod;
  private final T _dom;

  Interval(T dom, T cod) {
    _cod = cod;
    _dom = dom;
  }

  public T dom() {
    return _dom;
  }

  public T cod() {
    return _cod;
  }
}
