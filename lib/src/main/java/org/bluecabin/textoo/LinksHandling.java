package org.bluecabin.textoo;

/**
 * Configurator with link handling capability
 */
interface LinksHandling<T, C extends Configurator<T>> {
    C addLinksHandler(LinksHandler handler);
}
