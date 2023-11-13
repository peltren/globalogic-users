package com.globallogic.users.infrastructure.exceptions;

import lombok.Value;
import java.util.List;

@Value
public class ErrorResponse {
    List<Error> error;
}
