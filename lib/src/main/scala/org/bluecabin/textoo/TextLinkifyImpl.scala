package org.bluecabin.textoo

import java.util.regex.Pattern

import android.text.util.Linkify
import android.text.util.Linkify.{MatchFilter, TransformFilter}

import scala.collection.immutable.Queue

/**
  * Created by fergus on 1/6/16.
  */
private trait TextLinkifyImpl[R, S, C <: BaseConfigurator[R]]
  extends BaseConfiguratorImpl[R, S] with TextLinkify[C] {

  override final def linkifyPhoneNumbers(): C = appendAction(Linkify.PHONE_NUMBERS)

  override final def linkifyEmailAddresses(): C = appendAction(Linkify.EMAIL_ADDRESSES)

  override final def linkifyAll(): C = appendAction(Linkify.ALL)

  override final def linkifyMapAddresses(): C = appendAction(Linkify.MAP_ADDRESSES)

  override final def linkifyWebUrls(): C = appendAction(Linkify.WEB_URLS)

  private def appendAction(linkType: Int): C = createConfigurator(text, actions.enqueue { s: S =>
    linkifyText(s, linkType)
  })

  override final def linkify(pattern: Pattern, scheme: String): C = createConfigurator(text, actions.enqueue { s: S =>
    linkifyText(s, pattern, scheme)
  })

  override final def linkify(p: Pattern, scheme: String, matchFilter: MatchFilter, transformFilter: TransformFilter): C =
    createConfigurator(text, actions.enqueue { s: S =>
      linkifyText(s, p, scheme, matchFilter, transformFilter)
    })

  protected def createConfigurator(text: S, actions: Queue[S => S]): C

  protected def linkifyText(text: S, linkType: Int): S

  protected def linkifyText(text: S, pattern: Pattern, scheme: String): S

  protected def linkifyText(text: S, pattern: Pattern, scheme: String, matchFilter: Linkify.MatchFilter,
                            transformFilter: Linkify.TransformFilter): S
}
