package org.bluecabin.textoo;

import android.text.Html;
import android.text.Spanned;
import android.text.util.Linkify;

import java.util.regex.Pattern;

/**
 * Created by fergus on 1/5/16.
 */
public abstract class StringConfigurator extends BaseConfigurator<Spanned>
        implements TextLinkify<SpannedConfigurator> {
    StringConfigurator() {

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

    public abstract SpannedConfigurator parseHtml();

    public abstract SpannedConfigurator parseHtml(Html.ImageGetter imageGetter,
                                                  Html.TagHandler tagHandler);


}
