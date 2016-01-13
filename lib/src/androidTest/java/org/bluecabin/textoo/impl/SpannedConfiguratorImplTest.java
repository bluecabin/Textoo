package org.bluecabin.textoo.impl;

import android.text.Spanned;
import org.bluecabin.textoo.SpannedConfigurator;
import org.bluecabin.textoo.TextooContext;
import org.bluecabin.textoo.util.CharSequenceSupport;

/**
 * Created by fergus on 1/5/16.
 */
public class SpannedConfiguratorImplTest extends LinksHandlingConfiguratorImplTest<Spanned, SpannedConfigurator, Spanned, SpannedConfigurator> {

    @Override
    protected Spanned createInitState(CharSequence text) {
        return CharSequenceSupport.toSpanned(text);
    }

    @Override
    protected SpannedConfigurator createConfigurator(TextooContext textooContext, Spanned initState) {
        return SpannedConfiguratorImpl.create(textooContext, initState);
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
