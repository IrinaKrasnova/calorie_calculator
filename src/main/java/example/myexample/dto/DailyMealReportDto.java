package example.myexample.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class DailyMealReportDto {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private int totalCalories;
    private List<String> dishNames;
}
