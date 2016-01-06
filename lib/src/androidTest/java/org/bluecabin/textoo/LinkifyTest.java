package org.bluecabin.textoo;

import android.test.ActivityTestCase;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.URLSpan;
import android.text.util.Linkify;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fergus on 1/6/16.
 */
public abstract class LinkifyTest<C extends BaseConfigurator & TextLinkify> extends ActivityTestCase {
    private static final String inputText1 = "This: dummy@apple.com is an email address; " +
            "And http://www.google.com/a/b is an url; " +
            "And +852 12345678 is phone number; " +
            "And 1600 Amphitheatre Pkwy, Mountain View, CA 94043 is map address; ";
    private static final String inputText2 = "This: johnDoe@google.com is an email address; " +
            "And http://www.apple.com/a/b is an url; " +
            "And +852 87654321 is phone number; " +
            "And 1601 Amphitheatre Pkwy, Mountain View, CA 94043 is map address; ";

    protected final Object[] getSpans(Spanned actualText) {
        return actualText.getSpans(0, actualText.length(), Object.class);
    }

    protected final void assertURLSpan(Spanned actualText, Object actualSpan, String expectedURL, int expectedStart, int expectedEnd,
                                       int expectedFlags) {
        assertEquals(URLSpan.class, actualSpan.getClass());
        URLSpan urlSpan0 = (URLSpan) actualSpan;
        assertEquals(expectedStart, actualText.getSpanStart(urlSpan0));
        assertEquals(expectedEnd, actualText.getSpanEnd(urlSpan0));
        assertEquals(expectedFlags, actualText.getSpanFlags(urlSpan0));
        assertEquals(expectedURL, urlSpan0.getURL());
    }

    protected abstract C createConfigurator(Spanned text);

    protected abstract Spanned toSpanned(Object result);

    //
    // Test cases
    //

    public void testLinkifyEmailAddresses1() {
        String text = inputText1;
        C config = createConfigurator(new SpannableString(text));
        Spanned newText = toSpanned(config
                .linkifyEmailAddresses()
                .apply());
        assertEquals(text, newText.toString());
        Object[] spans = getSpans(newText);
        assertEquals(1, spans.length);
        assertURLSpan(newText, spans[0], "mailto:dummy@apple.com", 6, 21, 33);
    }

    public void testLinkifyEmailAddresses2() {
        String text = inputText1 + inputText2;
        C config = createConfigurator(new SpannableString(text));
        Spanned newText = toSpanned(config
                .linkifyEmailAddresses()
                .apply());
        assertEquals(text, newText.toString());
        Object[] spans = getSpans(newText);
        assertEquals(2, spans.length);
        assertURLSpan(newText, spans[0], "mailto:dummy@apple.com", 6, 21, 33);
        assertURLSpan(newText, spans[1], "mailto:johnDoe@google.com", 193, 211, 33);
    }

    public void testLinkifyMapAddresses1() {
        String text = inputText1;
        C config = createConfigurator(new SpannableString(text));
        Spanned newText = toSpanned(config
                .linkifyMapAddresses()
                .apply());
        assertEquals(text, newText.toString());
        Object[] spans = getSpans(newText);
        assertEquals(1, spans.length);
        assertURLSpan(newText, spans[0], "geo:0,0?q=1600+Amphitheatre+Pkwy%2C+Mountain+View%2C+CA+94043", 123, 170, 33);
    }

    public void testLinkifyMapAddresses2() {
        String text = inputText1 + inputText2;
        C config = createConfigurator(new SpannableString(text));
        Spanned newText = toSpanned(config
                .linkifyMapAddresses()
                .apply());
        assertEquals(text, newText.toString());
        Object[] spans = getSpans(newText);
        assertEquals(2, spans.length);
        assertURLSpan(newText, spans[0], "geo:0,0?q=1600+Amphitheatre+Pkwy%2C+Mountain+View%2C+CA+94043", 123, 170, 33);
        assertURLSpan(newText, spans[1], "geo:0,0?q=1601+Amphitheatre+Pkwy%2C+Mountain+View%2C+CA+94043", 312, 359, 33);
    }

