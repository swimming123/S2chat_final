package kr.co.ict.finalproject.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ict.finalproject.dao.DiaryDao;
import kr.co.ict.finalproject.vo.DiaryVO;

@Service
public class DiaryService {
    // 개발자 : 김건우

    @Autowired
    private DiaryDao diaryDao;

    public void diaryAdd(DiaryVO vo) {
        diaryDao.diaryAdd(vo);
    }

    public List<DiaryVO> diaryList(int membernum) {
        return diaryDao.diaryList(membernum);
    }

    public DiaryVO createDiary(Date diaryDate, String title, String content) {
        DiaryVO diary = new DiaryVO();
        diary.setDiarydate(diaryDate);
        diary.setTitle(title);
        diary.setContent(content);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.ENGLISH); // "EEEE"는 요일을 긴 형식으로 반환
        String weekDay = sdf.format(diaryDate);
        diary.setWeek(weekDay);

        return diary;
    }

    public DiaryVO diaryDetail(int num) {
        return diaryDao.diaryDetail(num);
    }

    public void diaryDelete(int num) {
        diaryDao.diaryDelete(num);
    }

    public void diaryUpdate(DiaryVO diaryVO) {
        diaryDao.diaryUpdate(diaryVO);
    }

}
