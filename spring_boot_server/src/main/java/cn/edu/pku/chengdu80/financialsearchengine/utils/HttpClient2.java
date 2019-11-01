//package cn.edu.pku.chengdu80.financialsearchengine.utils;
//
//import cn.edu.pku.chengdu80.financialsearchengine.entity.QueryParam;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//
//public class HttpClient2 {
//    public static String client(String url, HttpMethod method, MultiValueMap<String, String> params) {
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
//        return response.getBody();
//    }
//    public static String postClient(String url, String queryString, boolean isName) {
//        ObjectMapper mapper = new ObjectMapper();
//        QueryParam param = new QueryParam();
//        param.setContent(queryString);
//        param.setName(isName);
//
//        String msg = "";
//        try {
//            msg = mapper.writeValueAsString(param);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//        return client(url, HttpMethod.POST, )
//    }
//}
