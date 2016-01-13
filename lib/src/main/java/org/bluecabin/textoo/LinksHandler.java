package org.bluecabin.textoo;

import android.view.View;

/**
 * Callback when a link is clicked.  Register with {@link LinksHandling#addLinksHandler(LinksHandler)}.
 */
public interface LinksHandler {

    boolean onClick(View view, String url);
}
