package cn.edu.pku.chengdu80.financialsearchengine.entity;

import lombok.Data;

@Data
public class Relation {
    int from_id;
    int to_id;
    String from_name;
    String to_name;
    int weight;
}
