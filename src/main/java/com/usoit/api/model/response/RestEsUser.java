package com.usoit.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestEsUser {

	private String publicId;

	private String name;

	private String officialEmail;

	private String personalEmail;

	private String officialPhoneNumber;

	private String personalPhoneNumber;
}
