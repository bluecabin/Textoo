package org.bluecabin.textoo.impl

import java.util.regex.Pattern

import android.text.Spanned
import android.text.util.Linkify
import android.text.util.Linkify.{MatchFilter, TransformFilter}
import org.bluecabin.textoo.impl.Change.ChangeQueue
import org.bluecabin.textoo.util.CharSequenceSupport
import CharSequenceSupport._
import org.bluecabin.textoo.{SpannedConfigurator, TextooContext}

import scala.collection.immutable.Queue

/**
  * Created by fergus on 1/5/16.
  */
private class SpannedConfiguratorImpl private[impl](override protected val initState: () => Spanned,
                                                    override protected val changes: ChangeQueue[Spanned] = Queue.empty)
                                                   (implicit override protected val textooContext: TextooContext)
  extends SpannedConfigurator(textooContext)
  with ConfiguratorImpl[Spanned, SpannedConfigurator]
  with TextLinkifyImpl[Spanned, SpannedConfigurator] {

  override protected def copy(newChanges: ChangeQueue[Spanned]): SpannedConfiguratorImpl =
    new SpannedConfiguratorImpl(initState, newChanges)

  override protected def toResult(text: Spanned): Spanned = text


  override final protected def linkifyText(text: Spanned, linkSpannableype: Int): Spanned = {
    Linkify.addLinks(text.toSpannable, linkSpannableype)
    text
  }

  override final protected def linkifyText(text: Spanned, pattern: Pattern, scheme: String): Spanned = {
    Linkify.addLinks(text.toSpannable, pattern, scheme)
    text
  }

  override final protected def linkifyText(text: Spanned, pattern: Pattern, scheme: String, matchFilter: MatchFilter,
                                           transformFilter: TransformFilter): Spanned = {
    Linkify.addLinks(text.toSpannable, pattern, scheme, matchFilter, transformFilter)
    text
  }
}

private object SpannedConfiguratorImpl {
  def create(context: TextooContext, text: Spanned): SpannedConfigurator =
    new SpannedConfiguratorImpl(() => text)(context)


}