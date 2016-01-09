package org.bluecabin.textoo;

import android.text.util.Linkify;

import java.util.regex.Pattern;

/**
 * Created by fergus on 1/5/16.
 */
public interface TextLinkify<O, C extends BaseConfigurator<O>> {
    C linkifyEmailAddresses();

    C linkifyMapAddresses();

    C linkifyPhoneNumbers();

    C linkifyWebUrls();

    C linkifyAll();

    /**
     * @See android.text.util.Linkify#addLinks(android.text.Spannable, java.util.regex.Pattern, java.lang.String)
     */
    C linkify(Pattern pattern, String scheme);

    /**
     * @See android.text.util.Linkify#addLinks(android.text.Spannable, java.util.regex.Pattern, java.lang.String, android.text.util.Linkify.MatchFilter, android.text.util.Linkify.TransformFilter)
     */
    C linkify(Pattern p, String scheme,
              Linkify.MatchFilter matchFilter, Linkify.TransformFilter transformFilter);
}
