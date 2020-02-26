package com.coupon.user.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.coupon.auth.bean.PasswordResetToken;
import com.coupon.auth.bean.jpa.PasswordResetTokenEntity;
import com.coupon.exceptions.ResourceNotFoundException;
import com.coupon.service.mapper.ServiceMapper;
import com.coupon.user.repository.PasswordResetTokenRepository;
import com.coupon.user.service.PasswordResetTokenService;
import com.coupon.utils.RandomGenerator;

@Component("resetPasswordTokenService")
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {
    @Resource
    private PasswordResetTokenRepository passwordResetTokenJpaRepository;
    @Resource
    private ServiceMapper<PasswordResetToken, PasswordResetTokenEntity> passwordResetTokenServiceMapper;

    @Override
    public PasswordResetToken create(final String email) {
        PasswordResetTokenEntity passwordResetTokenEntity = new PasswordResetTokenEntity();
        passwordResetTokenEntity.setEmail(email);
        passwordResetTokenEntity.setToken(RandomGenerator.generateToken());

        PasswordResetTokenEntity multiplierEntitySaved = passwordResetTokenJpaRepository.save(passwordResetTokenEntity);
        return passwordResetTokenServiceMapper.mapEntityToDTO(multiplierEntitySaved, PasswordResetToken.class);
    }

    @Override
    public PasswordResetToken findByTokenAndEmail(final String token, final String email) {
        PasswordResetTokenEntity passwordResetTokenEntity = passwordResetTokenJpaRepository.findByTokenAndEmail(token, email).orElseThrow(
            () -> new ResourceNotFoundException("Token", "value", token)
        );

        return passwordResetTokenServiceMapper.mapEntityToDTO(passwordResetTokenEntity,PasswordResetToken.class);
    }

    @Override
    public PasswordResetToken findByToken(final String token) {
        PasswordResetTokenEntity passwordResetTokenEntity = passwordResetTokenJpaRepository.findByToken(token).orElseThrow(
            () -> new ResourceNotFoundException("Token", "value", token)
        );

        return passwordResetTokenServiceMapper.mapEntityToDTO(passwordResetTokenEntity, PasswordResetToken.class);
    }

    @Override
    public boolean existsByTokenAndEmail(final String token, final String email) {
        return passwordResetTokenJpaRepository.findByTokenAndEmail(token, email).isPresent();
    }

    @Override
    public void deleteToken(final String token) {
        passwordResetTokenJpaRepository.deleteByToken(token);
    }

}
