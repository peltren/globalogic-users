package com.globallogic.users.infrastructure.input;

import com.globallogic.users.application.sign_up.UserSignUp;
import com.globallogic.users.application.sign_up.dto.UserSignUpRequest;
import com.globallogic.users.application.sign_up.dto.UserSignUpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class UserSignUpPostController {

    private final UserSignUp userSignUp;

    @PostMapping("/sign-up")
    @Operation(summary = "Signs up a new User", tags = "Sign Up")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
    })
    public ResponseEntity<UserSignUpResponse> invoke(@RequestBody @Valid UserSignUpRequest request) {

        UserSignUpResponse response = userSignUp.invoke(request);

        return ResponseEntity.ok(response);
    }
}
