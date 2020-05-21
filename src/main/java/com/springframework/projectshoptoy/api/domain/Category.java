package com.springframework.projectshoptoy.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
//@Data để khởi tạo getter,setter,constructor mặc định
//@NoArgConstructor là khởi tạo constructor không tham số
//@AllArgsCOnstructor là khởi tạo constructor có tham số
//@Entity để thêm 1 collection vào mongodb
//@Table để tạo tên bảng
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categorys")
public class Category {
	@Id
    private String categoryID;

    @NotEmpty(message = "categoryName not valid")
    private String categoryName;

    @NotEmpty(message = "description not valid")
    private String description;

    @NotEmpty(message = "picture not valid")
    private String picture;
    
    //orpahnRemoval đảm bảo toàn vẹn dữ liệu khi product đã xóa ,thì category cũng phải cập nhập lại
    @OneToMany(mappedBy = "categoryID",fetch = FetchType.LAZY,orphanRemoval = true)
    private List<Product> products;

	public Category(@NotEmpty(message = "categoryName not valid") String categoryName,
			@NotEmpty(message = "description not valid") String description,
			@NotEmpty(message = "picture not valid") String picture) {
		super();
		this.categoryName = categoryName;
		this.description = description;
		this.picture = picture;
	}
    
    
}
