package vn.iostar.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties("storage")
@Primary
public class StorageProperties {
	private String location;

	public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
