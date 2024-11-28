package br.com.nexus.goat.controllers;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerAddressRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;

import br.com.nexus.goat.dto.PaymentDTO;

@RestController
@RequestMapping("/process_payment")
public class PaymentController {

    @Value("${access.token}")
    private String accessToken;

    @PostMapping("/card")
    public ResponseEntity<Payment> payment(@RequestBody PaymentDTO request) {
        Map<String, String> customHeaders = new HashMap<>();
        customHeaders.put("x-idempotency-key", UUID.randomUUID().toString());

        MPRequestOptions requestOptions = MPRequestOptions.builder()
                .customHeaders(customHeaders)
                .build();

        MercadoPagoConfig.setAccessToken(accessToken);

        PaymentClient client = new PaymentClient();

        PaymentCreateRequest paymentCreateRequest = PaymentCreateRequest.builder()
                .transactionAmount(request.getTransaction_amount())
                .token(request.getToken())
                .description(request.getDescription())
                .installments(request.getInstallments())
                .paymentMethodId(request.getPayment_method_id())
                .payer(
                        PaymentPayerRequest.builder()
                                .email(request.getPayer().getEmail())
                                .firstName(request.getPayer().getFirst_name())
                                .identification(
                                        IdentificationRequest.builder()
                                                .type(request.getPayer().getIdentification().getType())
                                                .number(request.getPayer().getIdentification().getNumber())
                                                .build())
                                .build())
                .build();
        try {
            Payment payment = client.create(paymentCreateRequest, requestOptions);
            return ResponseEntity.ok().body(payment);
        } catch (MPApiException ex) {
            System.err.printf(
                    "MercadoPago Error. Status: %s, Content: %s%n",
                    ex.getApiResponse().getStatusCode(), ex.getApiResponse().getContent());
            return ResponseEntity.status(ex.getStatusCode()).build();
        } catch (MPException ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/ticket")
    public ResponseEntity<?> ticket(@RequestBody PaymentDTO request) {
        Map<String, String> customHeaders = new HashMap<>();
        customHeaders.put("x-idempotency-key", UUID.randomUUID().toString());

        MPRequestOptions requestOptions = MPRequestOptions.builder()
                .customHeaders(customHeaders)
                .build();

        MercadoPagoConfig.setAccessToken(accessToken);

        PaymentClient client = new PaymentClient();

        LocalDate today = LocalDate.now();
        LocalDate expirationDate = today.plusDays(1); // Inicializa com 1 dia à frente

        // Adiciona 3 dias úteis
        int addedDays = 0;
        while (addedDays < 3) {
            expirationDate = expirationDate.plusDays(1);
            // Se o dia não for sábado (6) ou domingo (7), incrementa
            if (expirationDate.getDayOfWeek() != DayOfWeek.SATURDAY
                    && expirationDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
                addedDays++;
            }
        }

        // Converte para OffsetDateTime (por exemplo, com o fuso horário UTC)
        OffsetDateTime offsetDateTime = expirationDate.atStartOfDay().atOffset(ZoneOffset.UTC);

        PaymentCreateRequest paymentCreateRequest = PaymentCreateRequest.builder()
                .transactionAmount(request.getTransaction_amount())
                .description(request.getDescription())
                .paymentMethodId(request.getPayment_method_id())
                .dateOfExpiration(offsetDateTime)
                .payer(
                        PaymentPayerRequest.builder()
                                .email(request.getPayer().getEmail())
                                .firstName(request.getPayer().getFirst_name())
                                .lastName(request.getPayer().getLast_name())
                                .address(
                                        PaymentPayerAddressRequest.builder()
                                                .neighborhood(request.getPayer().getAddress().getNeighborhood())
                                                .city(request.getPayer().getAddress().getCity())
                                                .federalUnit(request.getPayer().getAddress().getFederal_unit())
                                                .zipCode(request.getPayer().getAddress().getZip_code())
                                                .streetName(request.getPayer().getAddress().getStreet_name())
                                                .streetNumber(request.getPayer().getAddress().getStreet_number())
                                                .build())
                                .identification(
                                        IdentificationRequest.builder()
                                                .type(request.getPayer().getIdentification().getType())
                                                .number(request.getPayer().getIdentification().getNumber())
                                                .build())
                                .build())
                .build();
        try {
            Payment payment = client.create(paymentCreateRequest, requestOptions);
            return ResponseEntity.ok().body(payment);
        } catch (MPApiException ex) {
            System.err.printf(
                    "MercadoPago Error. Status: %s, Content: %s%n",
                    ex.getApiResponse().getStatusCode(), ex.getApiResponse().getContent());
            return ResponseEntity.status(ex.getStatusCode()).build();
        } catch (MPException ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/pix")
    public ResponseEntity<?> pix(@RequestBody PaymentDTO request) {
        Map<String, String> customHeaders = new HashMap<>();
        customHeaders.put("x-idempotency-key", UUID.randomUUID().toString());

        MPRequestOptions requestOptions = MPRequestOptions.builder()
                .customHeaders(customHeaders)
                .build();

        MercadoPagoConfig.setAccessToken(accessToken);

        PaymentClient client = new PaymentClient();

        PaymentCreateRequest paymentCreateRequest = PaymentCreateRequest.builder()
                .transactionAmount(request.getTransaction_amount())
                .paymentMethodId(request.getPayment_method_id())
                .payer(
                        PaymentPayerRequest.builder()
                                .email(request.getPayer().getEmail())
                                .build())
                .build();
        try {
            Payment payment = client.create(paymentCreateRequest, requestOptions);
            return ResponseEntity.ok().body(payment);
        } catch (MPApiException ex) {
            System.err.printf(
                    "MercadoPago Error. Status: %s, Content: %s%n",
                    ex.getApiResponse().getStatusCode(), ex.getApiResponse().getContent());
            return ResponseEntity.status(ex.getStatusCode()).build();
        } catch (MPException ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
