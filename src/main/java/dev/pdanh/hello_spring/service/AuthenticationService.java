package dev.pdanh.hello_spring.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import dev.pdanh.hello_spring.dto.request.AuthenticateRequest;
import dev.pdanh.hello_spring.dto.request.IntrospectRequest;
import dev.pdanh.hello_spring.dto.response.AuthenticateResponse;
import dev.pdanh.hello_spring.dto.response.IntrospectResponse;
import dev.pdanh.hello_spring.entity.User;
import dev.pdanh.hello_spring.exception.AppException;
import dev.pdanh.hello_spring.exception.ErrorCode;
import dev.pdanh.hello_spring.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);
    UserRepository userRepository;
    @NonFinal
    @Value("${JWT_SIGNER_KEY}")
    protected  String jwtSignerKey;


    public IntrospectResponse introspect(IntrospectRequest request) {
        var token = request.getToken();

        try {
            JWSVerifier verifier = new MACVerifier(jwtSignerKey); // verifier

            SignedJWT signedJWT = SignedJWT.parse(token);  // parse token nhan duoc
            //kiem tra xem token het han chua
            Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();

            var verified =  signedJWT.verify(verifier);// ham nay tra ve true hoac false
            return IntrospectResponse.builder()
                    .valid(verified && expiration.after(new Date()))
                    .build();
        } catch (JOSEException | ParseException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public AuthenticateResponse authenticate(AuthenticateRequest request) {
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!authenticated) {
            // neu chua duoc xac thuc thanh cong thi tra ve exception
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        var token = generateToken(request.getUsername());

        return AuthenticateResponse.builder()
                .authenticated(true)
                .token(token)
                .build();

    }

    private String generateToken(String username) {
        //tao header cho tokem
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        //payload
        //Claim : la cac data trong body
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)//subject cua jwt
                .issuer("dev.pdanh")//issue boi ai
                .issueTime(new Date())//issue luc nao
                //het han sau bao lau
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                //custom claim
                .claim("customClaim", "Custom")
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        // sign token
        try {
            jwsObject.sign(new MACSigner(jwtSignerKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token");
            throw new RuntimeException(e);
        }
    }
}
