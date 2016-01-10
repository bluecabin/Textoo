package org.bluecabin.textoo.impl;

import android.text.Spanned;
import android.text.util.Linkify;
import org.bluecabin.textoo.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.bluecabin.textoo.TestUtils.assertURLSpan;

/**
 * Created by fergus on 1/6/16.
 */
public abstract class ConfiguratorImplTest<
        T1, C1 extends Configurator<T1> & TextLinkify<T2, C2>,
        T2, C2 extends Configurator<T2> & TextLinkify<T2, C2>
        > extends TextooTest {

    private static final String inputText1 = "This: dummy@apple.com is an email address; " +
            "And http://www.google.com/a/b is an url; " +
            "And +852 12345678 is phone number; " +
            "And 1600 Amphitheatre Pkwy, Mountain View, CA 94043 is map address; ";
    private static final String inputText2 = "This: johnDoe@google.com is an email address; " +
            "And http://www.apple.com/a/b is an url; " +
            "And +852 87654321 is phone number; " +
            "And 1601 Amphitheatre Pkwy, Mountain View, CA 94043 is map address; ";

    protected final Object[] getSpans(T2 actualResult) {
        Spanned actualSpanned = toSpanned(actualResult);
        return TestUtils.getSpans(actualSpanned);
    }


    protected abstract T1 createInitState(CharSequence text);

    protected abstract C1 createConfigurator(TextooContext textooContext, T1 initState);

    protected final C1 createConfigurator(TextooContext textooContext, CharSequence text) {
        return createConfigurator(textooContext, createInitState(text));
    }

    protected final C1 createConfigurator(CharSequence text) {
        return createConfigurator(createTextooContext(), text);
    }

    protected abstract String getText(T2 result);

    protected abstract Spanned toSpanned(T2 result);

    //
    // Test cases
    //

    public void testLinkifyEmailAddresses1() {
        String text = inputText1;
        C1 config = createConfigurator(text);
        T2 result = config
                .linkifyEmailAddresses()
                .apply();
        assertEquals(text, getText(result));
        Object[] spans = getSpans(result);
        assertEquals(1, spans.length);
        Spanned spanned = toSpanned(result);
        assertURLSpan(spanned, spans[0], 6, 21, 33, "mailto:dummy@apple.com");
    }

    public void testLinkifyEmailAddresses2() {
        String text = inputText1 + inputText2;
        C1 config = createConfigurator(text);
        T2 result = config
                .linkifyEmailAddresses()
                .apply();
        assertEquals(text, getText(result));
        Object[] spans = getSpans(result);
        assertEquals(2, spans.length);
        Spanned spanned = toSpanned(result);
        assertURLSpan(spanned, spans[0], 6, 21, 33, "mailto:dummy@apple.com");
        assertURLSpan(spanned, spans[1], 193, 211, 33, "mailto:johnDoe@google.com");
    }

    public void testLinkifyMapAddresses1() {
        String text = inputText1;
        C1 config = createConfigurator(text);
        T2 result = config
                .linkifyMapAddresses()
                .apply();
        assertEquals(text, getText(result));
        Object[] spans = getSpans(result);
        assertEquals(1, spans.length);
        Spanned spanned = toSpanned(result);
        assertURLSpan(spanned, spans[0], 123, 170, 33, "geo:0,0?q=1600+Amphitheatre+Pkwy%2C+Mountain+View%2C+CA+94043");
    }

    public void testLinkifyMapAddresses2() {
        String text = inputText1 + inputText2;
        C1 config = createConfigurator(text);
        T2 result = config
                .linkifyMapAddresses()
                .apply();
        assertEquals(text, getText(result));
        Object[] spans = getSpans(result);
        assertEquals(2, spans.length);
        Spanned spanned = toSpanned(result);
        assertURLSpan(spanned, spans[0], 123, 170, 33, "geo:0,0?q=1600+Amphitheatre+Pkwy%2C+Mountain+View%2C+CA+94043");
        assertURLSpan(spanned, spans[1], 312, 359, 33, "geo:0,0?q=1601+Amphitheatre+Pkwy%2C+Mountain+View%2C+CA+94043");
    }

    public void testLinkifyPhoneNumbers1() {
        String text = inputText1;
        C1 config = createConfigurator(text);
        T2 result = config
                .linkifyPhoneNumbers()
                .apply();
        assertEquals(text, getText(result));
        Object[] spans = getSpans(result);
        assertEquals(1, spans.length);
        Spanned spanned = toSpanned(result);
        assertURLSpan(spanned, spans[0], 88, 101, 33, "tel:+85212345678");
    }

    public void testLinkifyPhoneNumbers2() {
        String text = inputText1 + inputText2;
        C1 config = createConfigurator(text);
        T2 result = config
                .linkifyPhoneNumbers()
                .apply();
        assertEquals(text, getText(result));
        Object[] spans = getSpans(result);
        assertEquals(2, spans.length);
        Spanned spanned = toSpanned(result);
        assertURLSpan(spanned, spans[0], 88, 101, 33, "tel:+85212345678");
        assertURLSpan(spanned, spans[1], 277, 290, 33, "tel:+85287654321");
    }

    public void testLinkifyWebUrls1() {
        String text = inputText1;
        C1 config = createConfigurator(text);
        T2 result = config
                .linkifyWebUrls()
                .apply();
        assertEquals(text, getText(result));
        Object[] spans = getSpans(result);
        assertEquals(1, spans.length);
        Spanned spanned = toSpanned(result);
        assertURLSpan(spanned, spans[0], 47, 72, 33, "http://www.google.com/a/b");
    }

    public void testLinkifyWebUrls2() {
        String text = inputText1 + inputText2;
        C1 config = createConfigurator(text);
        T2 result = config
                .linkifyWebUrls()
                .apply();
        assertEquals(text, getText(result));
        Object[] spans = getSpans(result);
        assertEquals(2, spans.length);
        Spanned spanned = toSpanned(result);
        assertURLSpan(spanned, spans[0], 47, 72, 33, "http://www.google.com/a/b");
        assertURLSpan(spanned, spans[1], 237, 261, 33, "http://www.apple.com/a/b");
    }

    public void testLinkifyAll1() {
        String text = inputText1;
        C1 config = createConfigurator(text);
        T2 result = config
                .linkifyAll()
                .apply();
        assertEquals(text, getText(result));
        Object[] spans = getSpans(result);
        assertEquals(4, spans.length);
        Spanned spanned = toSpanned(result);
        assertURLSpan(spanned, spans[0], 6, 21, 33, "mailto:dummy@apple.com");
        assertURLSpan(spanned, spans[1], 47, 72, 33, "http://www.google.com/a/b");
        assertURLSpan(spanned, spans[2], 88, 101, 33, "tel:+85212345678");
        assertURLSpan(spanned, spans[3], 123, 170, 33, "geo:0,0?q=1600+Amphitheatre+Pkwy%2C+Mountain+View%2C+CA+94043");
    }

    public void testLinkifyAll2() {
        String text = inputText1 + inputText2;
        C1 config = createConfigurator(text);
        T2 result = config
                .linkifyAll()
                .apply();
        assertEquals(text, getText(result));
        Object[] spans = getSpans(result);
        assertEquals(8, spans.length);
        Spanned spanned = toSpanned(result);
        assertURLSpan(spanned, spans[0], 6, 21, 33, "mailto:dummy@apple.com");
        assertURLSpan(spanned, spans[1], 47, 72, 33, "http://www.google.com/a/b");
        assertURLSpan(spanned, spans[2], 88, 101, 33, "tel:+85212345678");
        assertURLSpan(spanned, spans[3], 123, 170, 33, "geo:0,0?q=1600+Amphitheatre+Pkwy%2C+Mountain+View%2C+CA+94043");
        assertURLSpan(spanned, spans[4], 193, 211, 33, "mailto:johnDoe@google.com");
        assertURLSpan(spanned, spans[5], 237, 261, 33, "http://www.apple.com/a/b");
        assertURLSpan(spanned, spans[6], 277, 290, 33, "tel:+85287654321");
        assertURLSpan(spanned, spans[7], 312, 359, 33, "geo:0,0?q=1601+Amphitheatre+Pkwy%2C+Mountain+View%2C+CA+94043");
    }

    public void testLnkifyPattern1() {
        String text = inputText1;
        Pattern pattern = Pattern.compile("CA");
        C1 config = createConfigurator(text);
        T2 result = config
                .linkify(pattern, "http://www.google.ie/search?q=")
                .apply();
        assertEquals(text, getText(result));
        Object[] spans = getSpans(result);
        assertEquals(1, spans.length);
        Spanned spanned = toSpanned(result);
        assertURLSpan(spanned, spans[0], 162, 164, 33, "http://www.google.ie/search?q=CA");

    }

    public void testLnkifyPattern2() {
        String text = inputText1 + inputText2;
        Pattern pattern = Pattern.compile("CA");
        C1 config = createConfigurator(text);
        T2 result = config
                .linkify(pattern, "http://www.google.ie/search?q=")
                .apply();
        assertEquals(text, getText(result));
        Object[] spans = getSpans(result);
        assertEquals(2, spans.length);
        Spanned spanned = toSpanned(result);
        assertURLSpan(spanned, spans[0], 162, 164, 33, "http://www.google.ie/search?q=CA");
        assertURLSpan(spanned, spans[1], 351, 353, 33, "http://www.google.ie/search?q=CA");

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
        C1 config = createConfigurator(text);
        T2 result = config
                .linkify(pattern, "http://www.google.ie/search?q=", matchFilter, null)
                .apply();
        assertEquals(text, getText(result));
        Object[] spans = getSpans(result);
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
        C1 config = createConfigurator(text);
        T2 result = config
                .linkify(pattern, "http://www.google.ie/search?q=", matchFilter, null)
                .apply();
        assertEquals(text, getText(result));
        Object[] spans = getSpans(result);
        assertEquals(1, spans.length);
        Spanned spanned = toSpanned(result);
        assertURLSpan(spanned, spans[0], 351, 353, 33, "http://www.google.ie/search?q=CA");
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
        C1 config = createConfigurator(text);
        T2 result = config
                .linkify(pattern, "http://www.google.ie/search?q=", null, transformFilter)
                .apply();
        assertEquals(text, getText(result));
        Object[] spans = getSpans(result);
        assertEquals(1, spans.length);
        Spanned spanned = toSpanned(result);
        assertURLSpan(spanned, spans[0], 162, 164, 33, "http://www.google.ie/search?q=CA/CA");

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
        C1 config = createConfigurator(text);
        T2 result = config
                .linkify(pattern, "http://www.google.ie/search?q=", null, transformFilter)
                .apply();
        assertEquals(text, getText(result));
        Object[] spans = getSpans(result);
        assertEquals(2, spans.length);
        Spanned spanned = toSpanned(result);
        assertURLSpan(spanned, spans[0], 162, 164, 33, "http://www.google.ie/search?q=CA/CA");
        assertURLSpan(spanned, spans[1], 351, 353, 33, "http://www.google.ie/search?q=CA/CA");
    }

}
