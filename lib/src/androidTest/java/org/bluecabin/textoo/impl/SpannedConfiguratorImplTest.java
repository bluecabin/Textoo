package org.bluecabin.textoo.impl;

import android.text.SpannableString;
import android.text.Spanned;
import org.bluecabin.textoo.SpannedConfigurator;
import org.bluecabin.textoo.TextooContext;
import org.bluecabin.textoo.impl.SpannedConfiguratorImpl;

/**
 * Created by fergus on 1/5/16.
 */
public class SpannedConfiguratorImplTest extends ConfiguratorImplTest<Spanned, SpannedConfigurator, Spanned, SpannedConfigurator> {

    @Override
    protected SpannedConfigurator createConfigurator(TextooContext textooContext, CharSequence text) {
        return SpannedConfiguratorImpl.create(textooContext, new SpannableString(text));
    }

    @Override
    protected String getText(Spanned result) {
        return result.toString();
    }

    @Override
    protected Spanned toSpanned(Spanned result) {
        return result;
    }
}
