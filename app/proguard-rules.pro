# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
-renamesourcefileattribute SourceFile

# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
-renamesourcefileattribute SourceFile


# Repackage classes into the top-level.
-repackageclasses
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-keepattributes Signature

-dontwarn okio.**
-dontwarn retrofit2.Call
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
-keep class android.support.v7.widget.RecyclerView { *; }

-keep class com.jdagnogo.welovemarathon.home.domain.Blog
-keep class com.jdagnogo.welovemarathon.home.domain.Blog
-keep class com.jdagnogo.welovemarathon.blog.data.BlogEntity
-keep class com.jdagnogo.welovemarathon.blog.data.BlogMapper

#Firebase
-keepattributes EnclosingMethod
-keepattributes InnerClasses
-dontnote org.xmlpull.v1.**
-keepclassmembers class org.xmlpull.** { *; }
-keep class com.firebase.** { *; }
