package application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ProductController {

    private final ProductRepository productRepository;
    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping("/insertProductPage")
    public Product getProduct(@RequestBody Product product){

        ProductEntity productEntity= new ProductEntity();
        productEntity.setId(product.getId());
        productEntity.setColour(product.getColour());
        productEntity.setName(product.getName());

        productRepository.save(productEntity);

        product.setId(productEntity.getId());

        return product;
}

    @GetMapping("/getProductDetails")
    public Product getProductDetails(@RequestParam(value = "id")Integer id){

        Optional<ProductEntity> optionalProductEntity = productRepository.findById(id);

        if(optionalProductEntity.isPresent()){
            ProductEntity productEntity = optionalProductEntity.get();

            Product product= new Product();
            product.setId(productEntity.getId());
            product.setName(productEntity.getName());
            product.setColour(productEntity.getColour());

            return product;
        }
        return null;
    }

    @PutMapping("/updateProductPage")
    public Product updateProductDetails(@RequestBody Product product){

        Optional<ProductEntity> optionalProductEntity = productRepository.findById(product.getId());

        if(optionalProductEntity.isPresent()){
            ProductEntity productEntity = optionalProductEntity.get();
            productEntity.setName(product.getName());
            productEntity.setColour(product.getColour());

            productRepository.save(productEntity);

            return product;
        }
        return null;
    }
    @DeleteMapping("/deleteProduct")
    public void deleteProductDetails(@RequestParam(value = "id") Integer id){

        if(productRepository.existsById(id)) productRepository.deleteById(id);
    }
}