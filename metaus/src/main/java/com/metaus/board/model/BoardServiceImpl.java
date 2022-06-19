package com.metaus.board.model;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.metaus.member.model.MemberVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
	
	private final BoardDAO boardDao;	
	@Override
	public int insertBoard(BoardVO boardVo) {
		return boardDao.insertBoard(boardVo);
	}
	@Override
	public List<Map<String, Object>> selectBoard(int btypeNo) {
		return boardDao.selectBoard(btypeNo);
	}
	@Override
	public int insertBoardAtc(BoardAtcVO boardAtcVo) {
		return boardDao.insertBoardAtc(boardAtcVo);
	}
	@Override
	public List<BoardAtcVO> selectBoardAtc() {
		return boardDao.selectBoardAtc();
	}
	@Override
	public BoardVO selectBoardDetail(int boardNo) {
		return boardDao.selectBoardDetail(boardNo);
	}
	@Override
	public BoardAtcVO selectBoardAtcByNo(int boardNo) {
		return boardDao.selectBoardAtcByNo(boardNo);
	}
	

}
