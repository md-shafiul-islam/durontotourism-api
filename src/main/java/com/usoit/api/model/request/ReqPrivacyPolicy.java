package com.usoit.api.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqPrivacyPolicy {

	private String publicId;

	private String name;

	private String description;
}
