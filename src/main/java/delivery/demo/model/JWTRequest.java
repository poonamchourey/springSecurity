package delivery.demo.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JWTRequest {
    private String email;
    private String password;
}
