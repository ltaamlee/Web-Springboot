package vn.iostar.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="Categories")
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable{
	private static final long serialVersionUID = 1L;


	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cate_id")
    private Integer cateId;

    @Column(name = "cate_name", nullable = false, length = 100)
    private String cateName;

    @Column(name = "img", length = 400)
    private String img;
    
    public Integer getCateId() { return cateId; }
    public void setCateId(Integer cateId) { this.cateId = cateId; }

    public String getCateName() { return cateName; }
    public void setCateName(String cateName) { this.cateName = cateName; }

    public String getImg() { return img; }
    public void setImg(String img) { this.img = img; }
}
