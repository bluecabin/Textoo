package org.bluecabin.textoo.impl;

import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.ClickableSpan;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;
import junit.framework.Assert;
import org.bluecabin.textoo.LinksHandler;
import org.bluecabin.textoo.TestUtils;
import org.bluecabin.textoo.TextViewConfigurator;
import org.bluecabin.textoo.TextooContext;
import org.bluecabin.textoo.impl.TextViewConfiguratorImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static org.bluecabin.textoo.TestUtils.assertClickableSpanWrapper;
import static org.bluecabin.textoo.TestUtils.assertStyleSpan;
import static org.bluecabin.textoo.TestUtils.assertURLSpan;

/**
 * Created by fergus on 1/7/16.
 */
public class TextViewConfiguratorImplTest extends ConfiguratorImplTest<TextView, TextViewConfigurator, TextView, TextViewConfigurator> {
    private static final String inputText1 = "This: dummy@apple.com is an email address; " +
            "And http://www.google.com/a/b is an url; " +
            "And +852 12345678 is phone number; " +
            "And 1600 Amphitheatre Pkwy, Mountain View, CA 94043 is map address; ";

    @Override
    protected TextView createInitState(CharSequence text) {
        TextView view = new TextView(getContext());
        view.setText(text);
        return view;
    }

    @Override
    protected TextViewConfigurator createConfigurator(TextooContext textooContext, TextView initState) {
        Assert.assertNotNull(getContext());
        return TextViewConfiguratorImpl.create(textooContext, initState);
    }

    @Override
    protected String getText(TextView result) {
        return result.getText().toString();
    }

    @Override
    protected Spanned toSpanned(TextView view) {
        return TestUtils.toSpanned(view);
    }

    public void testAddLinksHandler() {
        String testData = "Given links of: <a href='https://www.google.com'>https://www.google.com</a> " +
                "and <a href='https://www.apple.com'>https://www.apple.com</a> " +
                "and some styled text like <b>bold</b> and <i>italic</i>";
        TextViewConfigurator config = createConfigurator(Html.fromHtml(testData));
        final List<String> actualClicks = new ArrayList<String>();
        TextView newView = config
                .addLinksHandler(new LinksHandler() {
                    @Override
                    public boolean onClick(View view, String url) {
                        if ("https://www.google.com".equals(url)) {
                            actualClicks.add("google");
                            return true;
                        } else {
                            return false;
                        }
                    }
                })
                .addLinksHandler(new LinksHandler() {
                    @Override
                    public boolean onClick(View view, String url) {
                        if ("https://www.apple.com".equals(url)) {
                            actualClicks.add("apple");
                            return true;
                        } else {
                            return false;
                        }
                    }
                })
                .apply();
        Object[] spans = getSpans(newView);
        Assert.assertEquals(4, spans.length);
        Spanned spanned = toSpanned(newView);
        assertStyleSpan(spanned, spans[0], 91, 95, 33, 1);
        assertStyleSpan(spanned, spans[1], 100, 106, 33, 2);
        assertClickableSpanWrapper(spanned, spans[2], 16, 38, 33);
        assertClickableSpanWrapper(spanned, spans[3], 43, 64, 33);
        for (Object span : spans) {
            if (span instanceof ClickableSpan) {
                ((ClickableSpan) span).onClick(newView);
            }
        }
        List<String> expectedClicks = new ArrayList<String>();
        expectedClicks.add("google");
        expectedClicks.add("apple");
        Assert.assertEquals(expectedClicks, actualClicks);
    }

    private void testLinkifyAll(TextView initState, MovementMethod expectedMovementMethod) {
        TextViewConfigurator config = createConfigurator(createTextooContext(), initState);
        TextView result = config
                .linkifyAll()
                .apply();
        assertEquals(expectedMovementMethod, result.getMovementMethod());
    }

    private void testLinkifyPattern(TextView initState, Pattern pattern, MovementMethod expectedMovementMethod) {
        TextViewConfigurator config = createConfigurator(createTextooContext(), initState);
        TextView result = config
                .linkify(pattern, "http://www.google.ie/search?q=")
                .apply();
        assertEquals(expectedMovementMethod, result.getMovementMethod());
    }

    public void testTextViewSettings_linkify_linksAdded() {
        TextView view = createInitState(inputText1);
        assertNull(view.getMovementMethod());
        testLinkifyAll(view, LinkMovementMethod.getInstance());
    }

    public void testTextViewSettings_linkify_withoutLink() {
        TextView view = createInitState("text without any links");
        assertNull(view.getMovementMethod());
        testLinkifyAll(view, null);
    }

    public void testTextViewSettings_linkify_withoutLink_alreadyHasMovementMethod() {
        TextView view = createInitState("text without any links");
        MovementMethod movementMethod = LinkMovementMethod.getInstance();
        view.setMovementMethod(movementMethod);
        testLinkifyAll(view, movementMethod);
    }

    public void testTextViewSettings_linkify_pattern_linksAdded() {
        TextView view = createInitState(inputText1);
        assertNull(view.getMovementMethod());
        testLinkifyPattern(view, Pattern.compile("CA"), LinkMovementMethod.getInstance());
    }

    public void testTextViewSettings_linkify_pattern_noMatch() {
        TextView view = createInitState("text without links");
        assertNull(view.getMovementMethod());
        testLinkifyPattern(view, Pattern.compile("No"), null);
    }

    public void testTextViewSettings_linkify_pattern_noMatch_alreadyHasMovementMethod() {
        TextView view = createInitState("text without links");
        MovementMethod movementMethod = LinkMovementMethod.getInstance();
        view.setMovementMethod(movementMethod);
        testLinkifyPattern(view, Pattern.compile("No"), movementMethod);
    }

    public void testTextViewSettings_alreadyHasURLSpan() {
        TextView initState = createInitState(Html.fromHtml("<a href='https://www.google.com'>Google</a>"));
        assertNull(initState.getMovementMethod());
        TextViewConfigurator config = createConfigurator(createTextooContext(), initState);
        TextView result = config
                .apply();
        Object[] spans = getSpans(result);
        Assert.assertEquals(1, spans.length);
        Spanned spanned = toSpanned(result);
        assertURLSpan(spanned, spans[0], 0, 6, 33, "https://www.google.com");
        assertEquals(LinkMovementMethod.getInstance(), result.getMovementMethod());
    }

    public void testTextViewSettings_alreadyHasURLSpan_linkHandlersAdded() {
        TextView initState = createInitState(Html.fromHtml("<a href='https://www.google.com'>Google</a>"));
        assertNull(initState.getMovementMethod());
        TextViewConfigurator config = createConfigurator(createTextooContext(), initState);
        TextView result = config
                .addLinksHandler(new LinksHandler() {
                    @Override
                    public boolean onClick(View view, String url) {
                        return true;
                    }
                })
                .apply();
        Object[] spans = getSpans(result);
        Assert.assertEquals(1, spans.length);
        Spanned spanned = toSpanned(result);
        assertClickableSpanWrapper(spanned, spans[0], 0, 6, 33);
        assertEquals(LinkMovementMethod.getInstance(), result.getMovementMethod());
    }


}
