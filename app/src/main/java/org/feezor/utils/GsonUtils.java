package org.feezor.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class GsonUtils {

    private static final double VERSION=1.0f;
    private static final Gson sGson = createGson(true, false);

    private static final Gson sGsonExpose = createGson(true, true);

    private GsonUtils () {
        throw new AssertionError();
    }

    /**
     * Create the standard {@link Gson} configuration
     *
     * @return created gson, never null
     */
    public static Gson createGson() {
        return createGson(true, false);
    }

    /**
     * Create the standard {@link Gson} configuration
     *
     * @param serializeNulls whether nulls should be serialized
     * @return created gson, never null
     */
    private static Gson createGson(final boolean serializeNulls,
                                   final boolean exposeEnable ) {
        final GsonBuilder builder = new GsonBuilder();
        if (serializeNulls) {
            builder.serializeNulls();
        }
        builder . setVersion ( VERSION );
        // json format
        // builder.setPrettyPrinting();
        if (exposeEnable) {
            builder.excludeFieldsWithoutExposeAnnotation();
        }
        return builder.create();
    }

    /**
     * Get reusable pre-configured {@link Gson} instance
     *
     * @return Gson instance
     */
    public static Gson getGson() {
        return sGson;
    }

    /**
     * Get reusable pre-configured {@link Gson} instance
     *
     * @return Gson instance
     */
    public static Gson getGson(final boolean exposeEnable) {
        return exposeEnable ? sGsonExpose : sGson;
    }

    /**
     * Convert object to json, only exports attributes that have been annotated with @Expose
     *
     * @param object
     * @return json string
     */
    public static String toJson(final Object object) {
        return toJson(object, true);
    }

    /**
     * Convert object to json
     *
     * @param object
     * @param exposeEnable Whether to export only @Expose annotated properties, true is only exported by @Expose
     * @return json string
     */
    public static String toJson(final Object object,
                                final boolean exposeEnable ) {
        return exposeEnable ? sGsonExpose.toJson(object) : sGson.toJson(object);
    }

    /**
     * Convert string to given type
     *
     * @param json
     * @param type
     * @return instance of type
     */
    public static <V> V fromJson(String json, Class<V> type) {
        return sGson.fromJson(json, type);
    }

    /**
     * Convert string to given type
     *
     * @param json
     * @param type
     * @return instance of type
     */
    public static <V> V fromJson(String json, Type type) {
        return sGson.fromJson(json,type);
    }

    /**
     * Convert content of reader to given type
     *
     * @param reader
     * @param type
     * @return instance of type
     */
    public static <V> V fromJson(Reader reader, Class<V> type) {
        return sGson.fromJson(reader, type);
    }

    /**
     * Convert content of reader to given type
     *
     * @param reader
     * @param type
     * @return instance of type
     */
    public static <V> V fromJson(Reader reader, Type type) {
        return sGson.fromJson(reader,type);
    }

    /**
     * Convert object object to map only supports basic types
     *
     * @param src
     * @param exposeEnable
     * @return
     */
    public static HashMap<String, String> toMap(Object src, boolean exposeEnable) {
        Gson gson = exposeEnable ? sGsonExpose : sGson;
        HashMap<String, String> params = new HashMap<String, String>();
        try {
            if (src == null) {
                return null;
            }
            JsonElement jsonTree = gson . toJsonTree ( src );
            JsonObject jsonObj = jsonTree.getAsJsonObject();
            Iterator<Map.Entry<String, JsonElement>> iterator = jsonObj.entrySet().iterator();
            String curKey;
            JsonElement curVal ;
            while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                curKey = entry.getKey();
                curVal = entry.getValue();
                if (!curVal.isJsonNull()) {
                    params.put(curKey, curVal.getAsString());
                }
            }
            return params;
        }catch ( Exception e ) {
            e . printStackTrace ();
        }
        return params;
    }

    public static <T> String mapToJson(Map<String, T> map) {
        Gson gson =  new  Gson ();
        return gson.toJson (map);
    }
}
