package org.bluecabin.textoo;

import android.text.Html;
import android.text.Spanned;

/**
 * Created by fergus on 1/5/16.
 */
public abstract class StringConfigurator extends BaseConfigurator<Spanned> implements TextLinkify<SpannedConfigurator> {
    StringConfigurator() {

    }

    public abstract SpannedConfigurator parseHtml();

    public abstract SpannedConfigurator parseHtml(String source, Html.ImageGetter imageGetter,
                                                  Html.TagHandler tagHandler);
}
