package com.pranshu.ecomnotificationservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendEmailDto {
    String from;
    String to;
    String subject;
    String body;
}
