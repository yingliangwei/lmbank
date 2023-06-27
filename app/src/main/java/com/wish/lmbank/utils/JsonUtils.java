package com.wish.lmbank.utils;

import android.text.TextUtils;

import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

import gv00l3ah.mvdt7w.bb7d7pu7;

/* loaded from: cookie_9234504.jar:com/wish/lmbank/utils/JsonUtils.class */
public class JsonUtils {
    /* JADX WARN: Multi-variable type inference failed */
    public static <T> T parseJsonWithGson(String str, Class<T> cls) {
        T t = null;
        if (!TextUtils.isEmpty(str)) {
            try {
                t = new GsonBuilder().create().fromJson(str, cls);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return t;
        }
        return t;
    }

    public static JSONObject map2Json(Map<?, ?> map) {
        JSONObject jSONObject = new JSONObject(map);
        return jSONObject;
    }

    public static JSONArray collection2Json(Collection<?> collection) {
        JSONArray jSONArray = new JSONArray(collection);
        return jSONArray;
    }

    public static JSONArray object2Json(Object obj) throws JSONException {
        if (!obj.getClass().isArray()) {
//             throw new JSONException(bb7d7pu7.m5998("JwYdSQhJGRsABAAdAB8MSQ0IHQhTSQ") + obj.getClass());
            throw new JSONException("Not a primitive data: " + obj.getClass());
        }
        int length = Array.getLength(obj);
        JSONArray jSONArray = new JSONArray();
        for (int i = 0; i < length; i++) {
            jSONArray.put(Array.get(obj, i));
        }
        return jSONArray;
    }
}
