package org.bluecabin.textoo;

import android.text.Spanned;
import android.widget.TextView;
import org.bluecabin.textoo.impl.SpannedConfiguratorImpl;
import org.bluecabin.textoo.impl.StringConfiguratorImpl;
import org.bluecabin.textoo.impl.TextViewConfiguratorImpl;

/**
 * Created by fergus on 1/5/16.
 */
final class ConfiguratorFactory {
    private ConfiguratorFactory() {

    }

    private static final ConfiguratorFactory _instance = new ConfiguratorFactory();

    static public ConfiguratorFactory getInstance() {
        return _instance;
    }

    public StringConfigurator createStringConfigurator(TextooContext context, String text) {
        return StringConfiguratorImpl.create(context, text);
    }

    public SpannedConfigurator createSpannedConfigurator(TextooContext context, Spanned text) {
        return SpannedConfiguratorImpl.create(context, text);
    }

    public TextViewConfigurator createTextViewConfigurator(TextooContext context, TextView view) {
        return TextViewConfiguratorImpl.create(context, view);
    }
}
