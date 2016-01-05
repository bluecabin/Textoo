package org.bluecabin.textoo

import android.text.Html.{TagHandler, ImageGetter}
import android.text.Spanned

/**
  * Created by fergus on 1/5/16.
  */
class StringConfiguratorImpl private() extends StringConfigurator {
  override def parseHtml(): SpannedConfigurator = ???

  override def parseHtml(source: String, imageGetter: ImageGetter, tagHandler: TagHandler): SpannedConfigurator = ???

  override def apply(): Spanned = ???

  override def linkifyPhoneNumbers(): SpannedConfigurator = ???

  override def linkifyEmailAddresses(): SpannedConfigurator = ???

  override def linkifyAll(): SpannedConfigurator = ???

  override def linkifyMapAddresses(): SpannedConfigurator = ???

  override def linkifyWebUrls(): SpannedConfigurator = ???
}
object StringConfiguratorImpl {
  /**
    *
    * @param factory For access protection.  Only allow those callers who have access to ConfiguratorFactory.
    * @return
    */
  def create(factory: ConfiguratorFactory): StringConfigurator = new StringConfiguratorImpl
}