package org.bluecabin.textoo;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import org.xml.sax.XMLReader;

/**
 * Created by fergus on 1/6/16.
 */
public class StringConfiguratorImplTest extends LinkifyTest<StringConfigurator> {
    private static final String html1 = "This is a test only <b>bold</b> " +
            "<a href=\"http://a.b.c/def\">link</a> " +
            "<i>italic</i> " +
            "img: <img src=\"http://apple.com/img1.jpeg\"> " +
            "<unknown>This is a known tag</unknown>";

    @Override
    protected StringConfigurator createConfigurator(String text) {
        return StringConfiguratorImpl.create(ConfiguratorFactory.getInstance(), text);
    }

    @Override
    protected Spanned toSpanned(Object result) {
        return (Spanned) result;
    }

    public void testParseHTML() {
        String text = html1;
        StringConfigurator config = createConfigurator(text);
        Spanned newText = toSpanned(config
                .parseHtml()
                .apply());
        assertEquals("This is a test only bold link italic img: ￼ This is a known tag", newText.toString());
        Object[] spans = getSpans(newText);
        assertEquals(4, spans.length);
        assertStyleSpan(newText, spans[0], 20, 24, 33, 1);
        assertURLSpan(newText, spans[1], 25, 29, 33, "http://a.b.c/def");
        assertStyleSpan(newText, spans[2], 30, 36, 33, 2);
        assertImageSpan(newText, spans[3], 42, 43, 33, "http://apple.com/img1.jpeg");
    }

    public void testParseHTML_ImageGetter() {
        String text = html1;
        StringConfigurator config = createConfigurator(text);
        final Drawable drawable = new ColorDrawable();
        Html.ImageGetter imgGetter = new Html.ImageGetter() {

            @Override
            public Drawable getDrawable(String source) {
                assertEquals("http://apple.com/img1.jpeg", source);
                return drawable;
            }
        };
        Spanned newText = toSpanned(config
                .parseHtml(imgGetter, null)
                .apply());
        assertEquals("This is a test only bold link italic img: ￼ This is a known tag", newText.toString());
        Object[] spans = getSpans(newText);
        assertEquals(4, spans.length);
        assertStyleSpan(newText, spans[0], 20, 24, 33, 1);
        assertURLSpan(newText, spans[1], 25, 29, 33, "http://a.b.c/def");
        assertStyleSpan(newText, spans[2], 30, 36, 33, 2);
        assertImageSpan(newText, spans[3], 42, 43, 33, "http://apple.com/img1.jpeg", drawable);
    }

    public void testParseHTML_TagHandler() {
        String text = html1;
        StringConfigurator config = createConfigurator(text);
        final Drawable drawable = new ColorDrawable();
        Html.TagHandler tagHandler = new Html.TagHandler() {
            @Override
            public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
                if ("html".equals(tag)) return;
                if ("body".equals(tag)) return;
                if ("img".equals(tag)) return;
                assertEquals("unknown", tag);
                if (opening) {
                    output.append("[unknown:");
                } else {
                    output.append("]");
                }

            }
        };
        Spanned newText = toSpanned(config
                .parseHtml(null, tagHandler)
                .apply());
        assertEquals("This is a test only bold link italic img: ￼ [unknown:This is a known tag]", newText.toString());
        Object[] spans = getSpans(newText);
        assertEquals(4, spans.length);
        assertStyleSpan(newText, spans[0], 20, 24, 33, 1);
        assertURLSpan(newText, spans[1], 25, 29, 33, "http://a.b.c/def");
        assertStyleSpan(newText, spans[2], 30, 36, 33, 2);
        assertImageSpan(newText, spans[3], 42, 43, 33, "http://apple.com/img1.jpeg");
    }
}
