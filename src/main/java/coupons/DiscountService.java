package coupons;

public class DiscountService {
    public int calculateDiscount(String coupon, String country) {
        // the percentage discount is the first two digits of the coupon
        return Integer.parseInt(coupon.substring(0, 2));
    }
}
