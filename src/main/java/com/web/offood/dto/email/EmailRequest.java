package com.web.offood.dto.email;

import com.web.offood.exception.ApiErrorCode;
import com.web.offood.exception.ApiException;
import com.web.offood.util.TextUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {

    private String subject;
    private String content;

    @Column(columnDefinition = "type of email, 1= Personal, 2= Report, 3= System")
    private int type;

    @Column(columnDefinition = "List of account ids separated by comma")
    private String accountIds;

    private boolean sendAll;

    public void validate() {
        if (TextUtils.isNullOrEmpty(subject))
            throw new ApiException(ApiErrorCode.EMAIL_REWARD_SUBJECT_INVALID);
        if (TextUtils.isNullOrEmpty(content))
            throw new ApiException(ApiErrorCode.EMAIL_REWARD_CONTENT_INVALID);
    }
}
