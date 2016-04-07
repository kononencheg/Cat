package com.github.kononencheg.cat.poset;

import com.github.kononencheg.cat.stat.Arrow;
import com.github.kononencheg.cat.stat.Free;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;

class Category<P, I extends Interval<P, I>> implements Free<P, I> {
  private class Intersection {
    final Collection<I> base;
    final Collection<I> rest;

    Intersection(Collection<I> b,
                 Collection<I> r) {
      base = b;
      rest = r;
    }
  }

  private final SortedSet<I> _set = new TreeSet<>(this::_order);
  private final Poset<P, I> _core;

  Category(Poset<P, I> core) {
    _core = core;
  }

  public Optional<I> compose(P dom, P cod) {
    SortedSet<I> overlay =
        _set.subSet(_core.arrow(dom, dom), _core.arrow(cod, cod));


    throw new NotImplementedException();
  }

  public Category<P, I> join(I arrow) {
    if (_set.isEmpty()) {
      _set.add(arrow);
    } else {
      if (_core.commute(arrow.dom(), _initial()) &&
          _initial() != arrow.dom()) {

        _set.add(_core.arrow(arrow.dom(), _initial()));
      }

      if (_core.commute(_terminal(), arrow.cod()) &&
          _terminal() != arrow.cod()) {

        _set.add(_core.arrow(_terminal(), arrow.cod()));
      }

      _join(arrow);
    }

    return this;
  }

  private void _join(I arrow) {
    SortedSet<I> overlay = _set.tailSet(_inf(arrow));

    I base = overlay.first();
    Intersection intersection = _intersect(base, arrow);

    _set.remove(base);
    _set.addAll(intersection.base);

    intersection.rest.forEach(this::_join);
  }

  private I _inf(I arrow) {
    return _core.arrow(arrow.dom(), arrow.dom());
  }

  private P _initial() {
    return _set.first().dom();
  }

  private P _terminal() {
    return _set.last().cod();
  }

  private Intersection _intersect(I origin, I target) {
    Collection<I> base = new ArrayList<>();
    Collection<I> rest = new ArrayList<>();

    P a = origin.dom();
    P b = origin.cod();

    P c = target.dom();
    P d = target.cod();

    // a <= c &&
    if (_core.commute(a, c)) {

      // c <= b
      if (_core.commute(c, b)) {
        base.add(_core.arrow(a, c).add(origin));

        // d <= b
        if (_core.commute(d, b)) {
          base.add(_core.arrow(d, b).add(origin));
          base.add(target.add(origin));

          // b <= d
        } else if (_core.commute(b, d)) {
          base.add(_core.arrow(c, b).add(origin).add(target));
          rest.add(_core.arrow(b, d).add(target));
        }

        // b <= c
      } else if (_core.commute(b, c)) {
        base.add(origin);
        rest.add(_core.arrow(b, c));
        rest.add(target);
      }

      // c <= a
    } else if (_core.commute(c, a)) {

      // a <= d
      if (_core.commute(a, d)) {
        rest.add(_core.arrow(c, a).add(target));

        // d <= b
        if (_core.commute(d, b)) {
          base.add(_core.arrow(a, d).add(origin).add(target));
          base.add(_core.arrow(d, b).add(origin));
        }

        // b <= d
        if (_core.commute(b, d)) {
          base.add(origin.add(target));
          rest.add(_core.arrow(b, d).add(target));
        }

        // d <= a
      } else if (_core.commute(d, a)) {
        rest.add(target);
        rest.add(_core.arrow(d, a));
        base.add(origin);
      }
    }

    rest.removeIf(Arrow::id);
    base.removeIf(Arrow::id);

    return new Intersection(base, rest);
  }

  // Arrow category on poset
  private int _order(I a, I b) {
    // removal support
    if (a == b) {
      return 0;
    }

    // a <= b
    if (_core.commute(a.dom(), b.dom()) &&
        _core.commute(a.cod(), b.cod())) {
      return -1;
    }

    // b <= a
    if (_core.commute(b.dom(), a.dom()) &&
        _core.commute(b.cod(), a.cod())) {
      return 1;
    }

    // identity arrow search
    return 0;
  }

  @Override
  public String toString() {
    return _set.stream()
        .map(Object::toString)
        .reduce("", String::concat);
  }
}
