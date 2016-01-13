package org.bluecabin.textoo;

import android.text.Spanned;
import android.widget.TextView;

/**
 * The main class to present a simple fluent style API for configuring text of various types.
 */
public final class Textoo {
    private Textoo() {

    }

    protected static TextooContext createContext() {
        return new TextooContext();
    }

    /**
     * Configure a <a href="http://developer.android.com/reference/android/widget/TextView.html">android.widget.TextView</a>
     *
     * @param text the TextView to configure
     * @return configurator for the given TextView
     */
    public static TextViewConfigurator config(TextView text) {
        return ConfiguratorFactory.getInstance().createTextViewConfigurator(createContext(), text);
    }

    /**
     * Configure a <a href="http://developer.android.com/reference/android/text/Spanned.html">android.text.Spanned</a>
     *
     * @param text the Spanned to configure
     * @return configurator for the given Spanned
     */
    public static SpannedConfigurator config(Spanned text) {
        return ConfiguratorFactory.getInstance().createSpannedConfigurator(createContext(), text);
    }

    /**
     * Configure a String
     *
     * @param text the String to configure
     * @return configurator for the given String
     */
    public static StringConfigurator config(String text) {
        return ConfiguratorFactory.getInstance().createStringConfigurator(createContext(), text);
    }
}
