package org.bluecabin.textoo.impl;

import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import junit.framework.Assert;
import org.bluecabin.textoo.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static org.bluecabin.textoo.TestUtils.*;

/**
 * Created by fergus on 1/7/16.
 */
public abstract class LinksHandlingConfiguratorImplTest<
        T1, C1 extends Configurator<T1> & TextLinkify<T2, C2> & LinksHandling<T2, C2>,
        T2, C2 extends Configurator<T2> & TextLinkify<T2, C2> & LinksHandling<T2, C2>
        > extends ConfiguratorImplTest<T1, C1, T2, C2> {

    public final void testAddLinksHandler() {
        final TextView expectedView = new TextView(getContext());

        String testData = "Given links of: <a href='https://www.google.com'>https://www.google.com</a> " +
                "and <a href='https://www.apple.com'>https://www.apple.com</a> " +
                "and some styled text like <b>bold</b> and <i>italic</i>";
        C1 config = createConfigurator(Html.fromHtml(testData));
        final List<String> actualClicks = new ArrayList<String>();
        T2 newText = config
                .addLinksHandler(new LinksHandler() {
                    @Override
                    public boolean onClick(View view, String url) {
                        assertSame(expectedView, view);
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
                        assertSame(expectedView, view);
                        if ("https://www.apple.com".equals(url)) {
                            actualClicks.add("apple");
                            return true;
                        } else {
                            return false;
                        }
                    }
                })
                .apply();
        Object[] spans = getSpans(newText);
        Assert.assertEquals(4, spans.length);
        Spanned spanned = toSpanned(newText);
        assertStyleSpan(spanned, spans[0], 91, 95, 33, 1);
        assertStyleSpan(spanned, spans[1], 100, 106, 33, 2);
        assertClickableSpanWrapper(spanned, spans[2], 16, 38, 33);
        assertClickableSpanWrapper(spanned, spans[3], 43, 64, 33);
        for (Object span : spans) {
            if (span instanceof ClickableSpan) {
                ((ClickableSpan) span).onClick(expectedView);
            }
        }
        List<String> expectedClicks = new ArrayList<String>();
        expectedClicks.add("google");
        expectedClicks.add("apple");
        Assert.assertEquals(expectedClicks, actualClicks);
    }

}
