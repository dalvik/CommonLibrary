LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_STATIC_JAVA_LIBRARIES := gson \
							   ksoap2
								
LOCAL_MODULE := libnetservice

LOCAL_MODULE_TAGS := optional

LOCAL_SRC_FILES := $(call all-java-files-under, src/com/android/library/net)

include $(BUILD_STATIC_JAVA_LIBRARY)

include $(CLEAR_VARS)

LOCAL_STATIC_JAVA_LIBRARIES := gson \
							   ksoap2
								
LOCAL_MODULE := libactivity

LOCAL_MODULE_TAGS := optional

LOCAL_SRC_FILES := $(call all-java-files-under, src/com/android/library/activity)

include $(BUILD_STATIC_JAVA_LIBRARY)

########################################

include $(CLEAR_VARS)

LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES := ksoap2:libs/ksoap2-android-assembly-3.0.0.jar

LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES := gson:libs/gson-2.2.4.jar

include $(BUILD_MULTI_PREBUILT)

