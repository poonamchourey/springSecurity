package delivery.demo.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JWTResponse {
    String jwtToken;
    String username;
}
