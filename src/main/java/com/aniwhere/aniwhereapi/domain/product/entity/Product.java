package com.aniwhere.aniwhereapi.domain.product.entity;

import com.aniwhere.aniwhereapi.domain.product.enums.Adult;
import com.aniwhere.aniwhereapi.domain.product.enums.ProductMdPick;
import com.aniwhere.aniwhereapi.entity.BaseEntity;
import com.aniwhere.aniwhereapi.exception.OutOfStockException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString(exclude = "imageList")
@Table(name = "product")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "price", nullable = false)
    private Integer price;

    @Lob
    @Column(name = "story")
    private String story;

    @ColumnDefault("0")
    @Column(name = "del_flag")
    private Boolean delFlag;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "stock_number", nullable = false)
    private Integer stockNumber;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'N'")
    private ProductMdPick mdPick;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'N'")
    private Adult adult;

    // 방영시기
    private LocalDate releaseDate;

    // 제작사
    private String manufacturer;

    // 총화수
    private Integer totalEpisode;

    // 장르
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ElementCollection
    @Builder.Default
    private List<ProductImage> imageList = new ArrayList<>();

    // 태그 리스트
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductTag> productTagList = new ArrayList<>();

    // 재고수량 감소
    public void removeStock(int stockNumber) {
        int restStock = this.stockNumber - stockNumber;
        if (restStock < 0) {
            throw new OutOfStockException("상품의 재고가 부족합니다. (현재 재고 수량: " + this.stockNumber + ")");
        }
        this.stockNumber = restStock;
    }

    // 재고수량 증가
    public void addStock(int stockNumber) {
        this.stockNumber += stockNumber;
    }

    /**
     * 상품 이미지, 상품에 추가
     *
     * @param image 이미지
     */
    public void addImage(ProductImage image) {

        image.setOrd(this.imageList.size());
        imageList.add(image);
    }


    /**
     * 상품 이미지 파일, 상품이미지에 추가
     *
     * @param fileName 이미지 파일명
     */
    public void addImageString(String fileName) {

        ProductImage productImage = ProductImage.builder()
                .imageName(fileName)
                .build();
        addImage(productImage);

    }

    /**
     * 상품 이미지 리스트 초기화
     */
    public void clearImageList() {
        this.imageList.clear();
    }

    /*
     * 상품 수정 로직
     */
    public void changeName(String name) {
        this.name = name;
    }

    public void changePrice(Integer price) {
        this.price = price;
    }

    public void changeDescription(String description) {
        this.story = description;
    }

    public void changeStockNumber(Integer stockNumber) {
        this.stockNumber = stockNumber;
    }


    public void changeMdPick(ProductMdPick mdPick) {
        this.mdPick = mdPick;
    }

    public void changeCategory(Category category) {
        this.category = category;
    }

}
