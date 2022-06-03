package com.web.offood.service;

import com.web.offood.redis.RedisComponent;
import com.web.offood.repository.*;
import com.web.offood.service.AccountService;
import com.web.offood.service.AdminService;
import com.web.offood.service.OfficeService;
import com.web.offood.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Lazy
public class BaseService {
    @Autowired
    protected DiskInfoRepository diskInfoRepository;
    @Autowired
    protected DiskTypeRepository diskTypeRepository;
    @Autowired
    protected MenuRepository menuRepository;
    @Autowired
    protected OfficeRepository officeRepository;
    @Autowired
    protected OfficeStatusRepository officeStatusRepository;
    @Autowired
    protected RestaurantDebtRepository restaurantDebtRepository;
    @Autowired
    protected RestaurantRepository restaurantRepository;
    @Autowired
    protected RestaurantStatusRepository restaurantStatusRepository;
    @Autowired
    protected StaffRepository staffRepository;
    @Autowired
    protected AccountRepository accountRepository;
    @Autowired
    protected RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    protected RedisComponent redisComponent;
    @Autowired
    protected MenuDetailRepository menuDetailRepository;
    @Autowired
    protected AccountService accountService;
    @Autowired
    protected AdminService adminService;
    @Autowired
    protected RestaurantService restaurantService;
    @Autowired
    protected OfficeService officeService;

    public char[] randomOTP(int length) {

        String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        String values = Capital_chars + numbers;
        Random random_method = new Random();

        char[] password = new char[length];

        for (int i = 0; i < length; i++) {
            password[i] =
                    values.charAt(random_method.nextInt(values.length()));
        }
        return password;
    }

}
