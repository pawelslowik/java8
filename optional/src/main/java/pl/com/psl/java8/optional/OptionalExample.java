package pl.com.psl.java8.optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by psl on 07.04.17.
 */
public class OptionalExample {

    private static final Logger LOGGER = LoggerFactory.getLogger(OptionalExample.class);
    private static final Pattern FORMATTED_PHONE_NUMBER_PATTERN = Pattern.compile("\\d{3}-\\d{3}-\\d{3}");
    private static final Pattern UNFORMATTED_PHONE_NUMBER_PATTERN = Pattern.compile("\\d{9}");
    private static final Map<String, BigDecimal> PRODUCT_PRICE_MAP = new HashMap<>();

    static {
        PRODUCT_PRICE_MAP.put("cucumber", new BigDecimal(10));
        PRODUCT_PRICE_MAP.put("tomato", new BigDecimal(12));
        PRODUCT_PRICE_MAP.put("pear", new BigDecimal(8));
        PRODUCT_PRICE_MAP.put("apple", new BigDecimal(9));
    }

    public static void main(String[] args) {
        processOrder("bob", Arrays.asList("cucumber", "apple"), "warsaw", "999-999-999");
        processOrder("tom", Arrays.asList("apple", "pear", "tomato"), null, "777777777");
        processOrder("tim", Arrays.asList("pear", "cucumber"), "   ", null);
        processOrder("pat", Arrays.asList("chair"), "berlin", "888-888-888");
    }

    private static void processOrder(String customerId, List<String> products, String deliveryAddress, String recipientPhoneNumber) {
        LOGGER.info("------Processing order for customer={} with products={} and deliveryAddress={} and recipientPhoneNumber={}------",
                customerId, products, deliveryAddress, recipientPhoneNumber);
        try {
            BigDecimal totalPrice = calculateTotalPrice(products);
            LOGGER.info("Calculated total price={}", totalPrice);
            Optional<BigDecimal> customerDiscount = getCustomerDiscount(customerId);
            if (customerDiscount.isPresent()) {
                BigDecimal originalTotalPrice = totalPrice;
                totalPrice = totalPrice.subtract(totalPrice.multiply(customerDiscount.get()));
                LOGGER.info("Applied customer discount on total price={}, new total price={}", originalTotalPrice, totalPrice);
            }

            Optional<BigDecimal> productsAmountDiscount = getProductsAmountDiscount(products);
            if (productsAmountDiscount.isPresent()) {
                BigDecimal originalTotalPrice = totalPrice;
                totalPrice = totalPrice.subtract(totalPrice.multiply(productsAmountDiscount.get()));
                LOGGER.info("Applied products amount discount on total price={}, new total price={}", originalTotalPrice, totalPrice);
            }

            recipientPhoneNumber = Optional.ofNullable(recipientPhoneNumber)
                    .map(p -> {
                        if (p != null && !p.trim().isEmpty()) {
                            if (FORMATTED_PHONE_NUMBER_PATTERN.matcher(p).matches()) {
                                LOGGER.info("Using recipient phone number={} which matches pattern {}", p, FORMATTED_PHONE_NUMBER_PATTERN);
                                return p;
                            }
                            if (UNFORMATTED_PHONE_NUMBER_PATTERN.matcher(p).matches()) {
                                String reformattedNumber = p.substring(0, 3) + "-" + p.substring(3, 6) + "-" + p.substring(6, 9);
                                LOGGER.info("Valid recipient phone number={} present, but not matching format, so reformatting to={}",
                                        p, reformattedNumber);
                                return reformattedNumber;
                            }
                        }
                        return null;
                    })
                    .orElseGet(() -> {
                        String customerPhoneNumber = CustomerDataService.getPhoneNumber(customerId);
                        LOGGER.info("Recipient phone number is missing, so setting it to customer phone number={}", customerPhoneNumber);
                        return customerPhoneNumber;
                    });

            deliveryAddress = Optional.ofNullable(deliveryAddress)
                    .filter(a -> a != null && !a.trim().isEmpty())
                    .orElseGet(() -> {
                        String customerAddress = CustomerDataService.getCustomerAddress(customerId);
                        LOGGER.info("Delivery address is missing, so setting it to customer address={}", customerAddress);
                        return customerAddress;
                    });

            LOGGER.info("------Finished processing order for customer={} with products={} and deliveryAddress={} and recipientPhoneNumber={}------",
                    customerId, products, deliveryAddress, recipientPhoneNumber);

        } catch (Exception e) {
            LOGGER.error("Processing order failed", e);
        }
    }

    private static BigDecimal calculateTotalPrice(List<String> products) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (String product : products) {
            Optional<BigDecimal> price = Optional.ofNullable(PRODUCT_PRICE_MAP.get(product));
            totalPrice = totalPrice.add(price.orElseThrow(
                    () -> new IllegalArgumentException("Unavailable product=" + product))
            );
        }
        return totalPrice;
    }

    private static Optional<BigDecimal> getCustomerDiscount(String customerId) {
        LOGGER.info("Getting discount for customer={}", customerId);
        BigDecimal discount = CustomerDiscountService.getDiscount(customerId);
        LOGGER.info("Got discount={}", discount);
        return Optional.ofNullable(discount);
    }

    private static Optional<BigDecimal> getProductsAmountDiscount(List<String> products) {
        LOGGER.info("Getting discount for products={}", products);
        Optional<BigDecimal> productsAmountDiscount = ProductDiscountService.getProductsAmountDiscount(products.size());
        LOGGER.info("Got discount={}", productsAmountDiscount);
        return productsAmountDiscount;
    }


    private static class CustomerDiscountService {

        public static BigDecimal getDiscount(String customerId) {
            if ("bob".equals(customerId)) {
                return BigDecimal.valueOf(0.1);
            }
            return null;
        }
    }

    private static class ProductDiscountService {

        public static Optional<BigDecimal> getProductsAmountDiscount(int amount) {
            if (amount > 2) {
                return Optional.of(BigDecimal.valueOf(0.15));
            }
            if (amount > 1) {
                return Optional.of(BigDecimal.valueOf(0.1));
            }
            return Optional.empty();
        }
    }

    private static class CustomerDataService {

        public static String getCustomerAddress(String customerId) {
            return "wroclaw";
        }

        public static String getPhoneNumber(String customerId) {
            switch (customerId) {
                case "bob":
                    return "111-111-111";
                case "tom":
                    return "222-222-222";
                case "tim":
                    return "333-333-333";
                case "pat":
                    return "444-444-444";
                default:
                    throw new IllegalArgumentException();
            }
        }
    }
}
