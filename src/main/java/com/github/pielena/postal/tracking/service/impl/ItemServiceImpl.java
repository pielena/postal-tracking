package com.github.pielena.postal.tracking.service.impl;

import com.github.pielena.postal.tracking.dto.ItemDtoRq;
import com.github.pielena.postal.tracking.entity.Item;
import com.github.pielena.postal.tracking.entity.Operation;
import com.github.pielena.postal.tracking.entity.Person;
import com.github.pielena.postal.tracking.entity.PostOffice;
import com.github.pielena.postal.tracking.entity.State;
import com.github.pielena.postal.tracking.exception.S404ResourceNotFoundException;
import com.github.pielena.postal.tracking.repository.ItemRepository;
import com.github.pielena.postal.tracking.service.ItemService;
import com.github.pielena.postal.tracking.service.PersonService;
import com.github.pielena.postal.tracking.service.PostOfficeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final PostOfficeService postOfficeService;
    private final PersonService personService;

    @Override
    public Page<Item> getAll(int realPageIndex, int pageSize) {
        return itemRepository.findAll(PageRequest.of(realPageIndex - 1, pageSize));
    }

    @Override
    public Item getById(UUID id) {
        return itemRepository.findById(id).orElseThrow(() -> new S404ResourceNotFoundException(Item.class, id));
    }

    @Transactional
    @Override
    public Item create(ItemDtoRq itemDtoRq) {
        Person recipient = personService.findByNameAndAddressDescription(itemDtoRq.getRecipientName(), itemDtoRq.getRecipientAddress());
        Person sender = personService.findByNameAndIndex(itemDtoRq.getSenderName(), itemDtoRq.getSenderIndex());

        PostOffice destinationPostOffice = postOfficeService.findByIndex(itemDtoRq.getRecipientIndex());
        PostOffice senderPostOffice = postOfficeService.findByIndex(itemDtoRq.getSenderIndex());

        Item item = Item.builder()
                .type(itemDtoRq.getType())
                .sender(sender)
                .recipient(recipient)
                .destinationPostOffice(destinationPostOffice)
                .build();

        Operation operation = Operation.builder()
                .item(item)
                .postOffice(senderPostOffice)
                .state(State.REGISTERED)
                .date(LocalDateTime.now())
                .isDestination(senderPostOffice.equals(destinationPostOffice))
                .build();

        List<Operation> operationHistory = new ArrayList<>();
        operationHistory.add(operation);
        item.setOperationHistory(operationHistory);

        return save(item);
    }

    private Item save(Item item) {
        return itemRepository.save(item);
    }
}
