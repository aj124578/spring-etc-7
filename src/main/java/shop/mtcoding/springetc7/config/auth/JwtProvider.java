package shop.mtcoding.springetc7.config.auth;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import shop.mtcoding.springetc7.model.User;

public class JwtProvider {
    
    private static final String SUBJECT = "jwtsudy";
    private static final int EXP = 1000*60*60;
    public static final String TOKEN_PREFIX = "Bearer "; // ※주의 : 한칸 띄워야 함
    public static final String HEADER = "Authorization"; // 헤더만 바깥에서 접근할 수 있어야 하므로 public으로 설정
    private static final String SECRET = "메타코딩"; // 환경변수써서 ec2에 만들면 되지만 지금은 이렇게 하면됨


    public static String create(User user){
        String jwt = JWT.create()
                .withSubject(SUBJECT)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXP))
                .withClaim("id", user.getId()) // 해당 user의 pk를 넣어줘야 함
                .withClaim("role", user.getRole()) // 해당 user의 pk를 넣어줘야 함
                .sign(Algorithm.HMAC512(SECRET)); // 알고리즘이 들어가는 자리

        return TOKEN_PREFIX+jwt;        
    }

    // verify : 검증 이니까 여기서는 하나의 책임으로 검증만 하고 예외처리는 handler에게 넘겨라
    public static DecodedJWT verify(String jwt) throws SignatureVerificationException, TokenExpiredException{ // 여기서 try catch 하면 handler에게 exception을 못날림 그러므로 throws로 던져서 넘겨줌

            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512("메타코딩"))
                    .build().verify(jwt);
            // int id = decodedJWT.getClaim("id").asInt();
            // String role = decodedJWT.getClaim("role").asString();
            // System.out.println(id);
            // System.out.println(role);
        return decodedJWT; 
    }
}
