package com.student.springerp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtRsp {
    String jwtToken;
    Long studentId;
}
