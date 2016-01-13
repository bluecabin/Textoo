package org.bluecabin.textoo.impl

import java.util.regex.Pattern

import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.util.Linkify
import android.text.util.Linkify.{MatchFilter, TransformFilter}
import android.widget.TextView
import org.bluecabin.textoo.impl.Change.ChangeQueue
import org.bluecabin.textoo.{LinksHandler, TextViewConfigurator, TextooContext}

import scala.collection.immutable.Queue

/**
  * Created by fergus on 1/4/16.
  */
private class TextViewConfiguratorImpl private(override protected val initState: () => TextView,
                                               override protected val changes: ChangeQueue[TextView],
                                               override protected val handlers: Queue[LinksHandler])
                                              (implicit override protected val textooContext: TextooContext)
  extends TextViewConfigurator(textooContext)
  with ConfiguratorImpl[TextView, TextViewConfigurator]
  with TextLinkifyImpl[TextView, TextViewConfigurator]
  with LinksHandlingImpl[TextView, TextViewConfigurator] {

  override protected def updateHandlers(newHandlers: Queue[LinksHandler]): TextViewConfigurator =
    new TextViewConfiguratorImpl(initState, changes, handlers = newHandlers)

  override protected def updateChanges(newChanges: ChangeQueue[TextView]): TextViewConfigurator =
    new TextViewConfiguratorImpl(initState, newChanges, handlers)

  override protected def linkifyText(text: TextView, mask: Int): TextView = {
    Linkify.addLinks(text, mask)
    text
  }

  override protected def linkifyText(text: TextView, pattern: Pattern, scheme: String): TextView = {
    Linkify.addLinks(text, pattern, scheme)
    text
  }

  override protected def linkifyText(text: TextView, pattern: Pattern, scheme: String, matchFilter: MatchFilter,
                                     transformFilter: TransformFilter): TextView = {
    Linkify.addLinks(text, pattern, scheme, matchFilter, transformFilter)
    text
  }


  override protected def getSpannedFromResult(text: TextView): Option[Spanned] = text.getText match {
    case spanned: Spanned => Some(spanned)
    case _ => None
  }

  override protected def setSpannedToResult(spanned: Spanned, text: TextView): TextView = {
    text.setText(spanned)
    text
  }

  override protected def toResult(text: TextView): TextView = {
    val currResult = super.toResult(text)
    currResult.getText match {
      case spanned: Spanned =>
        if (spanned.getSpans(0, spanned.length(), classOf[ClickableSpan]).length > 0) {
          currResult.setMovementMethod(LinkMovementMethod.getInstance())
        }
        currResult
      case _ => currResult
    }

  }

}

private object TextViewConfiguratorImpl {

  def create(context: TextooContext, view: TextView): TextViewConfigurator =
    new TextViewConfiguratorImpl(() => view, Queue.empty, Queue.empty)(context)

  private type HandlerQueue = Queue[LinksHandler]

}