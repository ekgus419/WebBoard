/**
 * @author cdh
 * @since 2019-07-01
 * @copyright  Copyright dh-0419(https://github.com/ekgus419/WebBoard)
 *
 */
package com.dh.webservice.repository;

import com.dh.webservice.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @title Board Entity Query 설정 파일
 * @author cdh
 * @FileName BoardRepository
 *
 */
public interface BoardRepository extends JpaRepository<Board, Integer> {

    @Query(value="SELECT COALESCE(MAX(group_seq), 0) FROM BOARD WHERE group_no = :#{#groupNo}  ", nativeQuery=true)
    int findMaxGroupSeqByGroupNo(@Param("groupNo") int groupNo);

    Board findBoardByBNo(@Param("parentNo") int parentNo);

    Board findBoardByGroupNoAndGroupSeq(@Param("groupNo") int groupNo, @Param("parentGroupSeq") int parentGroupSeq);

    @Modifying
    @Transactional
    @Query(value="UPDATE BOARD SET group_seq = group_seq + 1 "
           +" WHERE group_no = :#{#groupNo} AND group_seq >= :#{#groupSeq} ", nativeQuery=true)
    int updateAllGroupSeq(@Param("groupNo") int groupNo, @Param("groupSeq") int groupSeq);

}
