package com.web.offood.dto.office;

import com.web.offood.entity.office.OfficeInfo;
import com.web.offood.entity.restaurant.Staff;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OfficeDetailResponse {
    public OfficeInfo officeInfo;
    public String email;
    public List<Staff> staff;
}
