package org.bluecabin.textoo;

/**
 * Created by fergus on 1/5/16.
 */
public abstract class Configurator<T> {
    protected Configurator(TextooContext textooContext) {
        textooContext.assertNotNull();
    }

    public abstract T apply();
}
