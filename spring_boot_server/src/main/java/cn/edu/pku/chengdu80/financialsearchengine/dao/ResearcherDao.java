package cn.edu.pku.chengdu80.financialsearchengine.dao;

import cn.edu.pku.chengdu80.financialsearchengine.entity.Researcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ResearcherDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ResearcherRowMapper researcherRowMapper;

    @Autowired
    PaperDao paperDao;

    public Researcher getResearcherById(long id) {
        Researcher researcher = null;
        try {
            researcher = jdbcTemplate.queryForObject(
            "select author_id, name, webpage, anchor_txt, image_url, institution, citation_cnt, paper_cnt\n" +
                    "from researcher\n" +
                    "where author_id = ?\n" +
                    "limit 1;",
                researcherRowMapper,
                id
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

        try{
            researcher.setField(
                jdbcTemplate.query(
                    "select distinct(expertise_name)\n" +
                    "from in_expertise\n" +
                    "where author_id = ?;",
                    (resultSet, i) -> resultSet.getString("expertise_name"),
                    id
                )
            );
        } catch (EmptyResultDataAccessException e) {
            researcher.setField(new ArrayList<>());
        }

        try{
            List<Long> paperIdList = jdbcTemplate.query(
            "select distinct(paper_id)\n" +
                    "from writes\n" +
                    "where author_id = ?;",
                (resultSet, i) -> resultSet.getLong("paper_id"),
                id
            );
            if(paperIdList.size() > 0) {
                researcher.setPapers(paperIdList.stream().map(
                        id1 -> paperDao.getPaperById(id1)
                ).collect(Collectors.toList()));
            }
        } catch (EmptyResultDataAccessException e) {
            researcher.setPapers(new ArrayList<>());
        }

        return researcher;
    }
}
