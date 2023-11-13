package com.globallogic.users.infrastructure.input;

import com.globallogic.users.application.login.UserLogin;
import com.globallogic.users.application.login.dto.UserLoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserLoginGetController {

    private final UserLogin userLogin;

    @GetMapping("/login")
    @Operation(summary = "Renews user's token", tags = "Login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
    })
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<UserLoginResponse> invoke(Authentication auth) {

        String userId = auth.getName();

        UserLoginResponse result = userLogin.invoke(userId);

        return ResponseEntity.ok(result);
    }
}
