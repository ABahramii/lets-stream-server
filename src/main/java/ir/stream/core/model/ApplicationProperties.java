package ir.stream.core.model;

import org.springframework.beans.factory.annotation.Value;

//@Component
public class ApplicationProperties {

    private final Integer verificationCodeLength;
    private final Integer verificationCodeExpireAfterMinute;


    public ApplicationProperties(
            @Value("${lets.auth.verificationCode.length}") Integer verificationCodeLength,
            @Value("${lets.auth.verificationCode.expireAfterMinute}") Integer verificationCodeExpireAfterMinute
    ) {
        this.verificationCodeLength = verificationCodeLength;
        this.verificationCodeExpireAfterMinute = verificationCodeExpireAfterMinute;
    }

    public Integer getVerificationCodeLength() {
        return verificationCodeLength;
    }

    public Integer getVerificationCodeExpireAfterMinute() {
        return verificationCodeExpireAfterMinute;
    }
}
