package org.bluecabin.textoo;

import android.test.AndroidTestCase;

/**
 * Created by fergus on 1/9/16.
 */
public abstract class TextooTest extends AndroidTestCase {
    protected final TextooContext createTextooContext() {
        return Textoo.createContext();
    }
}
