package com.backend.supermeproject.item.service;

import com.backend.supermeproject.global.exception.BusinessException;
import com.backend.supermeproject.global.exception.ErrorCode;
import com.backend.supermeproject.image.ImageEntity.ItemImage;
import com.backend.supermeproject.image.repository.ItemImageRepository;
import com.backend.supermeproject.image.service.ImageUploadUtil;
import com.backend.supermeproject.item.dto.*;
import com.backend.supermeproject.item.entity.Item;
import com.backend.supermeproject.item.entity.Size;
import com.backend.supermeproject.item.entity.Variant;
import com.backend.supermeproject.item.repository.ItemRepository;
import com.backend.supermeproject.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemImageRepository itemImageRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    //업로드
    @Transactional
    public String uploadItem(List<MultipartFile> files, Long user, ProductDTO request) {


        List<ItemImage> images = ImageUploadUtil.uploadImages(files, user, itemImageRepository);

        // 아이템 생성
        Item item = Item.builder()
                .productName(request.productName())
                .price(request.price())
                .category(request.category())
                .description(request.description())
                .memberId(user)
                .image(images)
                .build();


        for (ItemImage image : images) {
            image.setItem(item);
        }


        // 이미지 저장
        List<Variant> variants = request.variants().stream()
                .map(variantDTO -> {
                    Variant variant = Variant.builder()
                            .color(variantDTO.color())
                            .item(item)
                            .build();

                    List<Size> sizes = variantDTO.sizes().stream()
                            .map(sizeDTO -> Size.builder()
                                    .size(sizeDTO.size())
                                    .stock(sizeDTO.stock())
                                    .variant(variant)
                                    .build())
                            .collect(Collectors.toList());

                    variant.setSizes(sizes);
                    return variant;
                })
                .collect(Collectors.toList());

        // 아이템에 변형(Variant) 추가
        item.setVariants(variants);

        // 아이템 저장
        itemRepository.save(item);

        return "Item upload";
    }

    public List<AllItemResponse> getAllItem() {
        List<Item> items = itemRepository.findAll();

        return items.stream()
                .map(item -> new AllItemResponse(
                        item.getItemId(),
                        item.getProductName(),
                        item.getDescription(),
                        item.getPrice(),
                        item.getCategory(),
                        item.getImage().stream().map(ItemImage::getFilePath).toList()
                ))
                .toList();
    }

    public ItemResponse getIdItem(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));

        return econvertToItemResponse(item);

    }

    //상세조회
    private ItemResponse econvertToItemResponse(Item item) {
        List<VariantResponse> variantResponses = item.getVariants().stream()
                .map(this::convertToVariantResponse)
                .toList();

        return new ItemResponse(
                item.getProductName(),
                item.getPrice(),
                item.getCategory(),
                item.getDescription(),
                item.getImage().stream().map(image -> image.getFilePath()).toList(),
                variantResponses
        );
    }

    private VariantResponse convertToVariantResponse(Variant variant) {
        List<SizeResponse> sizeResponses = variant.getSizes().stream()
                .map(this::convertToSizeResponse)
                .toList();

        return new VariantResponse(
                variant.getColor(),
                sizeResponses
        );
    }

    private SizeResponse convertToSizeResponse(Size size) {
        return new SizeResponse(
                size.getSize(),
                size.getStock()
        );
    }
}
