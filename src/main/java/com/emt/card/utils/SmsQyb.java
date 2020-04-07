package com.emt.card.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class SmsQyb {

    private final String username = "yiyitong";
    private final String password = "123456";

    private final String url = "http://www.qybor.com:8500/shortMessage";

    private static SmsQyb instance = new SmsQyb();

    public static SmsQyb getInstance() {
        return instance;
    }


    public String getSerialNumber() {
        Random random = new Random();
        int randomN = random.nextInt(999)%(999-100+1) + 100;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmssSSS");
        String serialNumber = formatter.format(new Date())+randomN;
        return serialNumber;
    }

    public Boolean send(String phone, String message) {

        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("passwd", password));
        params.add(new BasicNameValuePair("phone", phone));
        params.add(new BasicNameValuePair("msg", message));
        params.add(new BasicNameValuePair("needstatus", "true"));
        params.add(new BasicNameValuePair("port", ""));
        params.add(new BasicNameValuePair("sendtime", ""));
        String paramsStr = URLEncodedUtils.format(params, StandardCharsets.UTF_8);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8");
        headers.setContentType(mediaType);
        HttpEntity<String> entity = new HttpEntity<>(paramsStr, headers);
        Map<?, ?> resp = restTemplate.postForObject(url, entity, Map.class);
        log.info("发送信息: {}", resp);
        if (resp.get("respcode").equals("0")) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        SmsQyb.getInstance().send("15817004195", "验证码PARAM1，您正在进行易医通身份验证，打死不要告诉别人哦！");

    }

}
