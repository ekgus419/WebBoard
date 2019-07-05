package com.dh.webservice.repository;

import com.dh.webservice.domain.Board;
import org.hibernate.query.criteria.internal.expression.function.AggregationFunction.MAX;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface BoardRepository extends JpaRepository<Board, Integer> {

    @Query(value="SELECT COALESCE(MAX(group_seq), 0) FROM board WHERE group_no = :#{#groupNo}  ", nativeQuery=true)
    int findMaxGroupSeqByGroupNo(@Param("groupNo") int groupNo);

    @Query(value="SELECT COALESCE(MAX(depth), 0) FROM board WHERE bNo = :#{#parentNo}", nativeQuery=true)
    int findMaxDepthByParentNo(@Param("parentNo") int parentNo);

    // findGroupNoBybNo
    // findMaxGroupSeqByGroupNo
//    @Query("SELECT COALESCE(MAX(groupSeq), 0) FROM BOARD b WHERE " + delCheck + " AND b.groupNo = :groupNo")
//    double findMaxGroupSeqByGroupNo(@Param("groupNo") int groupNo);

//    @Query("select u from User u where u.firstname = :#{#customer.firstname}")
//    List<User> findUsersByCustomersFirstname(@Param("customer") Customer customer);
}
