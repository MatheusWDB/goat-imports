/* 
package br.com.nexus.goat.api;

import java.util.HashMap;
import java.util.Map;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;

public class MercadoPago {

  public static void main(String[] args) {
    Map<String, String> customHeaders = new HashMap<>();
    customHeaders.put("x-idempotency-key", "<SOME_UNIQUE_VALUE>");

    MPRequestOptions requestOptions = MPRequestOptions.builder()
        .customHeaders(customHeaders)
        .build();

    MercadoPagoConfig.setAccessToken("TEST-612797048898669-112213-9c2da1ff75140be92b7320eae8fb4edf-210822430");

    PaymentClient client = new PaymentClient();

    PaymentCreateRequest paymentCreateRequest = PaymentCreateRequest.builder()
        .transactionAmount(request.getTransactionAmount())
        .token(request.getToken())
        .description(request.getDescription())
        .installments(request.getInstallments())
        .paymentMethodId(request.getPaymentMethodId())
        .payer(
            PaymentPayerRequest.builder()
                .email(request.getPayer().getEmail())
                .firstName(request.getPayer().getFirstName())
                .identification(
                    IdentificationRequest.builder()
                        .type(request.getPayer().getIdentification().getType())
                        .number(request.getPayer().getIdentification().getNumber())
                        .build())
                .build())
        .build();

    try {
      client.create(paymentCreateRequest, requestOptions);
      System.out.println(client);
    } catch (MPApiException ex) {
      System.out.printf(
          "MercadoPago Error. Status: %s, Content: %s%n",
          ex.getApiResponse().getStatusCode(), ex.getApiResponse().getContent());
    } catch (MPException ex) {
      ex.printStackTrace();
    }
  }
}
*/