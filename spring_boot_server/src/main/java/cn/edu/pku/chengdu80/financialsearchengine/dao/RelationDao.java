package cn.edu.pku.chengdu80.financialsearchengine.dao;

import cn.edu.pku.chengdu80.financialsearchengine.entity.Relation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class RelationDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    RelationRowMapper relationRowMapper;

    public List<Relation> getTwoOrderRelationById(Long id) {
        String uid_sql = "select unique_id from has_id where id = ? limit 1";
        Long [] ids = new Long[] {id};
        int uid;
        try {
            uid = jdbcTemplate.queryForObject(uid_sql, ids, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }

        List<Relation> oneOrderList = jdbcTemplate.query(
            "select from_id, to_id, ur1.name as from_name, ur2.name as to_name, count\n" +
                    "from works_with_cnt, unique_researcher as ur1, unique_researcher as ur2\n" +
                    "where from_id = ? and ur1.id = from_id and ur2.id = to_id;",
                relationRowMapper,
                uid
        );

        List<Relation> twoOrderList = jdbcTemplate.query(
            "select wwc2.from_id as from_id, wwc2.to_id as to_id, ur1.name as from_name, ur2.name as to_name, wwc2.count as count\n" +
                    "from works_with_cnt as wwc1, works_with_cnt as wwc2, unique_researcher as ur1, unique_researcher as ur2\n" +
                    "where wwc1.from_id = ? and wwc1.to_id = wwc2.from_id and ur1.id = wwc2.from_id and ur2.id = wwc2.to_id and wwc2.to_id <> ?;",
                relationRowMapper,
                uid,
                uid
        );

        for (Relation r: twoOrderList){
            oneOrderList.add(r);
        }
        return oneOrderList.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }
}
