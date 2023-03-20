package shop.mtcoding.springetc7.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.springetc7.config.auth.JwtProvider;
import shop.mtcoding.springetc7.config.auth.LoginUser;
import shop.mtcoding.springetc7.model.User;
import shop.mtcoding.springetc7.model.UserRepository;

@RequiredArgsConstructor
@RestController
public class UserController {
    

    private final UserRepository userRepository;

    private final HttpSession session;
    
    @GetMapping("/user") // 인증 필요 -> jwt 토큰 있어야 가능 -> 필터에서 검증하면 됨
    public ResponseEntity<?> user(){
        // 권한처리 이 사람이 이 게시글의 주인
        LoginUser loginUser = (LoginUser) session.getAttribute("loginUser");
        if (loginUser.getId() == 1) {
            return ResponseEntity.ok().body("접근 성공");
        } else {
            return new ResponseEntity<>("접근 실패", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/") // 인증 불필요 -> jwt 토큰 없어도 들어갈 수 있음
    public ResponseEntity<?> main(){
        return ResponseEntity.ok().body("접근 성공");
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(User user){
        Optional<User> userOP = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword()); // 리턴타입을 Optional로 하면 강제성이 부여돼서 null처리를 해야됨

        if (userOP.isPresent()) { // isPresent() : 값이 있다는것
            String jwt = JwtProvider.create(userOP.get());
            return ResponseEntity.ok().header(JwtProvider.HEADER, jwt).body("로그인 성공");
        }else{
            return ResponseEntity.badRequest().build();
        }
    }
}
