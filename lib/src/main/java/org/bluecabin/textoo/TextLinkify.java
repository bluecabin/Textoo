package org.bluecabin.textoo;

import android.text.util.Linkify;

import java.util.regex.Pattern;

/**
 * Configurator with linkify capability.  This wraps <a href="http://developer.android.com/reference/android/text/util/Linkify.html">android.text.util.Linkify</a>
 * and exposes the same set of functions in a fluent interface.
 */
public interface TextLinkify<T, C extends TextLinkify<T, C>> {
    /**
     * Linkify email addresses.  See <a href="http://developer.android.com/reference/android/text/util/Linkify.html#addLinks(android.widget.TextView, int)">Linkify.addLinks(TextView text, int mask)</a>
     *
     * @return updated configurator for further configurations
     */
    C linkifyEmailAddresses();

    /**
     * Linkify street addresses.  See <a href="http://developer.android.com/reference/android/text/util/Linkify.html#addLinks(android.widget.TextView, int)">Linkify.addLinks(TextView text, int mask)</a>
     *
     * @return updated configurator for further configurations
     */
    C linkifyMapAddresses();

    /**
     * Linkify phone numbers.  See <a href="http://developer.android.com/reference/android/text/util/Linkify.html#addLinks(android.widget.TextView, int)">Linkify.addLinks(TextView text, int mask)</a>
     *
     * @return updated configurator for further configurations
     */
    C linkifyPhoneNumbers();

    /**
     * Linkify web URLs.  See <a href="http://developer.android.com/reference/android/text/util/Linkify.html#addLinks(android.widget.TextView, int)">Linkify.addLinks(TextView text, int mask)</a>
     *
     * @return updated configurator for further configurations
     */
    C linkifyWebUrls();

    /**
     * Linkify with all available patterns.  See <a href="http://developer.android.com/reference/android/text/util/Linkify.html#addLinks(android.widget.TextView, int)">Linkify.addLinks(TextView text, int mask)</a>
     *
     * @return updated configurator for further configurations
     */
    C linkifyAll();

    /**
     * Applies a regex to the text and turn the matches into links.
     * See <a href="http://developer.android.com/reference/android/text/util/Linkify.html#addLinks(android.widget.TextView, java.util.regex.Pattern, java.lang.String)">
     * android.text.util.Linkify#addLinks(android.text.Spannable, java.util.regex.Pattern, java.lang.String)</a>
     *
     * @param pattern Regex pattern to be used for finding links
     * @param scheme  Url scheme string (eg http://) to be prepended to the url of links that do not have a scheme specified in the link text
     * @return updated configurator for further configurations
     */
    C linkify(Pattern pattern, String scheme);

    /**
     * Applies a regex to the text and turn the matches into links.
     * See <a href="http://developer.android.com/reference/android/text/util/Linkify.html#addLinks(android.widget.TextView, java.util.regex.Pattern, java.lang.String, android.text.util.Linkify.MatchFilter, android.text.util.Linkify.TransformFilter)">
     * android.text.util.Linkify#addLinks(android.text.Spannable, java.util.regex.Pattern, java.lang.String, android.text.util.Linkify.MatchFilter, android.text.util.Linkify.TransformFilter)</a>
     *
     * @param p               Regex pattern to be used for finding links
     * @param scheme          Url scheme string (eg http://) to be prepended to the url of links that do not have a scheme specified in the link text
     * @param matchFilter     The filter that is used to allow the client code additional control over which pattern matches are to be converted into links.
     * @param transformFilter enables client code to have more control over how matched patterns are represented as URLs.
     * @return updated configurator for further configurations
     */
    C linkify(Pattern p, String scheme,
              Linkify.MatchFilter matchFilter, Linkify.TransformFilter transformFilter);
}
