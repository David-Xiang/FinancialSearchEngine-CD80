package cn.edu.pku.chengdu80.financialsearchengine.dao;

import cn.edu.pku.chengdu80.financialsearchengine.entity.Paper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PaperDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    PaperRowMapper paperRowMapper;

    public Paper getPaperById(long id) {
        Paper paper = null;
        try{
             paper = jdbcTemplate.queryForObject(
                "select id, name, venue, date, src_link, author_cnt, citation_cnt, rnk\n" +
                    "from paper where id = ?",
                    paperRowMapper,
                    id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
//        List<String> fields = jdbcTemplate.query(
//                "select field.name\n" +
//                "from field, in_field\n" +
//                "where in_field.paper_id = ? and in_field.field_id = field.id;",
//                (resultSet, i) -> resultSet.getString("field.name"),
//                id
//        );
//        p.setField(fields);

//
//        Author author1 = new Author();
//        Author author2 = new Author();
//        author1.setName("Xiang");
//        author1.setInstitution("PKU");
//        author2.setName("Song");
//        author2.setInstitution("PKU");
//        p.setAuthors(Arrays.asList(
//                author1, author2
//        ));
        return paper;
    }
}
