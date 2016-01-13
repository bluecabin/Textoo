package org.bluecabin.textoo.impl;

import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.view.View;
import android.widget.TextView;
import junit.framework.Assert;
import org.bluecabin.textoo.LinksHandler;
import org.bluecabin.textoo.TestUtils;
import org.bluecabin.textoo.TextViewConfigurator;
import org.bluecabin.textoo.TextooContext;

import java.util.regex.Pattern;

import static org.bluecabin.textoo.TestUtils.assertClickableSpanWrapper;
import static org.bluecabin.textoo.TestUtils.assertURLSpan;

/**
 * Created by fergus on 1/7/16.
 */
public class TextViewConfiguratorImplTest extends LinksHandlingConfiguratorImplTest<TextView, TextViewConfigurator, TextView, TextViewConfigurator> {
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
