package org.bluecabin.textoo;

import android.text.SpannableString;
import android.text.Spanned;

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

    public StringConfigurator createStringConfigurator(String text) {
        return StringConfiguratorImpl.create(this, text);
    }

    public SpannedConfigurator createSpannedConfigurator(Spanned text) {
        return SpannedConfiguratorImpl.create(this, text);
    }

    public TextViewConfigurator createTextViewConfigurator() {
        return TextViewConfiguratorImpl.create(this);
    }
}
