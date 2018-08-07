package com.pivovarit.lazy;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 * Customized Relaxed Lazy (wait-free, weird)
 */
public final class CRazy<T> implements Supplier<T> {

    private final Supplier<T> supplier;
    private final AtomicReference<T> value;

    public CRazy(Supplier<T> supplier) {
        this.supplier = supplier;
        this.value = new AtomicReference<>();
    }

    @Override
    public T get() {
        T localValue = value.get();
        if (localValue == null) {
            localValue = supplier.get();
            value.lazySet(localValue);
        }
        return localValue;
    }

}
