/**
 * @author cdh
 * @since 2019-07-01
 * @copyright  Copyright dh-0419(https://github.com/ekgus419/WebBoard)
 *
 */
package com.dh.webservice.repository;

import com.dh.webservice.domain.Board;
import org.hibernate.query.criteria.internal.expression.function.AggregationFunction.MAX;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @title Board Entity Query 설정 파일
 * @author cdhfindOneByGroupNoAndGroupSeq
 * @FileName BoardRepository
 *
 */
public interface BoardRepository extends JpaRepository<Board, Integer> {

    @Query(value="SELECT COALESCE(MAX(group_seq), 0) FROM BOARD WHERE group_no = :#{#groupNo}  ", nativeQuery=true)
    int findMaxGroupSeqByGroupNo(@Param("groupNo") int groupNo);

//    @Query(value="SELECT COALESCE(depth, 0) FROM BOARD WHERE bNo = :#{#parentNo}", nativeQuery=true)
//    int findDepthByParentNo(@Param("parentNo") int parentNo);
//
//    @Query(value="SELECT group_seq FROM board WHERE bno = :#{#parentNo}", nativeQuery=true)
//    int findGroupSeqByParentNo(@Param("parentNo") int parentNo);

    @Query(value="SELECT * FROM BOARD WHERE group_no = :#{#groupNo} AND group_seq < :#{#parentGroupSeq} " , nativeQuery=true)
    Board findOneByGroupNoAndGroupSeq(@Param("groupNo") int groupNo, @Param("parentGroupSeq") int parentGroupSeq);

    @Query(value="SELECT * FROM BOARD WHERE bNo = :#{#parentNo} " , nativeQuery=true)
    Board findOneByBno(@Param("parentNo") int parentNo);

    @Modifying
    @Transactional
    @Query(value="UPDATE BOARD SET group_seq = group_seq + 1 "
           +" WHERE group_no = :#{#groupNo} AND group_seq > :#{#groupSeq} "
           + "AND group_seq != :#{#parentGroupSeq}", nativeQuery=true)
    int updateAllGroupSeq(@Param("groupNo") int groupNo, @Param("groupSeq") int groupSeq, @Param("parentGroupSeq") int parentGroupSeq);

}
