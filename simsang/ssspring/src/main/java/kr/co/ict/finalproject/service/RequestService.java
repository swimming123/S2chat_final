package kr.co.ict.finalproject.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ict.finalproject.dao.RequestDAO;
import kr.co.ict.finalproject.vo.RequestVO;

@Service
public class RequestService {
    // 개발자 : 이승희
    @Autowired
    private RequestDAO requestDAO;

    public void requestadd(RequestVO vo) {
        requestDAO.requestadd(vo);
    }

    public List<RequestVO> requestlist(Long membernum) {
        return requestDAO.requestlist(membernum);
    }

    public void requestupdate(RequestVO vo) {
        requestDAO.requestupdate(vo);
    }

    public void requestdelete(Long requestnum) {
        requestDAO.requestdelete(requestnum);
    }

    private static final AtomicInteger orderNumberSequence = new AtomicInteger(1);

    public Long generateOrderNumber() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String datePart = today.format(formatter);

        // 순번을 6자리 형식으로 포맷팅
        int sequenceNumber = orderNumberSequence.getAndIncrement();
        String sequencePart = String.format("%06d", sequenceNumber);

        // 주문번호 생성
        String orderNumberString = datePart + sequencePart;
        return Long.parseLong(orderNumberString);
    }

}