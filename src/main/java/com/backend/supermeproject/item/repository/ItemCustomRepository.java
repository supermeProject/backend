package com.backend.supermeproject.item.repository;

import com.backend.supermeproject.item.entity.Item;

public interface ItemCustomRepository {
    Item findItemById(Long itemId);
}
