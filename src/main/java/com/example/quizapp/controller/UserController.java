package com.example.quizapp.controller;

import com.example.quizapp.dao.request.AvatarRequest;
import com.example.quizapp.dto.PageDTO;
import com.example.quizapp.dto.UserDTO;
import com.example.quizapp.mapper.PageMapper;
import com.example.quizapp.mapper.UserMapper;
import com.example.quizapp.model.Avatar;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.model.User;
import com.example.quizapp.service.AnswerService;
import com.example.quizapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name="User", description = "User API. Contains all the operations that can be performed on a user.")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final PageMapper pageMapper;
    private final AnswerService answerService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Fetch list of users")
    @GetMapping("")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return ResponseEntity.ok(userMapper.toListDTO(userService.getAllUsers()));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Fetch list of admins")
    @GetMapping("/admins")
    public ResponseEntity<UserDTO> getAllAdmins(){
        return ResponseEntity.ok(userMapper.toUserDTO(userService.getAllAdmins()));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Fetch Quizzes For User")
    @GetMapping("/getQuiz")
    public ResponseEntity<Map<UserDTO, List<Quiz>>> getQuizForUser(@RequestParam Long id, @RequestParam String title){
        return ResponseEntity.ok(userService.getQuizForUser(id, title));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Fetch users pageable")
    @GetMapping("/pageable")
    public ResponseEntity<PageDTO<UserDTO>> getPagesOfUsers(Pageable pageable){
        return ResponseEntity.ok(pageMapper.toPageDto(userService.getUsersPageable(pageable).map(userMapper::toUserDTO)));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Fetch sorted by name users")
    @GetMapping("/sorted")
    public ResponseEntity<List<UserDTO>> getSortedUsers(){
        return ResponseEntity.ok(userMapper.toListDTO(userService.getSortedByNameList()));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Count answers of the user")
    @GetMapping("/countAnswers")
    public ResponseEntity<String> countCorrectAnswers(@RequestParam String username){
        return ResponseEntity.ok(answerService.countAnswersOfUser(username));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @Operation(summary = "Get Users with Avatar")
    @GetMapping("/avatar")
    public ResponseEntity<List<UserDTO>> getUserWithAvatar(){
        return ResponseEntity.ok(userMapper.toListDTO(userService.getAllUsersWithAvatar()));
    }
}