    public void testLinkifyPhoneNumbers1() {
        String text = inputText1;
        C config = createConfigurator(new SpannableString(text));
        Spanned newText = toSpanned(config
                .linkifyPhoneNumbers()
                .apply());
        assertEquals(text, newText.toString());
        Object[] spans = getSpans(newText);
        assertEquals(1, spans.length);
        assertURLSpan(newText, spans[0], "tel:+85212345678", 88, 101, 33);
    }

    public void testLinkifyPhoneNumbers2() {
        String text = inputText1 + inputText2;
        C config = createConfigurator(new SpannableString(text));
        Spanned newText = toSpanned(config
                .linkifyPhoneNumbers()
                .apply());
        assertEquals(text, newText.toString());
        Object[] spans = getSpans(newText);
        assertEquals(2, spans.length);
        assertURLSpan(newText, spans[0], "tel:+85212345678", 88, 101, 33);
        assertURLSpan(newText, spans[1], "tel:+85287654321", 277, 290, 33);
    }

    public void testLinkifyWebUrls1() {
        String text = inputText1;
        C config = createConfigurator(new SpannableString(text));
        Spanned newText = toSpanned(config
                .linkifyWebUrls()
                .apply());
        assertEquals(text, newText.toString());
        Object[] spans = getSpans(newText);
        assertEquals(1, spans.length);
        assertURLSpan(newText, spans[0], "http://www.google.com/a/b", 47, 72, 33);
    }

    public void testLinkifyWebUrls2() {
        String text = inputText1 + inputText2;
        C config = createConfigurator(new SpannableString(text));
        Spanned newText = toSpanned(config
                .linkifyWebUrls()
                .apply());
        assertEquals(text, newText.toString());
        Object[] spans = getSpans(newText);
        assertEquals(2, spans.length);
        assertURLSpan(newText, spans[0], "http://www.google.com/a/b", 47, 72, 33);
        assertURLSpan(newText, spans[1], "http://www.apple.com/a/b", 237, 261, 33);
    }

    public void testLinkifyAll1() {
        String text = inputText1;
        C config = createConfigurator(new SpannableString(text));
        Spanned newText = toSpanned(config
                .linkifyAll()
                .apply());
        assertEquals(text, newText.toString());
        Object[] spans = getSpans(newText);
        assertEquals(4, spans.length);
        assertURLSpan(newText, spans[0], "mailto:dummy@apple.com", 6, 21, 33);
        assertURLSpan(newText, spans[1], "http://www.google.com/a/b", 47, 72, 33);
        assertURLSpan(newText, spans[2], "tel:+85212345678", 88, 101, 33);
        assertURLSpan(newText, spans[3], "geo:0,0?q=1600+Amphitheatre+Pkwy%2C+Mountain+View%2C+CA+94043", 123, 170, 33);
    }

    public void testLinkifyAll2() {
        String text = inputText1 + inputText2;
        C config = createConfigurator(new SpannableString(text));
        Spanned newText = toSpanned(config
                .linkifyAll()
                .apply());
        assertEquals(text, newText.toString());
        Object[] spans = getSpans(newText);
        assertEquals(8, spans.length);
        assertURLSpan(newText, spans[0], "mailto:dummy@apple.com", 6, 21, 33);
        assertURLSpan(newText, spans[1], "http://www.google.com/a/b", 47, 72, 33);
        assertURLSpan(newText, spans[2], "tel:+85212345678", 88, 101, 33);
        assertURLSpan(newText, spans[3], "geo:0,0?q=1600+Amphitheatre+Pkwy%2C+Mountain+View%2C+CA+94043", 123, 170, 33);
        assertURLSpan(newText, spans[4], "mailto:johnDoe@google.com", 193, 211, 33);
        assertURLSpan(newText, spans[5], "http://www.apple.com/a/b", 237, 261, 33);
        assertURLSpan(newText, spans[6], "tel:+85287654321", 277, 290, 33);
        assertURLSpan(newText, spans[7], "geo:0,0?q=1601+Amphitheatre+Pkwy%2C+Mountain+View%2C+CA+94043", 312, 359, 33);
    }

