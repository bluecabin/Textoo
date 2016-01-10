package org.bluecabin.textoo.util

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

  }

}
