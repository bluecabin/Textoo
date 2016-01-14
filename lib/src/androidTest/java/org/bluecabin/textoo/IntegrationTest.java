package org.bluecabin.textoo;

import android.test.AndroidTestCase;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.util.Linkify;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.bluecabin.textoo.TestUtils.assertURLSpan;
import static org.bluecabin.textoo.TestUtils.getSpans;
import static org.bluecabin.textoo.TestUtils.toSpanned;

/**
 * Created by fergus on 1/9/16.
 */
public class IntegrationTest extends AndroidTestCase {
    private static final String inputText1 = "This: dummy@apple.com is an email address; " +
            "And http://www.google.com/a/b is an url; " +
            "And +852 12345678 is phone number; " +
            "And 1600 Amphitheatre Pkwy, Mountain View, CA 94043 is map address; " +
            "And pattern1";
    private static final String inputText2 = "This: johnDoe@google.com is an email address; " +
            "And http://www.apple.com/a/b is an url; " +
            "And +852 87654321 is phone number; " +
            "And 1601 Amphitheatre Pkwy, Mountain View, CA 94043 is map address; " +
            "And pattern2";
    private static Pattern pattern1 = Pattern.compile("pattern1");
    private static Pattern pattern2 = Pattern.compile("pattern2");
    private static Linkify.MatchFilter matchFilter = new Linkify.MatchFilter() {
        @Override
        public boolean acceptMatch(CharSequence cs, int start, int end) {
            return start > 162;
        }
    };
    private static Linkify.TransformFilter transformFilter = new Linkify.TransformFilter() {
        @Override
        public String transformUrl(Matcher match, String url) {
            return url + "/" + url;
        }
    };

    public void testConfigSpanned() {
        SpannableString input = new SpannableString(inputText1 + inputText2);
        Spanned result = Textoo
                .config(input)
                .linkifyEmailAddresses()
                .linkifyMapAddresses()
                .linkifyPhoneNumbers()
                .linkifyWebUrls()
                .linkify(pattern1, "http://www.google.ie/search1?q=", matchFilter, null)
                .linkify(pattern2, "http://www.google.ie/search2?q=", null, transformFilter)
                .apply();
        assertEquals(input.toString(), result.toString());
        Object[] spans = getSpans(result);
        assertEquals(10, spans.length);
        assertURLSpan(result, spans[0], 392, 400, 33, "http://www.google.ie/search2?q=pattern2/pattern2");
        assertURLSpan(result, spans[1], 191, 199, 33, "http://www.google.ie/search1?q=pattern1");
        assertURLSpan(result, spans[2], 6, 21, 33, "mailto:dummy@apple.com");
        assertURLSpan(result, spans[3], 47, 72, 33, "http://www.google.com/a/b");
        assertURLSpan(result, spans[4], 88, 101, 33, "tel:+85212345678");
        assertURLSpan(result, spans[5], 123, 170, 33, "geo:0,0?q=1600+Amphitheatre+Pkwy%2C+Mountain+View%2C+CA+94043");
        assertURLSpan(result, spans[6], 205, 223, 33, "mailto:johnDoe@google.com");
        assertURLSpan(result, spans[7], 249, 273, 33, "http://www.apple.com/a/b");
        assertURLSpan(result, spans[8], 289, 302, 33, "tel:+85287654321");
        assertURLSpan(result, spans[9], 324, 371, 33, "geo:0,0?q=1601+Amphitheatre+Pkwy%2C+Mountain+View%2C+CA+94043");

    }

    public void testConfigSpanned_mixLinks() {
        String str = inputText1 + " <a href='https://www.yahoo.com'>http://www.yahoo</a>.net";
        Spanned input = Textoo.config(str).parseHtml().apply();
        Spanned result = Textoo
                .config(input)
                .linkifyEmailAddresses()
                .linkifyMapAddresses()
                .linkifyPhoneNumbers()
                .linkifyWebUrls()
                .apply();
        assertEquals(input.toString(), result.toString());
        Object[] spans = getSpans(result);
        assertEquals(5, spans.length);
        assertURLSpan(result, spans[0], 6, 21, 33, "mailto:dummy@apple.com");
        assertURLSpan(result, spans[1], 47, 72, 33, "http://www.google.com/a/b");
        assertURLSpan(result, spans[2], 88, 101, 33, "tel:+85212345678");
        assertURLSpan(result, spans[3], 123, 170, 33, "geo:0,0?q=1600+Amphitheatre+Pkwy%2C+Mountain+View%2C+CA+94043");
        assertURLSpan(result, spans[4], 200, 216, 33, "https://www.yahoo.com");
    }

