package org.bluecabin.textoo.util

import android.text.style.ClickableSpan
import android.text.{SpannableString, Spanned, Spannable}

/**
  * Created by fergus on 1/10/16.
  */
object CharSequenceSupport {

  def toSpanned(chars: CharSequence): Spanned = {
    chars match {
      case s: Spanned => s
      case _ => charsToSpannable(chars)
    }
  }

  def toSpannable(chars: CharSequence): Spannable = chars match {
    case s: Spannable => s
    case _ => charsToSpannable(chars)
  }

  private def charsToSpannable(chars: CharSequence): Spannable = {
    Spannable.Factory.getInstance().newSpannable(chars)
  }

  final implicit class ImplicitCharSequence(val chars: CharSequence) extends AnyVal {
    def toSpannable = CharSequenceSupport.toSpannable(chars)

    def toSpanned = CharSequenceSupport.toSpanned(chars)

    def allLinks: Seq[SpanInfo] = {
      val spanned = toSpanned
      spanned.getSpans(0, spanned.length, classOf[ClickableSpan]).map { span =>
        SpanInfo(
          span = span,
          start = spanned.getSpanStart(span),
          end = spanned.getSpanEnd(span),
          flags = spanned.getSpanFlags(span)
        )
      }
    }
  }

  final case class SpanInfo(
                             span: AnyRef,
                             start: Int,
                             end: Int,
                             flags: Int
                           ) {
    def overlapsWith(other: SpanInfo) = this.start < other.end && other.start < this.end

    def addTo(spannable: Spannable) = spannable.setSpan(span, start, end, flags)
  }

}
