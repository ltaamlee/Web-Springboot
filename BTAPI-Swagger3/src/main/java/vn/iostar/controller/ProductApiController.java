package vn.iostar.controller;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import vn.iostar.entity.Category;
import vn.iostar.entity.Product;
import vn.iostar.model.Response;
import vn.iostar.service.CategoryService;
import vn.iostar.service.ProductService;
import vn.iostar.service.StorageService;

@RestController
@RequestMapping("/api/product")
public class ProductApiController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private StorageService storageService;

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(new Response(true, "Thành công", productService.findAll()));
    }

    @PostMapping("/getProduct")
    public ResponseEntity<?> getProduct(@Validated @RequestParam("productId") Integer productId) {
        Optional<Product> optProduct = productService.findById(productId);
        if (optProduct.isPresent()) {
            return ResponseEntity.ok(new Response(true, "Thành công", optProduct.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new Response(false, "Không tìm thấy sản phẩm", null));
        }
    }

    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(
            @RequestParam("productName") String productName,
            @RequestParam("image") MultipartFile productImage,
            @RequestParam("unitPrice") Double unitPrice,
            @RequestParam("discount") Double discount,
            @RequestParam("description") String description,
            @RequestParam("categoryId") Integer categoryId,
            @RequestParam("quantity") Integer quantity,
            @RequestParam("status") Short status) {

        Optional<Product> optProduct = productService.findByProductName(productName);
        if (optProduct.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response(false, "Sản phẩm này đã tồn tại", optProduct.get()));
        }

        try {
            Product product = new Product();
            product.setProductName(productName);
            product.setUnitPrice(unitPrice);
            product.setDiscount(discount);
            product.setDescription(description);
            product.setQuantity(quantity);
            product.setStatus(status);
            product.setCreateDate(new Date());

            Optional<Category> optCategory = categoryService.findById(categoryId);
            if (optCategory.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Response(false, "Category không tồn tại", null));
            }
            product.setCategory(optCategory.get());

            if (!productImage.isEmpty()) {
                String fileName = storageService.getStorageFilename(productImage, UUID.randomUUID().toString());
                storageService.store(productImage, fileName);
                product.setImages(fileName);
            }

            productService.save(product);

            return ResponseEntity.ok(new Response(true, "Thêm sản phẩm thành công", product));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response(false, "Lỗi khi thêm sản phẩm", null));
        }
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<?> updateProduct(
            @RequestParam("productId") Integer productId,
            @RequestParam("productName") String productName,
            @RequestParam("images") MultipartFile productImage,
            @RequestParam("unitPrice") Double unitPrice,
            @RequestParam("discount") Double discount,
            @RequestParam("description") String description,
            @RequestParam("cateId") Integer categoryId,
            @RequestParam("quantity") Integer quantity,
            @RequestParam("status") Short status) {

        Optional<Product> optProduct = productService.findById(productId);
        if (optProduct.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response(false, "Không tìm thấy sản phẩm", null));
        }

        try {
            Product product = optProduct.get();
            product.setProductName(productName);
            product.setUnitPrice(unitPrice);
            product.setDiscount(discount);
            product.setDescription(description);
            product.setQuantity(quantity);
            product.setStatus(status);

            Optional<Category> optCategory = categoryService.findById(categoryId);
            if (optCategory.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Response(false, "Category không tồn tại", null));
            }
            product.setCategory(optCategory.get());

            if (!productImage.isEmpty()) {
                String fileName = storageService.getStorageFilename(productImage, UUID.randomUUID().toString());
                storageService.store(productImage, fileName);
                product.setImages(fileName);
            }

            productService.save(product);

            return ResponseEntity.ok(new Response(true, "Cập nhật sản phẩm thành công", product));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response(false, "Lỗi khi cập nhật sản phẩm", null));
        }
    }

    @DeleteMapping("/deleteProduct")
    public ResponseEntity<?> deleteProduct(@RequestParam("productId") Integer productId) {
        Optional<Product> optProduct = productService.findById(productId);
        if (optProduct.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response(false, "Không tìm thấy sản phẩm", null));
        }

        productService.delete(optProduct.get());
        return ResponseEntity.ok(new Response(true, "Xóa sản phẩm thành công", optProduct.get()));
    }
}
