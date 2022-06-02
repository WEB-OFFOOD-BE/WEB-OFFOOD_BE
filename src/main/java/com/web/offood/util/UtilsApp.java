package com.web.offood.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.offood.dto.base.ActionBaseRequest;
import com.web.offood.exception.ApiErrorCode;
import com.web.offood.exception.ApiException;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import java.beans.FeatureDescriptor;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UtilsApp {

    static PrivateKey privateKey = null;
    static PKCS8EncodedKeySpec keySpec = null;
    static ObjectMapper objectMapper = null;

    public static String getIp() {
        InetAddress localhost = null;
        try {
            localhost = InetAddress.getLocalHost();
            return localhost.getHostAddress().trim();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "Không tìm thấy IP";
    }

    public static String getClientIp(HttpServletRequest request) {

        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }

        return remoteAddr;
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(
                        propertyName -> {
                            try {
                                return wrappedSource.getPropertyValue(propertyName) == null;
                            } catch (Exception e) {
                                return false;
                            }
                        })
                .toArray(String[]::new);
    }

    public static BigDecimal roundNumber(BigDecimal number) {
        return number.setScale(5, RoundingMode.FLOOR);
    }

    public static String roundNumberAndConvertToString(BigDecimal number) {
        DecimalFormat df = new DecimalFormat("#,###.#####");
        return df.format(number.setScale(5, RoundingMode.FLOOR));
    }

    public static String getLanguage(HttpServletRequest request) {
        String headerName = request.getHeader("Language");
        if (headerName == null) {
            // vi
            return "vi";
        }
        return headerName;
    }

    public static int integerDigits(BigDecimal n) {
        n = n.stripTrailingZeros();
        return n.precision() - n.scale();
    }

    public static int integerScale(BigDecimal n) {
        n = n.stripTrailingZeros();
        return n.scale();
    }

    public String encrypt(String content, Key pubKey)
            throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] contentBytes = content.getBytes();
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        byte[] cipherContent = cipher.doFinal(contentBytes);
        String encoded = Base64.getEncoder().encodeToString(cipherContent);
        return encoded;
    }

    public String decrypt(String cipherContent, Key privKey)
            throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privKey);
        byte[] cipherContentBytes = Base64.getDecoder().decode(cipherContent.getBytes());
        byte[] decryptedContent = cipher.doFinal(cipherContentBytes);
        String decoded = new String(decryptedContent);
        return decoded;
    }

    public static PrivateKey getPrivateKey(String base64PrivateKey) {
        //        Security.addProvider(new BouncyCastleProvider());
        //        if (keySpec == null) {
        keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey.getBytes()));
        //        }
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            assert keyFactory != null;
            return keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] encrypt(String data, String publicKey)
            throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException,
            NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
        return cipher.doFinal(data.getBytes());
    }

    public static RSAPublicKey getPublicKey(String base64PublicKey) {
        PublicKey publicKey = null;
        try {
            X509EncodedKeySpec keySpec =
                    new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
            return (RSAPublicKey) publicKey;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String data, String base64Key) throws Exception {
        privateKey = getPrivateKey(base64Key);
        return decrypt(Base64.getDecoder().decode(data.getBytes()), privateKey);
    }

    public static String decrypt(byte[] data, PrivateKey privateKey)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(data));
    }

    public static <T> T toTypeRef(
            ActionBaseRequest<Object> actionBaseRequest, TypeReference<T> type) {
        initMapper();
        try {
            return objectMapper.convertValue(actionBaseRequest.getParamObject(), type);
        } catch (Exception ex) {
            // Object param invalid - can not convert object
            throw new ApiException(ApiErrorCode.INPUT_INVALID);
        }
    }

    public static <T> T StringToTypeRef(String jsonString, TypeReference<T> type) {
        initMapper();
        try {
            return objectMapper.readValue(jsonString, type);
        } catch (Exception ex) {
            // Object param invalid - can not convert object
            throw new ApiException(ApiErrorCode.INPUT_INVALID);
        }
    }

    public static String toJson(Object object) {
        initMapper();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void initMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        }
    }

    public static Float roundUpper(double a) {
        return (float) Math.round(a * 100) / 100;
    }

    public static <T> boolean isListEmpty(List<T> tList) {
        return tList == null || tList.size() == 0;
    }


    @SafeVarargs
    public static <T> Predicate<T> distinctByKeys(final Function<? super T, ?>... keyExtractors) {
        final Map<List<?>, Boolean> seen = new ConcurrentHashMap<>();

        return t ->
        {
            final List<?> keys = Arrays.stream(keyExtractors)
                    .map(ke -> ke.apply(t))
                    .collect(Collectors.toList());

            return seen.putIfAbsent(keys, Boolean.TRUE) == null;
        };
    }

    public static int getRandomInt(Integer begin, Integer end) {
        return ThreadLocalRandom.current().nextInt(begin, end);
    }
}
