package org.bluecabin.textoo;

import android.view.View;

/**
 * Callback when a link is clicked.  Register with {@link LinksHandling#addLinksHandler(LinksHandler)}.
 */
public interface LinksHandler {

    /**
     * Called when a link has been clicked
     *
     * @param view View containing the text with links
     * @param url  URL of the link
     * @return true if the callback consumed the click, false otherwise.
     */
    boolean onClick(View view, String url);
}
