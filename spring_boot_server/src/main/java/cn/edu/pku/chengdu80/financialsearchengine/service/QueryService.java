package cn.edu.pku.chengdu80.financialsearchengine.service;

import cn.edu.pku.chengdu80.financialsearchengine.dao.PaperDao;
import cn.edu.pku.chengdu80.financialsearchengine.dao.RelationDao;
import cn.edu.pku.chengdu80.financialsearchengine.dao.ResearcherDao;
import cn.edu.pku.chengdu80.financialsearchengine.entity.DataResponse;
import cn.edu.pku.chengdu80.financialsearchengine.entity.Paper;
import cn.edu.pku.chengdu80.financialsearchengine.entity.Relation;
import cn.edu.pku.chengdu80.financialsearchengine.entity.Researcher;
import cn.edu.pku.chengdu80.financialsearchengine.utils.HttpClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QueryService {
    @Autowired
    PaperDao paperDao;

    @Autowired
    ResearcherDao researcherDao;

    @Autowired
    RelationDao relationDao;

    public Paper getPaperById(long id) {
        return paperDao.getPaperById(id);
    }

    public Researcher getResearcherById(long id) {
        return researcherDao.getResearcherById(id);
    }

    public DataResponse getHttpResponse(String queryString, boolean isName) {
        String hello = HttpClient.sendGetRequest("http://127.0.0.1:5000/", new LinkedMultiValueMap<>(), new HttpHeaders());
        System.out.println(hello);
        String jsonStr =  HttpClient.sendPostRequest("http://127.0.0.1:5000/query", queryString, isName);
        List<Long> idList = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            idList = mapper.readValue(jsonStr, new TypeReference<List<Long>>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
        DataResponse dataResponse = new DataResponse();
        dataResponse.setKeywords(queryString);
        dataResponse.setPage(isName ? 2 : 1);
        dataResponse.setResearchers(idList.stream().map(aLong -> researcherDao.getResearcherById(aLong)).collect(Collectors.toList()));
        List<Researcher> list = dataResponse.getResearchers();
        if (isName && list.size() > 0 &&
                !queryString.toLowerCase().equals(list.get(0).getName().toLowerCase())) { // 没有精确匹配结果
            list.sort((o1, o2) -> o2.getTotalCitationCount() - o1.getTotalCitationCount());
        }
        dataResponse.setRelations(
                getTwoOrderRelationById(
                        list.size() > 0 ? list.get(0).getId() : null
                )
        );
        if (list.size() > 10) {
            dataResponse.setResearchers(list.subList(0, 10));
        }
        return dataResponse;
    }

    public List<Relation> getTwoOrderRelationById(Long id) {
        return relationDao.getTwoOrderRelationById(id);
    }
}
