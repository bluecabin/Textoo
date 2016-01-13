package org.bluecabin.textoo.impl

import android.text.Spanned
import android.text.style.URLSpan
import android.view.View
import org.bluecabin.textoo._
import org.bluecabin.textoo.util.CharSequenceSupport._

import scala.collection.immutable.Queue

/**
  * Created by fergus on 1/13/16.
  */
private trait LinksHandlingImpl[T, C <: Configurator[T] with LinksHandling[T, C]]
  extends ConfiguratorImpl[T, C] with LinksHandling[T, C] {

  protected val handlers: Queue[LinksHandler]

  protected def updateHandlers(newHandlers: Queue[LinksHandler]): C

  override final def addLinksHandler(handler: LinksHandler): C = updateHandlers(handlers.enqueue(handler))

  protected def getSpannedFromResult(text: T): Option[Spanned]

  protected def setSpannedToResult(spanned: Spanned, text: T): T

  override protected def toResult(text: T): T = {
    val currResult = super.toResult(text)
    getSpannedFromResult(currResult) match {
      case Some(spanned) =>
        if (handlers.nonEmpty) {
          val spannable = spanned.toSpannable
          for {
            urlSpan <- spanned.getSpans(0, spanned.length(), classOf[URLSpan])
          } {
            val url = urlSpan.getURL
            val wrapper = new ClickableSpanWrapper(urlSpan, { v: View =>
              handlers.find(_.onClick(v, url)).nonEmpty
            })
            val start = spanned.getSpanStart(urlSpan)
            val end = spanned.getSpanEnd(urlSpan)
            val flags = spanned.getSpanFlags(urlSpan)
            spannable.removeSpan(urlSpan)
            spannable.setSpan(wrapper, start, end, flags)
          }
          setSpannedToResult(spannable, currResult)
        } else {
          currResult
        }
      case None => currResult
    }

  }
}
