package com.github.pielena.postal.tracking.service.impl;

import com.github.pielena.postal.tracking.dto.ItemDtoRq;
import com.github.pielena.postal.tracking.persistence.entity.Item;
import com.github.pielena.postal.tracking.persistence.entity.Operation;
import com.github.pielena.postal.tracking.persistence.entity.Person;
import com.github.pielena.postal.tracking.persistence.entity.PostOffice;
import com.github.pielena.postal.tracking.enums.State;
import com.github.pielena.postal.tracking.exception.S404NotFoundException;
import com.github.pielena.postal.tracking.persistence.repository.ItemRepository;
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
import java.util.Optional;
import java.util.UUID;

import static com.github.pielena.postal.tracking.exception.ExceptionMessageConstants.NOT_FOUND_BY_INDEX_MESSAGE;
import static com.github.pielena.postal.tracking.exception.ExceptionMessageConstants.NOT_FOUND_BY_NAME_AND_ADDRESS_MESSAGE;
import static com.github.pielena.postal.tracking.exception.ExceptionMessageConstants.NOT_FOUND_BY_NAME_AND_INDEX_MESSAGE;

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
    public Optional<Item> getById(UUID id) {
        return itemRepository.findById(id);
    }

    @Transactional
    @Override
    public Item create(ItemDtoRq itemDtoRq) {
        Person recipient = personService.findByNameAndAddressDescription(itemDtoRq.getRecipientName(), itemDtoRq.getRecipientAddress())
                .orElseThrow(() -> new S404NotFoundException(String.format(NOT_FOUND_BY_NAME_AND_ADDRESS_MESSAGE,
                        Person.class.getSimpleName(), itemDtoRq.getRecipientName(), itemDtoRq.getRecipientAddress())));

        Person sender = personService.findByNameAndIndex(itemDtoRq.getSenderName(), itemDtoRq.getSenderIndex())
                .orElseThrow(() -> new S404NotFoundException(String.format(NOT_FOUND_BY_NAME_AND_INDEX_MESSAGE,
                        Person.class.getSimpleName(), itemDtoRq.getSenderName(), itemDtoRq.getSenderIndex())));

        PostOffice destinationPostOffice = postOfficeService.findByIndex(itemDtoRq.getRecipientIndex())
                .orElseThrow(() -> new S404NotFoundException(String.format(NOT_FOUND_BY_INDEX_MESSAGE,
                        PostOffice.class.getSimpleName(), itemDtoRq.getRecipientIndex())));

        PostOffice senderPostOffice = postOfficeService.findByIndex(itemDtoRq.getSenderIndex())
                .orElseThrow(() -> new S404NotFoundException(String.format(NOT_FOUND_BY_INDEX_MESSAGE,
                        PostOffice.class.getSimpleName(), itemDtoRq.getSenderIndex())));

        Item item = buildItem(itemDtoRq, sender, recipient, destinationPostOffice);
        Operation operation = buildInitOperation(item, senderPostOffice, destinationPostOffice);

        List<Operation> operationHistory = new ArrayList<>();
        operationHistory.add(operation);
        item.setOperationHistory(operationHistory);

        return save(item);
    }

    private Item save(Item item) {
        return itemRepository.save(item);
    }

    private Item buildItem(ItemDtoRq itemDtoRq, Person sender, Person recipient, PostOffice destinationPostOffice) {
        return Item.builder()
                .type(itemDtoRq.getType())
                .sender(sender)
                .recipient(recipient)
                .destinationPostOffice(destinationPostOffice)
                .build();
    }

    private Operation buildInitOperation(Item item, PostOffice senderPostOffice, PostOffice destinationPostOffice) {
        return Operation.builder()
                .item(item)
                .postOffice(senderPostOffice)
                .state(State.REGISTERED)
                .date(LocalDateTime.now())
                .isDestination(senderPostOffice.equals(destinationPostOffice))
                .build();
    }
}
