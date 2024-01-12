package com.photo.master.data.util.core;


import org.springframework.data.util.Lazy;
import org.springframework.data.util.StreamUtils;
import org.springframework.util.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

@FunctionalInterface
public interface Streamable<T> extends Iterable<T>, Supplier<Stream<T>> {
    static <T> Streamable<T> empty() {
        return Collections::emptyIterator;
    }

    @SafeVarargs
    static <T> Streamable<T> of(T... t) {
        return () -> {
            return Arrays.asList(t).iterator();
        };
    }

    static <T> Streamable<T> of(Iterable<T> iterable) {
        Assert.notNull(iterable, "Iterable must not be null");
        return iterable::iterator;
    }

    static <T> Streamable<T> of(Supplier<? extends Stream<T>> supplier) {
        return (Streamable<T>) Lazy.of(supplier);
    }

    default Stream<T> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    default <R> Streamable<R> map(Function<? super T, ? extends R> mapper) {
        Assert.notNull(mapper, "Mapping function must not be null");
        return of(() -> {
            return this.stream().map(mapper);
        });
    }

    default <R> Streamable<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        Assert.notNull(mapper, "Mapping function must not be null");
        return of(() -> {
            return this.stream().flatMap(mapper);
        });
    }

    default Streamable<T> filter(Predicate<? super T> predicate) {
        Assert.notNull(predicate, "Filter predicate must not be null");
        return of(() -> {
            return this.stream().filter(predicate);
        });
    }

    default boolean isEmpty() {
        return !this.iterator().hasNext();
    }

    default Streamable<T> and(Supplier<? extends Stream<? extends T>> stream) {
        Assert.notNull(stream, "Stream must not be null");
        return of(() -> {
            return Stream.concat(this.stream(), (Stream)stream.get());
        });
    }

    default Streamable<T> and(T... others) {
        Assert.notNull(others, "Other values must not be null");
        return of(() -> {
            return Stream.concat(this.stream(), Arrays.stream(others));
        });
    }

    default Streamable<T> and(Iterable<? extends T> iterable) {
        Assert.notNull(iterable, "Iterable must not be null");
        return of(() -> {
            return Stream.concat(this.stream(), StreamSupport.stream(iterable.spliterator(), false));
        });
    }

    default Streamable<T> and(Streamable<? extends T> streamable) {
        return this.and((Supplier)streamable);
    }

    default List<T> toList() {
        return (List)this.stream().collect(StreamUtils.toUnmodifiableList());
    }

    default Set<T> toSet() {
        return (Set)this.stream().collect(StreamUtils.toUnmodifiableSet());
    }

    default Stream<T> get() {
        return this.stream();
    }

//    static <S> Collector<S, ?, Streamable<S>> toStreamable() {
//        return toStreamable(Collectors.toList());
//    }
//
//    static <S, T extends Iterable<S>> Collector<S, ?, Streamable<S>> toStreamable(Collector<S, ?, T> intermediate) {
//        return Collector.of(intermediate.supplier(), intermediate.accumulator(), intermediate.combiner(), Streamable::of);
//    }
}
