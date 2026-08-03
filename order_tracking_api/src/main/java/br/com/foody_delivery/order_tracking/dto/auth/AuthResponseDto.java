package br.com.foody_delivery.order_tracking.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthResponseDto (
        @Schema(example = "eyJhbGciOiJIUzI1NiIsonR5cCI6IkpXVCJ9.eyJpc3MiOiJGb29keSBEZWxpdmVyeSIsInN1YiI6InRlc3RlQHRlc3RlLmNvbSIsIm5hbWUiOiJVc3XDoXJpbyBUZXN0ZSIsImV4cCI6MTc4NTcxNtI0OH0.-1yWWWVDZpfS_Fh8ECIc5Q0_uuQUNfZiWURxJKmObPM")
        String token
) {
}
