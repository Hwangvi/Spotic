package com.backSpotic.backSpotic;

import com.backSpotic.backSpotic.controller.IpController;
import com.backSpotic.backSpotic.model.Ip;
import com.backSpotic.backSpotic.repo.IpRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IpController.class)
@AutoConfigureMockMvc(addFilters = false)
class IpControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IpRepository ipRepository;

    @Test
    @DisplayName("Registrar visita guarda la IP y devuelve mensaje")
    void registrarVisita_Success() throws Exception {
        when(ipRepository.save(any(Ip.class))).thenAnswer(i -> i.getArguments()[0]);

        mockMvc.perform(get("/registrar-visita")
                        .with(request -> {
                            request.setRemoteAddr("192.168.1.50");
                            return request;
                        }))
                .andExpect(status().isOk())
                .andExpect(content().string("Visita registrada desde la IP: 192.168.1.50"));

        verify(ipRepository).save(any(Ip.class));
    }

    @Test
    @DisplayName("Consultar visitas devuelve lista de IPs")
    void consultarVisitas_Success() throws Exception {
        Ip ip1 = new Ip();
        ip1.setId(1L);
        ip1.setDireccionIp("127.0.0.1");
        ip1.setFechaHora(LocalDateTime.now());

        Ip ip2 = new Ip();
        ip2.setId(2L);
        ip2.setDireccionIp("8.8.8.8");

        when(ipRepository.findAll()).thenReturn(Arrays.asList(ip1, ip2));

        mockMvc.perform(get("/consultar-visitas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].direccionIp", is("127.0.0.1")))
                .andExpect(jsonPath("$[1].direccionIp", is("8.8.8.8")));
    }

    @Test
    @DisplayName("Metodo para cuando no hay registros")
    void consultarVisitas_Empty() throws Exception {
        when(ipRepository.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/consultar-visitas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
