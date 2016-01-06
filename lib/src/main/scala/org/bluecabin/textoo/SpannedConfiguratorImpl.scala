package org.bluecabin.textoo

import java.util.regex.Pattern

import android.text.util.Linkify
import android.text.util.Linkify.{TransformFilter, MatchFilter}
import android.text.{Spannable, SpannableString, Spanned}
import org.bluecabin.textoo.SpannedConfiguratorImpl._

import scala.collection.immutable.Queue

/**
  * Created by fergus on 1/5/16.
  */
private class SpannedConfiguratorImpl private(text: Spannable, actions: Queue[Action]) extends SpannedConfigurator {

  override def apply(): Spanned = {
    def runActions(pending: Queue[Action], result: Spannable): Spannable = {
      pending.headOption match {
        case Some(action) => runActions(pending.tail, action(result))
        case None => result
      }
    }
    runActions(actions, text)
  }

  override def linkifyPhoneNumbers(): SpannedConfigurator = appendAction(Linkify.PHONE_NUMBERS)

  override def linkifyEmailAddresses(): SpannedConfigurator = appendAction(Linkify.EMAIL_ADDRESSES)

  override def linkifyAll(): SpannedConfigurator = appendAction(Linkify.ALL)

  override def linkifyMapAddresses(): SpannedConfigurator = appendAction(Linkify.MAP_ADDRESSES)

  override def linkifyWebUrls(): SpannedConfigurator = appendAction(Linkify.WEB_URLS)

  private def appendAction(linkType: Int): SpannedConfigurator = new SpannedConfiguratorImpl(text, actions.enqueue { s: Spannable =>
    Linkify.addLinks(s, linkType)
    s
  })

  override def linkify(pattern: Pattern, scheme: String): SpannedConfigurator = new SpannedConfiguratorImpl(text, actions.enqueue { s: Spannable =>
    Linkify.addLinks(s, pattern, scheme)
    s
  })

  override def linkify(p: Pattern, scheme: String, matchFilter: MatchFilter, transformFilter: TransformFilter): SpannedConfigurator = new SpannedConfiguratorImpl(text, actions.enqueue { s: Spannable =>
    Linkify.addLinks(s, p, scheme, matchFilter, transformFilter)
    s
  })

}

private object SpannedConfiguratorImpl {
  /**
    *
    * @param factory For access protection.  Only allow those callers who have access to ConfiguratorFactory.
    * @return
    */
  def create(factory: ConfiguratorFactory, text: Spanned): SpannedConfigurator = new SpannedConfiguratorImpl(text match {
    case s: Spannable => s
    case _ => new SpannableString(text)
  }, Queue.empty)

  private type Action = Spannable => Spannable
}