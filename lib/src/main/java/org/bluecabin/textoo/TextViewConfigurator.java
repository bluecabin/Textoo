package org.bluecabin.textoo;

import android.widget.TextView;

/**
 * Created by fergus on 1/4/16.
 */
public abstract class TextViewConfigurator extends BaseConfigurator<TextView>
        implements TextLinkify<TextViewConfigurator>, LinksHandling<TextViewConfigurator> {
    TextViewConfigurator() {

    }

}
