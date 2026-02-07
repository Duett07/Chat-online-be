package com.be.service;

import com.be.entity.Conversations;
import com.be.entity.Messages;
import com.be.entity.User;
import com.be.payload.ConversationResponse;
import com.be.payload.LastMessageResponse;
import com.be.payload.PartnerResponse;
import com.be.repository.IConversationsRepository;
import com.be.repository.IMessagesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ConversationService {
    private final IConversationsRepository conversationsRepository;
    private final IMessagesRepository messagesRepository;
    private final AuthService authService;

    public List<ConversationResponse> getMyConversations() {
        UUID me = authService.getCurrentUserId();

        List<Conversations> conversations = conversationsRepository.findAllByUser(me);

        return conversations.stream()
                .map(c -> mapToResponse(c, me))
                .filter(Objects::nonNull)
                .sorted((a, b) ->
                        b.lastMessage().createdAt()
                                .compareTo(a.lastMessage().createdAt())
                ).toList();
    }

    private ConversationResponse mapToResponse(Conversations conversations, UUID me) {
        User partner = conversations.getUser1().getId().equals(me) ? conversations.getUser2() : conversations.getUser1();

        Messages lastMessage = messagesRepository.findTopByConversationsOrderByCreatedAtDesc(conversations);

        if (lastMessage == null) {
            return null;
        }

        return new ConversationResponse(
                conversations.getId(),
                new PartnerResponse(
                        partner.getId(),
                        partner.getDisplayName(),
                        false,
                        partner.getImage()
                ),
                new LastMessageResponse(lastMessage.getContent(),
                        lastMessage.getCreatedAt(), lastMessage.getSender().getId())
        );
    }
}
