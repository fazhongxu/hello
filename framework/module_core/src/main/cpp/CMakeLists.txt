# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)
SET(lame_dir ${CMAKE_SOURCE_DIR}/lame-3.100_libmp3lame)
# 将给定目录添加到编译器用于搜索包含文件的目录中。相对路径被解释为相对于当前源目录
# 是用来提供编译搜索头文件路径的
include_directories(${lame_dir})
# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.
#设定一个目录
aux_source_directory(${lame_dir} SRC_LIST)

add_library( # Sets the name of the library.
        mp3lame
        # Sets the library as a shared library.
        SHARED
        # Provides a relative path to your source file(s).
        ${SRC_LIST}
        mp3lame-lib.cpp)

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
        log-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
        mp3lame

        # Links the target library to the log library
        # included in the NDK.
        ${log-lib})
#忽略警告
add_definitions(-w)