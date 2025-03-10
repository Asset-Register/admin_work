package com.project.ITAM.helper;

import org.apache.commons.lang3.ObjectUtils;

import java.util.function.Consumer;

public class ExtractJsonUtil {

    public static <T> void updateIfNotEmpty(T value, Consumer<T> setter) {
        if (!ObjectUtils.isEmpty(value)) {
            setter.accept(value);
        }
    }

    public static <T> void updateIfNotEmpty(T value, Consumer<T>... setters) {
        if (!ObjectUtils.isEmpty(value)) {
            for (Consumer<T> setter : setters) {
                setter.accept(value);
            }
        }
    }

}
