# WebBoard

이 프로젝트는 게시판의 기본 기능(CRUD)에 충실하게 만들되,
하나의 글에 트리 형태로 답변을 달 수 있는 계층형 로직을 추가해보자! 라는 생각에서 개발 하게 되었습니다.

##  SKILL
- Spring Boot
- Spring DevTools
- Spring data JPA
- Spring Security
- jquery
- BootStrap
- AdminLTE
- Handelbars
- Git
- Gradle

## 주요 기능

1. jQuery BootPag Plugin을 활용한 Paging 로직
2. jQuery Validation Plugin을 활용한 데이터 검증 로직
2. 3개의 Table을 활용한 로그인 로직(USER, ROLE, USER_ROLE)
3. 회원가입 로직
4. XSS 방지 로직( '<', '>', '&', '(' ,')' )
5. LocalDateTime을 활용한 공통 날짜 데이터 정의 로직
6. 계층형 게시판 기본 로직
- 글 목록
- 글 쓰기
- 글 수정
- 글 삭제
- 글 상세
- 타인이 쓴 글 읽을시 조회수 증가

## What was difficult?
프로젝트를 진행하며 가장 어려웠던 점은 역시나 계층형 로직이었습니다.
원글에 답글을 추가하는 로직을 구현 했었는데 여러번의 시행착오 끝에 아래와 같이 구현 할 수 있었습니다.

> View 단 로직
1. 게시글 클릭 후 상세 페이지로 이동
2. 답글 버튼 클릭
3. 답글 작성 화면으로 이동
4. 답글 작성 후 등록 버튼 클릭
5. 게시글 목록 페이지로 이동

> 서버단 로직
1. 원글의 답글인 경우 부모글 번호를 그룹 번호로 설정한다.
2. 부모글을 통해 전체 컬럼을 가져온다.
3. 위에서 가져온 부모글의 그룹 시퀀스의 값에 +1한 값을 답글의 그룹 시퀀스로 설정한다.
4. 위에서 가져온 부모글의 Depth 값에 +1한 값을 답글의 Depth로 설정한다.
5. 만약 설정한 그룹 시퀀스가 존재한다면 전체 데이터의 그룹 시퀀스를 +1 증가시킨다.


```java
@PostMapping("/writeReply")
@ResponseBody
public Board writeReply(@RequestBody Board board, Principal principal) {

	String writer = principal.getName();
	// 작성자인지 확인
	if(!writer.equals("") &&  writer.trim().length() > 0) {

		int groupNo = board.getGroupNo();
		int parentNo = board.getParentNo();
		// 원글의 답글인 경우
		if(parentNo == 0) {
			parentNo = groupNo;
		}

		// parentNo로 부모글 컬럼 가져오기
		Board parentBoard = boardRepository.findBoardByBNo(parentNo);

		// 부모글 groupSeq
		int parentGroupSeq = parentBoard.getGroupSeq();

		// 현재 넣으려는 글의 seq
		int nextGroupSeq = parentGroupSeq + 1;

		// 부모글 depth
		int parentDepth = parentBoard.getDepth();

		// 작성한 글의 depth
		int depth = parentDepth + 1;

		// max Seq
		int maxGroupSeq = boardRepository.findMaxGroupSeqByGroupNo(groupNo);

		if(nextGroupSeq <= maxGroupSeq){
		    // 답글이 존재함(이미 존재하는 group_seq인지 확인)
		    Board findBoard = boardRepository.findBoardByGroupNoAndGroupSeq(groupNo,nextGroupSeq);
		    if(findBoard.getParentNo() != parentNo) {
		       board.setGroupSeq(nextGroupSeq);
		       boardRepository.updateAllGroupSeq(findBoard.getGroupNo(), findBoard.getGroupSeq());
		     }
		 }
		if(board.getGroupSeq() == 0) {
		    board.setGroupSeq(maxGroupSeq + 1);
		}
		
		board.setParentNo(parentNo);
		board.setDepth(depth);
		board.setWriter(writer);

		return boardRepository.save(board);
	}else{
		return new Board();
	}

}
```

