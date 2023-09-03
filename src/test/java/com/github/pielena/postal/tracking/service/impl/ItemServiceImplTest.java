package com.github.pielena.postal.tracking.service.impl;

import com.github.pielena.postal.tracking.dto.ItemDtoRq;
import com.github.pielena.postal.tracking.exception.S404NotFoundException;
import com.github.pielena.postal.tracking.persistence.entity.Item;
import com.github.pielena.postal.tracking.enums.ItemType;
import com.github.pielena.postal.tracking.persistence.entity.Person;
import com.github.pielena.postal.tracking.persistence.entity.PostOffice;
import com.github.pielena.postal.tracking.persistence.repository.ItemRepository;
import com.github.pielena.postal.tracking.service.PersonService;
import com.github.pielena.postal.tracking.service.PostOfficeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @InjectMocks
    private ItemServiceImpl itemService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private PostOfficeService postOfficeService;

    @Mock
    private PersonService personService;

    @Test
    public void whenGetAll_thenItemPageReturned() {
        final Item item = createTestItem();
        final Page<Item> page = new PageImpl<>(List.of(item, item, item));

        when(itemRepository.findAll(PageRequest.of(0, 4))).thenReturn(page);

        Page<Item> resultPage = itemService.getAll(1, 4);

        assertEquals(3, resultPage.getContent().size());
        assertEquals(page, resultPage);
        verify(itemRepository, times(1)).findAll(PageRequest.of(0, 4));
    }

    @Test
    public void givenItemDtoRq_whenRecipientPersonDoesNotExist_thenThrowException() {
        final ItemDtoRq itemDtoRq = createTestItemDtoRq();

        when(personService.findByNameAndAddressDescription(anyString(), anyString())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(S404NotFoundException.class,
                () -> itemService.create(itemDtoRq));
        assertEquals("Person with name: Anna and address: Spring street, 12 not found", thrown.getMessage());
    }

    @Test
    public void givenItemDtoRq_whenSenderPersonDoesNotExist_thenThrowException() {
        final ItemDtoRq itemDtoRq = createTestItemDtoRq();

        when(personService.findByNameAndAddressDescription(anyString(), anyString())).thenReturn(Optional.of(new Person()));
        when(personService.findByNameAndIndex(anyString(), anyInt())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(S404NotFoundException.class,
                () -> itemService.create(itemDtoRq));
        assertEquals("Person with name: Ivan and index: 123456 not found", thrown.getMessage());
    }

    @Test
    public void givenItemId_whenFindById_thenOptionalItemReturned() {
        final UUID id = UUID.randomUUID();
        final Item item = createTestItem();

        when(itemRepository.findById(id)).thenReturn(Optional.of(item));

        Optional<Item> resultOptionalItem = itemService.getById(id);

        assertEquals(Optional.of(item), resultOptionalItem);
        verify(itemRepository, times(1)).findById(id);
    }

    @Test
    public void givenItemDtoRq_whenCreate_thenItemReturned() {
        final ItemDtoRq itemDtoRq = createTestItemDtoRq();
        final Item item = createTestItem();

        when(personService.findByNameAndAddressDescription(anyString(), anyString())).thenReturn(Optional.of(new Person()));
        when(personService.findByNameAndIndex(anyString(), anyInt())).thenReturn(Optional.of(new Person()));
        when(postOfficeService.findByIndex(anyInt())).thenReturn(Optional.of(new PostOffice()));
        when(itemRepository.save(any())).thenReturn(item);

        Item result = itemService.create(itemDtoRq);

        assertNotNull(result.getId());
        verify(itemRepository, times(1)).save(any());
    }

    private Item createTestItem() {
        return Item.builder()
                .id(UUID.fromString("7af49324-d3a3-4550-9448-38f00103565b"))
                .type(ItemType.LETTER)
                .sender(new Person())
                .recipient(new Person())
                .destinationPostOffice(new PostOffice())
                .build();
    }

    private ItemDtoRq createTestItemDtoRq() {
        return ItemDtoRq.builder()
                .type(ItemType.LETTER)
                .senderIndex(123456)
                .senderName("Ivan")
                .recipientIndex(345678)
                .recipientAddress("Spring street, 12")
                .recipientName("Anna")
                .build();
    }
}