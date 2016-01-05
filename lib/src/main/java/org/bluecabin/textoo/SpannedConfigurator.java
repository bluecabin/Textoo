package org.bluecabin.textoo;

import android.text.Spanned;

/**
 * Created by fergus on 1/4/16.
 */
public abstract class SpannedConfigurator extends BaseConfigurator<Spanned>
        implements TextLinkify<SpannedConfigurator>, LinksHandling<SpannedConfigurator> {
    SpannedConfigurator() {

    }

}
