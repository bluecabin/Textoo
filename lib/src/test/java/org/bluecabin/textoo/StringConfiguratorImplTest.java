package org.bluecabin.textoo;

import org.bluecabin.textoo.ConfiguratorFactory;
import org.bluecabin.textoo.StringConfigurator;
import org.bluecabin.textoo.StringConfiguratorImpl;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by fergus on 1/5/16.
 */
public class StringConfiguratorImplTest {
    @Test
    public void testCreate() {
        StringConfigurator config = StringConfiguratorImpl.create(ConfiguratorFactory.getInstance());
        config.parseHtml();
    }
}
