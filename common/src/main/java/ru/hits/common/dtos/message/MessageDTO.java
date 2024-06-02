package ru.hits.common.dtos.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    private String to;
    private String subject;
    private String message;
    private List<String> pool;
}
