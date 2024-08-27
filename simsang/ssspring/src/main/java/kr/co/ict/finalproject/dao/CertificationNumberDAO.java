package kr.co.ict.finalproject.dao;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CertificationNumberDAO {
    // 개발자 : 이승희, 이지영
    private final StringRedisTemplate stringRedisTemplate;

    public void saveCertificationNumber(String email, String authCode) {
        stringRedisTemplate.opsForValue().set(email, authCode, Duration.ofSeconds(10000));
    }

    public String getCertificationNumber(String email) {
        return stringRedisTemplate.opsForValue().get(email);
    }

    public void deleteCertificationNumber(String email) {
        stringRedisTemplate.delete(email);
    }

    public boolean hasKey(String email) {
        return stringRedisTemplate.hasKey(email);
    }
}
