package com.lijiadayuan.lishijituan.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

/**
 * Created by zhaoyi on 16/4/27.
 */
public class JsonParseUtil {
    //将String转化位json
    public static JsonElement getJsonByString(String result){
        JsonParser mJsonParser = new JsonParser();
        return mJsonParser.parse(result);
    }

    //根据json判断是否请求成功
    public static Boolean isSuccess(JsonObject mJsonObject){
        String status = mJsonObject.get("response_status").getAsString();
        if (status != null || !status.equals("")){
            if (status.equals("success")){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    //将json转换成对象
    public static <T> T toBeanByJson(JsonObject mJsonObject,Class<T> mClass){
        Gson mGson = new Gson();
        if (!mJsonObject.isJsonNull()){
            T mT = (T) mGson.fromJson(mJsonObject,mClass);
            return mT;

        }
        return null;
    }



    //将jsonarray转换成list集合
    public static <T> ArrayList<T> toListByJson(JsonArray mJsonArray,Class<T> mClass){
        Gson mGson = new Gson();
        ArrayList<T> mList = new ArrayList<>();
        if (!mJsonArray.isJsonNull()){
            for (int i = 0; i < mJsonArray.size(); i++){
                T mT = (T) mGson.fromJson(mJsonArray.get(i),mClass);
                mList.add(mT);
            }
        }
        return mList;
    }

}
