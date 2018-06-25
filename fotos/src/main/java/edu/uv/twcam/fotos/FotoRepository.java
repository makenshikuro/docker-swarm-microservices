package edu.uv.twcam.fotos;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FotoRepository extends MongoRepository<Foto, String> {

	public List<Foto> findByUser(String user);
	
}
