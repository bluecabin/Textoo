package org.bluecabin.textoo;

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

    public StringConfigurator createStringConfigurator() {
        return StringConfiguratorImpl.create(this);
    }

    public SpannedConfigurator createSpannedConfigurator() {
        return SpannedConfiguratorImpl.create(this);
    }

    public TextViewConfigurator createTextViewConfigurator() {
        return TextViewConfiguratorImpl.create(this);
    }
}
