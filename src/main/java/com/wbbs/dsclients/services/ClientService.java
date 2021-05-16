package com.wbbs.dsclients.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wbbs.dsclients.dto.ClientDTO;
import com.wbbs.dsclients.entities.Client;
import com.wbbs.dsclients.repositories.ClientRepository;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;
	
	@Transactional
	public List<ClientDTO> findAll(){
		List<Client> list = repository.findAll();
		List<ClientDTO> dtoList = new ArrayList<>();
		list.forEach(item -> dtoList.add(new ClientDTO(item)));
		return dtoList;
	}
}
