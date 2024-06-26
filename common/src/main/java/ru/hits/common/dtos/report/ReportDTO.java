package ru.hits.common.dtos.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {
    List<EmployeeReportDTO> employeeReports;
    private Double sum;
}
