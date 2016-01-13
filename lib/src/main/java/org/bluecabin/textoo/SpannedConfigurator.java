package org.bluecabin.textoo;

import android.text.Spanned;
import android.text.util.Linkify;

import java.util.regex.Pattern;

/**
 * Base configurator for <a href="http://developer.android.com/reference/android/text/Spanned.html">android.text.Spanned</a>
 */
public abstract class SpannedConfigurator extends Configurator<Spanned>
        implements TextLinkify<Spanned, SpannedConfigurator> {
    protected SpannedConfigurator(TextooContext textooContext) {
        super(textooContext);
    }

    // Re-declare abstract methods with generic return type to enable scala implementations

    @Override
    public abstract Spanned apply();

    @Override
    public abstract SpannedConfigurator linkifyEmailAddresses();

    @Override
    public abstract SpannedConfigurator linkifyMapAddresses();

    @Override
    public abstract SpannedConfigurator linkifyPhoneNumbers();

    @Override
    public abstract SpannedConfigurator linkifyWebUrls();

    @Override
    public abstract SpannedConfigurator linkifyAll();

    @Override
    public abstract SpannedConfigurator linkify(Pattern pattern, String scheme);

    @Override
    public abstract SpannedConfigurator linkify(Pattern p, String scheme, Linkify.MatchFilter matchFilter,
                                                Linkify.TransformFilter transformFilter);

}
