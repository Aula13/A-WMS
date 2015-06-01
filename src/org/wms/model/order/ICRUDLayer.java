package org.wms.model.order;

import java.util.List;
import java.util.Optional;

public interface ICRUDLayer<ObjectToPersist extends Object> {

	/**
	 * 
	 * Create a material on the database
	 * 
	 * @param material material to create
	 * @return true=material created succefully
	 */
	public boolean create(ObjectToPersist object);

	/**
	 * 
	 * Update a material on the database
	 * 
	 * @param material material to update
	 * @return true=material updated succefully
	 */
	public boolean update(ObjectToPersist object);

	/**
	 * 
	 * Delete a material on the database
	 * 
	 * @param material material to delete
	 * @return true=material deleted succefully
	 */
	public boolean delete(ObjectToPersist object);

	/**
	 * 
	 * Get a specific material on the database
	 * 
	 * @param materialId materialId to fetch
	 * @return optionally the material searched
	 */
	public Optional<ObjectToPersist> get(Long objectid);


	/**
	 * Return all the materials stored on the database
	 * 
	 * @return optionally the list of materials
	 */
	public Optional<List<ObjectToPersist>> selectAll();
	
}
