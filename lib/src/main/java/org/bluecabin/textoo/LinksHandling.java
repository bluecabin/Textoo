package org.bluecabin.textoo;

/**
 * Created by fergus on 1/5/16.
 */
interface LinksHandling<T, C extends Configurator<T>> {
    C addLinksHandler(LinksHandler handler);
}
