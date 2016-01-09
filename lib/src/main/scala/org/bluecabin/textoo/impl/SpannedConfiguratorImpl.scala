package org.bluecabin.textoo.impl

import java.util.regex.Pattern

import android.text.util.Linkify
import android.text.util.Linkify.{MatchFilter, TransformFilter}
import android.text.{Spannable, SpannableString, Spanned}
import org.bluecabin.textoo.impl.Change.ChangeQueue
import org.bluecabin.textoo.{SpannedConfigurator, TextooContext}

import scala.collection.immutable.Queue

/**
  * Created by fergus on 1/5/16.
  */
private class SpannedConfiguratorImpl private[impl](override protected val initState: () => Spannable,
                                                    override protected val changes: ChangeQueue[Spannable] = Queue.empty)
                                                   (implicit override protected val textooContext: TextooContext)
  extends SpannedConfigurator(textooContext)
  with BaseConfiguratorImpl[Spannable, Spanned, SpannedConfigurator]
  with TextLinkifyImpl[Spannable, Spanned, SpannedConfigurator] {

  override protected def copy(newChanges: ChangeQueue[Spannable]): SpannedConfiguratorImpl =
    new SpannedConfiguratorImpl(initState, newChanges)

  override protected def toResult(text: Spannable): Spanned = text


  override final protected def linkifyText(text: Spannable, linkSpannableype: Int): Spannable = {
    Linkify.addLinks(text, linkSpannableype)
    text
  }

  override final protected def linkifyText(text: Spannable, pattern: Pattern, scheme: String): Spannable = {
    Linkify.addLinks(text, pattern, scheme)
    text
  }

  override final protected def linkifyText(text: Spannable, pattern: Pattern, scheme: String, matchFilter: MatchFilter,
                                           transformFilter: TransformFilter): Spannable = {
    Linkify.addLinks(text, pattern, scheme, matchFilter, transformFilter)
    text
  }
}

private object SpannedConfiguratorImpl {
  def create(context: TextooContext, text: Spanned): SpannedConfigurator =
    new SpannedConfiguratorImpl(() => text match {
      case s: Spannable => s
      case _ => new SpannableString(text)
    })(context)

}