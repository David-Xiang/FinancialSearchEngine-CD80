package cn.edu.pku.chengdu80.financialsearchengine.entity;

import lombok.Data;

import java.util.List;

@Data
public class Researcher {
    public long id;
    public String name;
    public String homePageUrl;
    public String imageUrl;
    public List<String> field;
    public String introduction;
    public List<Paper> papers;
    public String institution;
    public int totalCitationCount;
    public int publicationCount;
}
