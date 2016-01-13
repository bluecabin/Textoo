package org.bluecabin.textoo;

/**
 * Configurator with link handling capability
 */
public interface LinksHandling<T, C extends LinksHandling<T, C>> {
    C addLinksHandler(LinksHandler handler);
}
