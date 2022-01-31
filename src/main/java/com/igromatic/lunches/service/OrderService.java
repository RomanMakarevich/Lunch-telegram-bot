package com.igromatic.lunches.service;

import com.igromatic.lunches.controller.CommandProcessor;
import com.igromatic.lunches.controller.MenuController;
import com.igromatic.lunches.dto.OrderDTO;
import com.igromatic.lunches.dto.RequestDTO;
import com.igromatic.lunches.dto.ResponseDTO;
import com.igromatic.lunches.entity.MenuEntity;
import com.igromatic.lunches.entity.PreorderEntity;
import com.igromatic.lunches.entity.ProductEntity;
import com.igromatic.lunches.entity.UserEntity;
import com.igromatic.lunches.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@Transactional
@Service
public class OrderService {

    private final MenuRepository menuRepository;
    private final UserRepository userRepository;
    private final PreorderRepository preorderRepository;
    private final ProductRepository productRepository;
    private String logPath;
    static Logger LOGGER;

    @Autowired
    public OrderService(MenuRepository menuRepository, UserRepository userRepository,
                        PreorderRepository preorderRepository, ProductRepository productRepository,
                        @Value("${spring.logpath}") String logpath) {
        this.menuRepository = menuRepository;
        this.userRepository = userRepository;
        this.preorderRepository = preorderRepository;
        this.productRepository = productRepository;
        this.logPath = logpath;
        initLogger();
    }

