package cn.edu.pku.chengdu80.financialsearchengine.utils;

import cn.edu.pku.chengdu80.financialsearchengine.entity.QueryParam;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class HttpClient {
    public static String sendPostRequest(String url, String queryString, boolean isName){
        System.out.println(queryString);
        ObjectMapper mapper = new ObjectMapper();
        QueryParam param = new QueryParam();
        param.setContent(queryString);
        param.setName(isName);

        String msg = "";
        try {
            msg = mapper.writeValueAsString(param);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        RestTemplate client = new RestTemplate();
        //新建Http头，add方法可以添加参数
        HttpHeaders headers = new HttpHeaders();
        //设置请求发送方式
        HttpMethod method = HttpMethod.POST;
        // 以表单的方式提交
        headers.setContentType(MediaType.APPLICATION_JSON);
        //将请求头部和参数合成一个请求
        HttpEntity<String> requestEntity = new HttpEntity<>(msg, headers);
        //执行HTTP请求，将返回的结构使用String 类格式化（可设置为对应返回值格式的类）
        ResponseEntity<String> response = client.exchange(url, method, requestEntity, String.class);

        return response.getBody();
    }
}