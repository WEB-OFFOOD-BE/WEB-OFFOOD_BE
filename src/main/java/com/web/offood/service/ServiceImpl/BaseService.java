package com.web.offood.service.ServiceImpl;

import com.web.offood.redis.RedisComponent;
import com.web.offood.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class BaseService {
    @Autowired
    protected AddressRepository addressRepository;
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

}
