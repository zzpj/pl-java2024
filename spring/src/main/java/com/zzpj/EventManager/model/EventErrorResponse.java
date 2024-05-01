package com.zzpj.EventManager.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventErrorResponse {

    LocalDateTime localDateTime;
    Integer status;
    List<String> errors;
}
