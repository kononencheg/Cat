package com.github.kononencheg.cat.proc;

@FunctionalInterface
public interface Morphism<D, C> {
  C arrow(D dom);
}
