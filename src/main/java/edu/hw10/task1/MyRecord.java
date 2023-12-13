package edu.hw10.task1;

import edu.hw10.task1.annotations.Max;
import edu.hw10.task1.annotations.Min;

public record MyRecord(@Min(min = 100) @Max(max = 101) int intValue, boolean booleanValue,
                       @Min(min = 100) @Max(max = 255) double doubleValue) {
}
