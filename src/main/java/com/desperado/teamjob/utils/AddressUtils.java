package com.desperado.teamjob.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.desperado.teamjob.utils.RequestUtils.decodeUnicode;
import static com.desperado.teamjob.utils.RequestUtils.getResult;

public class AddressUtils {

    /**
     *
     * @param content
     *            请求的参数 格式为：name=xxx&pwd=xxx
     * @param encodingString
     *            服务器端请求编码。如GBK,UTF-8等
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getAddresses(String content, String encodingString)
            throws UnsupportedEncodingException {
        // 这里调用pconline的接口
        String urlStr = "http://ip-api.com/json/"+content+"?lang=zh-CN";
        // 从http://whois.pconline.com.cn取得IP所在的省市区信息
        String returnStr = null;
        try {
            returnStr = HttpUtils.doGet(urlStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (returnStr != null) {
            // 处理返回的省市区信息
            JSONObject jsonObject = JSONObject.parseObject(returnStr);
            if(jsonObject.getString("status").equals("success")){
                String country = jsonObject.getString("country");
                String province = jsonObject.getString("regionName");
                String city = jsonObject.getString("city");
                return country+"-"+province+"-"+city;
            }
        }
        return null;
    }

    // 测试
    public static void main(String[] args) {
        AddressUtils addressUtils = new AddressUtils();
        // 测试ip 219.136.134.157 中国=华南=广东省=广州市=越秀区=电信
        String ip = "125.70.11.136";
        String address = "";
        try {
            address = addressUtils.getAddresses(ip, "utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(address);
        // 输出结果为：广东省,广州市,越秀区
    }

}
