package com.saauravsaxena.blog.payloads;

import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RoleDto {

	private int id;
	private String roleName;
}
