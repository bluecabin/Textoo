package org.bluecabin.textoo

import java.util.regex.Pattern

import android.text.Spannable
import android.text.util.Linkify
import android.text.util.Linkify.{MatchFilter, TransformFilter}

/**
  * Created by fergus on 1/6/16.
  */
private  trait SpannableLinkifyImpl[R, S <: Spannable, C <: BaseConfigurator[R]] extends TextLinkifyImpl[R, S, C] {
  override final protected def linkifyText(text: S, linkType: Int): S = {
    Linkify.addLinks(text, linkType)
    text
  }

  override final protected def linkifyText(text: S, pattern: Pattern, scheme: String): S = {
    Linkify.addLinks(text, pattern, scheme)
    text
  }

  override final protected def linkifyText(text: S, pattern: Pattern, scheme: String, matchFilter: MatchFilter,
                                           transformFilter: TransformFilter): S = {
    Linkify.addLinks(text, pattern, scheme, matchFilter, transformFilter)
    text
  }

}
