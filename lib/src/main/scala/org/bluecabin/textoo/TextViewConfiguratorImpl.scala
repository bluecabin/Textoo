package org.bluecabin.textoo

import android.text.Spannable
import android.widget.TextView

/**
  * Created by fergus on 1/4/16.
  */
private class TextViewConfiguratorImpl private() extends TextViewConfigurator {
  override def handleTextooLinks(): Unit = ???

  override def addLinksHandler(handler: LinksHandler): Unit = ???

  override def apply(): TextView = ???

  override def linkifyPhoneNumbers(): TextViewConfigurator = ???

  override def linkifyEmailAddresses(): TextViewConfigurator = ???

  override def linkifyAll(): TextViewConfigurator = ???

  override def linkifyMapAddresses(): TextViewConfigurator = ???

  override def linkifyWebUrls(): TextViewConfigurator = ???
}

private object TextViewConfiguratorImpl {
  /**
    *
    * @param factory For access protection.  Only allow those callers who have access to ConfiguratorFactory.
    * @return
    */
  def create(factory: ConfiguratorFactory): TextViewConfigurator = new TextViewConfiguratorImpl
}