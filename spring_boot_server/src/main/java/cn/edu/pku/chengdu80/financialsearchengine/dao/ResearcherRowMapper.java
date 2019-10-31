package cn.edu.pku.chengdu80.financialsearchengine.dao;

import cn.edu.pku.chengdu80.financialsearchengine.entity.Researcher;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ResearcherRowMapper implements RowMapper<Researcher> {

    @Override
    public Researcher mapRow(ResultSet rs, int i) throws SQLException {
        Researcher researcher = new Researcher();
        researcher.setId(rs.getLong("author_id"));
        researcher.setName(rs.getString("name"));
        researcher.setHomePageUrl(rs.getString("webpage"));
        researcher.setImageUrl(rs.getString("image_url"));
        researcher.setInstitution(rs.getString("institution"));
        researcher.setIntroduction(rs.getString("anchor_txt"));
        return researcher;
    }
}
