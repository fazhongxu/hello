/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_xxl_core_media_audio_utils_LameUtils */

#ifndef _Included_com_xxl_core_media_audio_utils_LameUtils
#define _Included_com_xxl_core_media_audio_utils_LameUtils
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_xxl_core_media_audio_utils_LameUtils
 * Method:    getVersion
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_xxl_core_media_audio_utils_LameUtils_getVersion
        (JNIEnv *, jobject);

/*
 * Class:     com_xxl_core_media_audio_utils_LameUtils
 * Method:    init
 * Signature: (IIIII)I
 */
JNIEXPORT jint JNICALL Java_com_xxl_core_media_audio_utils_LameUtils_init
        (JNIEnv *, jobject, jint, jint, jint, jint, jint);

/*
 * Class:     com_xxl_core_media_audio_utils_LameUtils
 * Method:    encode
 * Signature: ([S[SI[B)I
 */
JNIEXPORT jint JNICALL Java_com_xxl_core_media_audio_utils_LameUtils_encode
        (JNIEnv *, jobject, jshortArray, jshortArray, jint, jbyteArray);

/*
 * Class:     com_xxl_core_media_audio_utils_LameUtils
 * Method:    flush
 * Signature: ([B)I
 */
JNIEXPORT jint JNICALL Java_com_xxl_core_media_audio_utils_LameUtils_flush
        (JNIEnv *, jobject, jbyteArray);

/*
 * Class:     com_xxl_core_media_audio_utils_LameUtils
 * Method:    close
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_xxl_core_media_audio_utils_LameUtils_close
        (JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif
#endif