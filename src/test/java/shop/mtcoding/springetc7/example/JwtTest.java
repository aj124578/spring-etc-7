package shop.mtcoding.springetc7.example;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

// JSON Web Token
public class JwtTest {
    
    // ABC(메타코딩) -> ex)1313AB
    // ABC(시크) -> ex)5335KD

    // 1313AB 토큰
    @Test
    public void createJwt_test(){
        // given
        
        // when
        String jwt = JWT.create()
                .withSubject("토큰제목")
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000*60*60*24*7)) 
                .withClaim("id", "1") // 해당 user의 pk를 넣어줘야 함
                .withClaim("role", "quest") // 해당 user의 pk를 넣어줘야 함
                .sign(Algorithm.HMAC512("메타코딩"));  // 알고리즘이 들어가는 자리
                System.out.println("테스트 : " + jwt);
        
        // then
    }

      @Test
    public void verifyJwt_test(){
        // given
        String jwt = JWT.create()
                .withSubject("토큰제목")
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000*60*60*24*7)) 
                .withClaim("id", "1") // 해당 user의 pk를 넣어줘야 함
                .withClaim("role", "guest") // 해당 user의 pk를 넣어줘야 함
                .sign(Algorithm.HMAC512("메타코딩2"));  // 알고리즘이 들어가는 자리
                System.out.println("테스트 : " + jwt);
        
        // when
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512("메타코딩"))
                    .build().verify(jwt);
            int id = decodedJWT.getClaim("id").asInt();
            String role = decodedJWT.getClaim("role").asString();
            System.out.println(id);
            System.out.println(role);

        } catch (SignatureVerificationException sve) {
            System.out.println("검증 실패" + sve.getMessage()); // 위조
        } catch (TokenExpiredException tee) {
            System.out.println("토큰 만료" + tee.getMessage()); // 오래됨
        }
        // then
    }
}

