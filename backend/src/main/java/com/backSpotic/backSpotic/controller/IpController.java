package com.backSpotic.backSpotic.controller;
import com.backSpotic.backSpotic.model.Ip;
import com.backSpotic.backSpotic.repo.IpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class IpController {
    @Autowired
    private IpRepository ipRepository;

    @GetMapping("/registrar-visita")
    public String registrarVisita(HttpServletRequest request) {
        String ip = request.getRemoteAddr();

        Ip newIp = new Ip();
        newIp.setDireccionIp(ip);
        newIp.setFechaHora(LocalDateTime.now());

        ipRepository.save(newIp);

        return "Visita registrada desde la IP: " + ip;
    }

    @GetMapping("/consultar-visitas")
    public List<Ip> consultarVisitas() {
        return ipRepository.findAll();
    }
}
