package cn.edu.pku.chengdu80.financialsearchengine.controller;

import cn.edu.pku.chengdu80.financialsearchengine.entity.*;
import cn.edu.pku.chengdu80.financialsearchengine.service.HistoryService;
import cn.edu.pku.chengdu80.financialsearchengine.service.MockService;
import cn.edu.pku.chengdu80.financialsearchengine.service.NameService;
import cn.edu.pku.chengdu80.financialsearchengine.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class QueryController {
    @Autowired
    MockService mockService;
    @Autowired
    QueryService queryService;
    @Autowired
    HistoryService historyService;
    @Autowired
    NameService nameService;

    @PostMapping("/query")
    @ResponseBody
    public DataResponse postQuery(@RequestBody StringParam param) {
        if (nameService.checkName(param.content)) {
            return queryService.getHttpResponse(param.content, true);
        }
        historyService.searchFor(param.content);
        return queryService.getHttpResponse(param.content, false);
    }

    @GetMapping("/query")
    @ResponseBody
    public String postQuery() {
        return mockService.getQueryResultFromLocalFile("");
    }

    @PostMapping("/paper")
    @ResponseBody
    public Paper paperQuery(@RequestBody StringParam param) {
        // query paper by id
        return queryService.getPaperById(Long.parseLong(param.content));
    }

    @PostMapping("/mock")
    @ResponseBody
    public DataResponse mockPostQuery(@RequestBody StringParam param) {
        return mockService.getQueryRandomQueryResult(param.content);
    }

    @GetMapping("/mock")
    @ResponseBody
    public DataResponse mockGetQuery() {
        return mockService.getQueryRandomQueryResult("");
    }

    @PostMapping("/researcher")
    @ResponseBody
    public Researcher researcherQuery(@RequestBody StringParam param) {
        // query paper by id
        return queryService.getResearcherById(Long.parseLong(param.content));
    }

    @GetMapping("/history")
    @ResponseBody
    public List<String> historyQuery(){
        return historyService.getHitWords();
    }

    @PostMapping("/relation")
    @ResponseBody
    public List<Relation> relationQuery(@RequestBody LongParam param) {
        return queryService.getTwoOrderRelationById(param.content);
    }
}
