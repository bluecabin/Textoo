package org.bluecabin.textoo;

import android.test.AndroidTestCase;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
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
            "And 1600 Amphitheatre Pkwy, Mountain View, CA 94043 is map address; ";
    private static final String inputText2 = "This: johnDoe@google.com is an email address; " +
            "And http://www.apple.com/a/b is an url; " +
            "And +852 87654321 is phone number; " +
            "And 1601 Amphitheatre Pkwy, Mountain View, CA 94043 is map address; ";
    private static Pattern pattern = Pattern.compile("CA");
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
                .linkify(pattern, "http://www.google.ie/search1?q=", matchFilter, null)
                .linkify(pattern, "http://www.google.ie/search2?q=", null, transformFilter)
                .linkify(pattern, "http://www.google.ie/search3?q=", matchFilter, transformFilter)
                .apply();
        assertEquals(input.toString(), result.toString());
        Object[] spans = getSpans(result);
        assertEquals(12, spans.length);
        assertURLSpan(result, spans[0], 6, 21, 33, "mailto:dummy@apple.com");
        assertURLSpan(result, spans[1], 47, 72, 33, "http://www.google.com/a/b");
        assertURLSpan(result, spans[2], 88, 101, 33, "tel:+85212345678");
        assertURLSpan(result, spans[3], 123, 170, 33, "geo:0,0?q=1600+Amphitheatre+Pkwy%2C+Mountain+View%2C+CA+94043");
        assertURLSpan(result, spans[4], 193, 211, 33, "mailto:johnDoe@google.com");
        assertURLSpan(result, spans[5], 237, 261, 33, "http://www.apple.com/a/b");
        assertURLSpan(result, spans[6], 277, 290, 33, "tel:+85287654321");
        assertURLSpan(result, spans[7], 312, 359, 33, "geo:0,0?q=1601+Amphitheatre+Pkwy%2C+Mountain+View%2C+CA+94043");
        assertURLSpan(result, spans[8], 351, 353, 33, "http://www.google.ie/search1?q=CA");
        assertURLSpan(result, spans[9], 162, 164, 33, "http://www.google.ie/search2?q=CA/CA");
        assertURLSpan(result, spans[10], 351, 353, 33, "http://www.google.ie/search2?q=CA/CA");
        assertURLSpan(result, spans[11], 351, 353, 33, "http://www.google.ie/search3?q=CA/CA");

    }

    public void testConfigString() {
        String input = inputText1 + inputText2;
        Spanned result = Textoo
                .config(input)
                .linkifyEmailAddresses()
                .linkifyMapAddresses()
                .linkifyPhoneNumbers()
                .linkifyWebUrls()
                .linkify(pattern, "http://www.google.ie/search1?q=", matchFilter, null)
                .linkify(pattern, "http://www.google.ie/search2?q=", null, transformFilter)
                .linkify(pattern, "http://www.google.ie/search3?q=", matchFilter, transformFilter)
                .apply();
        assertEquals(input.toString(), result.toString());
        Object[] spans = getSpans(result);
        assertEquals(12, spans.length);
        assertURLSpan(result, spans[0], 6, 21, 33, "mailto:dummy@apple.com");
        assertURLSpan(result, spans[1], 47, 72, 33, "http://www.google.com/a/b");
        assertURLSpan(result, spans[2], 88, 101, 33, "tel:+85212345678");
        assertURLSpan(result, spans[3], 123, 170, 33, "geo:0,0?q=1600+Amphitheatre+Pkwy%2C+Mountain+View%2C+CA+94043");
        assertURLSpan(result, spans[4], 193, 211, 33, "mailto:johnDoe@google.com");
        assertURLSpan(result, spans[5], 237, 261, 33, "http://www.apple.com/a/b");
        assertURLSpan(result, spans[6], 277, 290, 33, "tel:+85287654321");
        assertURLSpan(result, spans[7], 312, 359, 33, "geo:0,0?q=1601+Amphitheatre+Pkwy%2C+Mountain+View%2C+CA+94043");
        assertURLSpan(result, spans[8], 351, 353, 33, "http://www.google.ie/search1?q=CA");
        assertURLSpan(result, spans[9], 162, 164, 33, "http://www.google.ie/search2?q=CA/CA");
        assertURLSpan(result, spans[10], 351, 353, 33, "http://www.google.ie/search2?q=CA/CA");
        assertURLSpan(result, spans[11], 351, 353, 33, "http://www.google.ie/search3?q=CA/CA");
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
                .linkify(pattern, "http://www.google.ie/search1?q=", matchFilter, null)
                .linkify(pattern, "http://www.google.ie/search2?q=", null, transformFilter)
                .linkify(pattern, "http://www.google.ie/search3?q=", matchFilter, transformFilter)
                .apply();
        assertSame(input, newView);
        assertEquals(str, newView.getText().toString());
        Spanned result = toSpanned(newView);
        Object[] spans = getSpans(result);
        assertEquals(12, spans.length);
        assertURLSpan(result, spans[0], 6, 21, 33, "mailto:dummy@apple.com");
        assertURLSpan(result, spans[1], 47, 72, 33, "http://www.google.com/a/b");
        assertURLSpan(result, spans[2], 88, 101, 33, "tel:+85212345678");
        assertURLSpan(result, spans[3], 123, 170, 33, "geo:0,0?q=1600+Amphitheatre+Pkwy%2C+Mountain+View%2C+CA+94043");
        assertURLSpan(result, spans[4], 193, 211, 33, "mailto:johnDoe@google.com");
        assertURLSpan(result, spans[5], 237, 261, 33, "http://www.apple.com/a/b");
        assertURLSpan(result, spans[6], 277, 290, 33, "tel:+85287654321");
        assertURLSpan(result, spans[7], 312, 359, 33, "geo:0,0?q=1601+Amphitheatre+Pkwy%2C+Mountain+View%2C+CA+94043");
        assertURLSpan(result, spans[8], 351, 353, 33, "http://www.google.ie/search1?q=CA");
        assertURLSpan(result, spans[9], 162, 164, 33, "http://www.google.ie/search2?q=CA/CA");
        assertURLSpan(result, spans[10], 351, 353, 33, "http://www.google.ie/search2?q=CA/CA");
        assertURLSpan(result, spans[11], 351, 353, 33, "http://www.google.ie/search3?q=CA/CA");
    }
}
