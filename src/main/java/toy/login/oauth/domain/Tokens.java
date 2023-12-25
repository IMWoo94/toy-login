package toy.login.oauth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tokens {

	private String access_token;
	private String refresh_token;
	private String token_type;
	private int expires_in;

	public String token() {
		return token_type + " " + access_token;
	}

}
