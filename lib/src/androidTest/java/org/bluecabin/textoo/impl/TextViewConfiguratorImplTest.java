package org.bluecabin.textoo.impl;

import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
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

import static org.bluecabin.textoo.TestUtils.assertClickableSpanWrapper;
import static org.bluecabin.textoo.TestUtils.assertStyleSpan;

/**
 * Created by fergus on 1/7/16.
 */
public class TextViewConfiguratorImplTest extends ConfiguratorImplTest<TextView, TextViewConfigurator, TextView, TextViewConfigurator> {

    @Override
    protected TextViewConfigurator createConfigurator(TextooContext textooContext, CharSequence text) {
        Assert.assertNotNull(getContext());
        TextView view = new TextView(getContext());
        view.setText(text);
        return TextViewConfiguratorImpl.create(textooContext, view);
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

}
