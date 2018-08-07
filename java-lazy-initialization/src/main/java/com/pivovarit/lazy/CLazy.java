package com.pivovarit.lazy;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 * Customized Lazy (Lazy with CAS, lock-free)
 */
public final class CLazy<T> implements Supplier<T> {

    private final AtomicReference<Supplier<T>> supplier;
    private final AtomicReference<T> value;

    public CLazy(Supplier<T> supplier) {
        this.supplier = new AtomicReference<>(supplier);
        this.value = new AtomicReference<>();
    }

    @Override
    public T get() {
        T localValue = value.get();
        if (localValue == null) {
            localValue = supplier.get().get();
            supplier.lazySet(null);
            value.compareAndSet(null, localValue);
        }
        return localValue;
    }
}
