package cn.edu.pku.chengdu80.financialsearchengine.entity;

import lombok.Data;

import java.util.List;

@Data
public class Paper {
    public long id;
    public String name;
    public String venue;
    public int date;
    public String url;
    public List<String> field;
    public int authorCount;
    public int citationCount;
    public int rank;
    public List<Author> authors;
}