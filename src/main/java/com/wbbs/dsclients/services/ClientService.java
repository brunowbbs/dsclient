package com.wbbs.dsclients.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wbbs.dsclients.dto.ClientDTO;
import com.wbbs.dsclients.entities.Client;
import com.wbbs.dsclients.repositories.ClientRepository;
import com.wbbs.dsclients.services.exceptions.DatabaseException;
import com.wbbs.dsclients.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;

	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
		Page<Client> list = repository.findAll(pageRequest);
		return list.map(item -> new ClientDTO(item));
	}

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> obj = repository.findById(id);
		Client entity = obj.orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
		return new ClientDTO(entity);

	}

	@Transactional
	public ClientDTO insert(ClientDTO dto) {
		Client client = generateClient(dto);
		client = repository.save(client);
		return new ClientDTO(client);
	}

	@Transactional
	public ClientDTO update(Long id, ClientDTO dto) {
		try {

			Client client = repository.getOne(id);
			client.setName(dto.getName());
			client.setCpf(dto.getCpf());
			client.setBirthDate(dto.getBirthDate());
			client.setIncome(dto.getIncome());
			client.setChildren(dto.getChildren());
			client = repository.save(client);
			return new ClientDTO(client);
		} catch (Exception e) {
			throw new ResourceNotFoundException("Resource not found");
		}
	}
	
	public void delete(Long id) {

		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found");
		}catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}

	private Client generateClient(ClientDTO dto) {

		Client client = new Client();
		client.setName(dto.getName());
		client.setCpf(dto.getCpf());
		client.setIncome(dto.getIncome());
		client.setBirthDate(dto.getBirthDate());
		client.setChildren(dto.getChildren());

		return client;

	}

}
