package com.github.kononencheg.cat.stat;

import java.util.Optional;

public interface Struct<O, A extends Arrow> {
  Optional<A> compose(O dom, O cod);
}
