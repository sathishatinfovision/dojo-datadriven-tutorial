package coupons;

import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SerenityJUnit5Extension.class)
public class CouponDiscountTest {

    DiscountService discountService = new DiscountService();

    @ParameterizedTest
    @CsvFileSource(resources = "/testdata/coupons.csv", numLinesToSkip = 1)
    void shouldCalculateDiscount(String coupon, String country, int expectedDiscount) {
        int actualDiscount = discountService.calculateDiscount(coupon, country);

        assertThat(actualDiscount).isEqualTo(expectedDiscount);
    }

    @ParameterizedTest
    @MethodSource("ukExcelData")
    void shouldCalculateUKDiscount(String coupon, String country, int expectedDiscount) {
        int actualDiscount = discountService.calculateDiscount(coupon, country);

        assertThat(actualDiscount).isEqualTo(expectedDiscount);
    }

    static Stream<Arguments> ukExcelData() throws IOException {
        Stream.Builder<Arguments> arguments = Stream.builder();

        InputStream inputStream = CouponDiscountTest.class.getResourceAsStream("/testdata/discount_coupons.xls");
        Workbook workbook = new HSSFWorkbook(inputStream);
        Sheet ukSheet = workbook.getSheet("UK");

        boolean firstRow = true;
        for(Row row : ukSheet) {
            if (firstRow) {
                firstRow = false;
            } else {
                Cell couponCell = row.getCell(0);
                Cell countryCell = row.getCell(1);
                Cell discountCell = row.getCell(2);
                arguments.add(Arguments.of(couponCell.getStringCellValue(),
                        countryCell.getStringCellValue(),
                        (int) discountCell.getNumericCellValue()));
            }
        }
        return arguments.build();
    }
}
