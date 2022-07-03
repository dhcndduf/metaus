package com.metaus.comment.model;

import java.util.List;
import java.util.Map;

public interface CommentService {
	int insertComment(CommentVO vo);
	List<Map<String, Object>> selectComment(int boardNo);
	int countComment(int boardNo);
	int updateComment(CommentVO vo);
	int reply(CommentVO vo);
	public void deleteComment(Map<String, String> map);
}
