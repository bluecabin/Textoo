package org.bluecabin.textoo;

import android.text.Spanned;
import android.text.util.Linkify;

import java.util.regex.Pattern;

/**
 * Created by fergus on 1/4/16.
 */
public abstract class SpannedConfigurator extends BaseConfigurator<Spanned>
        implements TextLinkify<Spanned, SpannedConfigurator> {
    protected SpannedConfigurator(TextooContext textooContext) {
        super(textooContext);
    }

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
