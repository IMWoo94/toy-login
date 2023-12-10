package toy.login.users.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import toy.login.commons.entity.BaseEntity;

@Entity
@Table(name = "Users")
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "user_id")
	private Long id;

	@NotBlank
	private String name;

	@NotNull
	private LocalDate birthDate;

	@NotBlank
	private String loginId;

	@NotBlank
	private String password;

	@Embedded
	private Address address;

	public User(String name, LocalDate birthDate, String loginId, String password, Address address) {
		this.name = name;
		this.birthDate = birthDate;
		this.loginId = loginId;
		this.password = password;
		this.address = address;
	}
}
