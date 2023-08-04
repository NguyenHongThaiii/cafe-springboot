package com.cafe.website.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class LoginDto {
    @NotEmpty(message = "Name should not be null or empty" )
    @Size(min = 6, max = 20)
    private String email;
    
    @NotEmpty(message = "Name should not be null or empty" )
    @Size(min = 6, max = 20)
	private String password;
}
