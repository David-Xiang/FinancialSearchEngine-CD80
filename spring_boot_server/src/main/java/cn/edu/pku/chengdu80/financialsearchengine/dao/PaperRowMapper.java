package cn.edu.pku.chengdu80.financialsearchengine.dao;

import cn.edu.pku.chengdu80.financialsearchengine.entity.Paper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PaperRowMapper implements RowMapper<Paper> {

    @Override
    public Paper mapRow(ResultSet rs, int i) throws SQLException {
        Paper paper = new Paper();
        paper.setId(rs.getLong("id"));
        paper.setName(rs.getString("name"));
        paper.setVenue(rs.getString("venue"));
        paper.setDate(rs.getInt("date"));
        paper.setUrl(rs.getString("src_link"));
        paper.setAuthorCount(rs.getInt("author_cnt"));
        paper.setCitationCount(rs.getInt("citation_cnt"));
        paper.setRank(rs.getInt("rnk"));

        return paper;
    }
}
