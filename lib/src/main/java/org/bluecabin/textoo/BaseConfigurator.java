package org.bluecabin.textoo;

/**
 * Created by fergus on 1/5/16.
 */
public abstract class BaseConfigurator<R> {
    BaseConfigurator() {

    }

    public abstract R apply();
}
