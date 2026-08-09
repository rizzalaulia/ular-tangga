# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /sdk/tools/proguard/proguard-android-optimize.txt

# Uncomment to keep line numbers for debugging crash reports
# -keepattributes SourceFile,LineNumberTable

# Keep Compose models (optional, tetap keep class yang dipake reflection)
-keep class com.ulartangga.game.domain.model.** { *; }
