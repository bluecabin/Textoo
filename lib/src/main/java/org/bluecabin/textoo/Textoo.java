package org.bluecabin.textoo;

import android.text.Spannable;
import android.widget.TextView;

/**
 * Created by fergus on 1/4/16.
 */
public final class Textoo {
    private Textoo() {

    }

    public static TextViewConfigurator config(TextView text) {
        return ConfiguratorFactory.getInstance().createTextViewConfigurator();
    }

    public static SpannedConfigurator config(Spannable text) {
        return ConfiguratorFactory.getInstance().createSpannedConfigurator();
    }

    public static StringConfigurator config(String text) {
        return ConfiguratorFactory.getInstance().createStringConfigurator();
    }
}
