package com.xxl.hello.service.data.local.db.convert;

import android.text.TextUtils;

import com.scottyab.aescrypt.AESCrypt;
import com.xxl.core.utils.AppExpandUtils;

import java.security.GeneralSecurityException;

import io.objectbox.converter.PropertyConverter;

/**
 * @author xxl.
 * @date 2026/4/8.
 */
public class EncryptConverter implements PropertyConverter<String, String> {

    @Override
    public String convertToEntityProperty(String databaseValue) {
        try {
            if (TextUtils.isEmpty(databaseValue)) {
                return "";
            }
            return AESCrypt.decrypt(AppExpandUtils.getLocalEncryptKey(), databaseValue);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public String convertToDatabaseValue(String entityProperty) {
        try {
            if (TextUtils.isEmpty(entityProperty )) {
                return "";
            }
            return AESCrypt.encrypt(AppExpandUtils.getLocalEncryptKey(), entityProperty);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return "";
    }
}