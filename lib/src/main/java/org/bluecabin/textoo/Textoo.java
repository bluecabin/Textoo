package org.bluecabin.textoo;

import android.text.Spanned;
import android.widget.TextView;

/**
 * Created by fergus on 1/4/16.
 */
public final class Textoo {
    private Textoo() {

    }

    static TextooContext createContext() {
        return new TextooContext();
    }

    public static TextViewConfigurator config(TextView text) {
        return ConfiguratorFactory.getInstance().createTextViewConfigurator(createContext(), text);
    }

    public static SpannedConfigurator config(Spanned text) {
        return ConfiguratorFactory.getInstance().createSpannedConfigurator(createContext(), text);
    }

    public static StringConfigurator config(String text) {
        return ConfiguratorFactory.getInstance().createStringConfigurator(createContext(), text);
    }
}
