package org.bluecabin.textoo

import java.util.regex.Pattern

import android.text.util.Linkify.{MatchFilter, TransformFilter}
import android.widget.TextView

/**
  * Created by fergus on 1/4/16.
  */
private class TextViewConfiguratorImpl private() extends TextViewConfigurator {

  override def apply(): TextView = ???

  override def linkifyPhoneNumbers(): TextViewConfigurator = ???

  override def linkifyEmailAddresses(): TextViewConfigurator = ???

  override def linkifyAll(): TextViewConfigurator = ???

  override def linkifyMapAddresses(): TextViewConfigurator = ???

  override def linkifyWebUrls(): TextViewConfigurator = ???

  override def linkify(pattern: Pattern, scheme: String): TextViewConfigurator = ???

  override def linkify(p: Pattern, scheme: String, matchFilter: MatchFilter, transformFilter: TransformFilter): TextViewConfigurator = ???

  override def handleTextooLinks(): TextViewConfigurator = ???

  override def addLinksHandler(handler: LinksHandler): TextViewConfigurator = ???
}

private object TextViewConfiguratorImpl {
  /**
    *
    * @param factory For access protection.  Only allow those callers who have access to ConfiguratorFactory.
    * @return
    */
  def create(factory: ConfiguratorFactory): TextViewConfigurator = new TextViewConfiguratorImpl
}