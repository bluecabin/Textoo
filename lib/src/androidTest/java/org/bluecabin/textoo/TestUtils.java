package org.bluecabin.textoo;

import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.widget.TextView;
import org.bluecabin.textoo.impl.ClickableSpanWrapper;
import org.bluecabin.textoo.util.CharSequenceSupport;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertSame;

/**
 * Created by fergus on 1/9/16.
 */
public class TestUtils {
    public static Object[] getSpans(Spanned spanned) {
        List<Object> result = new ArrayList<Object>();
        for (Object o : spanned.getSpans(0, spanned.length(), Object.class)) {
            if ((o instanceof URLSpan)
                    || (o instanceof StyleSpan)
                    || (o instanceof ImageSpan)
                    || (o instanceof ClickableSpanWrapper)) {
                result.add(o);
            }
        }
        return result.toArray(new Object[result.size()]);
    }

    public static Spanned toSpanned(TextView result) {
        CharSequence chars = result.getText();
        return CharSequenceSupport.toSpanned(chars);
    }

    public static void assertURLSpan(Spanned actualText, Object actualSpan, int expectedStart, int expectedEnd,
                                     int expectedFlags, String expectedURL) {
        assertEquals(URLSpan.class, actualSpan.getClass());
        assertSpan(actualText, actualSpan, expectedStart, expectedEnd, expectedFlags);
        URLSpan urlSpan = (URLSpan) actualSpan;
        assertEquals(expectedURL, urlSpan.getURL());
    }

    public static void assertStyleSpan(Spanned actualText, Object actualSpan, int expectedStart, int expectedEnd,
                                       int expectedFlags, int expectedStyle) {
        assertEquals(StyleSpan.class, actualSpan.getClass());
        assertSpan(actualText, actualSpan, expectedStart, expectedEnd, expectedFlags);
        StyleSpan styleSpan = (StyleSpan) actualSpan;
        assertEquals(expectedStyle, styleSpan.getStyle());
    }

    public static ImageSpan assertImageSpan(Spanned actualText, Object actualSpan, int expectedStart, int expectedEnd,
                                            int expectedFlags, String expectedSource) {
        assertEquals(ImageSpan.class, actualSpan.getClass());
        assertSpan(actualText, actualSpan, expectedStart, expectedEnd, expectedFlags);
        ImageSpan imgSpan = (ImageSpan) actualSpan;
        assertEquals(expectedSource, imgSpan.getSource());
        return imgSpan;
    }

    public static void assertImageSpan(Spanned actualText, Object actualSpan, int expectedStart, int expectedEnd,
                                       int expectedFlags, String expectedSource, Drawable expectedDrawable) {
        ImageSpan imgSpan = assertImageSpan(actualText, actualSpan, expectedStart, expectedEnd, expectedFlags,
                expectedSource);
        assertSame(expectedDrawable, imgSpan.getDrawable());

    }

    public static void assertClickableSpanWrapper(Spanned actualText, Object actualSpan, int expectedStart, int expectedEnd,
                                                  int expectedFlags) {
        assertEquals(ClickableSpanWrapper.class, actualSpan.getClass());
        assertSpan(actualText, actualSpan, expectedStart, expectedEnd, expectedFlags);
    }

    private static void assertSpan(Spanned actualText, Object actualSpan, int expectedStart, int expectedEnd,
                                   int expectedFlags) {
        assertEquals(expectedStart, actualText.getSpanStart(actualSpan));
        assertEquals(expectedEnd, actualText.getSpanEnd(actualSpan));
        assertEquals(expectedFlags, actualText.getSpanFlags(actualSpan));
    }


}
