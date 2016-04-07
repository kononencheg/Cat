package com.github.kononencheg.cat.poset;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PosetTest {

  private class Counter extends Interval<Integer, Counter> {
    float sum = 0;

    Counter(Integer dom, Integer cod) {
      super(dom, cod);
    }

    Counter(Integer dom, Integer cod, int s) {
      super(dom, cod);

      sum = s;
    }

    public Counter unit() {
      return new Counter(dom(), cod());
    }

    public Counter add(Counter counter) {
      sum += counter.sum * length() / counter.length();
      return this;
    }


    Integer length() {
      return cod() - dom();
    }

    @Override
    public String toString() {
      return "[ " + dom() + " (" + sum + ") " + cod() + " ]";
    }
  }

  private class Histogram implements Poset<Integer, Counter> {
    public boolean commute(Integer dom, Integer cod) {
      return dom <= cod;
    }

    public Counter arrow(Integer dom, Integer cod) {
      return new Counter(dom, cod);
    }
  }

  @Test
  public void testPoset() {
    Category<Integer, Counter> histogram =
        Poset.create(new Histogram());

    histogram.join(new Counter(0, 10, 1));
    histogram.join(new Counter(0, 9, 1));
    histogram.join(new Counter(3, 13, 2));
    histogram.join(new Counter(0, 11, 1));
    histogram.join(new Counter(15, 20, 1));

    System.out.println(histogram);
  }

  @Test
  public void testPoset1() {
    Category<Integer, Counter> histogram =
        Poset.create(new Histogram());

    histogram.join(new Counter(0, 10, 1));
    histogram.join(new Counter(10, 12, 1));
    histogram.join(new Counter(0, 11, 2));

    System.out.println(histogram);
  }
}
