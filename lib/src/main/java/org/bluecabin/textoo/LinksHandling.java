package org.bluecabin.textoo;

/**
 * Created by fergus on 1/5/16.
 */
interface LinksHandling<O, C extends BaseConfigurator<O>> {
    C addLinksHandler(LinksHandler handler);
}
