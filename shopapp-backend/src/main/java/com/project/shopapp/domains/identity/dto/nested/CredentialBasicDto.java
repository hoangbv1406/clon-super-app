package com.project.shopapp.domains.identity.dto.nested;
import lombok.Data;

@Data
public class CredentialBasicDto {
    private String deviceLabel;
    private String authenticatorType;
}