package com.web.offood.dto.restaurant;

import com.web.offood.entity.restaurant.DiskInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuDetailResponse {
    public Integer id;
    public List<DiskInfo> diskInfos;
}
