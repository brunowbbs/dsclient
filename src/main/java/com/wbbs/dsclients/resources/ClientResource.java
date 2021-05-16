package com.wbbs.dsclients.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wbbs.dsclients.dto.ClientDTO;
import com.wbbs.dsclients.services.ClientService;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {

	@Autowired
	private ClientService service;
	
	@GetMapping
	public ResponseEntity<List<ClientDTO>> findAll(){
		List<ClientDTO> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
}