    private void initLogger() {
        try (FileInputStream fis = new FileInputStream(logPath)) {
            LogManager.getLogManager().readConfiguration(fis);
            LOGGER = Logger.getLogger(MenuController.class.getName());
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
    }

    @Transactional
    public ResponseDTO order(RequestDTO requestDTO, Boolean isAll) {
        ResponseDTO responseDTO = new ResponseDTO();
        List<MenuEntity> menu = (isAll) ? menuRepository.findAll() : menuRepository.findAllByStatus("ACTIVE");
        UserEntity user = userRepository.findByChatId(requestDTO.getChatID());
        List<ProductEntity> products = productRepository.findLastProductByMenuId(menu.get(Integer.parseInt(requestDTO.getText().substring(1)) - 1).getId());
        ProductEntity productEntity = products.get(products.size() - 1);

        PreorderEntity preorderEntity = preorderRepository.findByCreatedBetweenAndId(LocalDate.now(), userRepository.findByChatId(requestDTO.getChatID()).getId());

        if (preorderEntity != null) {
            LOGGER.info("Add old order");

            preorderEntity.getProducts().add(productEntity);
            user.setMoney(user.getMoney().subtract(menu.get(Integer.parseInt(requestDTO.getText().substring(1)) - 1).getCost()));
        } else {
            LOGGER.info("Save new order");
            preorderEntity = new PreorderEntity();
            preorderEntity.setCreated(LocalDate.now());
            preorderEntity.setUser_id(user);
            ArrayList<ProductEntity> product = new ArrayList<>();
            product.add(productEntity);
            preorderEntity.setProducts(product);
            user.setMoney(user.getMoney().subtract(menu.get(Integer.parseInt(requestDTO.getText().substring(1)) - 1).getCost()));
        }

        LOGGER.info("SAVE" + preorderEntity.toString());
        preorderRepository.save(preorderEntity);
        String foodName = "";
        for (int i = 0; i < preorderEntity.getProducts().size(); i++) {
            foodName += ((i != 0) ? " " : "") + preorderEntity.getProducts().get(i).getProductName();
            foodName += " (" + preorderEntity.getProducts().get(i).getCost() + ((i != preorderEntity.getProducts().size() - 1) ? ")," : ").");
        }
        foodName += " Остаток: " + user.getMoney() + "p.";

        foodName += "\n" + "\n/del";

        responseDTO.setResponse(foodName);
        responseDTO.setChatId(requestDTO.getChatID());

        LOGGER.info("RETURN" + responseDTO.toString());
        return responseDTO;
    }

    public ArrayList<OrderDTO> getOrdersCreatedBetween(String firstData, String secondData) {


        ArrayList<OrderDTO> ordersDTO = new ArrayList<>();
        List<PreorderEntity> ordersEntity = preorderRepository.findByCreatedBetween(LocalDate.parse(firstData), LocalDate.parse(secondData));
        for (int i = 0; i < ordersEntity.size(); i++) {
            ordersDTO.add(OrderDTO.toDTO(ordersEntity.get(i)));
        }
        return ordersDTO;
    }

    public ArrayList<OrderDTO> getOrdersCreatedBetweenById(Long userId, String firstData, String secondData) {
        ArrayList<OrderDTO> ordersDTO = new ArrayList<>();
        List<PreorderEntity> ordersEntity = preorderRepository.findByCreatedBetweenAndIdList(LocalDate.parse(firstData), LocalDate.parse(secondData), userId);
        for (int i = 0; i < ordersEntity.size(); i++) {
            ordersDTO.add(OrderDTO.toDTO(ordersEntity.get(i)));
        }
        return ordersDTO;
    }

    @Transactional
    public OrderDTO addProductToOrder(Long orderId, Long productId) {
        PreorderEntity preorderEntity = preorderRepository.getById(orderId);

        ProductEntity productEntity = productRepository.findByCreatedAndProductName(menuRepository.getById(productId).getProductName());
        if (productEntity == null) {
            productRepository.save(ProductEntity.menuToProduct(menuRepository.getById(productId)));
            productEntity = productRepository.findByCreatedAndProductName(menuRepository.getById(productId).getProductName());
        }

        preorderEntity.getProducts().add(productEntity);

        UserEntity userEntity = userRepository.getById(preorderEntity.getUser_id().getId());
        userEntity.setMoney(userEntity.getMoney().subtract(menuRepository.getById(productId).getCost()));

        return OrderDTO.toDTO(preorderRepository.getById(orderId));
    }

    public OrderDTO delProductFromAnOrder(Long orderId, Long productId) {
        PreorderEntity preorderEntity = preorderRepository.getById(orderId);
        ProductEntity productEntity = productRepository.getById(productId);
        preorderEntity.getProducts().remove(productEntity);

        UserEntity userEntity = userRepository.getById(preorderEntity.getUser_id().getId());
        userEntity.setMoney(userEntity.getMoney().add(productEntity.getCost()));

        return OrderDTO.toDTO(preorderRepository.getById(orderId));
    }

    public BigDecimal getSumOfOrders(String firstData, String secondData) {
        List<PreorderEntity> ordersEntity = preorderRepository.findByCreatedBetween(LocalDate.parse(firstData),
                LocalDate.parse(secondData));

        return sum(ordersEntity);
    }

    public BigDecimal getSumOfOrdersByUser(String firstData, String secondData, Long userId) {
        List<PreorderEntity> ordersEntity = preorderRepository.findByCreatedBetweenAndIdList(LocalDate.parse(firstData),
                LocalDate.parse(secondData), userId);

        return sum(ordersEntity);
    }

    public HashMap<String, Integer> getSortTodayOrders() {
        ArrayList<OrderDTO> ordersDTO = getOrdersCreatedBetween(LocalDate.now().toString(), LocalDate.now().toString());

        HashMap<String, Integer> sortOrders = new HashMap<>();

        for (int i = 0; i < ordersDTO.size(); i++) {
            for (int j = 0; j < ordersDTO.get(i).getProducts().size(); j++) {
                if (sortOrders.containsKey(ordersDTO.get(i).getProducts().get(j).getProduct())) {
                    sortOrders.put(ordersDTO.get(i).getProducts().get(j).getProduct(),
                            sortOrders.get(ordersDTO.get(i).getProducts().get(j).getProduct()) + 1);
                } else {
                    sortOrders.put(ordersDTO.get(i).getProducts().get(j).getProduct(), 1);
                }
            }
        }
        return sortOrders;
    }

    private BigDecimal sum(List<PreorderEntity> ordersEntity) {
        BigDecimal sum = new BigDecimal(0.00);
        sum.setScale(2);

        for (int i = 0; i < ordersEntity.size(); i++) {
            for (int j = 0; j < ordersEntity.get(i).getProducts().size(); j++) {
                sum = sum.add(ordersEntity.get(i).getProducts().get(j).getCost());

            }
        }
        return sum;
    }
}
