LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := samples

# Only compile source java files in this apk.
LOCAL_SRC_FILES := $(call all-java-files-under, src)

LOCAL_PACKAGE_NAME := FtpKs

#LOCAL_SDK_VERSION := current
LOCAL_PRIVATE_PLATFORM_APIS := true

LOCAL_MIN_SDK_VERSION := 14

LOCAL_DEX_PREOPT := false

LOCAL_STATIC_JAVA_LIBRARIES := ma_lib

include $(BUILD_PACKAGE)

include $(CLEAR_VARS)
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES :=  ma_lib:libs/commons-net-3.9.0.jar 
include $(BUILD_MULTI_PREBUILT)

# Use the following include to make our test apk.
include $(call all-makefiles-under,$(LOCAL_PATH))
