package com.poluectov.rvlab1;

import com.poluectov.rvlab1.dto.issue.IssueRequestTo;
import com.poluectov.rvlab1.dto.marker.MarkerRequestTo;
import com.poluectov.rvlab1.dto.message.MessageRequestTo;
import com.poluectov.rvlab1.dto.user.UserRequestTo;
import com.poluectov.rvlab1.repository.IssueRepository;
import com.poluectov.rvlab1.repository.MarkerRepository;
import com.poluectov.rvlab1.repository.MessageRepository;
import com.poluectov.rvlab1.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Component
@AllArgsConstructor
public class InitBean {
    MarkerRepository markerRepository;
    UserRepository userRepository;
    MessageRepository messageRepository;
    IssueRepository issueRepository;

    @PostConstruct
    public void markerRepository() {
        markerRepository.save(MarkerRequestTo.builder()
                .name("mark1").build());
        markerRepository.save(MarkerRequestTo.builder()
                .name("mark2").build());

        userRepository.save(UserRequestTo.builder()
                .login("user1")
                .password("user1")
                .firstName("user1")
                .lastName("user1")
                .build());

        userRepository.save(UserRequestTo.builder()
                .login("user2")
                .password("user2")
                .firstName("user2")
                .lastName("user2")
                .build());

        issueRepository.save(IssueRequestTo.builder()
                .userId(BigInteger.ONE)
                .title("issue1")
                .content("issue1")
                .created(new Date())
                .modified(new Date())
                .markers(List.of(BigInteger.ONE, BigInteger.TWO))
                .build());

        messageRepository.save(MessageRequestTo.builder()
                .issueId(BigInteger.ONE)
                .content("message1")
                .build());

        messageRepository.save(MessageRequestTo.builder()
                .issueId(BigInteger.ONE)
                .content("message2")
                .build());
    }
}
