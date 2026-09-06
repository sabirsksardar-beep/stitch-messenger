#!/bin/bash

# Build Stitch Messenger APK
# This script builds both Debug and Release APK

echo "🚀 Starting Stitch Messenger APK Build..."
echo "========================================"

# Navigate to android directory
cd android || exit

# Clean build
echo "🧹 Cleaning build..."
./gradlew clean

# Build Debug APK
echo "🔨 Building Debug APK..."
./gradlew assembleDebug

if [ $? -eq 0 ]; then
    echo "✅ Debug APK built successfully!"
    echo "📁 Location: app/build/outputs/apk/debug/app-debug.apk"
else
    echo "❌ Debug build failed!"
    exit 1
fi

# Build Release APK (if keystore exists)
if [ -f "release.keystore" ]; then
    echo "🔨 Building Release APK..."
    ./gradlew assembleRelease
    
    if [ $? -eq 0 ]; then
        echo "✅ Release APK built successfully!"
        echo "📁 Location: app/build/outputs/apk/release/app-release.apk"
    else
        echo "❌ Release build failed!"
    fi
else
    echo "⚠️  Keystore not found for release build"
    echo "   To create release APK, run: ./gradlew assembleRelease"
fi

echo ""
echo "========================================"
echo "✅ Build process completed!"
echo "========================================"
echo ""
echo "Next steps:"
echo "1. Install on device: adb install app/build/outputs/apk/debug/app-debug.apk"
echo "2. Or open APK file with file manager"
echo "3. Grant permissions when prompted"
echo "4. Enter phone number and start chatting!"
echo ""
