package com.github.kononencheg.cat.stat;


public interface Free<O, A extends Arrow> extends Struct<O, A> {
  Free<O, A> join(A arrow);
}