    public void testConfigString() {
        String input = inputText1 + inputText2;
        Spanned result = Textoo
                .config(input)
                .linkifyEmailAddresses()
                .linkifyMapAddresses()
                .linkifyPhoneNumbers()
                .linkifyWebUrls()
                .linkify(pattern1, "http://www.google.ie/search1?q=", matchFilter, null)
                .linkify(pattern2, "http://www.google.ie/search2?q=", null, transformFilter)
                .apply();
        assertEquals(input.toString(), result.toString());
        Object[] spans = getSpans(result);
        assertEquals(10, spans.length);
        assertURLSpan(result, spans[0], 392, 400, 33, "http://www.google.ie/search2?q=pattern2/pattern2");
        assertURLSpan(result, spans[1], 191, 199, 33, "http://www.google.ie/search1?q=pattern1");
        assertURLSpan(result, spans[2], 6, 21, 33, "mailto:dummy@apple.com");
        assertURLSpan(result, spans[3], 47, 72, 33, "http://www.google.com/a/b");
        assertURLSpan(result, spans[4], 88, 101, 33, "tel:+85212345678");
        assertURLSpan(result, spans[5], 123, 170, 33, "geo:0,0?q=1600+Amphitheatre+Pkwy%2C+Mountain+View%2C+CA+94043");
        assertURLSpan(result, spans[6], 205, 223, 33, "mailto:johnDoe@google.com");
        assertURLSpan(result, spans[7], 249, 273, 33, "http://www.apple.com/a/b");
        assertURLSpan(result, spans[8], 289, 302, 33, "tel:+85287654321");
        assertURLSpan(result, spans[9], 324, 371, 33, "geo:0,0?q=1601+Amphitheatre+Pkwy%2C+Mountain+View%2C+CA+94043");
    }

    public void testConfigTextView() {
        String str = inputText1 + inputText2;
        TextView input = new TextView(getContext());
        input.setText(str);
        TextView newView = Textoo
                .config(input)
                .linkifyEmailAddresses()
                .linkifyMapAddresses()
                .linkifyPhoneNumbers()
                .linkifyWebUrls()
                .linkify(pattern1, "http://www.google.ie/search1?q=", matchFilter, null)
                .linkify(pattern2, "http://www.google.ie/search2?q=", null, transformFilter)
                .apply();
        assertSame(input, newView);
        assertEquals(str, newView.getText().toString());
        Spanned result = toSpanned(newView);
        Object[] spans = getSpans(result);
        assertEquals(10, spans.length);
        assertURLSpan(result, spans[0], 392, 400, 33, "http://www.google.ie/search2?q=pattern2/pattern2");
        assertURLSpan(result, spans[1], 191, 199, 33, "http://www.google.ie/search1?q=pattern1");
        assertURLSpan(result, spans[2], 6, 21, 33, "mailto:dummy@apple.com");
        assertURLSpan(result, spans[3], 47, 72, 33, "http://www.google.com/a/b");
        assertURLSpan(result, spans[4], 88, 101, 33, "tel:+85212345678");
        assertURLSpan(result, spans[5], 123, 170, 33, "geo:0,0?q=1600+Amphitheatre+Pkwy%2C+Mountain+View%2C+CA+94043");
        assertURLSpan(result, spans[6], 205, 223, 33, "mailto:johnDoe@google.com");
        assertURLSpan(result, spans[7], 249, 273, 33, "http://www.apple.com/a/b");
        assertURLSpan(result, spans[8], 289, 302, 33, "tel:+85287654321");
        assertURLSpan(result, spans[9], 324, 371, 33, "geo:0,0?q=1601+Amphitheatre+Pkwy%2C+Mountain+View%2C+CA+94043");
    }

    public void testConfigTextView_mixLinks() {
        String str = inputText1 + " <a href='https://www.yahoo.com'>http://www.yahoo</a>.net";
        Spanned strWithLink = Textoo.config(str).parseHtml().apply();
        TextView input = new TextView(getContext());
        input.setText(strWithLink);
        TextView newView = Textoo
                .config(input)
                .linkifyEmailAddresses()
                .linkifyMapAddresses()
                .linkifyPhoneNumbers()
                .linkifyWebUrls()
                .apply();
        assertSame(input, newView);
        assertEquals(strWithLink.toString(), newView.getText().toString());
        Spanned result = toSpanned(newView);
        Object[] spans = getSpans(result);
        assertEquals(5, spans.length);
        assertURLSpan(result, spans[0], 6, 21, 33, "mailto:dummy@apple.com");
        assertURLSpan(result, spans[1], 47, 72, 33, "http://www.google.com/a/b");
        assertURLSpan(result, spans[2], 88, 101, 33, "tel:+85212345678");
        assertURLSpan(result, spans[3], 123, 170, 33, "geo:0,0?q=1600+Amphitheatre+Pkwy%2C+Mountain+View%2C+CA+94043");
        assertURLSpan(result, spans[4], 200, 216, 33, "https://www.yahoo.com");
    }

}
