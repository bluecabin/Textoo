package org.bluecabin.textoo;

import android.text.util.Linkify;
import android.widget.TextView;

import java.util.regex.Pattern;

/**
 * Created by fergus on 1/4/16.
 */
public abstract class TextViewConfigurator extends Configurator<TextView>
        implements TextLinkify<TextView, TextViewConfigurator>, LinksHandling<TextView, TextViewConfigurator> {
    protected TextViewConfigurator(TextooContext textooContext) {
        super(textooContext);
    }

    // Re-declare abstract methods with generic return type to enable scala implementations

    @Override
    public abstract TextView apply();

    @Override
    public abstract TextViewConfigurator linkifyEmailAddresses();

    @Override
    public abstract TextViewConfigurator linkifyMapAddresses();

    @Override
    public abstract TextViewConfigurator linkifyPhoneNumbers();

    @Override
    public abstract TextViewConfigurator linkifyWebUrls();

    @Override
    public abstract TextViewConfigurator linkifyAll();

    @Override
    public abstract TextViewConfigurator linkify(Pattern pattern, String scheme);

    @Override
    public abstract TextViewConfigurator linkify(Pattern p, String scheme, Linkify.MatchFilter matchFilter,
                                                 Linkify.TransformFilter transformFilter);

    @Override
    public abstract TextViewConfigurator addLinksHandler(LinksHandler handler);

}
