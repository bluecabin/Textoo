# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/fergus/Development/android-sdk-linux/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

###
# Scala proguard config
###
-dontnote scala.ScalaObject
-dontnote org.xml.sax.EntityResolver
-dontnote scala.concurrent.forkjoin.**
-dontwarn scala.beans.ScalaBeanInfo
-dontwarn scala.concurrent.**
-dontnote scala.reflect.**
-dontwarn scala.reflect.**
-dontwarn scala.sys.process.package$

-dontwarn **$$anonfun$*
-dontwarn scala.collection.immutable.RedBlack$Empty
-dontwarn scala.tools.**,plugintemplate.**

-keep public class scala.reflect.ScalaSignature
# This is gone in 2.11
-keep public interface scala.ScalaObject

-keepclassmembers class * {
    ** MODULE$;
}

-keep class scala.collection.SeqLike {
    public java.lang.String toString();
}

-keepclassmembernames class scala.concurrent.forkjoin.ForkJoinPool {
    long eventCount;
    int  workerCounts;
    int  runControl;
    scala.concurrent.forkjoin.ForkJoinPool$WaitQueueNode syncStack;
    scala.concurrent.forkjoin.ForkJoinPool$WaitQueueNode spareStack;
}

-keepclassmembernames class scala.concurrent.forkjoin.ForkJoinWorkerThread {
    int base;
    int sp;
    int runState;
}

-keepclassmembernames class scala.concurrent.forkjoin.ForkJoinTask {
    int status;
}

-keepclassmembernames class scala.concurrent.forkjoin.LinkedTransferQueue {
    scala.concurrent.forkjoin.LinkedTransferQueue$PaddedAtomicReference head;
    scala.concurrent.forkjoin.LinkedTransferQueue$PaddedAtomicReference tail;
    scala.concurrent.forkjoin.LinkedTransferQueue$PaddedAtomicReference cleanMe;
}

# temporary workaround; see Scala issue SI-5397
-keep class scala.collection.SeqLike {
    public protected *;
}

###
# Project config
###
-keep class org.bluecabin.textoo.** { *; }
