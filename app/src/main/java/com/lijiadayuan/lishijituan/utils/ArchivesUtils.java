package com.lijiadayuan.lishijituan.utils;

import com.lijiadayuan.lishijituan.bean.NationBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaoyi on 16/5/2.
 */
public class ArchivesUtils {
    /**
     * 获取性别列表
     * @return
     */
    public static ArrayList<String> getAge(){
        ArrayList<String> mAgeList = new ArrayList<>();
        mAgeList.add("女");
        mAgeList.add("男");
        return  mAgeList;
    }


//    /**
//     * 获取民族
//     */
//    /**
//     * 获取性别列表
//     * @return
//     */
//    public static ArrayList<NationBean> getNation(){
//        ArrayList<NationBean> stringList = new ArrayList<>();
//        stringList.add(new NationBean("汉族","1"));
//        stringList.add(new NationBean("蒙古族","2"));
//        stringList.add(new NationBean("回族","3"));
//        stringList.add(new NationBean("藏族","4"));
//        stringList.add(new NationBean("维吾尔族","5"));
//        stringList.add(new NationBean("苗族","6"));
//        stringList.add(new NationBean("彝族","7"));
//        stringList.add(new NationBean("壮族","8"));
//        stringList.add(new NationBean("布依族","9"));
//        stringList.add(new NationBean("朝鲜族","10"));
//        stringList.add(new NationBean("满族","11"));
//        stringList.add(new NationBean("侗族","12"));
//        stringList.add(new NationBean("瑶族","13"));
//        stringList.add(new NationBean("白族","14"));
//        stringList.add(new NationBean("土家族","15"));
//
//
//        stringList.add(new NationBean("哈尼族","16"));
//        stringList.add(new NationBean("哈萨克族","17"));
//        stringList.add(new NationBean("傣族","18"));
//        stringList.add(new NationBean("黎族","19"));
//        stringList.add(new NationBean("傈僳族","20"));
//        stringList.add(new NationBean("佤族","21"));
//        stringList.add(new NationBean("畲族","22"));
//        stringList.add(new NationBean("高山族","23"));
//        stringList.add(new NationBean("拉祜族","24"));
//        stringList.add(new NationBean("水族","25"));
//        stringList.add(new NationBean("东乡族","26"));
//        stringList.add(new NationBean("纳西族","27"));
//        stringList.add(new NationBean("景颇族","28"));
//        stringList.add(new NationBean("柯尔克孜族","29"));
//        stringList.add(new NationBean("土族","30"));
//
//        stringList.add(new NationBean("达斡尔族","31"));
//        stringList.add(new NationBean("仫佬族","32"));
//        stringList.add(new NationBean("羌族","33"));
//        stringList.add(new NationBean("布朗族","34"));
//        stringList.add(new NationBean("撒拉族","35"));
//        stringList.add(new NationBean("毛难族","36"));
//        stringList.add(new NationBean("仡佬族","37"));
//        stringList.add(new NationBean("锡伯族","38"));
//        stringList.add(new NationBean("阿昌族","39"));
//        stringList.add(new NationBean("普米族","40"));
//        stringList.add(new NationBean("塔吉克族","41"));
//        stringList.add(new NationBean("怒族","42"));
//        stringList.add(new NationBean("乌孜别克族","43"));
//        stringList.add(new NationBean("俄罗斯族","44"));
//        stringList.add(new NationBean("鄂温克族","45"));
//
//        stringList.add(new NationBean("崩龙族","46"));
//        stringList.add(new NationBean("保安族","47"));
//        stringList.add(new NationBean("裕固族","48"));
//        stringList.add(new NationBean("京族","49"));
//        stringList.add(new NationBean("塔塔尔族","50"));
//        stringList.add(new NationBean("独龙族","51"));
//        stringList.add(new NationBean("鄂伦春族","52"));
//        stringList.add(new NationBean("赫哲族","53"));
//        stringList.add(new NationBean(" 门巴族","54"));
//        stringList.add(new NationBean("珞巴族","55"));
//        stringList.add(new NationBean("基诺族","56"));
//        return stringList;
//    }

    /**
     * 获取民族
     */
    /**
     * 获取性别列表
     * @return
     */
    public static ArrayList<String> getNationList(){
        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("汉族");
        stringList.add("蒙古族");
        stringList.add("回族");
        stringList.add("藏族");
        stringList.add("维吾尔族");
        stringList.add("苗族");
        stringList.add("彝族");
        stringList.add("壮族");
        stringList.add("布依族");
        stringList.add("朝鲜族");
        stringList.add("满族");
        stringList.add("侗族");
        stringList.add("瑶族");
        stringList.add("白族");
        stringList.add("土家族");

        stringList.add("哈尼族");
        stringList.add("哈萨克族");
        stringList.add("傣族");
        stringList.add("黎族");
        stringList.add("傈僳族");
        stringList.add("佤族");
        stringList.add("畲族");
        stringList.add("高山族");
        stringList.add("拉祜族");
        stringList.add("水族");
        stringList.add("东乡族");
        stringList.add("纳西族");
        stringList.add("景颇族");
        stringList.add("柯尔克孜族");
        stringList.add("土族");


        stringList.add("达斡尔族");
        stringList.add("仫佬族");
        stringList.add("羌族");
        stringList.add("布朗族");
        stringList.add("撒拉族");
        stringList.add("毛南族");
        stringList.add("仡佬族");
        stringList.add("锡伯族");
        stringList.add("阿昌族");
        stringList.add("普米族");
        stringList.add("塔吉克族");
        stringList.add("怒族");
        stringList.add("乌孜别克族");
        stringList.add("俄罗斯族");
        stringList.add("鄂温克族");

        stringList.add("德昂族");
        stringList.add("保安族");
        stringList.add("裕固族");
        stringList.add("京族");
        stringList.add("塔塔尔族");
        stringList.add("独龙族");
        stringList.add("鄂伦春族");
        stringList.add("赫哲族");
        stringList.add("门巴族");
        stringList.add("珞巴族");
        stringList.add("基诺族");
        return stringList;
    }
}