    public void testLnkifyPattern1() {
        String text = inputText1;
        Pattern pattern = Pattern.compile("CA");
        C config = createConfigurator(new SpannableString(text));
        Spanned newText = toSpanned(config
                .linkify(pattern, "http://www.google.ie/search?q=")
                .apply());
        assertEquals(text, newText.toString());
        Object[] spans = getSpans(newText);
        assertEquals(1, spans.length);
        assertURLSpan(newText, spans[0], "http://www.google.ie/search?q=CA", 162, 164, 33);

    }

    public void testLnkifyPattern2() {
        String text = inputText1 + inputText2;
        Pattern pattern = Pattern.compile("CA");
        C config = createConfigurator(new SpannableString(text));
        Spanned newText = toSpanned(config
                .linkify(pattern, "http://www.google.ie/search?q=")
                .apply());
        assertEquals(text, newText.toString());
        Object[] spans = getSpans(newText);
        assertEquals(2, spans.length);
        assertURLSpan(newText, spans[0], "http://www.google.ie/search?q=CA", 162, 164, 33);
        assertURLSpan(newText, spans[1], "http://www.google.ie/search?q=CA", 351, 353, 33);

    }

    public void testLinkifyPattern_MatchFilter0() {
        String text = inputText1;
        Pattern pattern = Pattern.compile("CA");
        Linkify.MatchFilter matchFilter = new Linkify.MatchFilter() {
            @Override
            public boolean acceptMatch(CharSequence cs, int start, int end) {
                return start > 162;
            }
        };
        C config = createConfigurator(new SpannableString(text));
        Spanned newText = toSpanned(config
                .linkify(pattern, "http://www.google.ie/search?q=", matchFilter, null)
                .apply());
        assertEquals(text, newText.toString());
        Object[] spans = getSpans(newText);
        assertEquals(0, spans.length);
    }

    public void testLinkifyPattern_MatchFilter1() {
        String text = inputText1 + inputText2;
        Pattern pattern = Pattern.compile("CA");
        Linkify.MatchFilter matchFilter = new Linkify.MatchFilter() {
            @Override
            public boolean acceptMatch(CharSequence cs, int start, int end) {
                return start > 162;
            }
        };
        C config = createConfigurator(new SpannableString(text));
        Spanned newText = toSpanned(config
                .linkify(pattern, "http://www.google.ie/search?q=", matchFilter, null)
                .apply());
        assertEquals(text, newText.toString());
        Object[] spans = getSpans(newText);
        assertEquals(1, spans.length);
        assertURLSpan(newText, spans[0], "http://www.google.ie/search?q=CA", 351, 353, 33);
    }

    public void testLnkifyPattern1_TransformFilter1() {
        String text = inputText1;
        Pattern pattern = Pattern.compile("CA");
        Linkify.TransformFilter transformFilter = new Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher match, String url) {
                return url + "/" + url;
            }
        };
        C config = createConfigurator(new SpannableString(text));
        Spanned newText = toSpanned(config
                .linkify(pattern, "http://www.google.ie/search?q=", null, transformFilter)
                .apply());
        assertEquals(text, newText.toString());
        Object[] spans = getSpans(newText);
        assertEquals(1, spans.length);
        assertURLSpan(newText, spans[0], "http://www.google.ie/search?q=CA/CA", 162, 164, 33);

    }

    public void testLnkifyPattern1_TransformFilter2() {
        String text = inputText1 + inputText2;
        Pattern pattern = Pattern.compile("CA");
        Linkify.TransformFilter transformFilter = new Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher match, String url) {
                return url + "/" + url;
            }
        };
        C config = createConfigurator(new SpannableString(text));
        Spanned newText = toSpanned(config
                .linkify(pattern, "http://www.google.ie/search?q=", null, transformFilter)
                .apply());
        assertEquals(text, newText.toString());
        Object[] spans = getSpans(newText);
        assertEquals(2, spans.length);
        assertURLSpan(newText, spans[0], "http://www.google.ie/search?q=CA/CA", 162, 164, 33);
        assertURLSpan(newText, spans[1], "http://www.google.ie/search?q=CA/CA", 351, 353, 33);
    }

}
