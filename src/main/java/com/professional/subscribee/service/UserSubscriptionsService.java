package com.professional.subscribee.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.professional.subscribee.jwt.JwtTokenProvider;
import com.professional.subscribee.model.Subscription;
import com.professional.subscribee.model.User;
import com.professional.subscribee.model.UserSubscriptionConfirm;
import com.professional.subscribee.model.UserSubscriptions;
import com.professional.subscribee.repository.SubscriptionRepo;
import com.professional.subscribee.repository.UserRepo;
import com.professional.subscribee.repository.UserSubscriptionConfirmRepo;
import com.professional.subscribee.repository.UserSubscriptionsRepo;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.image.BufferedImage;
import java.time.OffsetDateTime;
import java.time.Period;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@Slf4j
public class UserSubscriptionsService {
    private final UserRepo userRepo;
    private final SubscriptionRepo subscriptionRepo;
    private final UserSubscriptionsRepo userSubscriptionsRepo;
    private final UserSubscriptionConfirmRepo userSubscriptionConfirmRepo;
    private final JwtTokenProvider jwtTokenProvider;
    private final Long expireTimeMinutes;

    public UserSubscriptionsService(UserRepo userRepo,
                                    SubscriptionRepo subscriptionRepo,
                                    UserSubscriptionsRepo userSubscriptionsRepo,
                                    UserSubscriptionConfirmRepo userSubscriptionConfirmRepo,
                                    JwtTokenProvider jwtTokenProvider,
                                    @Value("${confirmSubscriptionTimeMinutes}") Long expireTimeMinutes) {
        this.userRepo = userRepo;
        this.subscriptionRepo = subscriptionRepo;
        this.userSubscriptionsRepo = userSubscriptionsRepo;
        this.userSubscriptionConfirmRepo = userSubscriptionConfirmRepo;
        this.expireTimeMinutes = expireTimeMinutes;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional
    public boolean subscribeUser(String accessToken, long subscriptionId) {
        User user = userRepo.findByPhone(jwtTokenProvider.getPhoneFromJwt(accessToken));
        Subscription subscription = subscriptionRepo.findById(subscriptionId).orElse(null);
        if (user == null || subscription == null)
            throw new RuntimeException("User or subscription(" + subscriptionId + ") not found");

        userSubscriptionsRepo.save(UserSubscriptions.builder()
                .user(user)
                .subscription(subscription)
                .cupsQty(subscription.getInitCupsQty())
                .startDate(OffsetDateTime.now())
                .endDate(OffsetDateTime.now().plus(Period.ofDays(subscription.getPeriodDays())).truncatedTo(DAYS))
                .build()
        );
        log.trace("User(phone: {}) subscribed(subscription id:{}) created", user.getPhone(), subscriptionId);

        userSubscriptionConfirmRepo.save(UserSubscriptionConfirm.builder()
                .user(user)
                .confirmingCafe(subscription.getCafe())
                .startConfirmDate(OffsetDateTime.now())
                .endConfirmDate(OffsetDateTime.now().plusMinutes(expireTimeMinutes))
                .build()
        );
        log.trace("User(phone: {}) cafeId for confirm: {}", user.getPhone(), subscription.getCafe());

        return true;
    }

    public void generateQRCode(String token, Long userSubscriptionId) throws WriterException {
        JSONObject json = new JSONObject();
        json.put("token", token);
        json.put("userSubscriptionId", userSubscriptionId);


        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix matrix = qrCodeWriter.encode(json.toString(), BarcodeFormat.QR_CODE, 100, 100);
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(matrix);
        //https://github.com/aryarasen/test/blob/dffa1f64e642b7646d069b2c4ae914a5fc04b7a9/spring-boot-modules/spring-boot-libraries/src/main/java/com/baeldung/barcodes/BarcodesController.java#L96
    }
}
