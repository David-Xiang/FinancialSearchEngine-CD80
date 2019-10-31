package cn.edu.pku.chengdu80.financialsearchengine.service;

import cn.edu.pku.chengdu80.financialsearchengine.dao.ResearcherDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Service
public class MockService {
    @Autowired
    ResearcherDao researcherDao;

    public String getQueryResultFromLocalFile(String queryString) {
        Resource resource = new ClassPathResource("api.json");
        File sourceFile = null;
        try{
            sourceFile =  resource.getFile();
            BufferedReader in = new BufferedReader(new FileReader(sourceFile));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = in.readLine()) != null){
                buffer.append(line);
            }
            return buffer.toString();
        } catch (IOException e) {
            return "{}";
        }
    }
//    public DataResponse getQueryRandomQueryResult(String queryString) {
//        DataResponse dataResponse = new DataResponse();
//        dataResponse.setPage(new Random().nextInt() % 2 + 1);
//        dataResponse.setKeywords(queryString);
//
//        List<Long> idList = asList(1937660000L, 225510000L, 2768462100L, 2105834400L, 1937660000L, 2271292300L, 1905582700L, 1994844700L);
//        dataResponse.setResearchers(idList.stream().map(aLong -> researcherDao.getResearcherById(aLong)).collect(Collectors.toList()));
//        return dataResponse;
//    }
}
