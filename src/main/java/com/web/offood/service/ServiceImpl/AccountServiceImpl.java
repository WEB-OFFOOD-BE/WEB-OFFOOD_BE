package com.web.offood.service.ServiceImpl;

import com.web.offood.dto.account.SignInRequest;
import com.web.offood.dto.account.SignInResponse;
import com.web.offood.dto.constant.status.OfficeStatus;
import com.web.offood.dto.constant.status.RestaurantStatus;
import com.web.offood.dto.email.EmailRequest;
import com.web.offood.dto.email.OTPOfficeRequest;
import com.web.offood.dto.email.OTPRestaurantRequest;
import com.web.offood.dto.user.RegisterRequest;
import com.web.offood.entity.account.Account;
import com.web.offood.entity.account.AccountRoles;
import com.web.offood.entity.office.OfficeInfo;
import com.web.offood.entity.restaurant.DiskInfo;
import com.web.offood.entity.restaurant.Menu;
import com.web.offood.entity.restaurant.MenuDetail;
import com.web.offood.entity.restaurant.RestaurantInfo;
import com.web.offood.exception.ApiErrorCode;
import com.web.offood.exception.ApiException;
import com.web.offood.redis.RedisKey;
import com.web.offood.security.JwtTokenProvider;
import com.web.offood.service.AccountService;
import com.web.offood.service.BaseService;
import com.web.offood.util.SendMailUtil;
import com.web.offood.util.TextUtils;
import com.web.offood.util.TimeUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.mail.EmailException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl extends BaseService implements AccountService {
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    public SignInResponse signIn(SignInRequest signInRequest) {
        signInRequest.validSignIn();
        var account = accountRepository.findByUsername(signInRequest.getUsername());
        if (account == null) {
            throw new ApiException(ApiErrorCode.LOGIN_FAILED);
        }
//        var restaurant = restaurantRepository.findByAccountIdAndStatusId(account.getId(), RestaurantStatus.ACTIVE.getValue());
//        if (restaurant == null) {
//            throw new ApiException(ApiErrorCode.ACCOUNT_WAITING_CONFIRMATION);
//        }
        var restaurant = restaurantRepository.findByAccountId(account.getId());

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword()));
            String accessToken = jwtTokenProvider.createToken(signInRequest.getUsername(), accountRepository.findByUsername(signInRequest.getUsername()).getAccountRoles());

        var response = SignInResponse.builder()
                .accessToken(accessToken)
                .accountRoles(account.getAccountRoles().toString())
                .restaurantInfo(restaurant)
                .email(account.getEmail())
                .username(account.getUsername())
                .build();
        return response;
        } catch (AuthenticationException e) {
            throw new ApiException(ApiErrorCode.LOGIN_FAILED);
        }
    }


    @Override
    public String signUp(RegisterRequest registerRequest) {
        registerRequest.validate();
        String email = registerRequest.getEmail().toLowerCase();
        String username = registerRequest.getUsername().toLowerCase();
        if (!TextUtils.isValidEmail(email)) {
            throw new ApiException(ApiErrorCode.INVALID_EMAIL);
        }

        ApiException.validPassword(registerRequest.getPassword());

        if (accountRepository.existsByUsername(registerRequest.getUsername())) {
            throw new ApiException(ApiErrorCode.USERNAME_IN_USE);
        }

        if (accountRepository.existsByEmail(registerRequest.getEmail())) {
            throw new ApiException(ApiErrorCode.EMAIL_IN_USE);
        }


        registerRequest.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        Account account = Account.builder()
                .username(username)
                .email(email)
                .password(registerRequest.getPassword())
                .accountRoles(registerRequest.getAccountRoles())
                .build();
        accountRepository.save(account);

        if (registerRequest.getAccountRoles().get(0) == AccountRoles.ROLE_ADMIN) {
            return "OK ADMIN";
        }

        if (registerRequest.getAccountRoles().get(0).toString().equals("ROLE_RESTAURANT")) {
            RestaurantInfo restaurantInfo = RestaurantInfo.builder()
                    .name(registerRequest.getName())
                    .accountId(account.getId())
                    .statusId(RestaurantStatus.ACTIVE_BUT_UNVERIFIED.getValue())
                    .avatarUrl("")
                    .createDate(TimeUtils.convertToTimestamp())
                    .businessCode("0")
                    .description("Mô tả")
                    .ratePoint(0.0)
                    .phoneNumber("0")
                    .build();
            restaurantRepository.save(restaurantInfo);
        }
        if (registerRequest.getAccountRoles().get(0) == AccountRoles.ROLE_OFFICE) {
            OfficeInfo officeInfo = OfficeInfo.builder()
                    .name(registerRequest.getName())
                    .accountId(account.getId())
                    .statusId(OfficeStatus.ACTIVE_BUT_UNVERIFIED.getValue())
                    .build();
            officeRepository.save(officeInfo);
        }

        //gủi OTP
        try {
            EmailRequest emailRequest = new EmailRequest();
            var otp = String.valueOf(randomOTP(6));
            emailRequest.setToTarget(account.getEmail());
            emailRequest.setSubject("Mã OTP của bạn!");
            emailRequest.setContent("OTP: " + otp);
            SendMailUtil.sendMail(emailRequest);
            redisComponent.set(RedisKey.OTP.name(), account.getEmail(), otp);
        } catch (EmailException e) {
            throw new ApiException(ApiErrorCode.ERROR_SEND_MAIL);
        }

        return "OK";
    }

    @Override
    public String verifyAccountRestaurant(OTPRestaurantRequest otpRequest) {
        otpRequest.validate();
        var account = accountRepository.findByEmail(otpRequest.getEmail());

        if (account == null) {
            throw new ApiException(ApiErrorCode.EMAIL_NOT_EMPTY);
        }

        var restaurants = restaurantRepository.findByPhoneNumber(otpRequest.getPhoneNumber());
        if (restaurants != null) {
            throw new ApiException(ApiErrorCode.PHONE_NUMBER_ALREADY_USE);
        }


        String otp = redisComponent.get(RedisKey.OTP.name(), otpRequest.getEmail());
        if (!otpRequest.getOtp().equals(otp)) {
            throw new ApiException(ApiErrorCode.OTP_NOT_EMPTY);
        }

        var restaurant =
                restaurantRepository.findByAccountIdAndStatusId(account.getId(), RestaurantStatus.ACTIVE_BUT_UNVERIFIED.getValue());
        if (restaurant == null) {
            throw new ApiException(ApiErrorCode.OBJECT_NOT_FOUND);
        }

//        restaurant = RestaurantInfo.builder()
//                .phoneNumber(otpRequest.getPhoneNumber())
//                .description(otpRequest.getDescription())
//                .address(otpRequest.getAddress())
//                .statusId(RestaurantStatus.WAITING_CONFIRMATION.getValue())
//                .build();
        restaurant.setPhoneNumber(otpRequest.getPhoneNumber())
                .setBusinessCode(otpRequest.getCode())
                .setStatusId(RestaurantStatus.WAITING_CONFIRMATION.getValue())
                .setAddress(otpRequest.getAddress())
                .setDescription(otpRequest.getDescription());

        restaurantRepository.save(restaurant);

        var menu = Menu.builder()
                .restaurantId(restaurant.getId())
                .isActive(false)
                .isUsed(false)
                .build();
        menuRepository.save(menu);

        List<DiskInfo> diskInfos = new ArrayList<>();

        var disk1 = DiskInfo.builder()
                .createDate(TimeUtils.convertToTimestamp())
                .foodName(otpRequest.getNameDisk_1())
                .imageUrl(otpRequest.getImgURL_1())
                .isActive(true)
                .price(otpRequest.getPriceDisk_1())
                .build();

        var disk2 = DiskInfo.builder()
                .createDate(TimeUtils.convertToTimestamp())
                .foodName(otpRequest.getNameDisk_2())
                .imageUrl(otpRequest.getImgURL_2())
                .isActive(true)
                .price(otpRequest.getPriceDisk_2())
                .build();

        var disk3 = DiskInfo.builder()
                .createDate(TimeUtils.convertToTimestamp())
                .foodName(otpRequest.getNameDisk_3())
                .imageUrl(otpRequest.getImgURL_3())
                .isActive(true)
                .price(otpRequest.getPriceDisk_3())
                .build();

        var disk4 = DiskInfo.builder()
                .createDate(TimeUtils.convertToTimestamp())
                .foodName(otpRequest.getNameDisk_4())
                .imageUrl(otpRequest.getImgURL_4())
                .isActive(true)
                .price(otpRequest.getPriceDisk_4())
                .build();

        var disk5 = DiskInfo.builder()
                .createDate(TimeUtils.convertToTimestamp())
                .foodName(otpRequest.getNameDisk_5())
                .imageUrl(otpRequest.getImgURL_5())
                .isActive(true)
                .price(otpRequest.getPriceDisk_5())
                .build();

        diskInfos.add(disk1);
        diskInfos.add(disk2);
        diskInfos.add(disk3);
        diskInfos.add(disk4);
        diskInfos.add(disk5);

        diskInfoRepository.saveAll(diskInfos);

        var menuDetail1 = MenuDetail.builder()
                .menuId(menu.getId())
                .diskId(disk1.getId())
                .build();

        var menuDetail2 = MenuDetail.builder()
                .menuId(menu.getId())
                .diskId(disk2.getId())
                .build();

        var menuDetail3 = MenuDetail.builder()
                .menuId(menu.getId())
                .diskId(disk3.getId())
                .build();

        var menuDetail4 = MenuDetail.builder()
                .menuId(menu.getId())
                .diskId(disk4.getId())
                .build();

        var menuDetail5 = MenuDetail.builder()
                .menuId(menu.getId())
                .diskId(disk5.getId())
                .build();

        List<MenuDetail> menuDetails = new ArrayList<>();
        menuDetails.add(menuDetail1);
        menuDetails.add(menuDetail2);
        menuDetails.add(menuDetail3);
        menuDetails.add(menuDetail4);
        menuDetails.add(menuDetail5);

        menuDetailRepository.saveAll(menuDetails);

        try {
            EmailRequest emailRequest = new EmailRequest();
            emailRequest.setToTarget(otpRequest.getEmail());
            emailRequest.setSubject("Tạo tài khoản thành công!");
            emailRequest.setContent("Bạn đã xác minh tài khoản nhà hàng, bạn vui lòng chờ quản trị viên xác nhận!");
            SendMailUtil.sendMail(emailRequest);
        } catch (EmailException e) {
            throw new ApiException(ApiErrorCode.ACTION_INVALID);
        }

        return "OK";
    }

    @Override
    public String verifyAccountOffice(OTPOfficeRequest otpRequest) {
        otpRequest.validate();
        var account = accountRepository.findByEmail(otpRequest.getEmail());

        if (account == null) {
            throw new ApiException(ApiErrorCode.EMAIL_NOT_EMPTY);
        }

        var officeInfos = officeRepository.findAll();
        for (int i = 0; i <= officeInfos.size(); i++) {
            if (officeInfos.get(i).getPhoneNumber().equals(otpRequest.getPhoneNumber())) {
                throw new ApiException(ApiErrorCode.PHONE_NUMBER_ALREADY_USE);
            }
        }

        String otp = redisComponent.get(RedisKey.OTP.name(), otpRequest.getEmail());
        if (!otpRequest.getOtp().equals(otp)) {
            throw new ApiException(ApiErrorCode.OTP_NOT_EMPTY);
        }

        var office =
                officeRepository.findByAccountIdAndStatusId(account.getId(), RestaurantStatus.ACTIVE_BUT_UNVERIFIED.getValue());
        if (office != null) {
            throw new ApiException(ApiErrorCode.OBJECT_NOT_FOUND);
        }

        office = OfficeInfo.builder()
                .phoneNumber(otpRequest.getPhoneNumber())
                .description(otpRequest.getDescription())
                .address(otpRequest.getAddress())
                .statusId(RestaurantStatus.WAITING_CONFIRMATION.getValue())
                .build();
        officeRepository.save(office);

        try {
            EmailRequest emailRequest = new EmailRequest();
            emailRequest.setToTarget(otpRequest.getEmail());
            emailRequest.setSubject("Tạo tài khoản thành công!");
            emailRequest.setContent("Bạn đã xác minh tài khoản nhà hàng, bạn vui lòng chờ quản trị viên xác nhận!");
            SendMailUtil.sendMail(emailRequest);
        } catch (EmailException e) {
            throw new ApiException(ApiErrorCode.ACTION_INVALID);
        }

        return "OK";
    }
}
