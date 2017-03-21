package com.rbkmoney.geck.filter;

/**
 * Created by tolkonepiu on 16/03/2017.
 */
public interface Filter<T> {

    boolean match(T object);

}
