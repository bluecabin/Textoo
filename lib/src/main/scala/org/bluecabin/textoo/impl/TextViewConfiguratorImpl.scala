package org.bluecabin.textoo.impl

import java.util.regex.Pattern

import android.text.method.LinkMovementMethod
import android.text.style.{ClickableSpan, URLSpan}
import android.text.util.Linkify
import android.text.util.Linkify.{MatchFilter, TransformFilter}
import android.text.{Editable, Spanned}
import android.view.View
import android.widget.TextView
import org.bluecabin.textoo.impl.Change.ChangeQueue
import org.bluecabin.textoo.{LinksHandler, TextViewConfigurator, TextooContext}
import org.bluecabin.textoo.util.CharSequenceSupport._
import scala.collection.immutable.Queue

/**
  * Created by fergus on 1/4/16.
  */
private class TextViewConfiguratorImpl private(override protected val initState: () => TextView,
                                               override protected val changes: ChangeQueue[TextView],
                                               val handlers: Queue[LinksHandler])
                                              (implicit override protected val textooContext: TextooContext)
  extends TextViewConfigurator(textooContext)
  with ConfiguratorImpl[TextView, TextViewConfigurator]
  with TextLinkifyImpl[TextView, TextViewConfigurator] {

  override def addLinksHandler(handler: LinksHandler): TextViewConfigurator =
    new TextViewConfiguratorImpl(initState, changes, handlers = handlers.enqueue(handler))

  override protected def copy(newChanges: ChangeQueue[TextView]): TextViewConfigurator =
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

  override protected def toResult(text: TextView): TextView = {
    text.getText match {
      case spanned: Spanned =>
        if (handlers.nonEmpty) {
          val spannable = spanned.toSpannable
          for {
            urlSpan <- spanned.getSpans(0, spanned.length(), classOf[URLSpan])
          } {
            val url = urlSpan.getURL
            val wrapper = new ClickableSpanWrapper(urlSpan, { v: View =>
              handlers.find(_.onClick(text, url)).nonEmpty
            })
            val start = spanned.getSpanStart(urlSpan)
            val end = spanned.getSpanEnd(urlSpan)
            val flags = spanned.getSpanFlags(urlSpan)
            spannable.removeSpan(urlSpan)
            spannable.setSpan(wrapper, start, end, flags)
          }
          text.setText(spannable)
        }
        if (spanned.getSpans(0, spanned.length(), classOf[ClickableSpan]).length > 0) {
          text.setMovementMethod(LinkMovementMethod.getInstance())
        }
        text
      case _ => text
    }

  }

}

private object TextViewConfiguratorImpl {

  def create(context: TextooContext, view: TextView): TextViewConfigurator =
    new TextViewConfiguratorImpl(() => view, Queue.empty, Queue.empty)(context)

  private type HandlerQueue = Queue[LinksHandler]

}