package cn.edu.pku.chengdu80.financialsearchengine.dao;

import cn.edu.pku.chengdu80.financialsearchengine.entity.Paper;
import cn.edu.pku.chengdu80.financialsearchengine.entity.Relation;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RelationRowMapper implements RowMapper<Relation> {

    @Override
    public Relation mapRow(ResultSet rs, int i) throws SQLException {
        Relation relation = new Relation();
        relation.setFrom_id(rs.getInt("from_id"));
        relation.setTo_id(rs.getInt("to_id"));
        relation.setFrom_name(rs.getString("from_name"));
        relation.setTo_name(rs.getString("to_name"));
        relation.setWeight(rs.getInt("count"));
        return relation;
    }
}
