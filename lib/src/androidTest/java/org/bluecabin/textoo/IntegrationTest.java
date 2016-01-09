package org.bluecabin.textoo;

import android.test.AndroidTestCase;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.util.Linkify;

import static org.bluecabin.textoo.TestUtils.assertURLSpan;
import static org.bluecabin.textoo.TestUtils.getSpans;

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

    public void testConfigSpanned() {
        SpannableString input = new SpannableString(inputText1 + inputText2);
        Spanned result = Textoo
                .config(input)
                .linkifyEmailAddresses()
                .linkifyMapAddresses()
                .linkifyPhoneNumbers()
                .linkifyWebUrls()
                .apply();
        assertEquals(input.toString(), result.toString());
        Object[] spans = getSpans(result);
        assertEquals(8, spans.length);
        assertURLSpan(result, spans[0], 6, 21, 33, "mailto:dummy@apple.com");
        assertURLSpan(result, spans[1], 47, 72, 33, "http://www.google.com/a/b");
        assertURLSpan(result, spans[2], 88, 101, 33, "tel:+85212345678");
        assertURLSpan(result, spans[3], 123, 170, 33, "geo:0,0?q=1600+Amphitheatre+Pkwy%2C+Mountain+View%2C+CA+94043");
        assertURLSpan(result, spans[4], 193, 211, 33, "mailto:johnDoe@google.com");
        assertURLSpan(result, spans[5], 237, 261, 33, "http://www.apple.com/a/b");
        assertURLSpan(result, spans[6], 277, 290, 33, "tel:+85287654321");
        assertURLSpan(result, spans[7], 312, 359, 33, "geo:0,0?q=1601+Amphitheatre+Pkwy%2C+Mountain+View%2C+CA+94043");

//        Pattern pattern = Pattern.compile("CA");
//        Linkify.MatchFilter matchFilter = new Linkify.MatchFilter() {
//            @Override
//            public boolean acceptMatch(CharSequence cs, int start, int end) {
//                return start > 162;
//            }
//        };
//        C1 config = createConfigurator(text);
//        R2 result = config
//                .linkify(pattern, "http://www.google.ie/search?q=", matchFilter, null)
//                .apply();
//        assertEquals(text, getText(result));
//        Object[] spans = getSpans(result);
//        assertEquals(1, spans.length);
//        Spanned spanned = toSpanned(result);
//        assertURLSpan(spanned, spans[0], 351, 353, 33, "http://www.google.ie/search?q=CA");


//        Pattern pattern = Pattern.compile("CA");
//        Linkify.TransformFilter transformFilter = new Linkify.TransformFilter() {
//            @Override
//            public String transformUrl(Matcher match, String url) {
//                return url + "/" + url;
//            }
//        };
//        C1 config = createConfigurator(text);
//        R2 result = config
//                .linkify(pattern, "http://www.google.ie/search?q=", null, transformFilter)
//                .apply();
//        assertEquals(text, getText(result));
//        Object[] spans = getSpans(result);
//        assertEquals(2, spans.length);
//        Spanned spanned = toSpanned(result);
//        assertURLSpan(spanned, spans[0], 162, 164, 33, "http://www.google.ie/search?q=CA/CA");
//        assertURLSpan(spanned, spans[1], 351, 353, 33, "http://www.google.ie/search?q=CA/CA");

    }
}
