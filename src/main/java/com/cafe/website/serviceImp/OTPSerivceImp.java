package com.cafe.website.serviceImp;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cafe.website.service.OTPService;

@Service
public class OTPSerivceImp implements OTPService {
	private CacheManager cacheManager;
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImp.class);

	public OTPSerivceImp(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	@Cacheable(value = "otpCache", key = "#email")
	@Override
	public String generateAndStoreOtp(String email) {
		String otp = generateUniqueOtp(email.toLowerCase(), "otpCache");
		
		return otp;
	}

	@Cacheable(value = "session", key = "#otp")
	@Override
	public String generateAndStoreAnotherData(String email) {
		String data = generateUniqueOtp(email.toLowerCase(), "session");
		return data;
	}

	@Cacheable(value = "otpCache", key = "#email")
	@Override
	public String getOtpByEmail(String email) {
		Cache otpCache = cacheManager.getCache("otpCache");
		if (otpCache != null) {
			Cache.ValueWrapper valueWrapper = otpCache.get(email.toLowerCase());
			if (valueWrapper != null) {
				return (String) valueWrapper.get();
			}
		}
		return null;
	}

	@Cacheable(value = "session", key = "#otp")
	@Override
	public String getOtpBySession(String email) {
		Cache otpCache = cacheManager.getCache("session");
		if (otpCache != null) {
			Cache.ValueWrapper valueWrapper = otpCache.get(email.toLowerCase());
			if (valueWrapper != null) {
				return (String) valueWrapper.get();
			}
		}
		return null;
	}

	private String generateUniqueOtp(String email, String nameCache) {
		Cache otpCache = cacheManager.getCache(nameCache);
		String otp;
		do {
			otp = RandomStringUtils.randomAlphanumeric(6);
		} while (isOtpExists(otpCache, otp));
		otpCache.put(email.toLowerCase(), otp);

		return otp;
	}

	private boolean isOtpExists(Cache otpCache, String otp) {
		Cache.ValueWrapper valueWrapper = otpCache.get(otp);
		return valueWrapper != null;
	}

	@Override
	public void clearCache(String name, String key) {
		Cache cache = cacheManager.getCache(name);
		if (cache != null) {
			cache.evict(key);
		}
	}
}
