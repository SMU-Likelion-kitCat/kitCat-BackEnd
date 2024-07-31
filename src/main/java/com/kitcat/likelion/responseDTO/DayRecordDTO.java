package com.kitcat.likelion.responseDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class DayRecordDTO {
    List<RecordDetailDTO> records = new ArrayList<>();

    public void addRecordDetail(RecordDetailDTO recordDetail) {
        records.add(recordDetail);
    }
}
