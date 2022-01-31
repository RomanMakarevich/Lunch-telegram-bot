package com.igromatic.lunches.service;

import com.igromatic.lunches.dto.MenuDTO;
import com.igromatic.lunches.entity.MenuEntity;
import com.igromatic.lunches.entity.ProductEntity;
import com.igromatic.lunches.repository.MenuRepository;
import com.igromatic.lunches.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final ProductRepository productRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository, ProductRepository productRepository) {
        this.menuRepository = menuRepository;
        this.productRepository = productRepository;
    }

    public MenuDTO addProduct(MenuDTO menuDTO) {
        menuRepository.save(MenuDTO.toEntity(menuDTO));

        return menuDTO;
    }

    public MenuDTO getById(Long id) {
        return MenuDTO.toDTO(menuRepository.getById(id));
    }

    public MenuDTO getByProductName(MenuDTO menuDTO) {
        return MenuDTO.toDTO(menuRepository.findByProductName(menuDTO.getProduct()));
    }

    public List<MenuDTO> getAll() {
        List<MenuDTO> products = new ArrayList<>();

        List<MenuEntity> productsEntity = menuRepository.findAll();

        for (int i = 0; i < productsEntity.size(); i++) {
            products.add(MenuDTO.toDTO(productsEntity.get(i)));
        }
        return products;
    }

    @Transactional
    public MenuDTO setCost(Long id, BigDecimal cost) {
        MenuEntity menuEntity = menuRepository.getById(id);
        menuEntity.setCost(cost);

        menuRepository.save(menuEntity);

        ProductEntity productEntity = ProductEntity.menuToProduct(menuEntity);
        productRepository.save(productEntity);

        return MenuDTO.toDTO(menuRepository.getById(id));
    }

    public MenuDTO setProductName(Long id, MenuDTO menuDTO) {
        MenuEntity menuEntity = menuRepository.getById(id);
        menuEntity.setProductName(menuDTO.getProduct());

        menuRepository.save(menuEntity);

        ProductEntity productEntity = ProductEntity.menuToProduct(menuEntity);
        productRepository.save(productEntity);

        return MenuDTO.toDTO(menuRepository.getById(id));
    }

    public MenuDTO setStatus(Long id) {
        MenuEntity menuEntity = menuRepository.getById(id);
        if (menuEntity.getStatus().equals("ACTIVE")) {
            menuEntity.setStatus("NOACTIVE");
        } else {
            menuEntity.setStatus("ACTIVE");
        }

        menuRepository.save(menuEntity);

        return MenuDTO.toDTO(menuRepository.getById(id));
    }

    public void deleteProduct(Long id) {
        menuRepository.deleteById(id);
    }
}
