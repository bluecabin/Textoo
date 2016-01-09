package org.bluecabin.textoo;

/**
 * Created by fergus on 1/5/16.
 */
public abstract class BaseConfigurator<O> {
    BaseConfigurator(TextooContext textooContext) {
        textooContext.assertNotNull();
    }

    public abstract O apply();
}
