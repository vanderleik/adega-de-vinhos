package adega.de.vinhos.adegadevinhos.controller;

import adega.de.vinhos.adegadevinhos.domain.Vinho;
import adega.de.vinhos.adegadevinhos.service.VinhoService;
import adega.de.vinhos.adegadevinhos.util.DateUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class VinhoControllerTest {

    @InjectMocks
    private VinhoController vinhoController;

    @Mock
    private VinhoService vinhoService;
    @Mock
    private DateUtil defaultUtil;

    private PageImpl<Vinho> validVinhoPage;
    @BeforeEach
    void setUp(){
        validVinhoPage = new PageImpl<>(List.of(createValidVinho()));
        BDDMockito.when(vinhoService.listAll(ArgumentMatchers.any())).thenReturn(validVinhoPage);

    }

    @Test
    void testList(){
        Page<Vinho> vinhoPage = assertDoesNotThrow(() -> vinhoController.list(null).getBody());
        assertNotNull(vinhoPage);
        assertFalse(vinhoPage.toList().isEmpty());
        assertEquals(1, vinhoPage.getSize());
        assertEquals(validVinhoPage.toList().get(0).getTipo(), vinhoPage.toList().get(0).getTipo());
    }

    private Vinho createValidVinho() {
        return Vinho.builder().id(1L).tipo("Branco").build();
    }
}