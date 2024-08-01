package com.kitcat.likelion.responseDTO;

import com.kitcat.likelion.requestDTO.LocationDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class RecordDetailDTO {

    @Schema(description = "산책 시간")
    private int walkTime;

    @Schema(description = "산책 거리")
    private int distance;

    @Schema(description = "견주 소모 칼로리")
    private int calorie;

    @Schema(description = "산책 끝난 시간")
    private LocalDateTime endTime;

    @Schema(description = "산책 경로 배열")
    private List<LocationDTO> locations;

    @Schema(description = "반려견 정보 배열")
    private List<PetRecordDTO> petRecords;
}
