package org.bluecabin.textoo

import android.text.Html.{ImageGetter, TagHandler}
import android.text.{Html, Spannable, SpannableString, Spanned}

import scala.collection.immutable.Queue

/**
  * Created by fergus on 1/5/16.
  */
private class StringConfiguratorImpl private(factory: ConfiguratorFactory,
                                             val rawText: String)
  extends StringConfigurator with BaseConfiguratorImpl[Spanned, Spannable]
  with SpannableLinkifyImpl[Spanned, Spannable, SpannedConfigurator] {

  override lazy val text: Spannable = new SpannableString(rawText)
  override protected val actions: Queue[Spannable => Spannable] = Queue.empty

  override def parseHtml(): SpannedConfigurator =
    createConfigurator(Spannable.Factory.getInstance().newSpannable(Html.fromHtml(rawText)), actions)

  override def parseHtml(imageGetter: ImageGetter, tagHandler: TagHandler): SpannedConfigurator =
    createConfigurator(Spannable.Factory.getInstance().newSpannable(Html.fromHtml(rawText, imageGetter, tagHandler)),
      actions)

  override protected def toResult(state: Spannable): Spanned = state

  override protected def createConfigurator(text: Spannable, actions: Queue[(Spannable) => Spannable]): SpannedConfigurator =
    new SpannedConfiguratorImpl(factory, text, actions)
}

private object StringConfiguratorImpl {
  /**
    *
    * @param factory For access protection.  Only allow those callers who have access to ConfiguratorFactory.
    * @return
    */
  def create(factory: ConfiguratorFactory, text: String): StringConfigurator =
    new StringConfiguratorImpl(factory, text)
}