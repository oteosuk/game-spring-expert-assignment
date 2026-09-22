package com.gameexpert.player.controller;

import com.gameexpert.player.dto.CreatePlayerRequest;
import com.gameexpert.player.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;

    // TODO Lv 3: API 명세에 맞게 요청을 매핑하고, 검증한 요청으로 등록 서비스를 호출한 뒤 성공 응답을 반환합니다.
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody CreatePlayerRequest request) {
        playerService.createPlayer(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
