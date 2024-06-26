package ru.hits.common.dtos.contract;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.hits.common.dtos.client.ClientShortResponseDTO;
import ru.hits.common.dtos.doc.PriceListResponseDTO;
import ru.hits.common.dtos.user.UserDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractResponseDTO {
    private UUID id;
    private int number;
    private LocalDate date;
    private ClientShortResponseDTO client;
    private boolean volume;
    private double price;
    private LocalDate startDate;
    private LocalDate endDoingDate;
    private LocalDate endLifeDate;
    private String subject;
    private UserDTO employee;
    private boolean isEnd;
    private List<PriceListResponseDTO> priceList;
}
