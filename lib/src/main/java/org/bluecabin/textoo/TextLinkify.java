package org.bluecabin.textoo;

/**
 * Created by fergus on 1/5/16.
 */
interface TextLinkify<S extends BaseConfigurator> {
    S linkifyEmailAddresses();

    S linkifyMapAddresses();

    S linkifyPhoneNumbers();

    S linkifyWebUrls();

    S linkifyAll();
}
