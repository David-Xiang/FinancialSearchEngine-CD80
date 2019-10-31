package cn.edu.pku.chengdu80.financialsearchengine.entity;

import lombok.Data;

import java.util.List;

@Data
public class DataResponse {
    public int page;
    public String keywords;
    public List<Researcher> researchers;
}
