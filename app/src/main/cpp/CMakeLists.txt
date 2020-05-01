cmake_minimum_required(VERSION 3.4.1)

add_library(
        native-lib
        SHARED
        native-lib.cpp)


find_library(
        log-lib
        log)

include_directories(include)

add_library(avcodec SHARED IMPORTED)
set_target_properties(avcodec PROPERTIES IMPORTED_LOCATION /Users/admin/Desktop/FFMPEGDemo/app/src/main/jniLibs/armeabi-v7a/libavcodec.so)

add_library(avfilter SHARED IMPORTED)
set_target_properties(avfilter PROPERTIES IMPORTED_LOCATION /Users/admin/Desktop/FFMPEGDemo/app/src/main/jniLibs/armeabi-v7a/libavfilter.so)

add_library(avformat SHARED IMPORTED)
set_target_properties(avformat PROPERTIES IMPORTED_LOCATION /Users/admin/Desktop/FFMPEGDemo/app/src/main/jniLibs/armeabi-v7a/libavformat.so)

add_library(avutil SHARED IMPORTED)
set_target_properties(avutil PROPERTIES IMPORTED_LOCATION /Users/admin/Desktop/FFMPEGDemo/app/src/main/jniLibs/armeabi-v7a/libavutil.so)

add_library(swresample SHARED IMPORTED)
set_target_properties(swresample PROPERTIES IMPORTED_LOCATION /Users/admin/Desktop/FFMPEGDemo/app/src/main/jniLibs/armeabi-v7a/libswresample.so)

add_library(swscale SHARED IMPORTED)
set_target_properties(swscale PROPERTIES IMPORTED_LOCATION /Users/admin/Desktop/FFMPEGDemo/app/src/main/jniLibs/armeabi-v7a/libswscale.so)

target_link_libraries(
        native-lib
        swscale
        swresample
        avutil
        avformat
        avfilter
        avcodec
        ${log-lib}
)