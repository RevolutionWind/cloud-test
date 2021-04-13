package java;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author sunxy
 * @date 2021/3/2 20:28
 */
@SuppressWarnings("all")
public class TestRequest {

    public static void sendReq(MultiValueMap<String, String> requestBody, boolean isLog) {
        //headers
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAccept(new ArrayList<MediaType>() {{
            add(MediaType.APPLICATION_JSON_UTF8);
        }});
        requestHeaders.add("userCode", "4c7e11d25089e1483935064d36d313aa");
        //HttpEntity
        HttpEntity<?> requestEntity = new HttpEntity<>(requestBody, requestHeaders);
        RestTemplate restTemplate = new RestTemplate();
        //post
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:8001/order/pay", requestEntity, String.class);
        if (isLog) {
            System.out.println(responseEntity.getBody());
        }
    }

    public static void testSentinel1() throws InterruptedException {
        /**
         * 1.限流测试
         */
        // 1.1发送60个请求触发限流
        for (int i = 0; i < 60; i++) {
            new Thread(() -> {
                //body
                MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
                    requestBody.add("goodsId", "18001006");
                requestBody.add("cardCount", "1");
                requestBody.add("userCode", "YYYYY");
                requestBody.add("agentId", "1");
                sendReq(requestBody, true);
            }).start();
        }
        // 1.2预期结果，35个被拦截，25个通过
    }

    /**
     * 测试sentinel
     */
    public static void testSentinel2() throws InterruptedException {
        /**
         * 2.降级测试
         */
        // 2.1发送20个慢请求触发5s降级
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                //body
                MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
                requestBody.add("goodsId", "18001006");
                requestBody.add("cardCount", "1");
                requestBody.add("userCode", "YYYYY");
                requestBody.add("agentId", "1");
                requestBody.add("isSleep", "true");
                sendReq(requestBody, false);
            }).start();
        }
        // 2.2发送10个均速请求，每秒1个，
        // 2.3预期结果，后10个请求，5个被拦截，5个通过
        for (int i = 0; i < 10; i++) {
            //body
            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("goodsId", "18001006");
            requestBody.add("cardCount", "1");
            requestBody.add("userCode", "YYYYY");
            requestBody.add("agentId", "1");
            sendReq(requestBody, true);
            TimeUnit.SECONDS.sleep(1L);
        }
    }

    /**
     * 测试事务回滚与熔断
     */
    public static void testRollback() {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("goodsId", "18001006");
        requestBody.add("cardCount", "1");
        requestBody.add("userCode", "YYYYY");
        requestBody.add("agentId", "1");
        requestBody.add("isErr", "true");
        sendReq(requestBody, true);
    }

    public static void testNormal() {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("goodsId", "18001006");
        requestBody.add("cardCount", "1");
        requestBody.add("userCode", "YYYYY");
        requestBody.add("agentId", "1");
        sendReq(requestBody, true);
    }

    public static void main(String[] args) throws InterruptedException {
        testSentinel2();
    }


}
