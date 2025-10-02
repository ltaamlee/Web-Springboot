package vn.iostar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryDTO {

	private Integer cateId;

    @NotBlank(message = "Tên danh mục không được để trống")
    @Size(max = 100, message = "Tên danh mục tối đa 100 ký tự")
    private String cateName;

    @Size(max = 400, message = "Đường dẫn ảnh tối đa 400 ký tự")
    private String img;

    // Constructor mặc định
    public CategoryDTO() {}

    // Constructor đầy đủ
    public CategoryDTO(Integer cateId, String cateName, String img) {
        this.cateId = cateId;
        this.cateName = cateName;
        this.img = img;
    }

    // Getter và Setter
    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
