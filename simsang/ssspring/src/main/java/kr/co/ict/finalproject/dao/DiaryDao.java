package kr.co.ict.finalproject.dao;

import java.util.List;
import kr.co.ict.finalproject.vo.DiaryVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DiaryDao {
    // 개발자 : 김건우

    public void diaryAdd(DiaryVO vo);

    public List<DiaryVO> diaryList(int membernum);

    public DiaryVO diaryDetail(int num);

    public void diaryDelete(int num);

    public void diaryUpdate(DiaryVO diaryVO);
}
