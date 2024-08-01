package com.kitcat.likelion.responseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class DayRecordDTO {
    @Schema(description = "해당 날의 사용자 산책 기록 배열")
    List<RecordDetailDTO> records = new ArrayList<>();

    public void addRecordDetail(RecordDetailDTO recordDetail) {
        records.add(recordDetail);
    }
}
