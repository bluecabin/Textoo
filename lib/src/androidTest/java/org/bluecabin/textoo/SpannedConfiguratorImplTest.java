package org.bluecabin.textoo;

import android.test.ActivityTestCase;
import android.text.SpannableString;
import android.text.Spanned;

/**
 * Created by fergus on 1/5/16.
 */
public class SpannedConfiguratorImplTest extends ActivityTestCase {
    public void testLinkifyEmailAddresses1() {
        Spanned text = new SpannableString("This: dummy@apple.com is an email address");
        SpannedConfigurator config = SpannedConfiguratorImpl.create(ConfiguratorFactory.getInstance(), text);
        Object newText = config
                .linkifyEmailAddresses()
                .apply();
        assertEquals(text, newText);
    }
}
