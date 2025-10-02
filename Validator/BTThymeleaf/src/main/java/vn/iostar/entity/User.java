package vn.iostar.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@Entity
@Table(name = "Users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
    @Column(name = "user_id", length = 20, nullable = false)
    private String userId;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 50)
    private String password;

    @Column(name = "roleid", length = 20)
    private String roleId;

    @Column(name = "img", length = 400)
    private String img;

	public String getRoleid() {
		return roleId;
	}

	public String getPassword() {
		return password;
	}
	
    public String getImg() { return img; }
    public void setImg(String img) { this.img = img; }

	public String getId() {
		return userId;
	}

}
