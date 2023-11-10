package com.example.springboot.dto;

import jakarta.validation.constraints.NotBlank;

public record PinRecordDto(@NotBlank String nome,@NotBlank String lat,@NotBlank String lng, String descricao) {
}
