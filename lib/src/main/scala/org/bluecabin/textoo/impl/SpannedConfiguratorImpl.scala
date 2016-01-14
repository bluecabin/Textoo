package org.bluecabin.textoo.impl

import java.util.regex.Pattern

import android.text.Spanned
import android.text.util.Linkify
import android.text.util.Linkify.{MatchFilter, TransformFilter}
import org.bluecabin.textoo.impl.Change.ChangeQueue
import org.bluecabin.textoo.util.CharSequenceSupport._
import org.bluecabin.textoo.{LinksHandler, SpannedConfigurator, TextooContext}

import scala.collection.immutable.Queue

/**
  * Created by fergus on 1/5/16.
  */
private class SpannedConfiguratorImpl private[impl](override protected val initState: () => Spanned,
                                                    override protected val changes: ChangeQueue[Spanned] = Queue.empty,
                                                    override protected val handlers: Queue[LinksHandler] = Queue.empty)
                                                   (implicit override protected val textooContext: TextooContext)
  extends SpannedConfigurator(textooContext)
  with ConfiguratorImpl[Spanned, SpannedConfigurator]
  with TextLinkifyImpl[Spanned, SpannedConfigurator]
  with LinksHandlingImpl[Spanned, SpannedConfigurator] {

  override protected def updateChanges(newChanges: ChangeQueue[Spanned]): SpannedConfiguratorImpl =
    new SpannedConfiguratorImpl(initState, newChanges, handlers)


  override protected def updateHandlers(newHandlers: Queue[LinksHandler]): SpannedConfiguratorImpl =
    new SpannedConfiguratorImpl(initState, changes, newHandlers)

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

  override protected def getSpannedFromResult(text: Spanned): Option[Spanned] = Some(text)

  override protected def setSpannedToResult(spanned: Spanned, text: Spanned): Spanned = spanned

  override protected def getSpanned(text: Spanned): Spanned = text

  override protected def setSpanned(text: Spanned, spanned: Spanned): Spanned = spanned
}

private object SpannedConfiguratorImpl {
  def create(context: TextooContext, text: Spanned): SpannedConfigurator =
    new SpannedConfiguratorImpl(() => text)(context)


}