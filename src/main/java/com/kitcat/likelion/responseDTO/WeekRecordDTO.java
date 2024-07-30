package com.kitcat.likelion.responseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WeekRecordDTO {

    @Schema(description = "주차별 산책 기록(인덱스 0 부터 1주차 기록)")
    private List<RecordDTO> weekRecordDTOList = new ArrayList<>();

    public void addWeekRecord(RecordDTO record) {
        this.weekRecordDTOList.add(record);
    }
}
