package org.bluecabin.textoo;

/**
 * Base class of all configurators
 * @param <T> Type of text to configure
 */
public abstract class Configurator<T> {
    protected Configurator(TextooContext textooContext) {
        textooContext.assertNotNull();
    }

    public abstract T apply();
}
