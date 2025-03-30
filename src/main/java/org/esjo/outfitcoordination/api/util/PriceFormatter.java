package org.esjo.outfitcoordination.api.util;

import lombok.experimental.UtilityClass;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

@UtilityClass
public class PriceFormatter {

    @Named("formatPrice")
    public static String formatPrice(BigDecimal price) {
        return NumberFormat.getInstance(Locale.KOREA).format(price);
    }
}
